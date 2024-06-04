# modificaciones:

Servidor: con dos hilos, uno es un receptor y otro repector.
Pasaran dichas lineas pasadas por el cliente a una estructura compartida de almacenamiento.

Cliente: envia las lineas (con string) y el elige cuando se cerrara dicha conexion, se cerrada con botones de conexion y de desconoxion.


Cliente usara interfaz grafica. 

En este ejercico,la clase conexion sobra. Solo hace falta: repector, emisor, almacen.
El emisor debe de saber porque socke debe de salir.

Este ejercico esta base echo a productores/consumidores. 


# NUEVA VERSION

El servidor ahora cra un  repector por cada cliente, un solo emisor que envia los datos al cliente. 
El repector almacena los datos que le mandes y despues, el emisor los tomara del almacen para despues mandarlos por algun socket.
El emisor simpre estara activo. 
