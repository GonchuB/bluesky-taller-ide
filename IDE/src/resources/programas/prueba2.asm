ldm r1,253 ;Leo de teclado numero hexa parte mas significativa de n1 y lo pongo en r1
ldm r2,253 ;Leo de teclado numero hexa parte menos significativa de n1 y lo pongo en r2
ldm r3,253 ;Leo de teclado numero hexa parte mas significativa de n2 y lo pongo en r3
ldm r4,253 ;Leo de teclado numero hexa parte menos significativa de n3 y lo pongo en r4
ldi r5,0 ;Inicializo en 0
add r6,r2,r4 ;Sumo las partes menos significativas de n1 y n2 y lo almaceno en r6
jnc 16 ;Salto la suma de 1 ya que no hay overflow y continuo normalmente, voy a 16 que es el add directamente
ldi r5,1 ;Sumo 1 por el overflow de la parte menos significativa
add r5,r5,r1 ;Sumo las partes mas significativas de n1 y n2 y lo almaceno en r5
add r5,r5,r3 ;Sumo las partes mas significativas de n1 y n2 y lo almaceno en r5
stm r5,255 ;Muestro en pantalla la parte mas significativa del resultado
stm r6,255 ;Muestro en pantalla la parte menos significativa del resultado