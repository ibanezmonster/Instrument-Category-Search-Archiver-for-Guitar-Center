package com.gc.model.search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.gc.file.FileFinder;
import com.gc.file.FileSearchType;
import com.gc.json.JsonList;
import com.gc.model.selection.InstrumentSelection;

public class InstrumentSearch
{		
	InstrumentSelection instrumentSelection;
	
	
	public InstrumentSearch(InstrumentSelection instrumentSelection)
	{
		this.instrumentSelection = instrumentSelection;
	}
	

	public InstrumentListDto getList()
	{
		InstrumentListDto li = new InstrumentListDto();
		boolean moreThanOneJsonFile = true;
		boolean noJsonFiles = false;
				
		//Get file path- get most recent
		String mostRecentJsonFile = FileFinder.getPath(instrumentSelection, FileSearchType.MOST_RECENT_DATA);
		System.out.println("------------------------------------------------------------------");
		System.out.println(mostRecentJsonFile);
		
		//get second most recent file
		String secondMostRecentJsonFile = ""; 
		secondMostRecentJsonFile = FileFinder.getPath(instrumentSelection, FileSearchType.SECOND_MOST_RECENT_DATA);
		System.out.println("------------------------------------------------------------------");
		System.out.println(secondMostRecentJsonFile);
		
		
		if(secondMostRecentJsonFile.isEmpty())
		{
			moreThanOneJsonFile = false;
			
			if(mostRecentJsonFile.isEmpty())
			{
				noJsonFiles = true;
			}
		}
		
		//difference if there is one vs. if there are > 1 files
		if(moreThanOneJsonFile && noJsonFiles == false)
		{
			//deserialize JSON to arraylist of instruments (most recent)
			JsonList jsonParser = new JsonList();
			List<Instrument> mostRecentInstrumentList = new ArrayList<>(); 
			mostRecentInstrumentList = jsonParser.getInstrumentSearchList(mostRecentJsonFile);
			
			//deserialize JSON to arraylist of instruments (second most recent)
			List<Instrument> secondMostRecentInstrumentList = new ArrayList<>();
			secondMostRecentInstrumentList = jsonParser.getInstrumentSearchList(secondMostRecentJsonFile);
									
			//compare both arraylists- get the difference to get a new (third) arraylist of new instruments			
			List<Instrument> onlyNewInstrumentList = new ArrayList<>();
			onlyNewInstrumentList = JsonList.newInstrumentsOnly(mostRecentInstrumentList, secondMostRecentInstrumentList);		
						
			//set all of the new release values
			mostRecentInstrumentList.stream().forEach(i -> i.setNewRelease(false));
			secondMostRecentInstrumentList.stream().forEach(i -> i.setNewRelease(false));
			onlyNewInstrumentList.stream().forEach(i -> i.setNewRelease(true));
									
			//switch all of the new release? values in the mostRecentInstrumenList to true if it appears in onlyNewInstrumentList
			for(int i = 0; i < mostRecentInstrumentList.size(); i++)
			{
				if(!secondMostRecentInstrumentList.contains(mostRecentInstrumentList.get(i)))	
					mostRecentInstrumentList.get(i).setNewRelease(true);
			}
			
			//return only the new instruments if checkbox selected
			if(instrumentSelection.getNewOnly())
			{
				//sort
				onlyNewInstrumentList = onlyNewInstrumentList.stream()
									.sorted(Comparator.comparing(Instrument::getModel))
									.collect(Collectors.toList());
				
				//add instruments in selected list to the InstrumentListDto 			
				for(Instrument i : onlyNewInstrumentList)
				{
					String model = i.getModel();
					String link = i.getLink();				
					boolean newRelease = i.isNewRelease();
					li.addInstrumentToList(i);
					
					System.out.println("\nModel: " + model);
					System.out.println("Link: " + link);
					System.out.println("New Release:" + newRelease);
				}
			}
			
			else
			{
				//sort new
				onlyNewInstrumentList = onlyNewInstrumentList.stream()
									.sorted(Comparator.comparing(Instrument::getModel))
									.collect(Collectors.toList());
				
				for(Instrument i : onlyNewInstrumentList)
				{
					String model = i.getModel();
					String link = i.getLink();				
					boolean newRelease = i.isNewRelease();
					
					System.out.println("------------Viewing new only----------------------");
					System.out.println("\nModel: " + model);
					System.out.println("Link: " + link);
					System.out.println("New Release:" + newRelease);
				}
				
				
				//sort non-new
				mostRecentInstrumentList = mostRecentInstrumentList.stream()
									.sorted(Comparator.comparing(Instrument::getModel))
									.collect(Collectors.toList());
				
				for(Instrument i : mostRecentInstrumentList)
				{
					String model = i.getModel();
					String link = i.getLink();				
					boolean newRelease = i.isNewRelease();
					
					System.out.println("------------Viewing non-new only----------------------");
					System.out.println("\nModel: " + model);
					System.out.println("Link: " + link);
					System.out.println("New Release:" + newRelease);
				}
				

				//merge non-new and new, placing new at the top of the list				
				List<Instrument> mergedInstrumentList = new ArrayList<Instrument>();
				mergedInstrumentList.addAll(onlyNewInstrumentList);
				mergedInstrumentList.addAll(mostRecentInstrumentList);
				
				//remove instruments from mostRecentInstrumentList that are also in onlyNewInstrumentList (second occurrence is removed)
				mergedInstrumentList = mergedInstrumentList.stream()
															.distinct()
															.collect(Collectors.toList());
				
				//add instruments in selected list to the InstrumentListDto 			
				for(Instrument i : mergedInstrumentList)
				{
					String model = i.getModel();
					String link = i.getLink();				
					boolean newRelease = i.isNewRelease();
					li.addInstrumentToList(i);
					
					System.out.println("------------Viewing merged only----------------------");
					System.out.println("\nModel: " + model);
					System.out.println("Link: " + link);
					System.out.println("New Release:" + newRelease);
				}
			}
		}
		
		else if(moreThanOneJsonFile == false && noJsonFiles == false)
		{
			//deserialize JSON to arraylist of instruments (most recent)
			JsonList jsonParser = new JsonList();
			List<Instrument> instrumentList = new ArrayList<>(); 
			instrumentList = jsonParser.getInstrumentSearchList(mostRecentJsonFile);
			
			//set all of release values to new since it's the only file
			instrumentList.stream().forEach(i -> i.setNewRelease(false));
			
			//sort
			instrumentList = instrumentList.stream()
								.sorted(Comparator.comparing(Instrument::getModel))
								.collect(Collectors.toList());
			
			//add instruments in selected list to the InstrumentListDto 			
			for(Instrument i : instrumentList)
			{
				String model = i.getModel();
				String link = i.getLink();				
				boolean newRelease = i.isNewRelease();
				li.addInstrumentToList(i);
				
				System.out.println("\nModel: " + model);
				System.out.println("Link: " + link);
				System.out.println("New Release:" + newRelease);
			}	
		}
		
		return li;
	}
}
