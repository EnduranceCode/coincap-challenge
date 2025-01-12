# CoinCap Code Challenge

This file documents the process to manage the database used with **CoinCap Code Challenge**.

## Database migration

The [database migrations](https://en.wikipedia.org/wiki/Schema_migration) are made using
[Flyway](https://www.red-gate.com/products/flyway/).
The [DDL](https://en.wikipedia.org/wiki/Data_definition_language) migration scripts are stored
in the folder [`migration/ddl`](./migration/ddl) and the
[DML](https://en.wikipedia.org/wiki/Data_manipulation_language) migration scripts are stored
in the folder [`migration/dml`](./migration/dml).

The [migration scripts](https://documentation.red-gate.com/flyway/quickstart-how-flyway-works/why-database-migrations)
must respect the following naming convention (which is compatible with the
[Flyway naming patterns](https://www.red-gate.com/blog/database-devops/flyway-naming-patterns-matter)):

    Vxxx.yyy.zzz.nnn__free-description.sql

> ***xxx*** : Major version number at the time of the script creation
>
> ***yyy*** : Minor version number at the time of the script creation
>
> ***zzz*** : Patch version number at the time of the script creation
>
> ***nnn*** : Order number for version number at the time of the script creation
>
> ***free_description*** : A short free description of the scripts actions with words separated with dashes 

## Migration scripts

1. [V000.000.001.001__create-tables.sql](./migration/ddl/V000.000.001.001__create-tables.sql) : Creates the tables
   for the CoinCap Challenge application;
2. [V000.000.001.002__create-indexes.sql](./migration/dml/V000.000.001.002__insert-data.sql) : Insert data
   into the CoinCap Challenge application tables.
