package com.jklas.kstore.entity.category;

import com.jklas.kstore.entity.site.Site;
import com.jklas.search.annotations.Indexable;
import com.jklas.search.annotations.SearchField;
import com.jklas.search.annotations.SearchId;

@Indexable
public class Category {
	
	@SearchId
	private long id;
	
	private Site site;
	
	@SearchField
	private String name;
	
	Category() {}	
	
	public Category(Site site, String name) {
		this.site = site;
		this.name = name;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public Site getSite() {
		return site;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setSite(Site site) {
		this.site = site;
	}
	
	@Override
	public String toString() {
		return 	"Site: " + getSite().toString() + "\n" +
				"ID: " + getId() + "\n" +
				"Name: " + getName();
	}
}
