	; **********************************
	;  File: KyleLFrisbie_HW4.s
	;  Programmer: Kyle L Frisbie
	;  Description: Converts a number between IEEE single precision
	;  floating point format and TNS single precision floating point
	;  format. Then compares this TNS converted number to the actual
	;  numbers hard coded into this program to insure their
	;  correctness.
	;
	;  Note: This program goes beyond specifications by printing
	;  	 a message "Success!" To the console if each comparison
	; 	 of converted numbers is correct. This program also
	;	 prints an error message to the user if the TNS number
	;	 you are attempting to convert is too large to be stored
	;	 in IEEE single floating point format due to TNS excess
	;	 256 compared to IEEE excess 127.
	;
	;  Project: Homework3_KyleLFrisbie               
	;  Date: 21 September 2014
	;************************************
	
	AREA Homework3, CODE, READONLY
SWI_WRITE	EQU	&0     		; output character in r0 
SWI_Exit	EQU	&11    		; finish program

		ENTRY
		ADR	r1, IEEE	; r1 gets address of STRING1
		ADR	r2, TNS		; r2 gets address of STRING1
		BL	TNSCONV		; branch to TNSCONV method (IEEE --> TNS)
		LDR	r7, [r2]	; r7 gets TNS number
		BL	PRINTHEX	; print TNS number
		LDR	r8, RETURN	; r8 gets carriage return line feed
		BL	PRINT		; carriage return line feed
		MOV	r7, r6		; r7 gets converted TNS number
		BL	PRINTHEX	; print converted TNS number
		LDR	r8, RETURN	; r8 gets carriage return line feed
		BL	PRINT
		LDR	r0, [r2]	; r0 gets address of TNS number
		CMP	r6, r0		; compare converted TNS to actual TNS
		ADR	r8, SUCCESS	; r8 gets address of IEEE number
		BLEQ	PRINT		; print message if successful
		BL	IEEECONV	; branch to IEEECONV method (TNS -->IEEE)
		LDR	r7, [r1]	; r7 gets IEEE number
		BL	PRINTHEX	; print IEEE number
		LDR	r8, RETURN	; r8 gets carriage return line feed
		BL	PRINT
		MOV	r7, r6		; r7 gets converted IEEE number
		BL	PRINTHEX	; print converted IEEE number
		LDR	r8, RETURN	; r8 gets carriage return line feed
		BL	PRINT
		LDR	r0, [r1]	; r0 gets IEEE number
		CMP	r6, r0		; compare converted IEEE to actual IEEE
		ADR	r8, SUCCESS
		BLEQ	PRINT		; print message if successful
		SWI	SWI_Exit	; finish program
		ALIGN

ERROR		LDR	r8, ERRORMSG
		LDRB	r0,[r8], #1	; get a character
		CMP 	r0, #0		; end mark NUL?
		SWINE 	SWI_WRITE	; if not, print
		BNE	PRINT
		MOV	pc, r14		; return

PRINT		LDRB	r0,[r8], #1	; get a character
		CMP 	r0, #0		; end mark NUL?
		SWINE 	SWI_WRITE	; if not, print
		BNE	PRINT
		MOV	pc, r14		; return

TNSCONV		LDR	r3, [r1]	; r3 := r1, will hold new TNS sign
		ADR	r7, IMASKS	; load r7 with IEEE sign bit mask
		LDR	r7, [r7]
		AND	r3, r3, r7	; unpack IEEE sign bit
		LDR	r4, [r1]	; r4 := r1, will hold new TNS exp
		ADR	r7, IMASKE	; load r7 with IEEE exponent mask
		LDR	r7, [r7]
		AND	r4, r4,	r7	; unpack IEEE exponent
		LDR	r5, [r1]	; r5 := r1, will hold new TNS sig
		ADR	r7, IMASKSI	; load r7 with IEEE significand mask
		LDR	r7, [r7]
		AND	r5, r5,	r7	; unpack IEEE significand
		MOV	r4, r4, LSR#23	; shift exponent to TNS position
		ADD	r4, r4, #&100	; add excess 256 (TNS)
		SUB	r4, r4, #&7F	; remove excess 127 (IEEE)
		ADR	r7, IMASKX	; load r7 with IEEE 23rd bit clear
		LDR	r7, [r7]
		AND	r5, r5, r7	; clear 23rd bit of sig (for TNS format)
		MOV	r5, r5, LSL#8	; move significand to TNS position
		ORR	r6, r3, r4	; pack TNS sign and exponent in r6
		ORR	r6, r6, r5	; pack TNS significand in r6, r6 now TNS number
		MOV	pc, lr

IEEECONV	LDR	r3, [r2]	; r3 := r2, will hold new IEEE sign
		ADR	r7, TMASKS	; load r7 with TMS sign bit mask
		LDR	r7, [r7]
		AND	r3, r3, r7	; unpack TNS sign bit
		LDR	r4, [r2]	; r4 := r2, will hold new IEEE exp
		ADR	r7, TMASKE	; load r7 with TMS exponent bit mask
		LDR	r7, [r7]
		AND	r4, r4, r7	; unpack TNS exponent
		LDR	r5, [r2]	; r5 := r2, will hold new IEEE sig
		ADR	r7, TMASKSI	; load r7 with TNS significand bit mask
		LDR	r7, [r7]
		AND	r5, r5, r7	; unpack TNS significand
		MOV	r5, r5, LSR#8	; move significand to IEEE position
		SUB	r4, r4, #&100	; remove excess 256 (TNS)
		ADD	r4, r4, #&7F	; add excess 127 (IEEE)
		CMP	r4, #0		; check for negative exponent (invalid)
		BLLT	ERROR		; branch to ERROR if exponent < 0
		MOV	r4, r4, LSL#23	; move exponent to IEEE exponent position
		ORR	r6, r3, r4	; pack IEEE sign and exponent in r6
		ORR	r6, r6, r5	; pack IEEE significand in r6, r6 now IEEE number
		MOV	pc, lr

PRINTHEX	MOV	r8, #8		; count of nibbles = 8
LOOPHEX		MOV	r0, r7, LSR #28	; get top nibble
		CMP 	r0, #9		; hexanumber 0-9 or A-F
		ADDGT 	r0, r0, #"A"-10	; ASCII alphabetic
		ADDLE 	r0, r0, #"0"	; ASCII numeric
		SWI 	SWI_WRITE	; print character
		MOV	r7, r7, LSL #4	; shift left one nibble
		SUBS	r8, r8, #1	; decrement nibble count
		BNE	LOOPHEX		; if more nibbles,loop back
		MOV 	pc, r14		; return
		

IEEE		DCD	&4492C800	; 01000100100100101100100000000000 ; (1174.25 base 10)
IMASKS		DCD	&80000000	; IEEE sign bit mask
IMASKE		DCD	&7F800000	; IEEE exponent mask
IMASKSI		DCD	&007FFFFF	; IEEE significand mask
IMASKX		DCD	&007FFFFE
TNS		DCD	&12C8010A	; 00010010110010000000000100001010 ; (1174.25 base 10)
TMASKS		DCD	&80000000	; TNS sign bit mask
TMASKE		DCD	&000001FF	; TNS exponent mask
TMASKSI		DCD	&7FFFFE00	; TNS significand mask
SUCCESS		DCB	"Success!",&0a,&0d,0
RETURN		DCB	" -next-  ",&0a,&0d,0
ERRORMSG	DCB	"TNS number too large for IEEE single floating point conversion.",&0a,&0d,0
		ALIGN

		END