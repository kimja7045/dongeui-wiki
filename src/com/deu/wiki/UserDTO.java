package com.deu.wiki;

public class UserDTO {
	private int idx;
	private String pNum;  // ȸ�� ��ȭ��ȣ
	private int point=0; // ȸ�� ����Ʈ
	
	public UserDTO() {	
		this.idx=0; this.pNum=null; this.point=0;
	}
	public UserDTO(String pNum) {
		this.idx=0; this.pNum=pNum; this.point=0;
	}
	public UserDTO(int idx,String pNum,int point) {
		this.idx=idx;
		this.pNum = pNum;
		this.point = point;
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