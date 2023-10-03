package com.example.pelaporanbencana.model.SocAssistDistributed;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("id_sa_types")
	private int idSaTypes;

	@SerializedName("date_sent")
	private Object dateSent;

	@SerializedName("recipient")
	private String recipient;

	@SerializedName("sa_distributed_amount")
	private int saDistributedAmount;

	@SerializedName("batch")
	private int batch;

	@SerializedName("id_disasters")
	private String idDisasters;

	@SerializedName("sa_distributed_units")
	private String saDistributedUnits;

	public int getIdSaTypes(){
		return idSaTypes;
	}

	public Object getDateSent(){
		return dateSent;
	}

	public String getRecipient(){
		return recipient;
	}

	public int getSaDistributedAmount(){
		return saDistributedAmount;
	}

	public int getBatch(){
		return batch;
	}

	public String getIdDisasters(){
		return idDisasters;
	}

	public String getSaDistributedUnits(){
		return saDistributedUnits;
	}
}