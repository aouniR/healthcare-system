
# Medical Records Management Application

## Table of Contents
- [Project Overview](#project-overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Microservices Architecture](#microservices-architecture)
- [Event-Driven Design](#event-driven-design)
- [Installation & Setup](#installation--setup)
- [Contact](#contact)

## Project Overview
The Medical Records Management Application is a scalable and flexible platform designed to help healthcare professionals manage patient records efficiently. Built on a **microservices architecture**, it integrates various hospital services and provides a secure, modern solution for managing medical data.

## Features
- **Medical Record Meta-Model Configuration**: Administrators can configure models tailored to the needs of their medical institutions.
- **Clinical Service Integration**: Seamlessly integrates with services like reception, pharmacy, and more for efficient medical record updates.
- **Asynchronous Communication**: Services communicate asynchronously using **Apache Kafka** for better scalability and fault tolerance.
- **User-Friendly Interfaces**: Intuitive UI for managing medical records and administrative tasks.
- **High Data Security**: Compliant with healthcare data security standards, ensuring confidentiality and integrity of patient data.

## Technologies Used
- **Backend**:
  - [Spring Boot](https://spring.io/projects/spring-boot)
  - [Apache Kafka](https://kafka.apache.org/)
  - [PostgreSQL](https://www.postgresql.org/)
  - [MongoDB](https://www.mongodb.com/)
  - [Docker](https://www.docker.com/) 
  
- **Frontend**:
  - [Angular](https://angular.io/)
  
## Microservices Architecture
The application is composed of multiple microservices, each with specific responsibilities:
1. **Authentication Service**: Provides authentication functionality.
2. **User Service**: Manages users (CRUD operations for administrators, healthcare staff).
3. **MetaModel Service**: Allows for the configuration of medical record meta-models.
4. **MedicalRecord Service**: Handles CRUD operations for patient medical records (medical-records, medical-procedures and monitoring).
5. **Notification Service**: Manages notifications for medical record updates and system events.
- This architecture implements _**Spring Cloud Native Eureka Discovery Server**_ for service registration and discovery, as well as _**Config Server**_ for centralized configuration management of all microservices.

Each service has its own isolated database, ensuring loose coupling and scalability.

## Event-Driven Design
- **Event-Driven Architecture (EDA)**: Services publish and consume events using **Apache Kafka**. This design allows the system to react to events asynchronously and maintain high availability.

## Installation & Setup

### Prerequisites
- **Docker Desktop** must be installed (includes Docker and Docker Compose).

### Steps to Run the Application

1. **Clone the Repository**:
   ```bash
   git clone https://gitlab.com/healthcare-system/healthcare-system-with-spring-boot-microservices.git
   cd healthcare-system-with-spring-boot-microservices
   ```

2. **Run the Backend Setup**:
   - The stable, final version of the application is in the staging branch (*The main branch is for development purposes*):
     ```bash
     git checkout staging
     ```
   - Add this .env file:
     ```bash
     # Database passwords
      USER_DB_PASSWORD=E8C7D%j9@#G5Z!2u8q&1E8kLx9m^Wb7S
      METAMODELE_DB_PASSWORD=E8C7D%j9@#G5Z!2u8q&1E8kLx9m^Wb7S
      NOTIFICATION_DB_PASSWORD=E8C7D%j9@#G5Z!2u8q&1E8kLx9m^Wb7S
      MEDICAL_RECORD_DB_PASSWORD=1234
      # MongoDB Environment
      MONGO_INITDB_ROOT_USERNAME=root
      MONGO_INITDB_ROOT_PASSWORD=1234
      MONGO_EXPRESS_USERNAME=medicalrecord-user
      MONGO_EXPRESS_PASSWORD=1234
     ```
   - Start the services, databases, Kafka brocker and Kafka-UI:
     ```bash
      # In Local Environment
      ./start-services-local.sh

      # In Containerized Environment
      ./start-services.sh
     ```

3. **Frontend Setup**:
   - Navigate to the frontend directory:
     ```bash
      cd healthCareSys-UI
     ```
   - Install dependencies and start the Angular app:
     ```bash
      npm install
      ng serve
     ```

4. **Access the Application**:
   - The frontend can be accessed at [healthCareSys-UI](http://localhost:4200/).
   - Backend endpoints are available through [Postman](https://app.getpostman.com/join-team?invite_code=5380bde7adbd64f509607b4f0ff2d05a&target_code=5d9aec0e2a8a4567a57840e2e2db92ac).
   - Real-time monitoring of all services is accessible at [Eureka Server](http://localhost:8761/).
   - The Kafka UI for managing Kafka topics and messages can be accessed at [Kafka UI](http://localhost:9090/).


## Contact

- **Developer**: Radhouan Aouni 
- **Email**: [aouni.radhouan@outlook.com](mailto:aouni.radhouan@outlook.com)
