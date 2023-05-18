### How to use this spring-boot project

- Install packages with `mvn package`
- Run `mvn spring-boot:run` for starting the application (or use your IDE)

Application (with the embedded H2 database) is ready to be used! You can access the URLs below for testing:

- Swagger UI: [http://localhost:8080/api/v1/swagger-ui.html](http://localhost:8080/api/v1/swagger-ui.html)
- H2 UI: [http://localhost:8080/api/v1/h2-console](http://localhost:8080/api/v1/h2-console)

> Don't forget to set the `JDBC URL` value as `jdbc:h2:mem:testdb` for H2 UI.

### Newly Added Features

- Added caching logic using Spring's caching annotations.
- Changed `getEmployees` method to return paginated results instead of a single list.
- Improved code syntax and readability where possible.
- Separated dtos from entity to avoid exposure of sensitve data.
- Added validations for apis.
- update needs full object because it is a put request. this is also done in validations

### Experience in Java

I have over 7 years of experience in Java development. Throughout my career, I have worked with various Java frameworks and technologies, including Swing, Struts, Spring, and Spring Boot. I have a strong understanding of memory management, JVM internals, and garbage collection. I am well-versed in Java 8, the latest version I have used.
