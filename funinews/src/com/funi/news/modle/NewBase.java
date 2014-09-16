package com.funi.news.modle;

public class NewBase {
	public NewBase(int id, String title, String imageUrl, int review,
			int degree, String intr) {
		super();
		Id = id;
		Title = title;
		ImageUrl = imageUrl;
		Review = review;
		Degree = degree;
		Intr = intr;
	}
	public NewBase() {

	}
	
	private int Id;
	private String Title;
	private String ImageUrl;
	private int Review;
	private int Degree;
	private String Intr;
	/**
	 * @return the id
	 */
	public int getId() {
		return Id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		Id = id;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return Title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		Title = title;
	}
	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return ImageUrl;
	}
	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		ImageUrl = imageUrl;
	}
	/**
	 * @return the review
	 */
	public int getReview() {
		return Review;
	}
	/**
	 * @param review the review to set
	 */
	public void setReview(int review) {
		Review = review;
	}
	/**
	 * @return the degree
	 */
	public int getDegree() {
		return Degree;
	}
	/**
	 * @param degree the degree to set
	 */
	public void setDegree(int degree) {
		Degree = degree;
	}
	/**
	 * @return the intr
	 */
	public String getIntr() {
		return Intr;
	}
	/**
	 * @param intr the intr to set
	 */
	public void setIntr(String intr) {
		Intr = intr;
	}
}
