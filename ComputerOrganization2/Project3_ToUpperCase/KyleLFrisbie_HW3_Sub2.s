	; **********************************
	;  File: KyleLFrisbie_HW3.s
	;  Programmer: Kyle L Frisbie
	;  Working with: Tom Selke
	;  Description: Analyzes vowels in a character
	;               string, and if the encountered
	;		character is a vowel, the
	;		program converts the vowel to
	;		uppercase, and increments a
	;		counter of vowels. This program
	;		also that prints the entry and
	;		resulting string.
	;
	;  Project: Homework3_KyleLFrisbie               
	;  Date: 21 September 2014
	;************************************
	
	AREA Homework3, CODE, READONLY
SWI_WRITE	EQU	&0     		; output character in r0 
SWI_Exit	EQU	&11    		; finish program

		ENTRY
		ADR	r1, STRING1	; r1 gets address of STRING1
		ADR	r2, STRING1	; r2 gets address of STRING1
		MOV	r5, #0		; set counter of vowels to r5
		BL	PRINT		; print STRING1
		BL	VOWEL		; print STRING1 with uppercase vowels
		BL	PRINT		; print modified STRING1
		BL	PRINTHEX	; print count of vowels
		SWI	SWI_Exit	; finish program
		ALIGN

PRINT		LDRB 	r0, [r1], #1	; get character
		CMP	r0, #0		; check for end of string
		SWINE	SWI_WRITE	; if not, print
		BNE	PRINT		; loop back to PRINT
		MOV	r1, r2		; r1 gets address of beginning of STRING1
		MOV	pc, lr

VOWEL		LDRB 	r0, [r1]	; get character
		TEQ	r0, #'a'	; check for vowel
		TEQNE	r0, #'e'	; check for vowel
		TEQNE	r0, #'i'	; check for vowel
		TEQNE	r0, #'o'	; check for vowel
		TEQNE	r0, #'u'	; check for vowel
		SUBEQ	r0, r0, #32	; if lowercase, make uppercase
		TEQNE	r0, #'A'	; check for uppercase vowel
		TEQNE	r0, #'E'	; check for uppercase vowel
		TEQNE	r0, #'I'	; check for uppercase vowel
		TEQNE	r0, #'O'	; check for uppercase vowel
		TEQNE	r0, #'U'	; check for uppercase vowel
		ADDEQ	r5, r5, #1	; if vowel, add 1 to counter
		STRB	r0, [r1], #1	; store byte of r0 to r1
		CMP	r0, #0		; check for end of string
		BNE	VOWEL		; loop back to ISVOWEL
		MOV	r1, r2		; r1 gets address of beginning of STRING1
		MOV	pc, lr

PRINTHEX	MOV	r3, #8		; count of nibbles = 8
LOOPHEX		MOV	r0, r5, LSR #28	; get top nibble
		CMP 	r0, #9		; hexanumber 0-9 or A-F
		ADDGT 	r0, r0, #"A"-10	; ASCII alphabetic
		ADDLE 	r0, r0, #"0"	; ASCII numeric
		SWI 	SWI_WRITE	; print character
		MOV	r5, r5, LSL #4	; shift left one nibble
		SUBS	r3, r3, #1	; decrement nibble count
		BNE	LOOPHEX		; if more nibbles,loop back
		MOV 	pc, r14		; return

STRING1		DCB	"I want to write software. I then want to learn how to write even better software over and over again.",&0a, &0d,0
		ALIGN

		END