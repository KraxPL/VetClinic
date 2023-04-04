Veterinary Clinic Web App

This is a web application for managing a veterinary clinic built with Java, Spring Boot, MySQL, and Hibernate.
Features

    Manage appointments
    Manage patient records
    Manage billing
    Manage inventory

Getting Started
Prerequisites

    Java 17 or higher
    MySQL 8 or higher
    Maven 3.6 or higher

Installation

    Clone the repository:

  bash

git clone https://github.com/KraxPL/VetClinic.git

    Create a MySQL database named VetClinic:

  sql

    CREATE DATABASE VetClinic CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

Configure the database connection in application.yaml:

  bash

    spring.datasource.url=jdbc:mysql://localhost:3306/VetClinic?serverTimezone=UTC
    spring.datasource.username=yourusername
    spring.datasource.password=yourpassword

  Build and run the application:

arduino

    mvn spring-boot:run

Open http://localhost:2020 in your web browser.

Technologies Used

    Java
    Spring Boot
    MySQL
    Hibernate
    Maven
