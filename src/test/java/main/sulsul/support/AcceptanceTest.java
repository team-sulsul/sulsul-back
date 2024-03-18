package main.sulsul.support;


import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {

    private static final String ROOT = "root";
    private static final String ROOT_PASSWORD = "test";

    @Autowired
    private DataInitializer dataInitializer;

    @Container
    protected static MySQLContainer container;

    static {
        container = (MySQLContainer) new MySQLContainer("mysql:8.0")
            .withDatabaseName("sulsul")
            .withEnv("MYSQL_ROOT_PASSWORD", ROOT_PASSWORD);

        container.start();
    }

    @DynamicPropertySource
    static void configureProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", () -> ROOT);
        registry.add("spring.datasource.password", () -> ROOT_PASSWORD);
        registry.add("spring.flyway.url", container::getJdbcUrl);
        registry.add("spring.flyway.user", () -> ROOT);
        registry.add("spring.flyway.password", () -> ROOT_PASSWORD);
    }

    @LocalServerPort
    private int port;

    @BeforeEach
    void delete() {
        dataInitializer.deleteAll();
        RestAssured.port = port;
    }
}
