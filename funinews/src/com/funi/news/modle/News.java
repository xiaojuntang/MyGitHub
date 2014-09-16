package com.funi.news.modle;

import java.sql.Date;

public class News {

	private int NewsId;
	private String NewsTitle;
	private String NewsAuth;
	public int getNewsId() {
		return NewsId;
	}
	public void setNewsId(int newsId) {
		NewsId = newsId;
	}
	public String getNewsTitle() {
		return NewsTitle;
	}
	public void setNewsTitle(String newsTitle) {
		NewsTitle = newsTitle;
	}
	public String getNewsAuth() {
		return NewsAuth;
	}
	public void setNewsAuth(String newsAuth) {
		NewsAuth = newsAuth;
	}
	public Date getNewsDate() {
		return NewsDate;
	}
	public void setNewsDate(Date newsDate) {
		NewsDate = newsDate;
	}
	public String getImagePath() {
		return ImagePath;
	}
	public void setImagePath(String imagePath) {
		ImagePath = imagePath;
	}
	private Date NewsDate;
	private String ImagePath;
	public News() {
		super();
	}
	
}
