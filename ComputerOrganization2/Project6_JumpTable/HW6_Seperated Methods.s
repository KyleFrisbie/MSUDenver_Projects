	; ******************************************************
	; File: KyleLFrisbie_HW6.s    
	; Programmer: Kyle L Frisbie
	; Description: Uses jump table and stacks to call a
	; sequence of subroutines that implement cryptography to
	; encrypt, decrypt, and compare a message to the
	; original message to check for correctness of the
	; encryption algorithm.
	; 
	; Project: Homework5_KyleLFrisbie
	; Date: 15 October 2014
	; ******************************************************

	AREA Homework6, CODE, READONLY
SWI_WRITE	EQU	&0			; output character in r0
SWI_EXIT	EQU	&11			; finish program

		ENTRY
MAIN		; call ENCRYPT
		MOV	r0, #0			; load r2 with 0, points to ENCRYPT subroutine
		BL	JUMPTABLE		; go to ENCRYPT via JUMPTABLE

		; call DECRYPT
		MOV	r0, #1			; load r0 with 1, points to DECRYPT subroutine
		BL	JUMPTABLE		; go to DECRYPT via JUMPTABLE

		; call COMPARE
		MOV	r0, #2			; load r0 with 2, points to PRINT subroutine
		BL	JUMPTABLE		; print MESSAGE
		SWI	SWI_EXIT		; end program

MASK		DCD	&000000FF
KEY		DCD	&FFFFFFFF
MESSAGE		DCB	"This is difficult.",&0a,&0d,&0000000
HOLDER		% 40
PSTRING		% 40
ESTRING		% 40
DSTRING		% 40
		ALIGN

JUMPTABLE	ADR	r1, SUBTABLE		; r1 := address of SUBTABLE
		LDR	pc, [r1, r0, LSL#2]	; jump to subroutine n

SUBTABLE	DCD	ENCRYPT			; entry point to ENCRYPT
		DCD	DECRYPT			; entry point to DECRYPT
		DCD	COMPARE			; entry point to COMPARE
		DCD	PRINT			; entry point to PRINT
		DCD	PERMU			; entry point to PERMU
		DCD	XOR			; entry point to XOR

ENCRYPT		; store registers in stack
		STMFD	sp!, {r0-r12, lr}	; store user registers

		; permutate the message
		ADR	r3, PSTRING		; get address of PSTRING and store in r1
		ADR	r2, MESSAGE		; load address of MESSAGE in r2
		MOV	r0, #4			; load r0 with 4, points to PERMU subroutine
		BL	JUMPTABLE		; go to PERMU via JUMPTABLE

		; XOR the message
		ADR	r3, HOLDER		; load r2 with address of ESTRING, for XOR subroutine
		ADR	r2, PSTRING		; load r2 with address of PSTRING, for XOR subroutine
		MOV	r0, #5			; load r0 with 5, points to XOR subroutine
		BL	JUMPTABLE		; go to XOR via JUMPTABLE
		
		; restore registers from stack
		LDMFD	sp!, {r0-r12, lr}	; load the registers
		MOV	pc, lr
		ALIGN

DECRYPT		; store registers in stack
		STMFD	sp!, {r0-r12, lr}	; store user registers

		; XOR the message
		ADR	r3, PSTRING		; load r2 with address of ESTRING, for XOR subroutine
		ADR	r2, HOLDER		; load r2 with address of PSTRING, for XOR subroutine
		MOV	r0, #5			; load r0 with 5, points to XOR subroutine
		BL	JUMPTABLE		; go to XOR via JUMPTABLE

		; permutate the message
		ADR	r3, DSTRING		; load r2 with address of DSTRING
		ADR	r2, PSTRING		; load r2 with address of ESTRING
		MOV	r0, #4			; load r0 with 4, points to PERMU subroutine
		BL	JUMPTABLE		; go to PERMU via JUMPTABLE

		; restore registers from stack
		LDMFD	sp!, {r0-r12, lr}	; load the registers
		MOV	pc, lr
		ALIGN

COMPARE		; store registers in stack
		STMFD	sp!, {r0-r12, lr}	; store user registers

		; print message
		ADR	r2, MESSAGE		; load address of MESSAGE in r2
		MOV	r0, #3			; load r0 with 3, points to PRINT subroutine
		BL	JUMPTABLE		; print MESSAGE

		; print encrypted string
		ADR	r2, ESTRING		; load address of ESTRING in r2
		MOV	r0, #3			; load r0 with 3, points to PRINT subroutin 
		BL	JUMPTABLE		; print ESTRING

		; print encrypted string
		ADR	r2, DSTRING		; load address of DSTRING in r2
		MOV	r0, #3			; load r0 with 3, points to PRINT subroutin 
		BL	JUMPTABLE		; print ESTRING

		; restore registers from stack
		LDMFD	sp!, {r0-r12, lr}	; load the registers
		MOV	pc, lr
		ALIGN

PRINT		LDRB	r0,[r2], #1		; get a character
		CMP 	r0, #0			; end mark NUL?
		SWINE 	SWI_WRITE		; if not, print
		BNE	PRINT			; if not end, loop back
		MOV	pc, lr			; return
		ALIGN

PERMU		; permutate the message
		ADR	r1, MASK		; store the address of MASK in r1
		LDR	r1, [r1]		; load the mask into r1
PLOOP		LDR	r0, [r2], #4		; get a word of characters
		CMP	r0, #0			; check for end of phrase
		MOVEQ	pc, lr			; if end of phrase, exit method
		AND	r8, r0, r1		; clear out all bytes except last byte, store in r8
		MOV	r8, r8, LSL#24		; shift last byte to position of first byte
		MOV	r7, r0, LSR#8		; shift third byte to the end of the word
		AND	r7, r7, r1		; clear out bytes except last byte, store in r7
		MOV	r7, r7, LSL#16		; shift byte to position of byte 2
		MOV	r6, r0, LSR#16		; shift second byte to the end of the word
		AND	r6, r6, r1		; clear out bytes except last byte, store in r6
		MOV	r6, r6, LSL#8		; shift byte to byte position 3
		MOV	r5, r0, LSR#24		; shift first byte to the end of the word
		AND	r5, r5, r1		; clear out bytes except last byte, store in r5
		ORR	r4, r5, r6		; pack into new word
		ORR	r4, r4, r7		; pack into new word
		ORR	r4, r4, r8		; pack into new word
		STR	r4, [r3], #4		; increment memory address by 4
		B	PLOOP
		ALIGN

XOR		; exclusive or the message
		ADR	r0, KEY			; store the address of KEY in r0
		LDR	r0, [r0]		; load the KEY into r0
XORLOOP		LDR	r1, [r2], #4		; get a word of characters from string
		CMP	r1, #0			; check for end of phrase
		MOVEQ	pc, lr			; if end of phrase, exit method
		EOR	r4, r1, r0		; encrypt word in r3 using KEY
		STR	r4, [r3], #4		; store encrypted word and increment memory address by 4
		B	XORLOOP			; branch back to XOR
		ALIGN
		END