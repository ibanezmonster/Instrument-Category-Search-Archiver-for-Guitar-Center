package com.gc.scripting;


import com.gc.file.FileFinder;
import com.gc.file.FileSearchType;
import com.gc.model.selection.InstrumentSelection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;



public class ScraperScriptRunner
{
	public static void execute(InstrumentSelection instrumentSelection) 
	{
		boolean passiveMode = false;
			
		//get file path
		String filePath = FileFinder.getPath(instrumentSelection, FileSearchType.SCRAPER);
						
		//read from config file to see if passive mode or not
		try(var in = new Scanner(new File(filePath + "\\runstyle.txt")))
		{
			String setting = in.nextLine();
			
			if(setting.equals("passive_mode=1"))
			{
				System.out.println("Running in passive mode");
				passiveMode = true;				
			}
		}
				
		catch (FileNotFoundException e)
		{
			System.out.println("Missing runstyle.config file");
			e.printStackTrace();
		}
				
		//simply update the txt to set flag to 1
		if(passiveMode)
		{
			try
			{
				Path toRun = Paths.get(filePath + "\\to_run.txt");
	
				Map<String, String> turnOn = new HashMap<>();
				turnOn.put("=0", "=1");
				
				String lineToUpdate = instrumentSelection.getInstrumentSelection().getDisplayName();
	
				try (Stream<String> lines = Files.lines(toRun, Charset.forName("UTF-8")))
				{
					List<String> replacedLines = lines
							.map(line -> line = 
										 line.toString().equals(lineToUpdate + "=0") ?
										 replace(line, turnOn) :									 
										 line) 												
							.collect(Collectors.toList());
					
					Files.write(toRun, replacedLines, Charset.forName("UTF-8"));
				}
			}
			
			catch (FileNotFoundException e)
			{			
				System.out.println("File to_run.txt not found");
				e.printStackTrace();
			}
			
			catch (IOException e)
			{
				System.out.println("Error reading/writing to to_run.txt");
				e.printStackTrace();
			}
			
			catch (Exception e)
			{				
				e.printStackTrace();
			}
		}
								
		
		else //active mode: make pull request now 
		{					
			try
			{			
				String cmdArg = "";
				
				switch(instrumentSelection.getInstrumentSelection())
				{
				case EIGHT_STRING_ELECTRIC_GUITAR:
					cmdArg = "8_string_electric_guitar";
					break;
				case SEVEN_STRING_ELECTRIC_GUITAR:
					cmdArg = "7_string_electric_guitar";
					break;
				case ACOUSTIC_GUITAR:
					cmdArg = "acoustic_guitar";
					break;
				}			
				
				Runtime.getRuntime().exec("cmd /c start cmd.exe /k " 
											+ "\""
											+ "cd " + filePath
											+ " && "
											+ "python -m gc_run " + cmdArg
											+ " && "
											+ "exit"
											+ "\"");
			}
			
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}	
		

			
	private static String replace(String str, Map<String, String> map)
	{
		for(Map.Entry<String, String> entry : map.entrySet())
		{
			if(str.contains(entry.getKey()))
			{
				str = str.replace(entry.getKey(), entry.getValue());
			}
		}
		
		return str;
	}
		
}
