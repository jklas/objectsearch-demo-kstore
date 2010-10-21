/**
 * Object Search Framework
 *
 * Copyright (C) 2010 Julian Klas
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
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

import com.jklas.kstore.entity.category.Category;
import com.jklas.kstore.entity.item.Item;
import com.jklas.kstore.entity.site.Site;
import com.jklas.search.exception.SearchEngineMappingException;
import com.jklas.search.index.berkeley.BerkeleyGlobalPropertyEditor;
import com.jklas.search.index.berkeley.BerkeleyIndexWriterFactory;
import com.jklas.search.indexer.DefaultIndexerService;
import com.jklas.search.indexer.pipeline.DefaultIndexingPipeline;
import com.jklas.search.interceptors.SearchInterceptor;
import com.jklas.search.interceptors.hibernate.HibernateEventInterceptor;
import com.jklas.search.util.SearchLibrary;

public class ItemIndexer {

	public static void main(String[] args) throws SearchEngineMappingException, IOException {
				
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(
						AdversitingIndexer.class.getClassLoader().getResourceAsStream("shopping-dot-com-scrap.tsv")));
				
		
		SearchLibrary.configureAndMap(Item.class);
		SearchLibrary.configureAndMap(Category.class);
		SearchLibrary.configureAndMap(Site.class);

		new BerkeleyGlobalPropertyEditor().setBaseDir("/home/julian/workspace/kstore/berkeley/");
		
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
		
		System.out.println("[INDEXER] Starting");
		
		long initTimestamp = System.currentTimeMillis();
		
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
			
			String currentLine;
			int itemNumber = 0;
			while(null!=(currentLine = reader.readLine())) {
				itemNumber++;
				String[] tokens = currentLine.split("\t");
				
				String itemTitle = tokens[0];
				String itemDescription = tokens[1];
				long categoryId = Long.parseLong(tokens[2]);
				String categoryName = tokens[3];
				double price = Double.parseDouble(tokens[4]);

				
				Category category = null;				
				retrieved = session.get(Category.class, categoryId);
				if(retrieved == null) {
					category = new Category(site,categoryName);
					category.setId(categoryId);
					session.save(category);
				} else {
					category = (Category)retrieved;
				}
				
				Item item = new Item(site, category, itemTitle, itemDescription, price);
				session.save(item);
				
				if(itemNumber%100 == 0) {
					session.getTransaction().commit();
					System.out.println("[INDEXER] Already indexed "+itemNumber+" items. Time elapsed: "+(float)(System.currentTimeMillis()-initTimestamp)/1000.0f);
					session.getTransaction().begin();
				}
			}
						
			session.getTransaction().commit();
			System.out.println("[INDEXER] Done. Indexed "+itemNumber+" items. Time elapsed: "+(float)(System.currentTimeMillis()-initTimestamp)/1000.0f);
		} catch(Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			session.close();
			reader.close();
		}
	}
	
}
