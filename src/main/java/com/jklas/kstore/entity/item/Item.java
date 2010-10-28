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
