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
