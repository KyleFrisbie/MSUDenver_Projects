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
		BL	PRINTCOUNT	; print count of vowels
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
		ADDEQ	r5, r5, #1	; if vowel, add 1 to counter
		STRB	r0, [r1], #1	; store byte of r0 to r1
		CMP	r0, #0		; check for end of string
		BNE	VOWEL		; loop back to ISVOWEL
		MOV	r1, r2		; r1 gets address of beginning of STRING1
		MOV	pc, lr

PRINTCOUNT	ADD	r0, r5, '0'	; get count of vowels
		SWI	SWI_WRITE	; print count
		MOV	pc, lr

STRING1		DCB	"Hello, my name is Kyle Frisbie!",&0a, &0d,0
		ALIGN

		END