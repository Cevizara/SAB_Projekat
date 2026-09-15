# Movie Recommendation System

A university project for the **Database Software Tools** course at the University of Belgrade School of Electrical Engineering (2024/2025).

The project implements a Java/JDBC backend for managing a movie database in Microsoft SQL Server. It follows the provided SAB operation interfaces and includes a test entry point for validating the implementation.

## Features

- Management of users, movies, genres, tags, and ratings
- Personal movie watchlists
- Movie recommendations based on favorite genres and rating statistics
- User reward tracking and profile classification
- Relational database schema with many-to-many movie relationships

## Technologies

- Java
- JDBC
- Microsoft SQL Server
- JUnit and the SAB test framework

## Running the project

1. Create the database tables using `BAZA.sql`.
2. Replace the example SQL Server connection settings in `DB.java` with your local configuration.
3. Add the required SAB interfaces, test library, JUnit, and Microsoft JDBC driver to the classpath.
4. Run `StudentMain.java` to execute the provided test suite.
