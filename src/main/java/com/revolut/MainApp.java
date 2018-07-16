package com.revolut;

import com.revolut.db.HibernateUtil;
import org.flywaydb.core.Flyway;

import static spark.Spark.afterAfter;

/**
 * Created by kubus on 16/07/2018.
 */
public class MainApp {

    public static void main(String[] args) {
        initDb();
        HibernateUtil.getSession();
        afterAfter((req, res) -> res.type("application/json"));
    }

    private static void initDb() {
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:h2:file:./target/com/revolut", "sa", null);
        flyway.clean();
        flyway.migrate();
    }
}
