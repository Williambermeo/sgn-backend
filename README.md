Gestión de Nómina - Proyecto Java Spring Boot

Descripción



Este proyecto es un módulo de gestión de nómina desarrollado con Java 17 y Spring Boot 3.0.6. Permite administrar empleados, registrar horas extras y ventas, y calcular la nómina con comisiones automáticas.



Está diseñado como una aplicación backend RESTful para facilitar futuras integraciones con aplicaciones web o móviles.



Tecnologías usadas



Java 17



Spring Boot 3.0.6



Spring Data JPA



Base de datos H2 (en memoria)



Maven



Lombok



Funcionalidades



CRUD completo para empleados (crear, leer, actualizar, eliminar)



Cálculo automático de nómina basado en salario base, horas extras y ventas realizadas



Exposición de endpoints REST para integración con frontend o herramientas externas



Configuración simple con base de datos en memoria para pruebas



Cómo ejecutar el proyecto



git clone mvn clean install
mvn spring-boot:run






Clonar el repositorio:



git clone https://github.com/Williambermeo/gestion-nomina2





Entrar al directorio del proyecto:



cd gestionnomina





Compilar y ejecutar usando Maven:



mvn clean spring-boot:run





El servidor arrancará en http://localhost:8080



Endpoints disponibles



GET /api/empleados - Listar todos los empleados



GET /api/empleados/{id} - Obtener empleado por ID



POST /api/empleados - Crear un nuevo empleado



PUT /api/empleados/{id} - Actualizar un empleado



DELETE /api/empleados/{id} - Eliminar un empleado



GET /api/nominas - Listar nóminas calculadas



Requisitos previos



Java 17 instalado



Maven instalado



Control de versiones



Este proyecto utiliza Git para el control de versiones. El repositorio está alojado en \[tu plataforma Git, por ejemplo GitHub] y puedes clonar o descargar desde allí.



Autor



William Esteban Bermeo Leyton

Ficha 2977355

Tecnología en análisis y desarrollo de software

