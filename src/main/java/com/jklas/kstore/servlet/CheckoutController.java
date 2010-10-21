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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class ConfirmController implements Controller {
 Pattern semicolonPattern = Pattern.compile(";");
	
	public ConfirmController() {
	}
	
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    	
    	String captchaInSession = (String)request.getSession().getAttribute("captcha");
    	String captchaInPost= (String)request.getParameter("answer");
    	   	
    	if(captchaInPost == null || captchaInSession == null) {
    		return new ModelAndView("badCaptcha");
    	}
    	
		boolean captchaPassed = captchaInPost.equals(captchaInSession);
		
		if(captchaPassed){
			Map<String, Object> model = new HashMap<String,Object>();
			
			doCreditCardFake(request, model);
			
    		return new ModelAndView("confirmView","checkout",model);
		} else{
			return new ModelAndView("badCaptcha");
		}	
    }

	private void doCreditCardFake(HttpServletRequest request, Map<String, Object> model) {
		
	}
}
