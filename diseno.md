# DISEÑO    DEL SISTEMA

## Model y Service

Se realizo una separacion de las capas model y service.

En el model se colocan las clases con sus atributos y metodos propios. Estas son caracteristicas de la entidad independientes de la logica del negocio. En el service trabaja en con la logica de negocio, es decir, aplicar las reglas de como funciona el sistema.

Esta separacion nos permite dividir responsabilidades y proteger a las entidades de ser accedidas por otras entidades que no correspondan.


## Recursos (Libros)

#### Model:

Para los libros, se tienen 2 tipos de libros, los libros fisicos y ebooks. Al haber caracteristicas que ambos tipos de libro tienen, se crea una interfaz recursos declarando una especie de contrato para ser un recurso.

Luego para los ebooks se crea un record. Los record son clases inmutables, es decir, de solo lectura. Se elige un record y no una clase que pueda mutar para los ebooks ya que no tendran ningun atributo que necesite cambiar en ningun momento de la ejecucion del sistema.

Para los libros fisicos se elige una clase. La razon deno utilizar records aqui se debe a que, a diferencia de un ebook, los libros fisicos tienen stock, el cual varia dependiendo de la cantidad de libros que se tengan almacenados. Esto significa que un atributo de esta clase debe mutar.

#### Service:

En cuanto al service de los libros, se tienen dos archivos, uno para todos los objetos recursos y otro para los libros fisicos. En el service de recurso se colocan metodos que se apliquen tanto a ebook como a libro fisico, en este caso, la busqueda de un libro por ejemplo.

La razon de que hay un service para libro fisico y no uno para ebook es que no se tiene ningun metodo exacto para ebook, mientras que en libro fisico se debe incrementar y decrementar el stock segun la situacion.

## Socios

#### Model:

Para los socios se tiene dos tipos de socios, los estudiantes y los docentes. Como comparten todos los atributos se crea una interfaz socio con los atributos de estos. Luego, las clases Estudiante y Docente son records ya que no necesitan modificar ningun valor de ellos. Dentro de estos records se redefine el limite de prestamos de cada tipo, ya que este cambia segun el tipo (polimorfismo).

#### Service:

Luego para el service, como todos los objetos socios pueden realizar las mismas acciones, solo se tiene un service de socio. En este se crea un metodo para crear un socio, el cual sigue las reglas del sistema, como por ejemplo, que el dni sea unico.

## Prestamos

#### Model:

En los prestamos se crea como una clase, ya que este si que cambiara. Esta clase es mas compleja. LLaman la atención dos atributos, la fechaDevolucion y el estado.

En cuanto a la fechDevolucion, al crear un prestamo no se tiene una fecha fecha de devolucion aun. Debido a esto, se crea como un Optional el cual adentro puede estar vacio o contener un dato de tipo LocalDate. De esta froma se crea un prestamo con fechaDevolucion vacia.

Para el atributo estado, se crea una clase enum, donde este solo puede tomar dos valores: ACTIVO o ENNTREGADO.

#### Service:

Para el service de prestamos se tienen varias funciones. Se maneja la creacion de un prestamo, respetando las reglas, como por ejemplo, si un libro fisico no tiene stock no se puede ejecutar el prestamo. Tambien un metodo de entrega, donde finalmente se asigna valor al opcional 'fechaDevolucion.



## Excepciones

Para las excepciones se creo una jerarquia donde la excepcion padre es 'BibliotechException'. Luego todas las excepciones del sistema extienden de esta. Esto permite que en la ejecución del sistema solo atrapando la excepción padre, se llame a la excepción especifica para esa situación.
