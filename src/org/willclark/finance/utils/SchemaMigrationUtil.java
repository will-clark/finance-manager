package org.willclark.finance.utils;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.hibernate.cfg.Configuration;
import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import com.googlecode.flyway.core.Flyway;

public class SchemaMigrationUtil {

	public static void updateIfNecessary(EntityManagerFactory emf) {
		Flyway flyway = initFlywayFromPersistence(emf);
		flyway.migrate();
	}

	private static Flyway initFlywayFromPersistence(EntityManagerFactory emf) {		
        Properties properties = new Properties();
        properties.setProperty("flyway.url", ServiceUtil.getPersistenceProperty(emf, "javax.persistence.jdbc.url"));
        properties.setProperty("flyway.user", ServiceUtil.getPersistenceProperty(emf, "javax.persistence.jdbc.user"));
        properties.setProperty("flyway.password",ServiceUtil.getPersistenceProperty(emf, "javax.persistence.jdbc.password"));
        properties.setProperty("flyway.driver", ServiceUtil.getPersistenceProperty(emf, "javax.persistence.jdbc.driver"));
        properties.setProperty("migration.locations", ServiceUtil.getPersistenceProperty(emf, "migration.locations"));
        
        Flyway flyway = new Flyway();
        flyway.configure(properties);
        return flyway;
	}
	
	public static void main(String... args) {
		   Configuration cfg = new Ejb3Configuration().configure("db", null ).getHibernateConfiguration();
		   SchemaExport export = new SchemaExport(cfg);
		   export.setDelimiter(";");
		   export.execute(true, false, false, true);
	}
	
}