# CoinCap Code Challenge

![Custom Badge](https://img.shields.io/badge/Spring_Boot-3.4.1-green)
![Custom Badge](https://img.shields.io/badge/java-17-orange)
![Custom Badge](https://img.shields.io/badge/database-MySQL-blue)


## Table Of Contents

1. [Introduction](#introduction)
2. [Prerequisites](#prerequisites)
3. [Development](#development)
   1. [Technologies](#technologies)
   2. [Entity Relationship Diagram](#entity-relationship-diagram)
   3. [Installation](#installation)
4. [Usage](#usage)
   1. [Automatic Token Price Updates](#automatic-token-price-updates)
   2. [Swagger UI](#swagger-ui)
5. [License](#license)

## Introduction

**CoinCap Code Challenge** is a project developed to address a coding challenge
that requires the use of [Java 17](https://javaalmanac.io/jdk/17/),
[Spring Boot 3](https://spring.io/projects/spring-boot), an [SQL](https://en.wikipedia.org/wiki/SQL) database,
and data sourced from [CoinCap API](https://docs.coincap.io/).

## Prerequisites

- [Java 17](https://javaalmanac.io/jdk/17/)
- [MySQL](https://www.mysql.com/)

## Development

### Technologies

**CoinCap Code Challenge** is a [Spring Boot](https://spring.io/projects/spring-boot)
application and uses a [MySQL](https://www.mysql.com/) database.

### Entity Relationship Diagram

The following image shows the Entity Relationship Diagram for the **CoinCap Code Challenge** application.

![Database ER Diagram](src/main/resources/db/entity-model.png)

### Installation

#### Clone the repository

Make sure that you have the prerequisites installed on your development machine and then clone this repository with the following command:

```sh
git clone git@github.com:EnduranceCode/coincap-challenge.git
```
#### Set up the database

##### Create a MySQL database

Login into the [MySQL](https://www.mysql.com/) server, replace the ***{LABEL}*** in the below command as appropriate
and then execute it to [create](https://www.mysqltutorial.org/mysql-create-database/) the **CoinCap Code Challenge**
database.

```sh
CREATE DATABASE {DATABASE_NAME} CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
```
> **Label Definition**
>
> + **{DATABASE_NAME}** : The name chosen for the new database;

##### Create a MySQL user

To [create a user](https://www.mysqltutorial.org/mysql-create-user.aspx) for the **CoinCap Code Challenge** database
management, replace the ***{LABELS}*** in the below command as appropriate and then execute it.

```sh
CREATE USER '{USERNAME}'@'localhost' IDENTIFIED WITH caching_sha2_password BY '{PASSWORD}';
```

> **Labels Definition**
>
> + **{USERNAME}** : The new account name in the MySQL Server;
> + **{PASSWORD}** : The password of the new account in the MySQL Server.

##### Grant privileges to the CoinCap Code Challenge MySQL user

To [grant the necessary privileges](https://www.mysqltutorial.org/mysql-grant.aspx) to the **CoinCap Code Challenge**
database user, replace the ***{LABELS}*** in the below command as appropriate and then execute it.

```sh
GRANT ALL PRIVILEGES ON {DATABASE_NAME}.* TO '{USERNAME}'@'localhost';
```

> **Labels Definition**
>
> + **{DATABASE_NAME}** : The database where the MySQL user will be granted privileges;
> + **{USERNAME}** : The account name in the MySQL Server to whom the privileges will be assigned.

##### Configure the application's database access

To configure the **CoinCap Code Challenge**  access to the database, take the following
steps:

1. Copy the file [`application-secure-TEMPLATE.yml`](./src/main/resources/application-secure-TEMPLATE.yml),
   located in the `resources` folder and rename it to `application-secure.yaml`;
2. Replace the {DATABASE_NAME} placeholder with the **CoinCap Code Challenge** database name;
3. Replace the {DATABASE_USER} placeholder with the **CoinCap Code Challenge** database user's username;
4. Replace the {DATABASE_NAME} placeholder with the **CoinCap Code Challenge**database user's password;
5. Delete the comments block, at the top of the file, that contains these instructions;
6. Add the comment 'DO NOT COMMIT THIS FILE!' to the first line of the file.

##### Manage the application's database migrations

The file [`DATABASE.md`](./src/main/resources/db/DATABASE.md), stored in the folder
[`src/main/resources/db/`](./src/main/resources/db), documents the process to manage the **CoinCap Code Challenge**
database migrations.

## Usage

To run the **CoinCap Code Challenge** application, execute the following command:

```sh
./mvnw spring-boot:run
```

The application will start and be accessible at the following URL:

> http://localhost:8083

As it is out of scope, the **CoinCap Code Challenge** application doesn't provide any CRUD operations
for the User resource.

The **CoinCap Code Challenge** application exposes the following endpoints:

+ **GET** `/v1/users/{userId}/wallets/{walletId}` : Retrieves the wallet information for the given user with the
  specified wallet identifier;
+ **POST** `/v1/users/{userId}/wallets/{walletId}/assets` : Inserts a new asset into the user's wallet;
+ **PUT** `/v1/users/{userId}/wallets/{walletId}/assets/{symbol}` : Increments the quantity of a specified asset
  in the user's wallet;
+ **POST** `/v1//v1/wallet-evaluation/` : Evaluates a wallet's performance based on a list of input tokens
  and their average buy values

An authentication mechanism is out of scope for this project, so the **CoinCap Code Challenge** application doesn't
provide such mechanism. Nonetheless, the application validates if the given user owns the given wallet.

The application database is pre-populated with the following data:

+ **User** : A user with the identifier `1` and the nickname `EnduranceCode`;
+ **User** : A user with the identifier `2` and the nickname `EnduranceTrio`;
+ **Wallet** : A wallet with the identifier `1` owned by the user with the identifier `1`.
+ **Wallet** : A wallet with the identifier `2` owned by the user with the identifier `2`.
+ **Asset** : Two assets, one with the symbol `BTC` and the other with the symbol `ETH` belonging to wallet with id `1`.
+ **Asset** : Two assets, one with the symbol `BTC` and the other with the symbol `ETH` belonging to wallet with id `2`.

### Automatic Token Price Updates

The **CoinCap Code Challenge** application includes a `@Scheduled` job that automatically updates token prices
at regular intervals. This job fetches the latest prices from the [CoinCap API](https://docs.coincap.io/)
and updates the database to reflect the current market value.

The update frequency is configurable via the `application-secure.yaml` file
located at [`src/main/resources/application.yml`](src/main/resources/application.yml). The configuration key
is `app.scheduler.token.update-interval`, and the value specifies the interval in milliseconds between updates.

The following YAML snippet shows the default configuration that has an update interval of 30 minutes:

```yaml
app:
  scheduler:
    token:
      update-interval: 1800000
```

### Swagger UI

The **CoinCap Code Challenge** application provides a Swagger UI for API documentation and testing. It's available
at the following URL:

> http://localhost:8083/swagger-ui.html

## License

**CoinCap Code Challenge** is licensed under the terms of [MIT License](./LICENSE).
