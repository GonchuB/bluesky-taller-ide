ldi r0,0
ldm r4,253 ;obtengo el valor ingresado por teclado para numero de celda
cpy r4,r1 ;guardo en r1 la celda de memoria de incio de datos
ldi r2,0 ;guardo en r2 el offset a celda de memoria
ldi r3,1 ;guardo el tamanio de cada instruccion
ldi r4,0
cpy r4,r9 ;en r9 guardo el maximo valor ingresado
ldi r4,127
cpy r4,r10 ;en r10 guardo el minimo valor ingresado     
ldm r4,253 ;obtengo el valor ingresado por teclado
cmp r5,r4,r0 ;comparo el valor ingresado con cero 
jpz r5,18 ;si el valor ingresado es negativo no lo tengo en cuenta y pido ingresar de nuevo 
jpz r4,52 ;si es cero el valor ingresado
add r6,r1,r2 ;guardo en r6 la celda donde debo guardar el dato ingresado
add r2,r2,r3 ;actualizo el offset de celda de datos
str r4,r6 ;guardo el valor ingresado por teclado en el valor de celda que contiene r6
cmp r8,r9,r4 ;comparo el valor ingresado contra el maximo
cmp r11,r4,r10 ;comparo el valor ingresado contra el minimo
jpz r8,42 ;si el valor ingreaso es mas grande que el maximo lo guardo como maximo 
jpz r11,48 ;si el valor ingresado es menor que el minimo lo guardo como minimo 19
jpz r0,18 ;vuelvo a ingresar un dato nuevo   
cpy r4,r9 ;en r9 guardo el maximo valor ingresado 
jpz r0,38 ;vuelvo para comparar con el minimo
jpz r0,18 ;vuelvo a ingresar un dato nuevo  
cpy r4,r10 ;en r10 guardo el minimo valor ingresado 
jpz r0,18 ;vuelvo a ingresar un dato nuevo  26            
ldm r4,253 ;obtengo el valor ingresado por teclado
jpz r4,68 ;si es cero el valor ingresado muestro el minimo 28
ldi r7,255
add r7,r4,r7
jpz r7,64 ;si 255 mas el valor ingresado por teclado es cero significa que se ingreaso 1 y entonces tengo que mostrar el maximo
jpz r0,52 ;sino pido ingresar de nuevo una opcion para mostrar el resultado
stm r9,255 ;muestro por pantalla el maximo
stp
stm r10,255 ;muestro por pantalla el minimo valor