# Spring Server

Simple Java REST API interface using [Spring](https://spring.io/).

- Spring REST server
- Compiles & runs under Java 15
- Built with Maven

## Setup

To run the server, from the root directory `spring-server`:

```
mvn clean install
mvn exec:java -Dexec.mainClass=com.jamesmoreton.ServerMainline
```

...or run from your favourite IDE.

## cURL examples

#### Create user

```
curl -X POST 'http://localhost:8080/api/v1/users' -H 'Content-Type: application/json' --data-raw '{
    "userType": "BASIC",
    "dateOfBirth": "2000-01-01",
    "countryCode": "GB"
}'
```

#### Get user

```
curl -X GET 'http://localhost:8080/api/v1/users/a1801623-e287-4523-bfbe-1db0e2d7fcae'
```

#### Get users

```
curl -X GET 'http://localhost:8080/api/v1/users?userType=PREMIUM&countryCode=US'
```

#### Update user

```
curl -X PUT 'http://localhost:8080/api/v1/users/a1801623-e287-4523-bfbe-1db0e2d7fcae' -H 'Content-Type: application/json' --data-raw '{
    "userType": "PREMIUM"
}'
```

#### Delete user

```
curl -X DELETE 'http://localhost:8080/api/v1/users/a1801623-e287-4523-bfbe-1db0e2d7fcae'
```