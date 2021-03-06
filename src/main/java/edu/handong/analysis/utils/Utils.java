package edu.handong.analysis.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class Utils {
	
	public static ArrayList<CSVRecord> getLines(String file,boolean removeHeader){
		
		ArrayList<CSVRecord> lines = new ArrayList<CSVRecord>();
		File dataFile = new File(file);
		
		try {
			
			CSVParser parser = new CSVParser(new FileReader(dataFile), CSVFormat.DEFAULT.withHeader()); 
			
	
			for (CSVRecord record : parser) 
				lines.add(record); 
				
		}catch (FileNotFoundException e) {
			System.out.println("File is not exist");
            System.exit(0);
        }
		catch(IOException e){
            System.out.println(e);
            System.exit(0);
        }

		
		
		return lines;
	}
	
	public static void writeAFile(ArrayList<String> lines, String targetFileName){
		
		PrintWriter outputStream = null;
		
		try {
			File file = new File(targetFileName);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
			}
			outputStream = new PrintWriter(targetFileName);
		} catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		for (String line: lines) {
            outputStream.println (line);
		}
		
		outputStream.close();
	}
		
}
