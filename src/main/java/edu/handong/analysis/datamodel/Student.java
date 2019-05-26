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
		
		if(!(semestersByYearAndSemester.containsKey(newRecord.getYearTaken()))) {
			
			semestersByYearAndSemester.put(newRecord.getYearTaken(),semestersByYearAndSemester.size()+1 );
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
				System.out.println(semestersByYearAndSemester.get(str));
				break;
			}
			
		}
		
		for(Course crs : coursesTaken) {
			if(crs.getYearTaken().equals(yearAndSemester)) {
				semesterCount++;
			}
		}
		
		return semesterCount;
	}
	
	public String getStdId() {
		
		return this.studentId;
	}

}
