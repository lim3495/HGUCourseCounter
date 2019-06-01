package edu.handong.analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.csv.CSVRecord;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.utils.NotEnoughArgumentException;
import edu.handong.analysis.utils.Utils;

public class HGUCoursePatternAnalyzer {

	private HashMap<String,Student> students;
	private ArrayList<Course> courses;
	private String dataPath;// csv file to be analyzed
	private String resultPath; // the file path where the results are saved.
	private int startYear;
	private int endYear;	
	private int analysis;
	private String courseCode = null;
	private boolean help;
	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 * @throws IOException 
	 * @throws NotEnoughArgumentException 
	 */
	public void run(String[] args) {
		
		Options options = createOptions();
		
		//ArrayList<CSVRecord> lines = Utils.getLines(dataPath, true);
	
		
		if(parseOption(options,args)) {
			if(help) {
				System.out.println("help");
				printHelp(options);
				System.exit(0);
				
			}
			
			else if(analysis == 1) {
				ArrayList<CSVRecord> lines = Utils.getLines(dataPath, true);
				
			try {
				students = loadStudentCourseRecords(lines);
				
				// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
				Map<String, Student> sortedStudents = new TreeMap<String,Student>(students);
				
				// Generate result lines to be saved.
				ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
				
				// Write a file (named like the value of resultPath) with linesTobeSaved.
				Utils.writeAFile(linesToBeSaved, resultPath);
			} catch (Exception e) {
				printHelp(options);
				System.exit(0);
			}
			}//anaylsis 1
			
			else if(analysis == 2) {
				
				ArrayList<CSVRecord> lines = Utils.getLines(dataPath, true);
				
				if(courseCode == null) {
					System.out.println("Error. input option -c");
					printHelp(options);
					System.exit(0);
				}
				
				try {
					students = loadStudentCourseRecords(lines);
					
					// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
					Map<String, Student> sortedStudents = new TreeMap<String,Student>(students);
					
					// Generate result lines to be saved.
					ArrayList<String> linesToBeSaved = countNumberOfStudentsTakenInEachSemester(sortedStudents);
					
					// Write a file (named like the value of resultPath) with linesTobeSaved.
					Utils.writeAFile(linesToBeSaved, resultPath);
					
				}catch (Exception e) {
					printHelp(options);
					System.exit(0);
				}
				
			}//analysis 2
			else {
				System.out.println("Your input is wrong. Please input analysis 1 or 2");
				System.exit(0);
			}
			
			
		}
	}
	

	private void printHelp(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		String header = "HGU Course Analyzer";
		String footer = "";
		formatter.printHelp("HGU Course Counter",header,options,footer,true);
	}
	
	private boolean parseOption(Options options, String[] args) {
		DefaultParser parser = new DefaultParser();
		
		try {
			CommandLine cmd = parser.parse(options, args);
			
			dataPath = cmd.getOptionValue("i");
			resultPath = cmd.getOptionValue("o");
			analysis = Integer.parseInt(cmd.getOptionValue("a"));	
			courseCode = cmd.getOptionValue("c");		
			startYear = Integer.parseInt(cmd.getOptionValue("s"));
			endYear = Integer.parseInt(cmd.getOptionValue("e"));
			help = cmd.hasOption("h");
					
		} catch(Exception e) {
			printHelp(options);
			return false;
		}
		
		return true;
		
	}
	
