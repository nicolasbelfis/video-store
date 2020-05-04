# Video store

## requirements
JDK 1.8
Maven

# Run the server

The class `SpringBootApp.java`

# Functional tests

run `FunctionalTest.java`

# Build
command `mvn clean install` ( optionally with profile `pit` to run
 the test and mutation coverage only in domain module)
 
# Swagger UI
you can play with the app, using swagger : http://localhost:8080/swagger-ui.html

some data are already available as there is no service to create customer or inventory.

check from the view api http://localhost:8080/video-store/customer/{customerId} who is already existing
(id 1 and 2 only)
and from the view api http://localhost:8080/video-store/available-films to see existing films.

you can then create and return rentals using data gathered from the view api

# Todo next
- unit test are missing in infrastructue and application modules
- some edge cases are not covered by the functional tests
- renting and returning same film with same customer is bugged
- compensates actions should be created in order to do a saga that makes the 2 main operations atomic

# Choices
- main motivation is to follow DDD approach by separating the problem in 3 independent aggregates 
(inventory, rental and customer), their logic is in domain modules and is stateless. which means that each
of them should not directly communicate together, they remains consistent independently, but shared information
should be handle outside the domain which remains eventually consistent.
- to organise the code, hexagonal architecture is chosen. Domain and application are the core, they are in plain 
java so don't depend on anything. Application supervise the logic of the domain and provides some interfaces that
are implemented by the infrastructure.
- as each aggregates store his data independently, CQRS approach is used for crossing aggregates data in views.
aggregates store their state in a write-only store, infomation available to the user are in the read only ones.
- an event dispatcher takes care of pubishing events from the application asynchronously. subscribers receive them and 
updates views store.