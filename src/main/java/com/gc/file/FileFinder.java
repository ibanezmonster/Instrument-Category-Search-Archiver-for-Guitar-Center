package com.gc.file;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.gc.model.selection.InstrumentSelection;

public final class FileFinder
{
	public static String getPath(InstrumentSelection instrumentSelection, FileSearchType fileSearchType)
	{
		String fileName = "";
		
		if(fileSearchType == FileSearchType.MOST_RECENT_DATA)
		{
			fileName = getMostRecentJSONFilePath(instrumentSelection);
		}
		
		else if(fileSearchType == FileSearchType.SECOND_MOST_RECENT_DATA)
		{
			fileName = getSecondMostRecentJSONFilePath(instrumentSelection);
		}
		
		else if(fileSearchType == FileSearchType.SCRAPER)
		{
			fileName = getScraperPath();
		}

		return fileName;
	}	
	
	
	private static String getScraperPath()
	{	
		//need to put ../ to run from JAR file. From IDE, remove ../
		String fileNameRelative = "../src/main/resources/scripts/GuitarCenterScraper/";
		String fullPath = "";
				
		Path path = Paths.get(fileNameRelative);
		Path absolutePath = path;
		
		if(!path.isAbsolute())
		{
			Path base = Paths.get("");
			absolutePath = base.resolve(path).toAbsolutePath();
		}
		
		fullPath = absolutePath.normalize().toString();		
		
		return fullPath;
	}
	
	
	
	private static String getMostRecentJSONFilePath(InstrumentSelection instrumentSelection)
	{		
		StringBuilder path = new StringBuilder(); 
		path.append(getScraperPath() + "\\list");
		String mostRecentFile = "";
				
		String folder = "";
		
		switch(instrumentSelection.getInstrumentSelection())
		{
		case SEVEN_STRING_ELECTRIC_GUITAR: 
			folder = "\\7_string_electric_guitar\\";
			break;
		case EIGHT_STRING_ELECTRIC_GUITAR: 
			folder = "\\8_string_electric_guitar\\";
			break;
		case ACOUSTIC_GUITAR: 
			folder = "\\acoustic_guitar\\";
			break;
		}
		
		path.append(folder);
		
		//get list of file names
		File jsonFileFolder = new File(path.toString());
		File[] fileList = jsonFileFolder.listFiles();
		
		
		//-----------------------------------------------------------
		System.out.println("File path: " + path.toString());		//edited for JAR
		
		for(File i : fileList) {System.out.println(i.toString());}
		//-----------------------------------------------------------
		
				
		List<String> fileNames = new ArrayList<>();
	
		for(File i : fileList)
		{
			fileNames.add(i.toString());
		}
		
		try
		{
			//cut out date strings from list of file names
			List<String> dateCutouts = new ArrayList<>();
			
			for(String d : fileNames)
			{
				String date = d.substring(d.length() - 15, d.length() - 5);				
				dateCutouts.add(date);
			}
			 
		
			//-----------------------------------------------------------
			System.out.println("Date strings: ");
			
			for(String s : dateCutouts) {System.out.println(s);}
			//-----------------------------------------------------------
			
		
			//store date strings into Date objects
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");			
			List<Date> dateList = new ArrayList<>(); 

			for(String date : dateCutouts)
			{
				Date d1 = sdFormat.parse(date);
				dateList.add(d1);								
			}
			
			
			
			//----------------------------------------------------
			//get latest date
			//----------------------------------------------------
			Date mostRecentDate = Collections.max(dateList);
			String formattedMostRecentDate = sdFormat.format(mostRecentDate);

			//-----------------------------------------------------------
			System.out.println("\nMost recent date is: " + formattedMostRecentDate);
			//-----------------------------------------------------------
			

			mostRecentFile = path.toString() + "list-" + formattedMostRecentDate + ".json";  
		
			//-----------------------------------------------------------
			System.out.println("File name of most recent date: " + mostRecentFile);
			//-----------------------------------------------------------			
		}
		
		
		catch (Exception e)
		{		
			System.out.println("File Not Found.");
		}

		return mostRecentFile;
	}
	
	
	
	
	
	
	public static String getSecondMostRecentJSONFilePath(InstrumentSelection instrumentSelection)
	{
		StringBuilder path = new StringBuilder(); 
		path.append(getScraperPath() + "\\list");		
		String mostRecentJsonFile = "";
		String secondMostRecentJsonFile = "";
				
		String folder = "";
		
		switch(instrumentSelection.getInstrumentSelection())
		{
		case SEVEN_STRING_ELECTRIC_GUITAR: 
			folder = "\\7_string_electric_guitar\\";
			break;
		case EIGHT_STRING_ELECTRIC_GUITAR: 
			folder = "\\8_string_electric_guitar\\";
			break;
		case ACOUSTIC_GUITAR: 
			folder = "\\acoustic_guitar\\";
			break;
		}
		
		path.append(folder);
		
		//get list of file names
		File jsonFileFolder = new File(path.toString());
		File[] fileList = jsonFileFolder.listFiles();
		
		
		//-----------------------------------------------------------
		System.out.println("File path: " + path.toString());
		
		for(File i : fileList) {System.out.println(i.toString());}
		//-----------------------------------------------------------
		
				
		List<String> fileNames = new ArrayList<>();
		
		for(File i : fileList)
		{
			fileNames.add(i.toString());
		}
		
		
		try
		{
			//cut out date strings from list of file names
			List<String> dateCutouts = new ArrayList<>();
			
			for(String d : fileNames)
			{
				String date = d.substring(d.length() - 15, d.length() - 5);				
				dateCutouts.add(date);
			}
			 
			//store date strings into Date objects
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");			
			List<Date> dateList = new ArrayList<>(); 

			for(String date : dateCutouts)
			{
				Date d1 = sdFormat.parse(date);
				dateList.add(d1);								
			}
					
			//----------------------------------------------------
			//get latest date
			//----------------------------------------------------
			Date mostRecentDate = Collections.max(dateList);
			String formattedMostRecentDate = sdFormat.format(mostRecentDate);

			mostRecentJsonFile = path.toString() + "list-" + formattedMostRecentDate + ".json";  
			
			int indexToRemove = 0;
			
			for(int i = 0; i <= dateCutouts.size() - 1; i++)
			{
				if(formattedMostRecentDate.equals(dateCutouts.get(i)))
				{
					indexToRemove = i;
				}
			}
			
			dateCutouts.remove(indexToRemove);
			dateList.clear();
			
			for(String date : dateCutouts)
			{
				Date d1 = sdFormat.parse(date);
				dateList.add(d1);								
			}
						
			
			Date secondMostRecentDate = Collections.max(dateList);
			String formattedSecondMostRecentDate = sdFormat.format(secondMostRecentDate);
			secondMostRecentJsonFile = path.toString() + "list-" + formattedSecondMostRecentDate + ".json"; 
					
			//-----------------------------------------------------------
			System.out.println("\nSecond most recent date is: " + formattedSecondMostRecentDate);
			System.out.println("File name of second most recent date: " + secondMostRecentJsonFile);
			//-----------------------------------------------------------

		}
			
		catch (Exception e)
		{		
			System.out.println("File Not Found.");
		}

		return secondMostRecentJsonFile;
	}
}
