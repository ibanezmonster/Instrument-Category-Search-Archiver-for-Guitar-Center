package com.gc.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.gc.model.search.Instrument;

public class FileIO
{
	private final String fileName;
	
	public FileIO(String fileName)
	{
		this.fileName = fileName;
	}
	
	public void writeFile(List<Instrument> instrumentList) throws IOException
	{
		try(var out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName))))
		{			
			out.writeObject(instrumentList);			
		}
		
		catch(FileNotFoundException fnfe)
		{
		    fnfe.printStackTrace();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public List<Instrument> readFile(String fileName)
	{
		List<Instrument> instruments = new ArrayList<>();
		
		try(var in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName))))
		{
			instruments = (ArrayList<Instrument>) in.readObject();			
		}
		
		catch(FileNotFoundException fnfe)
		{
		    fnfe.printStackTrace();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return instruments; 
	}	

}
