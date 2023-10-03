package com.example.pelaporanbencana.model.PicDisastersShowall;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("id_pictures")
	private String idPictures;

	@SerializedName("id_disasters")
	private String idDisasters;

	@SerializedName("pictures")
	private String pictures;

	public String getIdPictures(){
		return idPictures;
	}

	public String getIdDisasters(){
		return idDisasters;
	}

	public String getPictures(){
		return pictures;
	}
}