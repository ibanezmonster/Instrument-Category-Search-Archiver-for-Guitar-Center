package com.gc.model.search;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;



@JsonPropertyOrder(value = {"model", "link"})
public class Instrument //implements Serializable
{
	private String model;
	private String link;
	private boolean newRelease;
	
	public Instrument() {}
	
	public Instrument(String model, String link) {
		this.model = model;
		this.link = link;
	}
	
	public Instrument(String model, String link, boolean newRelease) {
		this.model = model;
		this.link = link;
		this.newRelease = newRelease;
	}
	
	
	@JsonGetter
	public String getModel(){return model; }
	
	@JsonGetter
	public String getLink(){ return link; }
	
	@JsonSetter("model")
	public void setModel(String model){this.model = model;}
	
	@JsonSetter("link")
	public void setLink(String link){this.link = link;}

	public void setNewRelease(boolean newRelease){ this.newRelease = newRelease; }
	
	public boolean isNewRelease(){ return newRelease; }
	
	@Override
	public String toString()
	{
		return "Model: {" + model + "}, Link: {" + link + "}, New Release: {" + newRelease + "}";
	}
	
	
	
	@Override
	public boolean equals(Object o)
	{
		if(o == this)
			return true;
		if(!(o instanceof Instrument))
			return false;
		Instrument i = (Instrument) o;
		return i.model.equals(model) && i.link.equals(link);
	}
}