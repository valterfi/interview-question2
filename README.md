Interview question
==================


This is a very basic spring-boot app. Run it (using `mvn spring-boot:run`) or your favorite IDE.
Try the url `http://localhost:5000/greeting?name=David`, it should return the string: "Hello David".

There is also a package persistence which contains a basic CRUD example with persistence - this will be addressed in part 3.

# Requirements
### Part one - Basic local service
We would like to create 2 APIs, 
* one to store an array of numbers, 
* one that returns a random permutation of that array.

Storage should be in memory without using any database.

### Acceptance criteria
* Sending `http://localhost:5000/store?numbers=2,1,3,4,6,5,7` should 
return an ID of the array (e.g., 1)
* Sending `http://localhost:5000/permutation?id=1` should return 
a random permutation of the array (e.g., `2,3,6,7,1,3,5`)


### Part two - Adding persistence layer
We would like to have persistence of the data in case the server drops.
`application.yml` is configured for H2 database, but feel free to use any other relational DB you are comfortable with to save the data.
Make sure that your app will work with H2 as well as it will be tested with H2 (integartion-tests can help here).

#### Acceptance criteria
* Sending `http://localhost:5000/store?numbers=2,1,3,4,6,5,7` should 
return an ID of the array (e.g., 1)
* Sending `http://localhost:5000/permutation?id=1` should return 
a random permutation of the array (e.g., `2,3,6,7,1,3,5`)
Restarting the spring-boot app and Sending `http://localhost:5000/permutation?id=1` 
should give back a random permutation of the array (e.g., `2,3,6,7,1,3,5`)

## Guidelines
* Fork this repository and push your commits
* Use the spring-boot template given
* Write unit-tests, integration-tests 
  * Write in javadocs what scenarios are in test
  * Higher coverage is better
* Write code documentation
* All classes given are meant to used as reference - once they are not needed, they can be removed.
* This project uses [lombok](https://projectlombok.org/) - use it when possible
* Properly organize your project with `.gitignore` file, `readme` file explaining how to run the project, etc.
* Do all 3 parts, and use git tags to mark the commit fulfilling part 1 and part 2.

## Deliverables
* Send us a link to a repository fulfilling the requirements with the three tags to check part 1 and 2.
* Your code will be tested using different tests.
* Successful implementation will move to interview.
