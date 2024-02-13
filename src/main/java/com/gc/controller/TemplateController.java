package com.gc.controller;


import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.gc.model.search.Instrument;
import com.gc.model.search.InstrumentListDto;
import com.gc.model.search.InstrumentSearch;
import com.gc.model.selection.InstrumentSelection;
import com.gc.scripting.ScraperScriptRunner;

@Controller
@RequestMapping("/")
public class TemplateController {

	@GetMapping("/")
    public RedirectView redirectWithUsingRedirectView(
        RedirectAttributes attributes) {
            attributes.addFlashAttribute("flashAttribute", "redirectWithRedirectView");
            attributes.addAttribute("attribute", "redirectWithRedirectView");
            return new RedirectView("login");
    }
	
    @GetMapping("login")
    public String getLogin() {	
    	
    	//test
//    	String fileNameRelative = "src/main/resources/scripts/GuitarCenterScraper/list/acoustic_guitar/list-2023-09-30.json";
//		Path path = Paths.get(fileNameRelative);
//		File jsonFileFolder = new File(path.toString());
//		File[] fileList = jsonFileFolder.listFiles();
//		String mostRecentFile = "";
//
//		
//		//-----------------------------------------------------------
//		System.out.println("File path: " + path.toString());
//		
//		for(File i : fileList) {System.out.println(i.toString());}
//		//-----------------------------------------------------------
		
				
//		List<String> fileNames = new ArrayList<>();
//		
//		for(File i : fileList)
//		{
//			fileNames.add(i.toString());
//		}
		
//		try
//		{
			//cut out date strings from list of file names
//			List<String> dateCutouts = new ArrayList<>();
//			
//			for(String d : fileNames)
//			{
//				String date = d.substring(d.length() - 15, d.length() - 5);				
//				dateCutouts.add(date);
//			}
//			 
		
			//-----------------------------------------------------------
			//System.out.println("Date strings: ");
			
			//for(String s : dateCutouts) {System.out.println(s);}
			//-----------------------------------------------------------
			
		
			//store date strings into Date objects
			//SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");			
			//List<Date> dateList = new ArrayList<>(); 

//			for(String date : dateCutouts)
//			{
//				Date d1 = sdFormat.parse(date);
//				dateList.add(d1);								
//			}
			
			
			
			//----------------------------------------------------
			//get latest date
			//----------------------------------------------------
//			Date mostRecentDate = Collections.max(dateList);
//			String formattedMostRecentDate = sdFormat.format(mostRecentDate);
//
//			//-----------------------------------------------------------
//			System.out.println("\nMost recent date is: " + formattedMostRecentDate);
//			//-----------------------------------------------------------
//			
//
//			mostRecentFile = path.toString() + "list-" + formattedMostRecentDate + ".json";  
//		
//			//-----------------------------------------------------------
//			System.out.println("File name of most recent date: " + mostRecentFile);
			//-----------------------------------------------------------			
//		}
		
		
//		catch (Exception e)
//		{		
//			System.out.println("File Not Found.");
//		}
		//test
		
		
        return "login";
    }
	
	
	@PostMapping("logout")
	public String getLogout(){
		return "login";
	}
	
	
    @GetMapping("home")
    public String getHome(Model model) {  	
    	model.addAttribute("instrumentSelectionForm", new InstrumentSelection());
    	
        return "home";
    }
    
    
    
    @PostMapping("search")
    public String getSearch(Model model, InstrumentSelection instrumentSelection) {

		model.addAttribute("instrumentSelectionForm", new InstrumentSelection());
    	model.addAttribute("instrumentSelectionModel", instrumentSelection);
    	   	
    	//test selected checkbox
    	if(instrumentSelection.getNewOnly())
    	{
    		System.out.println("Checkbox is selected!");
    	}
    	
    	else
    	{
    		System.out.println("Checkbox is NOT selected!");
    	}
    	
    	   	
    	//make one method that starts the whole process of reading from file and then retrieving data into list
    	InstrumentSearch searchProcess = new InstrumentSearch(instrumentSelection);
    	    	    	    	
    	//something that return an InstrumentListDto
    	InstrumentListDto listDto = searchProcess.getList();
    	
    	if(listDto.getList().size() == 0)
    	{
    		Instrument noResults = new Instrument();
    		noResults.setModel("No Results");
    		noResults.setLink("home");    		
    		listDto.addInstrumentToList(noResults);
    		model.addAttribute("searchResults", listDto.getList());
    	}    	
    	
    	else 
    	{			 
			model.addAttribute("searchResults", listDto.getList());
    	}
    	
        return "home";
    }
    
    
    @PostMapping("newpull")
    public String getRequestPull(Model model, InstrumentSelection instrumentSelection) 
    {	
    	//run instrument search scraper, which outputs to JSON file
    	ScraperScriptRunner.execute(instrumentSelection);
    	    	  	
		model.addAttribute("instrumentSelectionForm", new InstrumentSelection());
    	    	    	  
    	return "home";
    }
    
  
    @GetMapping("about")
    public String getAbout(Model model) {   
    	
		model.addAttribute("instrumentSelectionForm", new InstrumentSelection());
    	
        return "home";
    }
}
