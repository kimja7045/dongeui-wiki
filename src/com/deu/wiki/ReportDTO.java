package com.deu.wiki;

public class ReportDTO {
	private String user_id; 
	private String Keyword;
	private String review_num;
	private String coment;
	
	public ReportDTO(String user_id,String Keyword,String coment) {
		this.user_id = user_id;
		this.Keyword = Keyword;
		this.coment = coment;
	}
	
	public ReportDTO(String user_id,String Keyword,String coment, String review_num) {
		this.user_id = user_id;
		this.Keyword = Keyword;
		this.coment = coment;
		this.review_num = review_num;
	}
	
	public String getReview_num() {
		return review_num;
	}

	public void setReview_num(String review_num) {
		this.review_num = review_num;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getKeyword() {
		return Keyword;
	}

	public void setKeyword(String keyword) {
		Keyword = keyword;
	}

	public String getComent() {
		return coment;
	}

	public void setComent(String coment) {
		this.coment = coment;
	}


}