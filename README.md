
# Blog Application

A brief description of what this project does and who it's for


### How to set key cloak config for this application

Key cloak will create automatically when application start  on port *8080* by default with username: *admin* and password : *password* with the help docker compose file.

U need to create realm and client and assign it to realm admin

## how to run code formatter

```bash
mvn spotless:check 
mvn spotless:apply 
```



## Authors

- [@mabhisheksingh](https://github.com/mabhisheksingh)


## Installation

Install blogApplication with mvn

```bash
 mvn clean ; mvn compile spring-boot:run
```
    