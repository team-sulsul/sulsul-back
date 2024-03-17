package main.sulsul.support;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//@TestComponent
@Component
public class DataInitializer {

    private static final int OFF = 0;
    private static final int ON = 1;
    private static final String FLYWAY = "flyway";
    private static final String TRUNCATE = "TRUNCATE ";

    @PersistenceContext
    private EntityManager em;

    private final List<String> truncationDMLs = new ArrayList<>();

    @BeforeEach
    @Transactional
    public void deleteAll() {
        if (truncationDMLs.isEmpty()) {
            init();
        }

        setForeignKEyEnabled(OFF);
        truncateAllTables();
        setForeignKEyEnabled(ON);
    }

    private void init() {
        final List<String> tableNames = em.createNativeQuery("SHOW TABLES ").getResultList();

        tableNames.stream()
            .filter(name -> !name.contains(FLYWAY))
            .map(TRUNCATE::concat)
            .forEach(truncationDMLs::add);
    }

    private void truncateAllTables() {
        truncationDMLs.stream()
            .map(em::createNativeQuery)
            .forEach(Query::executeUpdate);
    }

    private void setForeignKEyEnabled(final int enabled) {
        em.createNativeQuery("SET foreign_key_checks = " + enabled).executeUpdate();
    }
}
