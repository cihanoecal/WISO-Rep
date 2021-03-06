package view;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import logic.WISOGrades;
import model.Area;
import model.CompleteAreaList;
import model.Exam;
import model.Grade;
import model.Student;
import model.WISOColors;

public class ResultFrame extends JFrame {

	private static final long serialVersionUID = 6264200744924413001L;
	@SuppressWarnings("unused")
	private WISOGrades app;
	private Student student;
	private CompleteAreaList allAreas;
	private JButton close;
	@SuppressWarnings("unused")
	private JButton excelButton; // TODO EXCEL-Export
	private JPanel contentPane;
	
	/* following lists are only needed for ui-colors of rects etc. */
	private List<JLabel> averageLabels = new ArrayList<JLabel>();
	private List<JLabel> areaLabels = new ArrayList<JLabel>();
	private List<JLabel> examGradeLabels = new ArrayList<JLabel>();
	private List<JLabel> firstExamLabelsOfArea = new ArrayList<JLabel>();
	
	private JLabel averageOverallLabel;
	private Font defaultFont = new Font("default", Font.PLAIN, 11);
	
	/**
	 * Constructor
	 * @param app
	 * @author Cihan
	 * <i> 26.03.2013 </i>
	 */
	public ResultFrame(WISOGrades app) {
		this.app = app;
		student = app.getExams().getStudent();
		allAreas = app.getExams();
		
		String username = student.getUsername();
		int prNr = student.getPrNr();
		setTitle("Notenübersicht von " + username + " (" + prNr + ")");
		init();
	}
	
