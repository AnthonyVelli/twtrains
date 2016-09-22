package com.velli.main;

import java.util.Scanner;

public class CustomReader {
	
	
	private String readString;
	private Scanner scanner;
	private String sourcePath;

	public CustomReader(){
		
	}
	
	public void setString(String source){
		sourcePath = source;
		try {
			scanner = new Scanner(this.getClass().getResourceAsStream(sourcePath), "UTF-8");
			readString = scanner.useDelimiter("\\A").next(); 
		} finally {
			scanner.close();
		}
		    
	}
	
	public String getString() {
		return readString;
	}
	
}
