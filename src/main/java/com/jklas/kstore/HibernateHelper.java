package com.jklas.kstore;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateHelper {
	
	private static SessionFactory sessionFactory = null;
	
	private String filename ;
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public synchronized SessionFactory getSessionFactory() {		
		if(sessionFactory==null) {
			Configuration configuration = new Configuration().configure(filename);
			sessionFactory = configuration.buildSessionFactory();			
		}
		return sessionFactory;
	}
	
}
