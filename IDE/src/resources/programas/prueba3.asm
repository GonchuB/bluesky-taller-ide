ldi r0,0
ldi r1,194 ;guardo en r1 la celda de memoria de incio de datos
ldi r2,0 ;guardo en r2 el offset a celda de memoria
ldi r3,1 ;guardo el tamanio de cada instruccion
ldi r4,0
cpy r4,r9 ;en r9 guardo el maximo valor ingresado
ldi r4,127
cpy r4,r10 ;en r10 guardo el minimo valor ingresado     8
ldm r4,253 ;obtengo el valor ingresado por teclado
;cmp r5,r4,r0 ;comparo el valor ingresado con cero
;jpz r5,16 si el valor ingresado es negativo no lo tengo en cuenta y pido ingresar de nuevo
jpz r4,46 ;si es cero el valor ingresado
add r6,r1,r2 ;guardo en r6 la celda donde debo guardar el dato ingresado
add r2,r2,r3 ;actualizo el offset de celda de datos
str r4,r6 ;guardo el valor ingresado por teclado en el valor de celda que contiene r6
cmp r8,r9,r4 ;comparo el valor ingresado contra el maximo
cmp r11,r4,r10 ;comparo el valor ingresado contra el minimo
jpz r8,36 ;si el valor ingreaso es mas grande que el maximo lo guardo como maximo 
jpz r11,42 ;si el valor ingresado es menor que el minimo lo guardo como minimo
jpz r0,16 ;vuelvo a ingresar un dato nuevo   
cpy r4,r9 ;en r9 guardo el maximo valor ingresado
jpz r0,32 ;vuelvo para comparar con el minimo
jpz r0,16 ;vuelvo a ingresar un dato nuevo  
cpy r4,r10 ;en r10 guardo el minimo valor ingresado
jpz r0,16 ;vuelvo a ingresar un dato nuevo               
ldm r4,253 ;obtengo el valor ingresado por teclado
jpz r4,54 ;si es cero el valor ingresado muestro el minimo
stm r9,255 ;muestro por pantalla el maximo
stp
;jpz r0,56 ;salto al final del programa para terminar 
stm r10,255 ;muestro por pantalla el minimo valor