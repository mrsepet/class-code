	global product
	section .text
product:
    push  ebp		; Save the old base pointer   WILLIAM EMMANUEL
    mov   ebp, esp	; Set new value of the base pointer   WRE9FZ
    push  esi		; Save registers		LAB 8
    push ecx
    xor   eax, eax	; Place zero in EAX (sum)
    mov   esi, [ebp+8]	; Put the product 1 in EAX
    mov   ecx, [ebp+12]	; Put the product 2 in ECX (counter)
    cmp   ecx, 0	; If either inputs are 0 exit
    jle   product_done
    cmp   esi, 0
    jle product_done
product_loop:
    mov   edx, esi	; Put product 1 into edx
    add   eax, edx	; Add the 1st product into running sum
    dec   ecx		; Decrement ECX, the second product
    cmp   ecx, 0	; If there are more than zero elements
    jg    product_loop	; left to add up, then do the loop again.
product_done:
    pop ecx
    pop   esi		; Restore registers that we used.
    pop   ebp		; Restore the caller's base pointer.
    ret			; Return to the caller.

	global power
	section .text
power:
	push  ebp		; Save the old base pointer
	mov   ebp, esp		; Move new base pointer
	push esi		; push esi so we can use it
	push edx		; push edx so we can use it
	mov edx, [ebp+12]	; exponent to eax
	mov esi, [ebp+8]	; base to esi
	cmp edx, 0		; cmp to 0
	jle power_basecase	; done? then set eax 1 and return
	dec edx			; decrement exponent
	push edx		; push exponent first
	push esi		; then push base
	call power		; call power on exponent-1, base
	add esp, 8		; deallocate the eax and esi pushes
	push eax		; push the return (1) 
	push esi		; push the base for multiplication
	call product		; call product
	add esp, 8		; deallocate push eax and push esi 
	jmp power_done		; jump to return (do pops, return product eax)
power_basecase:
	mov eax, 1		; Base Case!
power_done:
	pop edx			; Put original edx back
	pop esi			; Put original esi back
	mov esp, ebp		; Put back stack pointer
	pop ebp			; Put back base pointer
	ret			; Return exponent!
