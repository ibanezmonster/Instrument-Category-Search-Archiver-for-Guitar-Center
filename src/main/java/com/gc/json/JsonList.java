package com.gc.json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gc.model.search.Instrument;

public class JsonList
{
	public List<Instrument> getInstrumentSearchList(String jsonFile)
	{
		List<Instrument> instrumentSearchList = new ArrayList<>();
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		try
		{
			Instrument[] instrument = objectMapper.readValue(
					new File(jsonFile),
					Instrument[].class);
			
			instrumentSearchList = 
					objectMapper.readValue(
					//testJson,
					new File(jsonFile),
					new TypeReference<List<Instrument>>() {});
			
			instrumentSearchList = Arrays.asList(instrument);			
		}
		
		catch (JsonParseException e)
		{
			e.printStackTrace();
		}
		
		catch (JsonMappingException e)
		{
			e.printStackTrace();
		}
		
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return instrumentSearchList;
	}
	
	
	
	
	
	
	
	
	
	public static List<Instrument> newInstrumentsOnly(List<Instrument> mostRecentInstrumentList, List<Instrument> secondMostRecentInstrumentList)
	{
		List<Instrument> instrumentList = new ArrayList<>();
		

		for(int i = 0; i < mostRecentInstrumentList.size(); i++)
		{
			if(!secondMostRecentInstrumentList.contains(mostRecentInstrumentList.get(i)))	
				instrumentList.add(mostRecentInstrumentList.get(i));
		}
		
		
		return instrumentList;
	}
}
