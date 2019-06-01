package edu.handong.analysis.datamodel;

//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
import java.util.ArrayList;

import org.apache.commons.csv.CSVRecord;

public class Course {
	
	private String studentId;
	private String yearMonthGraduated;
	private String firstMajor;
	private String secondMajor;
	private String courseCode;
	private String courseName;
	private String courseCredit;
	private int yearTaken;
	private int semesterCourseTaken;
	
	public Course(CSVRecord line) {
	
		this.studentId =line.get(0).trim();
		this.yearMonthGraduated =line.get(1).trim();
		this.firstMajor =line.get(2).trim();
		this.secondMajor =line.get(3).trim();
		this.courseCode =line.get(4).trim();
		this.courseName =line.get(5).trim();
		this.courseCredit =line.get(6).trim();
		this.yearTaken =Integer.parseInt(line.get(7).trim());
		this.semesterCourseTaken = Integer.parseInt(line.get(8).trim());

	}
	
	public String getYearandSemesterTaken() {
		
		String yearReturn = Integer.toString(yearTaken) +"-"+ Integer.toString(semesterCourseTaken);
		return yearReturn;
	}
	
	public String getStdId() {
		
		return this.studentId;
	}
	
	public int getYearTaken() {
		return this.yearTaken;
	}
	
	public String getCourseCode() {
		return this.courseCode;
	}
	
	public String getCourseName() {
		return this.courseName;
	}
	
}
