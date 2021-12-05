package config;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerConfig {
	private static final EntityManagerFactory entityManagerFactory;
	
	static {		
		  entityManagerFactory = Persistence.createEntityManagerFactory("chat_application");
		}
	
	public static EntityManagerFactory getInstance() {
		return entityManagerFactory;
	}
	
	}


