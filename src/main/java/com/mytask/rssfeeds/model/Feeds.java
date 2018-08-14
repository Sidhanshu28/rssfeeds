package com.mytask.rssfeeds.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;



/**
 * The persistent class for the feeds database table.
 * 
 */
@Entity
@NamedQuery(name="Feeds.findAll", query="SELECT s FROM Feeds s")
public class Feeds implements Serializable {
	private static final long serialVersionUID = 1L;

	/**  feeds table variables */
	
	@Id
	private int feed_id;
	
	@Size(min=3, max=20)
	private String feed_name;
	
	@NotEmpty
	private String feed_url;

	private String last_updated;

	private String feed_title;

	
	/**   items table variables */
	@Id
	private int item_id;
	
	private String item_title;
	
	private String item_link;

	private String item_description;
	
	private String item_published;
	
	private int feedArticleCount;

	public int getFeedId() {
		return this.feed_id;
	}
	
	public void setFeedId(int fid) {
		this.feed_id = fid;
	}
	
	public int getItemId() {
		return this.item_id;
	}
	
	public void setItemId(int iid) {
		this.item_id = iid;
	}
	
	public String getFeedName() {
		return this.feed_name;
	}

	public void setFeedName(String fname) {
		this.feed_name = fname;
	}

	public String getFeedUrl() {
		return this.feed_url;
	}

	public void setFeedUrl(String furl) {
		this.feed_url = furl;
	}

	public String getLastUpdated() {
		return this.last_updated;
	}

	public void setLastUpdated(String lastupdate) {
		this.last_updated = lastupdate;
	}
 
	public String getFeedTitle() {
		return this.feed_title;
	}

	public void setFeedTitle(String ftitle) {
		this.feed_title = ftitle;
	}

	public String getItemTitle() {
		return this.item_title;
	}

	public void setItemTitle(String ititle) {
		this.item_title = ititle;
	}

	public String getItemLink() {
		return this.item_link;
	}

	public void setItemLink(String ilink) {
		this.item_link = ilink;
	}

	public String getItemDescription() {
		return this.item_description;
	}

	public void setItemDescription(String idesc) {
		this.item_description = idesc;
	}

	public String getItemPublished() {
		return this.item_published;
	}

	public void setItemPublished(String publish) {
		this.item_published = publish;
	}
	
	public int getFeedArticleCount() {
		return this.feedArticleCount;
	}
	
	public void setFeedArticleCount(int c) {
		this.feedArticleCount = c;
	}


}