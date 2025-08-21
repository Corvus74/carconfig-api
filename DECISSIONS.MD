# Decisions
## Build Tool
- Maven vs Gradle --> Gradle is more popular and easier to use. Kotlin vs Groovy --> Groovy is less secure than kotlin

## Databases
- Best choice would be DynamoDB on AWS instead of Postgres for productivity. But NoSQL needs a lot of data in this Scenario SQL with JPA is enough. Better MongoDB but same problem the NOSQL are big Date this example is small.

## Frameworks Java
- Spring Boot or Jakarta EE --> Spring Boot is more popular and easier to use. Jakarta is from JEE but its configuration is more timeintensive.

## Architecture
- Microservices for this configurator its not ideal because the user where to loadbalance the requests. Bottleneck is the Application. Its a simple Database configuration

## Logging DB
- Liqibase could be a good option for this. but logging is to much for this purpose

## CI/CD
- Github Actions is a good option for this.

## Testing
- JUnit5 is a good option for this.