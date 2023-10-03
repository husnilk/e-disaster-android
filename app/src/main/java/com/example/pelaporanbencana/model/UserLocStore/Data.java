package com.example.pelaporanbencana.model.UserLocStore;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("date")
	private String date;

	@SerializedName("id_disaster")
	private String idDisaster;

	@SerializedName("user_lat")
	private String userLat;

	@SerializedName("user_long")
	private String userLong;

	@SerializedName("location")
	private String location;

	@SerializedName("id_user")
	private int idUser;

	public String getDate(){
		return date;
	}

	public String getIdDisaster(){
		return idDisaster;
	}

	public String getUserLat(){
		return userLat;
	}

	public String getUserLong(){
		return userLong;
	}

	public String getLocation(){
		return location;
	}

	public int getIdUser(){
		return idUser;
	}
}