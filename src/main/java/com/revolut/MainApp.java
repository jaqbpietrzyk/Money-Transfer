package com.revolut;

import org.flywaydb.core.Flyway;

/**
 * Created by kubus on 16/07/2018.
 */
public class MainApp {

    public static void main(String[] args) {
        initDb();
    }

    private static void initDb() {
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:h2:file:./target/com/revolut", "sa", null);
        flyway.clean();
        flyway.migrate();
    }
}
