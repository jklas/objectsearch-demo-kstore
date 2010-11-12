package com.jklas.kstore.servlet;

import java.io.Serializable;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.jklas.kstore.HibernateHelper;
import com.jklas.kstore.entity.advertising.Advertising;
import com.jklas.kstore.entity.item.Item;
import com.jklas.search.engine.VectorSearch;
import com.jklas.search.engine.dto.VectorRankedResult;
import com.jklas.search.engine.score.DefaultVectorRanker;
import com.jklas.search.engine.score.VectorRanker;
import com.jklas.search.index.IndexId;
import com.jklas.search.index.berkeley.BerkeleyIndexReaderFactory;
import com.jklas.search.query.vectorial.VectorQuery;
import com.jklas.search.query.vectorial.VectorQueryParser;

public class SearchController implements Controller {

	private SessionFactory sessionFactory;
	
	private Pattern semicolonPattern = Pattern.compile(";");
		
	public SearchController(HibernateHelper helper) {
		this.sessionFactory = helper.getSessionFactory();
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    	
    	Map<String, Object> model = new HashMap<String,Object>();
    	
    	int page= request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
    	int pageSize = request.getParameter("pageSize") == null ? 10 : Integer.parseInt(request.getParameter("pageSize"));
    	
    	setPagingValues(model, page, pageSize);
    	
    	String searchExpression = request.getParameter("q");
		
    	VectorQuery query = new VectorQueryParser(searchExpression).getQuery();

    	query.setPage(page);
    	query.setPageSize(pageSize+1);
    	
		long init = System.currentTimeMillis();
		VectorSearch vectorSearch = new VectorSearch(query, BerkeleyIndexReaderFactory.getInstance());
		long totalTime = System.currentTimeMillis() - init;
		
		List<VectorRankedResult> results = vectorSearch.search(new DefaultVectorRanker());
		
		
		if(results.size()>pageSize) {
			results.remove(results.size()-1);
			model.put("search.more_pages","true");
		}
		query.setPageSize(pageSize);
		query.setSelectedIndex(IndexId.getDefaultIndexId());

		model.put("search.query", searchExpression);
		
		if(results.size()==0) {
			model.put("results.start", 0);
			model.put("results.end", 0);
		} else {
			model.put("results.start", (query.getPage()-1)*query.getPageSize()+1);		
			model.put("results.end", (query.getPage()-1)*query.getPageSize()+results.size());			
		}
		
		model.put("search.time", ((float)totalTime)/1000);

		Session session = sessionFactory.openSession();
		try {
			putCartObjectsVariables(request, model, session);
			
			List<Object> resultList = new ArrayList<Object>();			
			model.put("results", resultList);
			
			for (VectorRankedResult result : results) {
				Class<?> resultClass = result.getKey().getClazz();
				Serializable resultId = result.getKey().getId();
				Object loadedObject = session.get(resultClass, resultId);
				
				if(loadedObject == null) continue;
				
				if(!loadedObject.getClass().equals(Item.class)) continue;
				
				resultList.add(
							new ResultRow(loadedObject,result.getScore())
						);
			}
			
			getAds(model, searchExpression, session);
		} finally {
			if(session!=null) session.close();
		}
		
		
        return new ModelAndView("searchResultsView","searchResults",model);
    }

	private void putCartObjectsVariables(HttpServletRequest request, Map<String, Object> model, Session session) {		
		Cookie[] cookies = request.getCookies();
		
		if(cookies == null) return;

		List<Object> resultList = new ArrayList<Object>();			
		model.put("cartItemsAtLoad", resultList);
		
		try {
			for (Cookie cookie : cookies) {
				if("cart".equals(cookie.getName())) {
					String[] itemIds = semicolonPattern.split(URLDecoder.decode(cookie.getValue(),"UTF-8"));

					for (int i = 0; i < itemIds.length; i++) {
						Long id = Long.parseLong(itemIds[i]);
						
						Object loadedObject = session.get(Item.class, id);
						
						resultList.add(loadedObject);
					}
				}
			}			
		} catch (Exception e) {
			return;
		}		
	}

	private void getAds(Map<String, Object> model, String searchExpression, Session session) {
    	VectorQuery query = new VectorQueryParser(searchExpression).getQuery();

    	query.setPage(1);
    	query.setPageSize(3);
    	query.setSelectedIndex(new IndexId("ADS"));
    	
		long init = System.currentTimeMillis();
		VectorSearch vectorSearch = new VectorSearch(query, BerkeleyIndexReaderFactory.getInstance());
		long totalTime = System.currentTimeMillis() - init;
		
		model.put("search.ads_time", ((float)totalTime)/1000);
		
		List<VectorRankedResult> results = vectorSearch.search(new DefaultVectorRanker());
				
		List<Object> resultList = new ArrayList<Object>();
		
		for (VectorRankedResult result : results) {
			
			model.put("ads", resultList);
			
			Class<?> resultClass = result.getKey().getClazz();
			Serializable resultId = result.getKey().getId();
			Object loadedObject = session.get(resultClass, resultId);
			
			if(loadedObject == null) continue;
			
			if(!loadedObject.getClass().equals(Advertising.class)) continue;
			
			resultList.add(
						new ResultRow(loadedObject,result.getScore())
					);
		}
	}

	private void setPagingValues(Map<String, Object> model, int page, int pageSize) {
		model.put("search.next_page",String.valueOf(page+1));
    	model.put("search.page_size",String.valueOf(pageSize));
    	model.put("search.current_page",String.valueOf(page));
	}
    
	public class ResultRow {
		public final Object result;
		public final Double score;
		
		public ResultRow(Object result, Double score) {
			this.result = result;
			this.score = score;
		}
		
		public Object getResult() {
			return result;
		}
		
		public Double getScore() {
			return score;
		}
	}	
}
