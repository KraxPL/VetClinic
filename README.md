Veterinary Clinic Web App

This is a web application for managing a veterinary clinic built with Java, Spring Boot, MySQL, and Hibernate.
Features

    Manage appointments
    Manage patient records
    Manage owner's records
    Manage veterinarians
    Customers can request an appointment

Getting Started
Prerequisites

    Java 17 or higher
    MySQL 8 or higher
    Maven 3.6 or higher

Installation

Clone the repository:


    git clone https://github.com/KraxPL/VetClinic.git

Create a MySQL database named VetClinic:


    CREATE DATABASE VetClinic CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

Configure the database connection in application.yaml:


    spring.datasource.url=jdbc:mysql://localhost:3306/VetClinic?serverTimezone=UTC
    spring.datasource.username=yourusername
    spring.datasource.password=yourpassword

Build and run the application:


    mvn spring-boot:run

Open http://localhost:2020/index in your web browser.

To log in to the Veterinary Clinic web application, you can go to the following address: http://localhost:2020/login.

To log in as an admin, use the following credentials:

    Email: admin@vetclinic.pl
    Password: password

To log in as a regular user, use the following credentials:

    Email: user@vetclinic.pl
    Password: password

Once you are logged in, you will have access to the features of the web application based on your user role. 
For now Admin has access to user management and creation over the regular user.

If you would like to check how customer may request appointment, open http://localhost:2020/booking/form , select Veterinarian and available hour from plan and create an appointment. 
Then log in as a user or admin and check "appointments" tile. As you can see you can now accept your visit or reject it.


Technologies Used

    Java
    Spring Boot
    Spring MVC
    Spring Data
    Spring Security
    MySQL
    Hibernate
    Maven
    Lombok
    Thymeleaf
    MapStruct
