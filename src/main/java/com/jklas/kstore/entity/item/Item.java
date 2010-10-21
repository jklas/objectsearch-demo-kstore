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
package com.jklas.kstore.entity.item;

import com.jklas.kstore.entity.category.Category;
import com.jklas.kstore.entity.site.Site;
import com.jklas.search.annotations.Indexable;
import com.jklas.search.annotations.SearchField;
import com.jklas.search.annotations.SearchId;
import com.jklas.search.annotations.Stemming;
import com.jklas.search.engine.stemming.IdentityStemmerStrategy;
import com.jklas.search.engine.stemming.StemType;

@Indexable
public class Item {

	@SearchId
	private long id;

	private Site site;
	
	private double price;
	
	@SearchField
	@Stemming(strategy=IdentityStemmerStrategy.class,stemType=StemType.FULL_STEM)
	private String title;
	
	private String description;
	
	private Category category;
	
	// Hibernate
	Item() {}
	
	public Item(Site site, Category category, String title, String description, double price) {
		setSite(site);
		setCategory(category);
		setTitle(title);
		setDescription(description);
		setPrice(price);
	}
	
	void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Category getCategory() {
		return category;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public Site getSite() {
		return site;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getPrice() {
		return price;
	}	
	
	@Override
	public String toString() {
		return "Item ID: "+ getId() + "\n" +
		"Title: "+ getTitle()+ "\n" +
		( getDescription().length()>64 ?
				"Description: '"+ getDescription().substring(0, 64) + "..'\n" :
				"Description: '"+ getDescription() + "'\n");
	}
}
