package com.gc.model.search;

import java.util.ArrayList;
import java.util.List;


public class InstrumentListDto
{
	List<Instrument> list;
	
	public InstrumentListDto()
	{
		list = new ArrayList<>();
	}
	
	public void addInstrumentToList(Instrument instrument)
	{
		this.list.add(instrument);
	}
	
	public List<Instrument> getList(){return list; }
	
	public void setList(List<Instrument> list){ this.list = list; }
}
