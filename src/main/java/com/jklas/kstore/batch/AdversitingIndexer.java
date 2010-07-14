package com.jklas.kstore.batch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.PostDeleteEventListener;
import org.hibernate.event.PostInsertEventListener;
import org.hibernate.event.PostUpdateEventListener;

import com.jklas.kstore.entity.advertising.Advertising;
import com.jklas.kstore.entity.site.Site;
import com.jklas.search.exception.SearchEngineMappingException;
import com.jklas.search.index.IndexId;
import com.jklas.search.index.berkeley.BerkeleyGlobalPropertyEditor;
import com.jklas.search.index.berkeley.BerkeleyIndex;
import com.jklas.search.index.berkeley.BerkeleyIndexWriterFactory;
import com.jklas.search.indexer.DefaultIndexerService;
import com.jklas.search.indexer.pipeline.DefaultIndexingPipeline;
import com.jklas.search.interceptors.SearchInterceptor;
import com.jklas.search.interceptors.hibernate.HibernateEventInterceptor;
import com.jklas.search.util.SearchLibrary;

public class AdversitingIndexer {

	public static void main(String[] args) throws SearchEngineMappingException, IOException {
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(
						AdversitingIndexer.class.getClassLoader().getResourceAsStream("ads-scrap.tsv")));
				
		SearchLibrary.configureAndMap(Advertising.class);

		new BerkeleyGlobalPropertyEditor().setBaseDir("/home/julian/workspace/modelsearch-Kstore/berkeley/");
	
		BerkeleyIndex.renewIndex(new IndexId("ADS"));

		System.out.println("Total Objects: "+new BerkeleyIndex(new IndexId("ADS")).getObjectCount());
		
		HibernateEventInterceptor listener= 
			new HibernateEventInterceptor(
					new SearchInterceptor(
							new DefaultIndexerService(
									new DefaultIndexingPipeline(),
									BerkeleyIndexWriterFactory.getInstance())));


		Configuration configuration = new Configuration().configure("/com/jklas/kstore/batch/hibernate-hsqldb-server.cfg.xml");
		configuration.getEventListeners().setPostInsertEventListeners(new PostInsertEventListener[]{listener});
		configuration.getEventListeners().setPostUpdateEventListeners(new PostUpdateEventListener[]{listener});
		configuration.getEventListeners().setPostDeleteEventListeners(new PostDeleteEventListener[]{listener});
		
		SessionFactory sessionFactory = configuration.buildSessionFactory();
		
		Session session = sessionFactory.openSession();		
		session.beginTransaction();
		
		System.out.println("[AD-INDEXER] Starting");
		
		long initTimestamp = System.currentTimeMillis();
		
		String currentLine ="";
		try {
			Site site = new Site("US");
			Object retrieved = session.get(Site.class, "US");
			if(retrieved == null) {		
				session.save(site);
				session.getTransaction().commit();
				session.getTransaction().begin();
			} else {
				site = (Site)retrieved;
			}
			
			int adNumber = 0;
			while(null!=(currentLine = reader.readLine())) {
				adNumber++;
				String[] tokens = currentLine.split("\t");
				
				String adTitle = tokens[0];
				String adSubtitle = tokens[1];
				String linkTitle = tokens[2];
				String link = tokens[3];
				
				Advertising advertising = new Advertising(site, adTitle, adSubtitle, linkTitle, link);
				session.save(advertising);
				
				if(adNumber%100 == 0) {
					session.getTransaction().commit();
					System.out.println("[AD-INDEXER] Already indexed "+adNumber+" ads. Time elapsed: "+(float)(System.currentTimeMillis()-initTimestamp)/1000.0f);
					session.getTransaction().begin();
				}
			}
						
			session.getTransaction().commit();
			System.out.println("[AD-INDEXER] Done. Indexed "+adNumber+" ads. Time elapsed: "+(float)(System.currentTimeMillis()-initTimestamp)/1000.0f);
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Exception on line: "+currentLine);
			session.getTransaction().rollback();
			return;
		} finally {
			session.close();
			reader.close();
		}
	}
	
}
