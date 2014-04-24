ldm r0,253 ;Leo de teclado numero hexa fp y lo pongo en r0
ldm r1,253 ;Leo de teclado numero hexa fp y lo pongo en r1
multf r2,r0,r1 ;Hace la multiplicación
stm r2,255 ;Muestro en pantalla el resultado
