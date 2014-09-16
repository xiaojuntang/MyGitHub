package com.funi.news.modle;

public class Category {
	// ���ͱ��
	private int cid;
	// ��������
	private String title;
	// ���ʹ���
	private int sequnce;

	public Category() {
		super();
	}

	public Category(int cid, String title) {
		super();
		this.cid = cid;
		this.title = title;
	}

	public Category(int cid, String title, int sequnce) {
		super();
		this.cid = cid;
		this.title = title;
		this.sequnce = sequnce;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getSequnce() {
		return sequnce;
	}

	public void setSequnce(int sequnce) {
		this.sequnce = sequnce;
	}

	@Override
	public String toString() {
		return title;
	}
}
