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

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CaptchaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;


	protected void processRequest(HttpServletRequest request, 
			HttpServletResponse response) 
	throws ServletException, IOException {

		int width = 150;
		int height = 50;

		char data[] = {
				 'a', 'f', 'k', 'p', 't', 'x', '1', 'W', 
				 'b', 'g', 'l', 'q', 'u', '3', '4', 'E' ,
				 'c', 'h', 'm', 'r', 'v', 'y', '2', 'R', 
				 'd', 'i', 'n', 's', 'w', 'z', '@', 'T',
				 'e', 'j', 'o', '5', '6', '7', '8', '9' };


		BufferedImage bufferedImage = new BufferedImage(width, height, 
				BufferedImage.TYPE_INT_RGB);

		Graphics2D g2d = bufferedImage.createGraphics();

		Font font = new Font("Georgia", Font.BOLD, 18);
		g2d.setFont(font);

		RenderingHints rh = new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		rh.put(RenderingHints.KEY_RENDERING, 
				RenderingHints.VALUE_RENDER_QUALITY);

		g2d.setRenderingHints(rh);

		GradientPaint gp = new GradientPaint(0, 0, 
				Color.white, 0, height/2, Color.orange, true);

		g2d.setPaint(gp);
		g2d.fillRect(0, 0, width, height);

		g2d.setColor(new Color(255, 153, 0));

		Random r = new Random();

		String captcha = selectText(data);
		
		request.getSession().setAttribute("captcha", captcha );

		int x = 0; 
		int y = 0;

		for (int i=0; i< captcha.length(); i++) {
			x += 10 + (Math.abs(r.nextInt()) % 15);
			y = 20 + Math.abs(r.nextInt()) % 20;
			g2d.drawChars(captcha.toCharArray(), i, 1, x, y);
		}

		g2d.dispose();

		response.setContentType("image/png");
		OutputStream os = response.getOutputStream();
		ImageIO.write(bufferedImage, "png", os);
		os.close();
	} 


	private String selectText(char[] data) {
		StringBuffer buffer = new StringBuffer();
		int size= (int) (Math.random()*3+5);
		
		for (int i = 0; i < size; i++) {
			int r = (int) Math.floor(Math.random() * data.length);
			buffer.append(data[r]);
		}
		return buffer.toString();
	}


	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response)
	throws ServletException, IOException {
		processRequest(request, response);
	} 


	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response)
	throws ServletException, IOException {
		processRequest(request, response);
	}
}
