# Sistema de Biblioteca – DOSW Library
![biblioteca.png](Images/biblioteca.png)
## Descripción del Proyecto

Este proyecto consiste en el desarrollo de una API REST para la gestión de una biblioteca, implementada utilizando Spring Boot. El sistema permite administrar usuarios, libros y préstamos, simulando el funcionamiento básico de una biblioteca digital.

La aplicación sigue una arquitectura por capas que separa responsabilidades en controladores, servicios, modelos y utilidades, facilitando el mantenimiento, la escalabilidad y la realización de pruebas.

---

## Objetivos

* Registrar y gestionar usuarios
* Administrar el catálogo de libros
* Controlar el préstamo y devolución de libros
* Validar reglas de negocio
* Manejar errores de forma estructurada
* Documentar la API de manera interactiva

---

## Funcionalidades principales

### Gestión de Usuarios

* Crear usuarios
* Consultar usuarios
* Actualizar información de usuarios
* Eliminar usuarios

### Gestión de Libros

* Registrar libros en el sistema
* Consultar disponibilidad de libros

### Gestión de Préstamos

* Registrar préstamos de libros a usuarios
* Validar disponibilidad del libro
* Registrar devolución de libros
* Consultar préstamos por usuario

---

## Reglas de Negocio

* Un libro solo puede ser prestado si está disponible
* Al devolver un libro, su estado cambia a disponible
* No se permiten datos inválidos en usuarios o libros
* Se manejan errores en caso de operaciones inválidas

---

## Arquitectura

* **Controller:** Expone los endpoints de la API
* **Service:** Contiene la lógica de negocio
* **Model:** Representa las entidades del sistema
* **Validator:** Encargado de validar reglas y datos
* **Util:** Clases de apoyo para funcionalidades comunes

---

## Documentación de API

La API está documentada utilizando Swagger, lo que permite visualizar y probar los endpoints desde una interfaz gráfica.

---

## Pruebas

El proyecto incluye pruebas unitarias para validar:

* Lógica de negocio
* Validaciones
* Comportamiento de servicios

Además, se incluye análisis de cobertura de código y análisis estático para garantizar la calidad del software.

---

## Tecnologías utilizadas

* Java 21
* Spring Boot
* Maven
* Swagger (OpenAPI)
* JUnit
* JaCoCo
* SonarQube

---

## Conclusión

Este proyecto demuestra la implementación de buenas prácticas de desarrollo como separación de responsabilidades, 
pruebas automatizadas, documentación de API y control de calidad, aplicadas en un sistema realista de gestión de biblioteca.


## Diagramas

### Diagrama de Clases
![diagrama-de-clases.png](Images/diagrama-de-clases.png)

### Diagrama de Componentes
![diagrama-de-componentes-general.png](Images/diagrama-de-componentes-general.png)

### Diagrama ER PostgreSQL

![DiagramaER.png](Images/DiagramaER.png)


### Diagrama ER MongoDB

![ER-Mongo.png](Images/ER-Mongo.png)

## Cobertura de pruebas (JaCoCo)

![cobertura.png](Images/cobertura.png)

## Análisis estático

![SonarQube_prueba.png](Images/SonarQube_prueba.png)

## Pruebas JUnit

![PruebasJUnit.png](Images/PruebasJUnit.png)

## Ejecución de funcionalidades (Postman)

### GET
![postman1.png](Images/postman1.png)

### POST
![postman2.png](Images/postman2.png)

### PUT
![postman3.png](Images/postman3.png)

### PATCH
![postman4.png](Images/postman4.png)

### DELETE
![postman5.png](Images/postman5.png)
![postman5-1.png](Images/postman5-1.png)

### HEAD
![postman6.png](Images/postman6.png)

### OPTIONS
![postman7.png](Images/postman7.png)


## Implementación de Swagger para la documentación 

### Implementación completa
![swagger1.png](Images/swagger1.png)

### User Controller
![swagger2.png](Images/swagger2.png)

### Loan Controller
![swagger3.png](Images/swagger3.png)

### Book  Controller
![swagger4.png](Images/swagger4.png)

### Schemas
![swagger5.png](Images/swagger5.png)

### Video de pruebas funcionales con cada enpoint simulando los flujos de usuarios, libros y prestamos

https://drive.google.com/file/d/1YUX3c6SwBLBU-1-J2SRzSawBQtEuO3I4/view?usp=sharing






video