	private void init(){
		contentPane = new JPanel();
		setLocationByPlatform(true);
		setContentPane(contentPane);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		contentPane.setLayout(new GridBagLayout());
		
		
		/*
		 * GRIDBAGCONSTRAINTS:
		 * c0 for arealabels and examid
		 * c1 for examname
		 * c2 for semester
		 * c3 for creditpoints
		 * c4 for examgrades
		 */
		int row = 0;
		Insets spaceRight = new Insets(0, 0, 0, 5);
		Insets biggerSpaceRight = new Insets(0, 0, 0, 10);
		Insets biggerSpaceLeftAndRight = new Insets(0, 20, 0, 10);
		// Insets just for c0
		Insets c0areaInsets = new Insets(0, 0, 0, 5);
		Insets c0examIdInsets = new Insets(0, 5, 0, 5); 
		
		GridBagConstraints c0 = new GridBagConstraints();
		c0.gridx = 0;
		c0.gridy = row;
		c0.anchor = GridBagConstraints.LINE_START;
		c0.insets = c0areaInsets;
		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridx = 1;
		c1.gridy = row;
		c1.anchor = GridBagConstraints.LINE_START;
		c1.insets = spaceRight;
		GridBagConstraints c2 = new GridBagConstraints();
		c2.gridx = 2;
		c2.gridy = row;
		c2.anchor = GridBagConstraints.LINE_END;
		c2.insets = biggerSpaceLeftAndRight;
		GridBagConstraints c3 = new GridBagConstraints();
		c3.gridx = 3;
		c3.gridy = row;
		c3.anchor = GridBagConstraints.LINE_END;
		c3.insets = biggerSpaceRight;
		GridBagConstraints c4 = new GridBagConstraints();
		c4.gridx = 4;
		c4.gridy = row;
		c4.anchor = GridBagConstraints.LINE_END;
		c4.insets = spaceRight;
		
		for (Area area : allAreas){
			
			c0.gridy = row;
			c1.gridy = row;
			c2.gridy = row;
			c3.gridy = row;
			c4.gridy = row;
			
			
			// let arealabel be more left than examIDs
			c0.insets = c0areaInsets;
			
			JLabel areaLabel = new JLabel(area.getCompleteName()); 
			contentPane.add(areaLabel, c0);
			areaLabels.add(areaLabel);
			
			row++;
			
			c0.gridy = row;
			c1.gridy = row;
			c2.gridy = row;
			c3.gridy = row;
			c4.gridy = row;
		
			// let arealabel be more left than examIDs
			c0.insets = c0examIdInsets;
			/**
			 * Integer to determine whether current exam is the
			 * first in its area to draw first line grey. 
			 */
			int posOfExamInArea = -1;  
			
			for (Exam exam : area.getAllExams()){
				
				contentPane.add(new JLabel(""));
				
				JLabel idLabel = new JLabel(String.valueOf(exam.getId()));
				idLabel.setHorizontalAlignment(JLabel.RIGHT);
				idLabel.setFont(defaultFont);
				contentPane.add(idLabel, c0);
				
				JLabel nameLabel = new JLabel(exam.getName());
				nameLabel.setHorizontalAlignment(JLabel.RIGHT);
				nameLabel.setFont(defaultFont);
				contentPane.add(nameLabel, c1);
				
				JLabel semesterLabel = new JLabel(exam.getSemester());
				semesterLabel.setHorizontalAlignment(JLabel.RIGHT);
				semesterLabel.setFont(defaultFont);
				contentPane.add(semesterLabel, c2);
				
				JLabel cpLabel = new JLabel(String.valueOf(exam.getCreditpoints())
									.concat(" CP"));
				cpLabel.setHorizontalAlignment(JLabel.RIGHT);
				cpLabel.setFont(defaultFont);
				contentPane.add(cpLabel, c3);
				
				JLabel gradeLabel = new JLabel();
				gradeLabel.setText(exam.getRating().toString());
				
				
				gradeLabel.setHorizontalAlignment(JLabel.RIGHT);
				gradeLabel.setFont(defaultFont);
				contentPane.add(gradeLabel, c4);
				examGradeLabels.add(gradeLabel);
				
				if (++posOfExamInArea == 0){
					firstExamLabelsOfArea.add(gradeLabel);
				}
				
				
				if (exam.getRating().equals(Grade.FIVE)){
					idLabel.setForeground(WISOColors.MALUSPOINTSCOLOR);
					nameLabel.setForeground(WISOColors.MALUSPOINTSCOLOR);
					cpLabel.setForeground(WISOColors.MALUSPOINTSCOLOR);
					gradeLabel.setForeground(WISOColors.MALUSPOINTSCOLOR);
				}
				
				row++;
				c0.gridy = row;
				c1.gridy = row;
				c2.gridy = row;
				c3.gridy = row;
				c4.gridy = row;
			
			}
			
			/* All exams of One area are processed
			 * so show the average grade of area 
			 * (Except for 'Studium Integrale'):
			 */
			if (area.getAverage() != 0){
				final JLabel averageLabel = new JLabel("Durchschnitt in "
						+ area.getName() + ": " + area.getAverage());
				averageLabel.setForeground(WISOColors.DARKGREENTEXT);
				contentPane.add(averageLabel, c4);
				averageLabels.add(averageLabel);
				}
			
			row++;
		}

		Insets bottomInset = new Insets(30, 0, 0, 5);
		
		/* 
		 * All areas are processed
		 * so show sum maluspoints:
		 */
		byte malus = allAreas.getSumMP();
		JLabel mpLabel = new JLabel("Maluspunkte: " + malus);
		mpLabel.setForeground(WISOColors.MALUSPOINTSCOLOR);
		c0.gridy = row;
		c0.insets = bottomInset;
		contentPane.add(mpLabel, c0);
		
		/* 
		 * All areas are processed
		 * so show achieved creditpoints:
		 */
		JLabel cpLabel = new JLabel("Erreichte CreditPoints: " + allAreas.getSumCP());
		c3.gridy = row;
		c3.insets = bottomInset;
		contentPane.add(cpLabel, c3);
		
		/* All areas are processed
		 * so show overall average:
		 */		
		double averageDouble = allAreas.getAverageOverall(true);
		double averageSingle = Math.floor(averageDouble * 10) / 10d;
		c4.gridy = row;
		c4.insets = bottomInset;
		
		averageOverallLabel = new JLabel("Gesamtdurchschnitt: " +
					": " + averageSingle+ 
					" (" +averageDouble+ ")");
			contentPane.add(averageOverallLabel, c4);

		
		
		close = new JButton("schließen");
		close.addActionListener(new ActionListener() {
			@Override//Gees prog
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		contentPane.setBackground(WISOColors.BACKGROUND);
		
		
		pack();
		
		setResizable(false);
		setVisible(true);
	}
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		
		int width = 0;
		int height;
		int x; 
		int y;
				
		final int offsetTop = getInsets().top;
		final int offsetBottom = getInsets().bottom;
		final int offsetLeft = getInsets().left;
		@SuppressWarnings("unused")
		final int offsetRight = getInsets().right;
		
//		/* Highlight averages */
//		g.setColor(yellowTransp);
//		
//		// get biggest width of all averageLabels
//		for (JLabel averageLabel : averageLabels){
//			width = Math.max(width, averageLabel.getWidth());
//		}
//		width += 10; // makes it longer and moves it a bit to the left
//		x = getWidth() - width - offsetLeft - offsetRight;		
//		
//		for (JLabel averageLabel : averageLabels){
//			y = averageLabel.getY() + offsetTop;
//			height = averageLabel.getHeight();
//			g.fillRect(x, y, width, height);
//		}
		
		/* Highlight areas */
		width = getWidth();
		x = offsetLeft;
		g.setColor(WISOColors.AREAHIGHLIGHTINGCOLOR);
		for (JLabel areaLabel : areaLabels){
			y = areaLabel.getY() + offsetTop;
			height = areaLabel.getHeight();
			g.fillRect(x, y, width, height);
		}
		
		/* Alternate grey and white for examlist */
		boolean firstLineGray = true;
		boolean grey = firstLineGray;
		g.setColor(WISOColors.ALTERNATINGLINESCOLOR);
		
		for (JLabel gradeLabel : examGradeLabels){
			if (firstExamLabelsOfArea.contains(gradeLabel)){
				grey = firstLineGray ;
			}
			if (grey){
				gradeLabel.getY();
				g.fillRect(offsetLeft, offsetTop + gradeLabel.getY(), 
						getWidth(), gradeLabel.getHeight());
			}
			grey = !grey;
		}
		
				
		/* Make a black bar at bottom*/
		width = getWidth();
		height = averageOverallLabel.getHeight();
		x = offsetLeft;
		y = getHeight()-(height + offsetTop) + offsetBottom;
		
		g.setColor(WISOColors.AREAHIGHLIGHTINGCOLOR2);
		g.fillRect(x, y , width, height/2);
	}
}
