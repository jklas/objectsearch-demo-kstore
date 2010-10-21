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


public class SingleQuery {
//	public static void main(String[] args) {
//		Configuration configuration = new Configuration().configure("/com/jklas/kstore/hibernate-hsqldb-server.cfg.xml");
//		SessionFactory sessionFactory = configuration.buildSessionFactory();
//		Session session = sessionFactory.openSession();
//		session.beginTransaction();
//		
//		VectorQuery query = new VectorQueryParser("soccer ball").getQuery();
//
//		BerkeleyIndex.setBaseDir(System.getProperty("user.dir")+"/berkeley/");
//		
//		List<VectorRankedResult> search = new VectorSearch(query, BerkeleyIndexReaderFactory.getInstance()).search(new VectorRanker());
//		
//		for (VectorRankedResult vectorRankedResult : search) {
//			Serializable id = vectorRankedResult.getKey().getId();
//			Object obj = session.load(Item.class, id);
//			
//			try {
//				if(obj == null || obj.getClass().equals(Item.class))
//					System.out.println("Result: "+id + " - Score: " + vectorRankedResult.getScore());
//				else {
//					System.out.println("("+id + ") "+ ((Item)obj).getTitle() +" - Score: " + vectorRankedResult.getScore());
//				}				
//			} catch(Exception e) {e.printStackTrace();}
//		}
//		
//	}
}
