# Rental Manager Backend

This project is a Spring Boot application for managing rental properties, landlords, tenants, and contracts.

## Project Structure

```
backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── rentalmanager/
│   │   │           ├── controller/
│   │   │           ├── model/
│   │   │           ├── repository/
│   │   │           ├── service/
│   │   │           └── RentalManagerApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   └── test/
│       └── java/
│           └── com/
│               └── rentalmanager/
│                   ├── controller/
│                   ├── model/
│                   ├── repository/
│                   └── service/
├── build.gradle
└── README.md
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/fooBar`)
3. Commit your changes (`git commit -am 'Add some fooBar'`)
4. Push to the branch (`git push origin feature/fooBar`)
5. Create a new Pull Request

## License

Distributed under the MIT License. See `LICENSE` for more information.

## Contact

Your Name - [your.email@example.com](mailto:your.email@example.com)

Project Link: [https://github.com/mgerhards/rental-manager-backend](https://github.com/mgerhards/rental-manager-backend)

## Database Configuration

This project uses MariaDB as the database. Ensure you have MariaDB installed and running.

### MariaDB Installation

1. Install MariaDB from the [official website](https://mariadb.org/download/).
2. Start the MariaDB service:
    ```sh
    sudo systemctl start mariadb
    ```
3. Secure your MariaDB installation:
    ```sh
    sudo mysql_secure_installation
    ```

### MariaDB Configuration

Update the `application.properties` file with your MariaDB configuration:
```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/yourdatabase
spring.datasource.username=yourusername
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect
```

## Building the Project

This project uses Maven as the build tool. To build the project, use the supplied Maven Wrapper (`mvnw`).

### Building with Maven Wrapper

To build the project, run:
```sh
./mvnw clean install
```

### Running the Application with Maven

To run the application using Maven, use:
```sh
./mvnw spring-boot:run
```

## Additional Resources

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [MariaDB Documentation](https://mariadb.com/kb/en/documentation/)
- [Maven Documentation](https://maven.apache.org/guides/index.html)

## Acknowledgements

- Thanks to the Spring Boot community for their extensive documentation and support.
- Special thanks to the contributors of the MariaDB project.

## Troubleshooting

If you encounter any issues, please check the following:

- Ensure MariaDB is running and accessible.
- Verify your `application.properties` configuration.
- Check the logs for any error messages and stack traces.

For further assistance, feel free to open an issue on the project's GitHub repository.



































































