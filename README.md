# Employee API


## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)
- [DOCKER](https://docs.docker.com/install/)


## Running the application locally

There are several ways to start the application Locally

For development purposes you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:
in the command line from the root path of the project run:

```shell
mvn spring-boot:run
```

This will fire up the application with an H2 on-memory database loaded. When you start the app you can access the
h2 db using this url: http://localhost:8000/h2/


```
to check the status of the application please use this url: http://localhost:8000/actuator/health

to see the API endpoints documentation you please check the swagger ui URL: http://localhost:8000/swagger-ui.html
```

Also you can use docker-compose to load the app with a MYSQL db.

in the command line from the root path of the project run:
```shell
docker-compose up
```

all the enviroment variables for docker were place in the .env file

```
to check the status of the application please use this url: http://localhost:9000/actuator/health

to check the swagger ui please check this url: http://localhost:9000/swagger-ui.html
```

if you need to re-run (without delete and stop all the process) the build and deploy
new changes you can run in the command line from the root path:
```shell
docker-compose up --build
```

in order to stop the the containers you should run in the command line from the root path:
```shell
docker-compose down --rmi local
```

this will stop the containers and remove the images from your computer.



## Integration Test for Challenge points

* Crear un empleado
    - /src/it/java/com.disid.api/employees/CreateEmployeeIT.java

* Crear un departamento
    - /src/it/java/com.disid.api/departments/CreateDepartmentsIT.java

* Listar varios empleados de un departamento
    - /Users/gondev/workspace/api/src/it/java/com.disid.api/employees/ListEmployeesIT.java

* Listar un empleado concreto que pertenece a un departamento
    - /Users/gondev/workspace/api/src/it/java/com.disid.api/employees/ListEmployeesIT.java

* Borrar un empleado que pertenece a un departamento
    - /Users/gondev/workspace/api/src/it/java/com.disid.api/employees/DeleteEmployeeIT.java

* Modificar los atributos de la entidad Empleado
    - /Users/gondev/workspace/api/src/it/java/com.disid.api/employees/ModifyEmployeeIT.java

* Buscar un empleado por antigüedad en la empresa, mostrando todos los que se han incorporado a la empresa con posterioridad a una fecha
    - /Users/gondev/workspace/api/src/it/java/com.disid.api/employees/GetEmployeesFilteredByRegisteredDateIT.java

## Other tips

When you use the build using the docker-compose file you will see that for every build you are going to generate
a new image... so in order to delete the unused image please use this command:

```shell
docker image prune -f
```





## To be Improved

* Deploying on Kubernetes With MiniKube

* Integration Test:
    - Improve the maven process to run the integration tests with maven profile
    - Generate Fixtures to load specific data before the test runs
    - Use [Rest-Assured](http://rest-assured.io/) to fluently create HTTP requests and assertions about the response.
    - Integration Test that Asserts the Pagination of the List Employees Endpoint

* Performance:
    - Add a Jmeter to run performance Tests
