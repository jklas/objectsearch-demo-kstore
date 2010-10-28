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
