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
		MOV	r0, #3			; set r0 to 3, to call PERMU subroutine
		ADR	r2, MESSAGE		; store the address of the message in r2
		BL	JUMPTABLE		; branch and link to jumpTABLE, run PERMU
		MOV	r0, #0			; set r0 to 0, to call ENCRYPT subroutine
		BL	JUMPTABLE		; branch and link to jumpTABLE, run ENCRYPT
		MOV	r0, #1			; set r0 to 1, to call DECRYPT subroutine
		BL	JUMPTABLE		; branch and link to jumpTABLE, run DECRYPT
		ADR	r2, DECRYPT
		MOV	r0, #3			; set r0 to 3, to call PERMU subroutine
		BL	JUMPTABLE		; branch and link to JUMPTABLE, run PERMU
		MOV	r0, #4			; set r0 to 4, to call COMPARE subroutine
		BL	JUMPTABLE		; branch and link to JUMPTABLE, run COMPARE
		SUB	sp, sp, #4		; reserve a word for CPSR
		STMFD	sp!, {r0-r12, lr}	; store user registers
		MRS	r2, cpsr		; move CPSR to r2
		STR	r2, [sp, #14*4]		; store CPSR in the stack above the registers
		
		SWI	SWI_EXIT
		
JUMPTABLE	ADR	r1, subTABLE		; r1 := address of address table of subroutines
		LDR	pc, [r1, r0, LSL#2]	; jump to subroutine n

subTABLE	DCD	ENCRYPT			; entry point to ENCRYPT
		DCD 	DECRYPT			; entry point to DECRYPT
		DCD	PRINT			; entry point to PRINT
		DCD	PERMU			; entry point to PERMU
		DCD	COMPARE			; entry point to COMAPRE


ENCRYPT		ADR	r2, PSTRING		; store the address of the PSTRING in r2
		ADR	r1, ESTRING		; get address of ESTRING and store in r1
ELOOP		LDR	r3, [r2], #4		; get a word of characters from PSTRING
		CMP	r3, #0			; check for end of phrase
		MOVEQ	pc, lr			; if end of phrase, exit method
		ADR	r0, KEY			; store the address of KEY in r0
		LDR	r0, [r0]		; load the KEY into r0
		EOR	r4, r3, r0		; encrypt word in r4 using KEY
		STR	r4, [r1], #4		; increment memory address by 4
		B	ELOOP			; branch back to ENCRYPT

DECRYPT		ADR	r2, ESTRING		; store the address of the ESTRING in r2
		ADR	r1, DSTRING		; get address of DSTRING and store in r1
DLOOP		LDR	r3, [r2], #4		; get a word of characters
		CMP	r3, #0			; check for end of phrase
		MOVEQ	pc, lr			; if end of phrase, exit method
		ADR	r0, KEY			; store the address of KEY in r0
		LDR	r0, [r0]		; load the KEY into r0
		EOR	r4, r3, r0		; encrypt word in r4 using KEY
		STR	r4, [r1], #4		; increment memory address by 4
		B	DLOOP			; branch back to DECRYPT

PRINT		LDRB	r0,[r4], #1		; get a character
		CMP 	r0, #0			; end mark NUL?
		SWINE 	SWI_WRITE		; if not, print
		BNE	PRINT			; if not end, loop back
		MOV	pc, lr			; return

PERMU		ADR	r0, PSTRING		; get address of PSTRING and store in r1
PLOOP		LDR	r3, [r2], #4		; get a word of characters
		CMP	r3, #0			; check for end of phrase
		MOVEQ	pc, lr			; if end of phrase, exit method
		ADR	r1, MASK		; store the address of MASK in r1
		LDR	r1, [r1]		; load the mask into r1
		AND	r8, r3, r1		; clear out all bytes except last byte, store in r8
		MOV	r8, r8, LSL#24		; shift last byte to position of first byte
		MOV	r7, r3, LSR#8		; shift third byte to the end of the word
		AND	r7, r7, r1		; clear out bytes except last byte, store in r7
		MOV	r7, r7, LSL#16		; shift byte to position of byte 2
		MOV	r6, r3, LSR#16		; shift second byte to the end of the word
		AND	r6, r6, r1		; clear out bytes except last byte, store in r6
		MOV	r6, r6, LSL#8		; shift byte to byte position 3
		MOV	r5, r3, LSR#24		; shift first byte to the end of the word
		AND	r5, r5, r1		; clear out bytes except last byte, store in r5
		ORR	r4, r5, r6		; pack into new word
		ORR	r4, r4, r7		; pack into new word
		ORR	r4, r4, r8		; pack into new word
		STR	r4, [r0], #4		; increment memory address by 4
		B	PLOOP

PSTRING		% 20
ESTRING		% 20
		ALIGN

COMPARE		MOV	r0, #2			; set r0 to 2, to call PRINT subroutine
		ADR	r4, MESSAGE		; store address of MESSAGE to r4
		BL	JUMPTABLE		; branch and link to jumpTABLE, run PRINT 
		MOV	r0, #2			; set r0 to 2, to call PRINT subroutine
		ADR	r4, ESTRING		; store address of ESTRING to r4
		BL	JUMPTABLE		; branch and link to jumpTABLE, run PRINT 
		MOV	r0, #2			; set r0 to 2, to call PRINT subroutine
		ADR	r4, PSTRING		; store address of PSTRING to r4
		BL	JUMPTABLE		; branch and link to jumpTABLE, run PRINT
		LDR	r2, [SP, #14*4]		; load CPSR to r2
		MSR	cpsr, r2		; recover CPSR
		LDMFD	sp!, {r0-r12, lr}	; load the registers
		ADD	sp, sp, #4		; remove space from top of stack
		MOV	pc, lr

		AREA DATA, DATA
MASK		DCD	&000000FF
KEY		DCD	&CCCCCCCC
MESSAGE		DCB	"ME oh my", 0
DSTRING		% 20
		ALIGN
		END