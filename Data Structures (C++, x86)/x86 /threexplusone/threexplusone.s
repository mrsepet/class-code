	;; William Emmanuel
	;; wre9fz
	;; 4/8/13
	;; threexplusone.s

	;; Optimizations Used:
	;; -Made code as few lines as possible
	;; -Only accessed the paremeter once, then stored it in a register
	;; -Used cdq to combine EDX:EAX instead of xor'ing EDX every operation
	;; -Tried (but faild) at using bitshifts instead of idiv
	;; -Used imul and idiv only once each
	
	global threexplusone
	section .text
threexplusone:
	push ebp 		; Save old bp
	mov ebp, esp 		; Reset bp to stack middle
	mov eax, [ebp+8] 	; Move the paremeter (x) into eax
	mov esi, eax		; Copy x into esi for later use
	cmp eax, 1 		; Check if x == 1 
	je threex_base		; If x == 1, then jump to base
	mov ebx, 2		; Move 2 into ebx for division
	cdq			; This treats EDX:EAX as one 64 bit reg.
	idiv ebx		; Divide x by 2
	cmp edx, 0 		; Check edx (the remainder); is x even?
	je threex_even		; Yes, its even--jump to even
	jmp threex_odd 		; No, its odd--jump to odd
threex_even:
	push eax 		; Push x/2 as the paremeter
	call threexplusone	; Recursively call the function
	add eax, 1 		; Add one to the number of steps (eax return)
	jmp threex_done		; Jump to the end
threex_odd:
	mov eax, esi		; Move in the parameter again
	mov ebx, 3		; Move 3 into ebx for multiplication
	imul eax, ebx		; Multiply eax (parem) by ebx (3)
	inc eax			; Increment product (3x+1)
	push eax		; Push this new number for function call
	call threexplusone	; Call the function recursively
	inc eax			; Add one to the number of steps (eax return)
	jmp threex_done		; Jump to the end
threex_base:	
	xor eax, eax		; Return 0 (All the step additions are done in odd/even)
threex_done:	
	mov esp, ebp		; Reset the stack pointer
	pop ebp			; Reset the base pointer
	ret			; Return
