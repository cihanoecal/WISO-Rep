package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Area implements Comparable<Area>{
	private final String name;
	private int id;
	private List<Exam> exams = new ArrayList<Exam>();
	
	public Area(final String name) {
		int i = 0;
		while (Character.isDigit(name.charAt(i))){
			i++;
		}
		
		try{
			id = Integer.parseInt(name.substring(0, i));
		} catch(NumberFormatException e){
			id = -1;
			e.printStackTrace();
			System.err.println("Konnte ID von " + name + " nicht auslesen!");
		}
		this.name = name.substring(i + 1);
	}
	
	/**
	 * Only adds up CP of exams, which are relevant for average.
	 * => CP of exams which are made in a semester abroad will not be counted 
	 * @return
	 * @author Cihan Öcal
	 * <i> 22.06.2014 </i>
	 */
	protected short getSumCpForAverage(){
		short cp = 0;
		
		for (Exam exam : getAllExams()){
			
			if (exam.getRating() != Grade.FIVE && exam.getRating() != Grade.NaN)
				cp += exam.getCreditpoints();
		}
		
		return cp;
	}
	
	public double getAverage(){
		
		BigDecimal sumCP = new BigDecimal(getSumCpForAverage());
		BigDecimal sumWeightedGrades = BigDecimal.ZERO;
		for (Exam exam : exams){
			Grade grade = exam.getRating();
			if (grade != Grade.FIVE && grade != Grade.NaN){
				
				BigDecimal cp = new BigDecimal(exam.getCreditpoints());
				BigDecimal gradeNumeric = new BigDecimal(grade.getNumericValue());
				sumWeightedGrades = sumWeightedGrades.add(cp.multiply(gradeNumeric));
			}
		}
	
		if (!sumCP.equals(BigDecimal.ZERO)){
			BigDecimal result;
			
			result = sumWeightedGrades.divide(sumCP, 5, RoundingMode.HALF_UP);
			result = result.setScale(1, RoundingMode.DOWN);
			return (result.doubleValue());
		}else
			return 0;
		
	}
		
	
	/*
	 * Overridden for having no multiple entries in sets.
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj){
		if (obj instanceof Area){
			if (this.getCompleteName().equals(((Area) obj).getCompleteName()))
				return true;
			else
				return false;
		} else
			return super.equals(obj);
		
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}
	
	public String getCompleteName(){
		return String.valueOf(getId()).concat(" ").concat(name);
	}

	@Override
	public int compareTo(Area targetArea) {
		return getCompleteName().compareTo(targetArea.getCompleteName());
	}
	
	public void addExam(Exam exam){
		exams.add(exam);
	}

	public Exam getExam(int i){
		return exams.get(i);
	}

	public List<Exam> getAllExams() {
		return exams;
	}
}
