Examen Mutantes de MercadoLibre
----------------------------------
Introducción:
El siguiente ejercicio consiste en un API REST que nos permitirá consultar adns y verificar si se trata de un adn mutante o humano.
También podremos consultar el ratio de mutantes / humanos registrados luego de ser analizados en cada una de las requests al api.

El enunciado se encuentra en el root del repositorio.

-----------
Funcionalidades:

Verificación de ADN:

Se debe realizar un post al endpoint http://mutants.us-east-1.elasticbeanstalk.com/mutant de la siguiente forma de ejemplo:

POST → /mutant/
{
“dna”:["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}

En caso de verificarse el ADN mutante, se obtendrá un 200-OK. Caso contrario, si es humano obtendremos un 403-FORBIDDEN.

Nota: las cadenas enviadas deben unicamente estar compuestas por las bases nitrogenadas A, C, G o T. y la cantidad de bases por cadena debe ser igual para todas e igual a la cantidad de cadenas enviadas.

Estadisticas:

Se debe realizar un get a la siguiente URL: http://mutants.us-east-1.elasticbeanstalk.com/stats

Será devuelto un JSON con el siguiente formato de ejemplo:

{"count_mutant_dna":2,"count_human_dna":3,"ratio":0.0}

-----

Tecnologías involucradas y requisitos para el desarrollo local:

El proyecto está desarrollado en JAVA 1.8. Se utilizó Maven para la gestión de dependencias y actividades de instalación, build y packaging.
La tecnología utilizada para crear el API Rest fue la última versión de SpringBoot (1.5.9). 
La base de datos es una DynamoDB Cloud que se consume a través del API de AWS para JAVA.
Para los tests se utilizó JUnit y Mockito.

En el caso de querer trabajar local el proyecto, se debe bajar los fuentes y correr la clase principal AppRunner para iniciar SpringBoot.
Para poder tener conectividad con la base de datos, me podes enviar un mail a alvarez.carlosalb@gmail.com para solicitarme credenciales de AWS.
Por razones obvias de seguridad, me es imposible poner las credenciales aquí.

Estas credenciales son necesarias para tener comunicacion con la base de datos de DynamoDB. Las mismas deben ser incorporadas a las variables
de entorno de Windows / Linux, para que el JAVA AWS SDK pueda escribir / leer de la base. Tambien es posible utilizar las credenciales seteandolas en
las JAVA System Properties, o colocando el archivo credentials en el usuario local/.aws

En caso de no querer trabajar con la versión de DynamoDB online, es posible bajarse la base de datos local:
https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.html


















