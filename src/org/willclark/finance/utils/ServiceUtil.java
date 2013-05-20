package org.willclark.finance.utils;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.DataSources;

public class ServiceUtil {

	private static Logger log = Logger.getLogger(ServiceUtil.class);
	
	protected static Map<String, EntityManagerFactory> factories = new HashMap<String, EntityManagerFactory>(0);
	protected static Map<String, DataSource> datasources = new HashMap<String, DataSource>(0);
	
	static {
		factories.put("db", getEntityManagerFactory("db", localConfiguration()));
		datasources.put("db", getDataSource("db"));
	}
	
	private static EntityManagerFactory getEntityManagerFactory(String persistenceUnit) {
		return getEntityManagerFactory(persistenceUnit, null);
	}
	
	private static EntityManagerFactory getEntityManagerFactory(String persistenceUnit, Map<String, Object> configuration) {
		EntityManagerFactory emf = null;
		
		try {		
			emf = Persistence.createEntityManagerFactory(persistenceUnit, configuration);
		}
		catch (Exception e) {
			SystemUtil.shutdown(e);
		}
		
		if (emf != null) {
			if (!"true".equalsIgnoreCase(ServiceUtil.getPersistenceProperty(emf, "flyway.disable"))) {			
				// automatically run any required db schema migrations
				SchemaMigrationUtil.updateIfNecessary(emf);
			}
		}
		
		return emf;
	}
	
    public static EntityManager getEntityManager() {
    	return factories.get("db").createEntityManager();
    }    
	
    public static EntityManager getEntityManager(String persistenceUnit) {
    	EntityManagerFactory emf = factories.get(persistenceUnit);
    	if (emf == null) {
    		emf = getEntityManagerFactory(persistenceUnit);
    	}
    	return emf.createEntityManager();
    }
    
    public static DataSource getDataSource() {
    	return datasources.get("local");
    }
    
    public static DataSource getDataSource(String persistenceUnit) {
    	DataSource ds = datasources.get(persistenceUnit);
    	if (ds == null) {
	    	EntityManagerFactory emf = getEntityManager(persistenceUnit).getEntityManagerFactory();
	    	try {
	    		ds = DataSources.unpooledDataSource(getPersistenceProperty(emf, "javax.persistence.jdbc.url"), getPersistenceProperty(emf, "javax.persistence.jdbc.user"), getPersistenceProperty(emf, "javax.persistence.jdbc.password"));
		    	datasources.put(persistenceUnit, ds);
	    	}
	    	catch (SQLException e) {
	    		SystemUtil.shutdown(e);
	    	}
    	}
    	return ds;
    }

	public static String getPersistenceProperty(EntityManagerFactory emf, String key) {
		if (emf != null) {
			Map<String, Object> properties = emf.getProperties();
			if (properties != null) {
				return StringUtil.toString(properties.get(key));
			}
		}		
		return null;
	}
		    
 	private static Map<String, Object> localConfiguration() { 		
 		String DATABASE_URL = null;
 		
 		// DATABASE_URL = postgres://user:password@hostname:port/dbname
 		
 		if (System.getenv("DATABASE_URL") != null) {
 			DATABASE_URL = System.getenv("DATABASE_URL");
 		}
 		
		if (System.getProperty("DATABASE_URL") != null)  {
			DATABASE_URL = System.getProperty("DATABASE_URL");
		}

		if (DATABASE_URL == null) throw new RuntimeException("DATABASE_URL property is required before start up can continue...");

		log.info("Found DATABASE_URL="+DATABASE_URL);
		
 		URI databaseUrl = null;
 		
 		try {
 			databaseUrl = new URI(DATABASE_URL);
 		}
 		catch (URISyntaxException e) {
 			throw new RuntimeException("Unable to properly parse DATABASE_URL");
 		}
 		
 		System.out.println(databaseUrl);
 		
 		String url = "jdbc:postgresql://"+databaseUrl.getHost()+":"+databaseUrl.getPort()+databaseUrl.getPath();
 		String user = databaseUrl.getUserInfo().split(":")[0];
 		String password = databaseUrl.getUserInfo().split(":")[1];
 		
 		Map<String, Object> configuration = new HashMap<String, Object>();
 		configuration.put("javax.persistence.jdbc.url", url);
 		configuration.put("javax.persistence.jdbc.user", user);
 		configuration.put("javax.persistence.jdbc.password", password);
 		
 		return configuration;
	}
 	
 	public static void main(String... args) { 		
 		System.out.println("Hello World!");
 	}
 	
}