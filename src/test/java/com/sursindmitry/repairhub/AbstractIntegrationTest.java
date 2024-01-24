package com.sursindmitry.repairhub;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbstractIntegrationTest {
  private static final PostgreSQLContainer<?> POSTGRES_SQL_CONTAINER =
      new PostgreSQLContainer<>("postgres:alpine")
          .withExposedPorts(5432)
          .withPassword("root")
          .withUsername("root");

  @DynamicPropertySource
  static void postgresProperties(DynamicPropertyRegistry registry) {
    Startables.deepStart(POSTGRES_SQL_CONTAINER).join();

    registry.add("spring.datasource.url", POSTGRES_SQL_CONTAINER::getJdbcUrl);
    registry.add("spring.datasource.username", POSTGRES_SQL_CONTAINER::getUsername);
    registry.add("spring.datasource.password", POSTGRES_SQL_CONTAINER::getPassword);
  }
}
