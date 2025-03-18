# Virtual Store Project

This is an API project for an online store system. The API manages the product catalog and customer orders. The goal is to create a robust solution capable of handling a large volume of data while ensuring performance, security, and best development practices, built with Spring Boot and MySQL.

## Installation and Configuration

### Prerequisites

- Java 17 or later  
- Maven 3.x  
- MySQL 8.x  
- IDE of your choice (recommended: IntelliJ IDEA or Eclipse)  

### Installation Steps

1. **Clone the repository**:  
   ```bash
   git clone https://github.com/emanuelconte/loja-virtual.git
   cd loja-virtual
   ```

### Database Configuration

1. Manually create a database named `lojavirtual`.

2. Configure the database credentials in the `application.properties` file:
   ```bash
   spring.datasource.url=jdbc:mysql://localhost:3306/lojavirtual
   spring.datasource.username=root
   spring.datasource.password=admin
   ```

### Migrations

This project uses Flyway to manage database migrations. The migration scripts are located in `src/main/resources/db/migration`.

- **Applied Migrations**: Flyway tracks applied migrations in the `flyway_schema_history` table.  
- **Database Recreation**: If you recreate the database, Flyway will reapply all migrations.

### Initial Data

Initial data is inserted through the migration file `V2__populate_data.sql`. Ensure this migration is configured correctly.

## Running the Project

### Run the project using Maven:
```bash
mvn spring-boot:run
```

## API Usage

### API Documentation

The API documentation was generated using Springdoc OpenAPI, which is the most modern and compatible library for Spring Boot 3.x.

### Accessing the Documentation

To access the documentation in Swagger, open your browser while the project is running and go to:

[http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

### Testing with Postman

To facilitate API testing, you can import the Postman collection available at the following link:

[https://documenter.getpostman.com/view/9651766/2sAYXCkK6g](https://documenter.getpostman.com/view/9651766/2sAYXCkK6g)

## Unit Tests

Unit tests have been implemented to ensure the quality and correct functionality of the API. The tests were developed using JUnit and Mockito, following best software development practices.

### Running Tests

To run all unit tests, use the following command:

```bash
mvn test
```

## Contribution

If you want to contribute to this project, feel free to open issues or submit pull requests. All contributions are welcome!  

1. Fork the repository.  
2. Create a branch with the feature (`git checkout -b feature/your-feature-name`).  
3. Implement your feature and commit the changes.  
4. Submit a pull request for review.  

## License

This project is licensed under the MIT License. See the `LICENSE` file for more details.  

## Contact

For questions or suggestions, feel free to reach out:  

- **Name**: Emanuel  
- **Email**: [emanuelcontecardoso@gmail.com](mailto:emanuelcontecardoso@gmail.com)  
- **GitHub**: [emanuelconte](https://github.com/emanuelconte)  
