## Tabla de contenidos
1. [Ejercicios: fumadores](#arquitectura-tcpip)
2. [Desarollo con Java](#desarollo-con-java)



------------------------------------------------------------

## Arquitectura TCP/IP:

5. Aplicacines

4. _Transporte  > UDP, TCP_ (Puerto 80 > HTTP/Apache). 

3. _Red > IP_

2. Enlace

1. Fisica
>[!NOTE]
> Tener en cuenta la arquitectura del TCP/Ip (No confundir con la OSI _Esa es para redes_)
>
> https://docs.oracle.com/javase/8/docs/api/java/net/Socket.html

------------------------------------------------------------

## Desarollo con Java
Socket (UDP, TCP)
Cliente --- Servidor

 - Siguiendo el modelo ⤴️ TCP

_Se debe de especificar con el puerto y la ip._

Cuando se crean los scoket se especifica en el puerto, y la ip en un string o con IpAddress.


SERVIDOR -> IP: 170.4.5.6 ========= CLIENTE -> IP: 60.10.20.21

El servidor crea un server socket que esta conectado a un puerto determinado (_usar los puertos REGISTRADOS, algunos de ellos estan libres_). 

> [!IMPORTANT]
>
>Siempre se ha de escoger un protocolo (_hacemos referencia a la capa Aplicacion_), esto hara que Cliente y Server tengan conexion.

--------------------------------------------------------------------
> [!IMPORTANT]
>
> Videos de interes: https://www.youtube.com/playlist?list=PLaxZkGlLWHGVpAWynB1UwnIq_yOsVyGYB
>
> Chismes tambien de interes:  https://www.redeszone.net/tutoriales/configuracion-puertos/puertos-tcp-udp/