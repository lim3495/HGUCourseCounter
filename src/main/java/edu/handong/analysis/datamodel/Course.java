package edu.handong.analysis.datamodel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

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
	
	public Course(String line) {
	
		this.studentId =line.split(",")[0].trim();
		this.yearMonthGraduated =line.split(",")[1].trim();
		this.firstMajor =line.split(",")[2].trim();
		this.secondMajor =line.split(",")[3].trim();
		this.courseCode =line.split(",")[4].trim();
		this.courseName =line.split(",")[5].trim();
		this.courseCredit =line.split(",")[6].trim();
		this.yearTaken =Integer.parseInt(line.split(",")[7].trim());
		this.semesterCourseTaken = Integer.parseInt(line.split(",")[8].trim());

	}
	
	public String getYearTaken() {
		
		String yearReturn = Integer.toString(yearTaken) +"-"+ Integer.toString(semesterCourseTaken);
		return yearReturn;
	}
	
	public String getStdId() {
		
		return this.studentId;
	}
	
	
	
}
