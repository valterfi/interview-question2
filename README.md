Interview question
==================

### Tech
  * [Swagger] 
  * [H2 Database] 
  * [Spring Boot] 
  * [Maven]

### Prerequisites

- Maven
- JDK 8 or higher   

### REST API

| Method   |        Path         |
|----------|:--------------------|
| POST     |    /store           |
| GET      |    /permutation     |  
  
### Store an array
Send an array of numbers to be stored and return an ID

**URL** : `/store`

**Method** : `GET`

**Query Parameter** : `numbers`

**Query Parameter Type** : `Array[integer]`

Provides the stored array ID.

Example: Sending `http://localhost:5000/store?numbers=2,1,3,4,6,5,7` should 
return an ID of the array (e.g., 1)
  
### Permute an array
Send an array of numbers to be stored and return an ID

**URL** : `/permutation`

**Method** : `POST`

**Query Parameter** : `id`

**Query Parameter Type** : `long`

Provides a random permutation of the array with such ID.

Example: Sending `http://localhost:5000/permutation?id=1` should return 
a random permutation of the array (e.g., `2,3,6,7,1,3,5`)

### Some considerations

- In this version, storage was developed in memory without using any database
- I assumed that it is possible to store empty lists
- The NumberContainer class is the representation for storing lists of numbers
- By default the application will run on port 5000
- Automated tests can be run at different phases of the maven
  - Running unit tests:
  ```sh
    $ mvn clean test
  ```
  - Runinng unit tests and integration tests:
  ```sh
    $ mvn clean verify
  ```
- Unit and integration test scenarios are the same
- I configured a Documentation API using swagger. It is available in the /swagger-ui.html path.

### How to run

- At the root of the project:

  - Running the jar packed by maven;
    ```sh
    $ mvn clean package
    $ java -jar target/numbers-1.0.0-PART1.jar
    ``` 

  - Using the maven spring boot plugin;
    ```sh
    $ mvn spring-boot:run
    ```

  - Or Providing a docker image and deploying anywhere
    ```sh
    $ mvn clean package
    $ docker build -f Dockerfile -t numbers .
    $ docker run -p 5000:5000 numbers
    ``` 
[Swagger]: <https://swagger.io/>
[H2 Database]: <http://www.h2database.com/>
[Spring Boot]: <https://spring.io/>
[Maven]: <https://maven.apache.org/>
