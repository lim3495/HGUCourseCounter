package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;

public class Student {

	private String studentId;
	private ArrayList<Course> coursesTaken;
	private HashMap<String,Integer> semestersByYearAndSemester; 
	
	public Student(String studentId) {
		
		this.studentId = studentId;
		this.coursesTaken = new ArrayList<Course>();
		this.semestersByYearAndSemester = new HashMap<String,Integer>();
	
	}

	public void addCourse(Course newRecord) {
		
		coursesTaken.add(newRecord);
		
		if(!(semestersByYearAndSemester.containsKey(newRecord.getYearandSemesterTaken()))) {
			
			semestersByYearAndSemester.put(newRecord.getYearandSemesterTaken(),semestersByYearAndSemester.size()+1 );
		}
		
	}
	
	public HashMap<String,Integer> getSemestersByYearAndSemester(){

		return semestersByYearAndSemester;
		
	}
	
	public int getNumCourseInNthSementer(int semester) {
		
		int semesterCount=0;
		String yearAndSemester=null;
		
		for(String str : semestersByYearAndSemester.keySet()) {
			if(semestersByYearAndSemester.get(str).equals(semester)) {
				yearAndSemester = str;
				//System.out.println(semestersByYearAndSemester.get(str));
				break;
			}
			
		}
		
		for(Course crs : coursesTaken) {
			if(crs.getYearandSemesterTaken().equals(yearAndSemester)) {
				semesterCount++;
			}
		}
		
		return semesterCount;
	}
	
	public String getStdId() {
		
		return this.studentId;
	}
	
	public boolean yearCheck(String year) {
		for(Course cs : this.coursesTaken) {
			if(cs.getYearandSemesterTaken().equals(year)) {
				return true;
			}
		}
		return false;
	}
	
	public int courseCheck(String year, String code) {
		for(Course cs : this.coursesTaken) {
			if(cs.getCourseCode().equals(code) && cs.getYearandSemesterTaken().equals(year))
				return 1;
		}
		
		return 0;
	}
	
	public String courseName(String code) {
		for(Course cs : this.coursesTaken) {
			if(cs.getCourseCode().equals(code)) {
				return cs.getCourseName();
			}
		}
		
		return null;
	}

}
