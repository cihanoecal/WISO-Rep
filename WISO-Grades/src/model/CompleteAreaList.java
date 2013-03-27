package model;

import java.util.ArrayList;
import java.util.List;

public class CompleteAreaList extends ArrayList<Area> {

	private static final long serialVersionUID = 6768416802026411605L;
	private Student student = null; // Student that belongs to these exams
	
	
	/* ------------------- GETTER -------------------*/ 
	
	public List<Exam> getExamsOfArea(Area area){
		
		List<Exam> examsOfArea = area.getAllExams();
		return examsOfArea;
	}
	
	
	public double getAverage(Area area){
		
		if (area.getName().equalsIgnoreCase("Studium Integrale")){
			return 0;
		}
		
		double sumCP = 0;
		double sumWeightedGrades = 0;
		for (Exam exam : getExamsOfArea(area)){
			Grade grade = exam.getRating();
			if (grade != Grade.FIVE){
				
				byte cp = exam.getCreditpoints();
				sumCP += exam.getCreditpoints();
				sumWeightedGrades += (cp * grade.getNumericValue());
			}
		}
	
		if (sumCP != 0)
			return (Math.floor(
					Math.round((sumWeightedGrades / sumCP) * 1000)/1000d * 10 ) / 10d );
		else
			return 0;
		
	}
	
	public double getAverageOverall(boolean twoFractionalDigits){
		
		double accuracy = twoFractionalDigits ? 100 : 10;
		int cp;
		int sumCP = 0;
		double sumWeightedAreaGrades = 0d;
		
		for (Area area : this){
			
			/* Studium Integrale does not affect Overall Average */
			if (area.getName().equalsIgnoreCase("Studium Integrale")){
				continue;
			}
			
			// else
			cp = getCpOfArea(area); 
			sumWeightedAreaGrades += (getAverage(area) * cp);
			sumCP += cp;
		}
				
		if (sumCP != 0){
			return (Math.floor(
					Math.round((sumWeightedAreaGrades / sumCP) * 1000)/1000d * accuracy) / accuracy);
		} else
			return 0f;
	}
	
	private short getCpOfArea(Area area){
		short cp = 0;
		
		for (Exam exam : getExamsOfArea(area)){
			
			if (exam.getRating() != Grade.FIVE)
				cp += exam.getCreditpoints();
		}
		
		return cp;
	}
	
	/**
	 * 
	 * @return
	 * @author Cihan Öcal
	 * <i> 26.02.2013 </i>
	 */
	public short getSumCP(){
		
		short sumCP = 0;
		for (Area area : this){
			for (Exam exam : area.getAllExams()){
				if (!exam.getRating().equals(Grade.FIVE)){ 
					sumCP += exam.getCreditpoints();
				}
			}
		}
		
		return sumCP;
	}
	
	public byte getSumMP(){
		
		byte malP = 0;
		for (Area area : this){
			for (Exam exam : area.getAllExams()){
				if (exam.getRating().equals(Grade.FIVE)){ 
					malP -= exam.getCreditpoints();
				}
			}
		}
		
		return malP;
	}

	public Student getStudent(){
		return student;
	}

	/* ------------------- SETTER -------------------*/ 

	/**
	 * @param student
	 * @author Cihan
	 * <i> 26.03.2013 </i>
	 */
	public void setStudent(Student student) {
		if (this.student !=null){
			System.err.println("Student " + student.getUsername() +
					" überschreibt Studenten " +
					this.student.getUsername());
		}
		this.student = student;
	}


	public void print(){
	
		for (Area area : this){
			System.out.println("AREA: " + area.getCompleteName());
			for (Exam exam : area.getAllExams()){
				System.out.println("              " +
						exam.getId() + " " + exam.getName() + " " +
						exam.getCreditpoints() + "CP; Note: " + 
						exam.getRating().toString());
			
			}
		}
	}
}