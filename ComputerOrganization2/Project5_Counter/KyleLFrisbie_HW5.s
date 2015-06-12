	; **********************************
	;  File: KyleLFrisbie_HW5.s
	;  Programmer: Kyle L Frisbie
	;  Description: Generates a series of random integers
	;	using randomnumber.s and puts them into a
	;	string of words. Counts the number of 1's and
	; 	0's in two seperate subroutines. Prints the
	;	count of 1's and 0's.
	;
	;  Project: Homework5_KyleLFrisbie
	;  Date: 07 October 2014
	;************************************
	
	AREA KyleLFrisbie_HW5, CODE, READONLY
SWI_WRITE	EQU	&0     ;output character in r0 
SWI_Exit	EQU	&11    ;finish program

		ENTRY
		LDR	r0, NUMBER	; r0 gets address of NUM
		MOV	r5, #0		; r5 becomes counter of ones, set to 0
		BL	LOOPONE		; branch and link to LOOPONE (count number of ones in NUMBER).
		MOV	r7, r5		; move value of r5 to r7
		BL	PRINTHEX	; print hexadecimal count of 1's
		ADR	r1, NUMBER	; r1 gets address of NUMBER
		MOV	r6, #0		; r6 becomes counter of zeros, set to 0
		BL	LOOPZERO	; branch and link to LOOPZERO (count number of zeros in NUMBER).
		MOV	r7, r6		; move value of r6 to r7
		BL	PRINTHEX	; print hexadecimal count of 0's
		SWI 	SWI_Exit	; finish
		ALIGN

LOOPONE		CMP	r0, #0		; checks to see if r0 has been completely assessed (all zeros).
		SUBNE	r1, r0, #1	; subtract 1 from r0 and store number in r1
		ADDNE	r5, r5, #1	; add 1 to "ones counter" if a one is present
		ANDNE	r0, r0, r1	; clear bits changed by subtraction operation in r0
		BNE	LOOPONE		; branch back to LOOPONE
		MOV 	pc, lr

LOOPZERO	LDR	r4, ONEMASK	; get mask of ones in r4
		CMP	r0, r4		; checks to see if r0 has been completely assessed (all ones).
		ADDNE	r1, r0, #1	; add 1 to r0 and store number in r1
		ADDNE	r6, r6, #1	; add 1 to "zeros counter" if a zero is present
		ORRNE	r0, r0, r1	; clear bits changed by addition operation in r0
		BNE	LOOPZERO	; branch back to LOOPZERO
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
 		END


NUMBER		DCD	&4492C800
ONEMASK		DCD	&FFFFFFFF
		ALIGN
		END