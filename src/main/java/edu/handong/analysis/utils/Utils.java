package edu.handong.analysis.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Utils {
	
	public static ArrayList<String> getLines(String file,boolean removeHeader){
		
		ArrayList<String> lines = new ArrayList<String>();
		File dataFile = new File(file);
		
		try {
			FileReader new_file = new FileReader(dataFile);
			BufferedReader new_bff = new BufferedReader(new_file);
			String line=null;
			
			while((line =new_bff.readLine())!= null) {
				lines.add(line);
			}
			
			if(removeHeader) {
				lines.remove(0);
			}
			
			new_bff.close();
			
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
			outputStream = new PrintWriter(file);
			//System.out.println("test!");
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
