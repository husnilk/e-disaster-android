package com.example.pelaporanbencana.model.Login;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("access_token")
	private String accessToken;

	@SerializedName("name")
	private String name;

	@SerializedName("token_type")
	private String tokenType;

	@SerializedName("institution")
	private String institution;

	@SerializedName("id_user")
	private int id_user;

	public int getId() {
		return id_user;
	}

	public void setId(int id_user) {
		this.id_user = id_user;
	}

	public void setAccessToken(String accessToken){
		this.accessToken = accessToken;
	}

	public String getAccessToken(){
		return accessToken;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setTokenType(String tokenType){
		this.tokenType = tokenType;
	}

	public String getTokenType(){
		return tokenType;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}
}