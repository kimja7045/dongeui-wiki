package com.deu.wiki;


public class AdminDTO {
    // Getter/Setter
    private int idx;
    private String nickname;
    private String id;
    private String password;
    private String type;
    
    public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public AdminDTO() {}
    public AdminDTO(int idx) {this.idx=idx;}
    public AdminDTO(String id, String password) {
        this.id = id;
        this.password = password;
    }
    public AdminDTO(int idx, String nickname, String id, String password) {
        this.idx = idx;
        this.nickname = nickname;
        this.id = id;
        this.password = password;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getNickname() {
        if(nickname == "") {
            nickname = null;
        }
        return nickname;
    }

    public void setnickname(String nickname) {
        this.nickname = nickname;
    }

    public String getId() {
        if(id == "") {
            id = null;
        }
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        if(password == "") {
            password = null;
        }
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}