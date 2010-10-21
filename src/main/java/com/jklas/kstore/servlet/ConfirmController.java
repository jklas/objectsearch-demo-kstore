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
package com.jklas.kstore.servlet;

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
import com.jklas.kstore.entity.item.Item;

public class CheckoutController implements Controller {

	private SessionFactory sessionFactory;
	
	private Pattern semicolonPattern = Pattern.compile(";");
	
	public CheckoutController(HibernateHelper helper) {
		sessionFactory = helper.getSessionFactory();
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    	
    	Map<String, Object> model = new HashMap<String,Object>();
    	
    	putCartObjectsVariables(request, model, sessionFactory.getCurrentSession());
    	
        return new ModelAndView("checkoutView","checkout",model);
    }

	private void putCartObjectsVariables(HttpServletRequest request, Map<String, Object> model, Session session) {		
		Cookie[] cookies = request.getCookies();
		
		if(cookies == null) return;

		List<Object> resultList = new ArrayList<Object>();			
		model.put("cartItems", resultList);
		
		try {
			session.beginTransaction();
			
			float totalCharges = 0.0f;
			
			for (Cookie cookie : cookies) {
				if("cart".equals(cookie.getName())) {
					String[] itemIds = semicolonPattern.split(URLDecoder.decode(cookie.getValue(),"UTF-8"));

					for (int i = 0; i < itemIds.length; i++) {
						Long id = Long.parseLong(itemIds[i]);
						
						Object loadedObject = session.get(Item.class, id);
						
						resultList.add(loadedObject);
						
						totalCharges+=((Item)loadedObject).getPrice();
					}
				}
			}
			
			model.put("totalCharges", totalCharges);
		} catch (Exception e) {
			return;
		} finally {
			if(session!=null && session.isOpen()) session.close();
		}
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
