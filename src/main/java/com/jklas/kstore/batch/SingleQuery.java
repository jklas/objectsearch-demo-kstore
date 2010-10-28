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
