package com.deu.wiki;

public class Track {
	private String listImage; //�뷡 ���� â ǥ�� �̹���
	private String listMusic; //�뷡 ���� â ����
	
	public Track(String listMusic) {
		this.listMusic = listMusic;
	}
	public Track(String listImage, String listMusic) {
		this.listImage = listImage;
		this.listMusic = listMusic;
	}
	public String getListImage() {
		return listImage;
	}
	public void setListImage(String listImage) {
		this.listImage = listImage;
	}
	public String getListMusic() {
		return listMusic;
	}
	public void setListMusic(String listMusic) {
		this.listMusic = listMusic;
	}
}