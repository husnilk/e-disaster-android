package com.example.pelaporanbencana.model.SocAssistDistributedShowAll;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class DataItem{

	@SerializedName("id_sa_types")
	private int id_sa_types;

	@SerializedName("date_sent")
	private Date dateSent;

	@SerializedName("recipient")
	private String recipient;

	@SerializedName("batch")
	private int batch;

	@SerializedName("sa_distributed_amount")
	private int saDistributedAmount;

	@SerializedName("sa_types_name")
	private String saTypesName;

	@SerializedName("sa_distributed_units")
	private String saDistributedUnits;

	public int getId_sa_types() {
		return id_sa_types;
	}

	public void setId_sa_types(int id_sa_types) {
		this.id_sa_types = id_sa_types;
	}

	public Date getDateSent(){
		return dateSent;
	}

	public String getRecipient(){
		return recipient;
	}

	public int getBatch(){
		return batch;
	}

	public int getSaDistributedAmount(){
		return saDistributedAmount;
	}

	public String getSaTypesName(){
		return saTypesName;
	}

	public String getSaDistributedUnits(){
		return saDistributedUnits;
	}
}