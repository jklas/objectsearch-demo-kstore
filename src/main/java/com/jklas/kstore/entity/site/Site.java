package com.jklas.kstore.entity.site;


public class Site {	
	
	private String siteID;
	
	Site(){}
	
	public Site(String siteID) {
		this.siteID = siteID;
	}

	public void setSiteID(String name) {
		this.siteID = name;
	}

	public String getSiteID() {
		return siteID;
	}
	
	@Override
	public String toString() {	
		return siteID;
	}
}
