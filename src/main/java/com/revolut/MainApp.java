package com.revolut;

import com.google.inject.Guice;
import com.google.inject.Stage;
import com.revolut.db.HibernateUtil;
import org.flywaydb.core.Flyway;

import static spark.Spark.afterAfter;

/**
 * Created by kubus on 16/07/2018.
 */
public class MainApp {

    public static void main(String[] args) {
        initDb();
        Guice.createInjector(Stage.PRODUCTION, new MainModule());
        afterAfter((req, res) -> res.type("application/json"));
    }

    private static void initDb() {
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:h2:mem:revolut;DB_CLOSE_DELAY=-1", "sa", null);
        flyway.migrate();
    }
}
