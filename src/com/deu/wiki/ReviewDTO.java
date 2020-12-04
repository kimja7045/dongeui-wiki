package com.deu.wiki;

public class ReviewDTO {
	private int idx;
	private String pNum;  // ´ñ±Û ³»¿ë
	private int point=0; // ´ñ±Û Á¡¼ö
	private String ID;
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public ReviewDTO() {
		this.idx=0; this.pNum=null; this.point=0;
	}
	public ReviewDTO(String pNum) {
		this.idx=0; this.pNum=pNum; this.point=0;
	}
	public ReviewDTO(String pNum, int point, String ID) {
		this.pNum = pNum;
		this.point = point;
		this.ID = ID;
	}
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getpNum() {
		if(pNum == "") {
			pNum = null;
		}
		return pNum;
	}

	public void setpNum(String pNum) {
		this.pNum = pNum;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}


}