	private Options createOptions() {
		Options options = new Options();
		
		options.addOption(Option.builder("i").longOpt("input")
				.desc("Set an input file path")
				.hasArg()
				.argName("Input path")
				.required()
				.build());
		
		options.addOption(Option.builder("o").longOpt("output")
				.desc("Set an output file path")
				.hasArg()
				.argName("Output path")
				.required()
				.build());
		 
		options.addOption(Option.builder("a").longOpt("analysis")
				.desc("1: Count courses per semester, 2: Count per course name and year")
				.hasArg()
				.argName("Analysis option")
				.required()
				.build());
		
		options.addOption(Option.builder("c").longOpt("coursecode")
				.desc("Course code for '-a 2' option")
				.hasArg()
				.argName("Course code")
				.build());
		
		options.addOption(Option.builder("s").longOpt("startyear")
				.desc("Set the start year for analysis e.g., -s 2002")
				.hasArg()
				.argName("Start year for analysis")
				.required()
				.build());
		
		options.addOption(Option.builder("e").longOpt("endyear")
				.desc("Set the end year for analysis e.g., -e 2005")
				.hasArg()
				.argName("End year for analysis")
				.required()
				.build());
		
		options.addOption(Option.builder("h").longOpt("help")
				.desc("Show a Help page")
				.argName("Help")
				.build());
		
		return options;
	}
	
	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return HashMap<String,Student>
	 */
	private HashMap<String,Student> loadStudentCourseRecords(ArrayList<CSVRecord> lines) {
	
		students = new HashMap<String, Student>();
		
		Student onestudent= new Student(lines.get(0).get(0));
		
		
		for(CSVRecord new_lines : lines) {
			
			Course newCorse = new Course(new_lines);
	
			if(onestudent.getStdId().equals(newCorse.getStdId()) && newCorse.getYearTaken()>=this.startYear && newCorse.getYearTaken() <= this.endYear){
				
				onestudent.addCourse(newCorse);
				students.put(newCorse.getStdId(),onestudent);
	
			}else {
				onestudent = new Student(newCorse.getStdId());
				onestudent.addCourse(newCorse);
			}
			
		}
		
		return students; // do not forget to return a proper variable.
	}
	
	

	/**
	 * This method generate the number of courses taken by a student in each semester. The result file look like this:
	 * StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester
	 * 0001,14,1,9
     * 0001,14,2,8
	 * ....
	 * 
	 * 0001,14,1,9 => this means, 0001 student registered 14 semeters in total. In the first semeter (1), the student took 9 courses.
	 * 
	 * 
	 * @param sortedStudents
	 * @return
	 */
	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {
		
		String first , last;
		ArrayList<String> all = new ArrayList<String>();
		all.add("StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester");
		
		for (Student sort :sortedStudents.values()) {
			first = sort.getStdId();
			first += "," + Integer.toString(sort.getSemestersByYearAndSemester().size());
			
			for(int i=1 ; i<= sort.getSemestersByYearAndSemester().size();i++) {
				last = ",";
				last += Integer.toString(i);
				last += ",";
				last += Integer.toString(sort.getNumCourseInNthSementer(i));
				all.add(first+last);
			
		}

		}
		
		return all; // do not forget to return a proper variable.
	}
	
	private ArrayList<String> countNumberOfStudentsTakenInEachSemester(Map<String, Student> sortedStudents) {
		
		int count , allcount = 0;
		ArrayList<String> all = new ArrayList<String>();
		String line,course = null;
		all.add("Year,Semester,CouseCode,CourseName,TotalStudents,StudentsTaken,Rate");
		
		for (int i = this.startYear ; i<= this.endYear ; i++) {
			for(int j=1 ; j<=4 ; j++) {
				String year= Integer.toString(i) +"-"+ Integer.toString(j);
				count = 0 ;
				allcount = 0;
				
				for(Student std : sortedStudents.values()) {
					if(std.yearCheck(year)) {
					 allcount ++;
					 count += std.courseCheck(year,courseCode);
					 
					 if(course == null) {
						 course = std.courseName(courseCode);
					 }
					}
				}
				
				if(count != 0) {
					float rate = (float)allcount/(float)count;
					double rateFormat = Math.round(rate*10)/10.0;
					line = year.split("-")[0] + "," + year.split("-")[1] 
							+ "," + courseCode + ","  + course + "," +
							Integer.toString(count) + Double.toString(rateFormat)+"%";
					all.add(line);
				}
			}

		}
		
		return all; // do not forget to return a proper variable.
	}
		
}
