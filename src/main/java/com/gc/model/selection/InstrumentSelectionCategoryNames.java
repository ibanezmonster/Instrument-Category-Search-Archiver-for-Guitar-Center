package com.gc.model.selection;


//public class InstrumentSelectionCategory
//{

public enum InstrumentSelectionCategoryNames 
{
	EIGHT_STRING_ELECTRIC_GUITAR("8-string Electric Guitar"),
	SEVEN_STRING_ELECTRIC_GUITAR("7-string Electric Guitar"),
	ACOUSTIC_GUITAR("Acoustic Guitar");
	
	private final String displayName;
	
	InstrumentSelectionCategoryNames(String displayName)
	{
		this.displayName = displayName;
	}
	
	public String getDisplayName()
	{
		return displayName;
	}
	
}