ldm r0,0 ; inicializo r0 en 0
ldm r1,253 ;Leo de teclado numero de celda inicial y lo pongo en r1
ldm r2,253 ;Leo un numero
jpz r2,18 ;Termino de leer numeros
stm r2,*r1   ;Hay q implementar manejo de punteros
ldm r3,1 ;Steto 1
;Setear maximos y minimos a medida q voy leyendo
add r1,r1,r3 ;Muevo al proximo casillero de memoria
ldm r2,0 ; seteo r2 en 0
jpz r2,4 ;Voy a leer otro numero
ldm r2,253 ;Leo un numero para ver si es maximo o minimo
jpz r2,30 ;jumpeo si es 0
ldm r0,1 ;r0 = 1
jpz r2,34 ;jumpeo es 1
stp
stm r4,255 ;Muestro en pantalla el minimo
stp
stm r5,255 ;Muestro en pantalla el maximo
