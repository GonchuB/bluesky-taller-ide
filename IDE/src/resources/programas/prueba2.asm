ldm r1,253 ;Leo de teclado numero hexa parte mas significativa de n1 y lo pongo en r1
ldm r2,253 ;Leo de teclado numero hexa parte menos significativa de n1 y lo pongo en r2
ldm r3,253 ;Leo de teclado numero hexa parte mas significativa de n2 y lo pongo en r3
ldm r4,253 ;Leo de teclado numero hexa parte menos significativa de n3 y lo pongo en r4
add r6,r2,r4 ;Sumo las partes menos significativas de n1 y n2 y lo almaceno en r6
ldi r0,0 ;Asigno 0 a r0 para comparar los if


ldi r5,0 ;Si la parte menos significativa cumple ciertas condiciones a r5 hay que sumarle 1.
; GOTO add r5,r5,r1
ldi r5,1 ;Las condiciones son: Si el n°+ es > al n°- en valor absoluto o si ambos numeros son negativos

add r5,r5,r1 ;Sumo las partes mas significativas de n1 y n2 y lo almaceno en r5
add r5,r5,r3 ;Sumo las partes mas significativas de n1 y n2 y lo almaceno en r5

stm r5,255 ;Muestro en pantalla la parte mas significativa del resultado
stm r6,255 ;Muestro en pantalla la parte menos significativa del resultado