package application;
	
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import com.itextpdf.text.pdf.PdfWriter;

public class Main extends Application{
	///////////////////////////////////////////////////////////////////
	static Map<String,Batch> B = new HashMap<String,Batch>();
	static Map<String,Professor> PR = new HashMap<String,Professor>();
	static Map<String,Course> CR = new HashMap<String,Course>();
	static ArrayList<String> ProfessorList = new ArrayList<String>();
	static ArrayList<String> AssociateProfessorList = new ArrayList<String>();
	static ArrayList<String> AssistantProfessorList = new ArrayList<String>();
	static ArrayList<String> BatchList = new ArrayList<String>();
	static ArrayList<String> CourseList = new ArrayList<String>();
	static ArrayList<String> LabList = new ArrayList<String>();
	static ArrayList<String> AssignCourseList = new ArrayList<String>();
	static ArrayList<String> RemainCourseList = new ArrayList<String>();
	static String LABroutine[][] = new String[5][6];
	static Map<String, String[][]> SessionRoutine = new HashMap<>();
	@FXML
	TextField session;
	@FXML
	TextField course;
	@FXML
	TextField EditSession;
	@FXML
	CheckBox credit1;
	@FXML
	CheckBox credit2;
	@FXML
	TextField coursecode;
	@FXML
	TextField professorname;
	@FXML
	CheckBox lecturer;
	
	@FXML
	TextField professornumber;
	@FXML
	TextField professorcode;
	@FXML
	CheckBox professor;
	@FXML
	CheckBox associateprofessor;
	@FXML
	CheckBox assistantprofessor;
	@FXML
	ChoiceBox<String> courselist;
	@FXML
	ChoiceBox<String> professorlist;
	@FXML
	ChoiceBox<String> batchlist;
	@FXML
	ChoiceBox<String> SessionSelection;
	
	// Session Table
	@FXML
    private TableView<TableBatch> SessionTable;
	@FXML
	private TableColumn<TableBatch, String> SessionData;
	////
	void DemoGeneratePdf() {
		  Rectangle pagesize = new Rectangle(1400,800);
	  	  Document document = new Document(pagesize);
	        try {
	            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(this.getAlphaNumericString(8)+"Full_Routine.pdf"));
	            document.open();
	            String s1 = "DEPT. OF COMPUTER SCIENCE AND ENGINEERING";
	            String s2 = "JATIYA KABI KAZI NAZRUL ISLAM UNIVERSITY ";
	            String s3 = "CLASS ROUTINE - EFECTIVE FROM FEBRUARY,2024";
	            Paragraph text = new Paragraph(s1+"\n"+s2+"\n"+s3);
	            text.setAlignment(Element.ALIGN_CENTER);
	            document.add(text);

	            PdfPTable table = new PdfPTable(8);
	            table.setWidthPercentage(105);
	            table.setSpacingBefore(11f);
	            table.setSpacingAfter(11f);

	            //float[] colWidth = {2f, 2f, 2f, 2f, 2f, 2f, 2f,2f};
	            //table.setWidths(colWidth);
	            String[] time = {"Day","Session","9:30-10:30","10:30-11:30","11:30-12:30","12:30-1:30","02:00-03:00","3:00-04:00",};
	            String[] day  = {"Sunday","Monday","Tuesday","Wednesday","Thurseday"};

	            // Add time cells for the first row
	            for (int i = 0; i < time.length; i++) {
	                PdfPCell timeCell = new PdfPCell(new Paragraph(time[i]));
	                timeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                timeCell.setBackgroundColor(BaseColor.GREEN);
	                table.addCell(timeCell);
	            }
	            // Add time cells for the second row
	            Collections.sort(BatchList, Collections.reverseOrder());
	            for(int j=0;j<5;j++) {
	          	  PdfPCell d = new PdfPCell(new Paragraph(day[j]));
	          	  d.setRowspan(BatchList.size());
	          	  d.setHorizontalAlignment(Element.ALIGN_CENTER);
	          	  table.addCell(d);
	          	  for(String sss : BatchList) {
	          		  String[][] routine = SessionRoutine.get(sss);
	          		  PdfPCell ss = new PdfPCell(new Paragraph(sss));
	          		  ss.setHorizontalAlignment(Element.ALIGN_CENTER);
	          		  table.addCell(ss);
	          		  for(int i=0;i<6;i++) {
	          			if(routine[j][i]==null) {
		            		  PdfPCell timeCell = new PdfPCell(new Paragraph(""));
			                  table.addCell(timeCell);
		            	  }
		            	  else {
		            		  PdfPCell timeCell = new PdfPCell(new Paragraph(routine[j][i]));
		            		  if(routine[j][i].contains("LAB")) {
		            			  timeCell.setColspan(3);
		            			  i+=2;
		            		  }
		            		  timeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			                  table.addCell(timeCell);
		            	  }
	          		  }
	          	  }
	            }
	            document.add(table);
	            String[] title = {"Course Teachers","Mobile No"};
	            PdfPTable table1 = new PdfPTable(6);
	            table1.setWidthPercentage(80f);
	            table1.setHorizontalAlignment(Element.ALIGN_LEFT);
	            
	            for (int i = 0; i < 6; i++) {
	            	if(i%2 == 0) {
	            		PdfPCell timeCell = new PdfPCell(new Paragraph(title[0]));
		                timeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		                timeCell.setBackgroundColor(BaseColor.GREEN);
		                table1.addCell(timeCell);
	            	}
	            	else {
	            		PdfPCell timeCell = new PdfPCell(new Paragraph(title[1]));
		                timeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		                timeCell.setBackgroundColor(BaseColor.GREEN);
		                table1.addCell(timeCell);
	            	}
	            }
	            ArrayList<String> professor=new ArrayList<String>();
	        	Collections.shuffle(ProfessorList);
	        	Collections.shuffle(AssociateProfessorList);
	        	Collections.shuffle(AssistantProfessorList);
	        	professor.addAll(ProfessorList);
	    		professor.addAll(AssociateProfessorList);
	    		professor.addAll(AssistantProfessorList);
	    		int sum = 0;
	            for (String s : professor) {
	                Professor p = PR.get(s);
	                String name = p.ProfessorCode+"-"+p.ProfessorName;
	                String Number = p.ProfessorNumber;
	                PdfPCell timeCell = new PdfPCell(new Paragraph(name));
          		  	timeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
	                table1.addCell(timeCell);
	                PdfPCell timeCell1 = new PdfPCell(new Paragraph(Number));
          		  	timeCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
	                table1.addCell(timeCell1);
	                System.out.print(name);
	                System.out.print(Number);
	                System.out.print("Hello");
	                sum += 2;
	            }
	            while(sum%6 != 0) {
	            	PdfPCell timeCell = new PdfPCell(new Paragraph(""));
	                table1.addCell(timeCell);
	                sum++;
	            }
	            document.add(table1);
	            String ss = "(Professor Dr. Md. Sujon Ali)\nHead of the Department\nDept. of CSE,JKKNIU";
	            Paragraph p = new Paragraph(ss);
	            p.setAlignment(Element.ALIGN_RIGHT);
	            document.add(p);
	            document.close();
	            pdfWriter.close();
	        } catch (DocumentException e) {
	            e.printStackTrace();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
	  }

	String getAlphaNumericString(int n) 
    {  
	     String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
	            + "0123456789"
	            + "abcdefghijklmnopqrstuvxyz"; 
	    
	     StringBuilder sb = new StringBuilder(n); 
	    
	     for (int i = 0; i < n; i++) { 
	      int index 
	       = (int)(AlphaNumericString.length() 
	         * Math.random());
	      sb.append(AlphaNumericString 
	         .charAt(index)); 
	     } 
	    
	     return sb.toString(); 
    }
	
	void TotalGeneratePdf() {
  	  Document document = new Document();
        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(this.getAlphaNumericString(8)+"_Total.pdf"));
            document.open();
            ArrayList<String> professor=new ArrayList<String>();
        	Collections.shuffle(ProfessorList);
        	Collections.shuffle(AssociateProfessorList);
        	Collections.shuffle(AssistantProfessorList);
        	professor.addAll(ProfessorList);
    		professor.addAll(AssociateProfessorList);
    		professor.addAll(AssistantProfessorList);
    		
    		for (String s : professor) {
                Professor p = PR.get(s);
	            document.add(new Paragraph(p.ProfessorName));
	
	            PdfPTable table = new PdfPTable(7);
	            table.setWidthPercentage(105);
	            table.setSpacingBefore(11f);
	            table.setSpacingAfter(11f);
	
	            float[] colWidth = {2f, 2f, 2f, 2f, 2f, 2f, 2f};
	            table.setWidths(colWidth);
	            String[] time = {"9:30-10:30","10:30-11:30","11:30-12:30","12:30-1:30","02:00-03:00","3:00-04:00",};
	            String[] day  = {"Sunday","Monday","Tuesday","Wednesday","Thurseday"};
	            
	            
	            // Add the "Day" header cell to the first row
	      	  PdfPCell dayCell = new PdfPCell(new Paragraph("Day"));
	      	  table.addCell(dayCell);
	
	            // Add time cells for the first row
	            for (int i = 0; i < 6; i++) {
	                PdfPCell timeCell = new PdfPCell(new Paragraph(time[i]));
	                table.addCell(timeCell);
	            }
	            // Add time cells for the second row
	            for(int j=0;j<5;j++) {
	          	  PdfPCell d = new PdfPCell(new Paragraph(day[j]));
	          	  table.addCell(d);
		              for (int i = 0; i < 6; i++) {
		            	  if(p.routine[j][i]==null) {
		            		  PdfPCell timeCell = new PdfPCell(new Paragraph(""));
			                  table.addCell(timeCell);
		            	  }
		            	  else {
		            		  PdfPCell timeCell = new PdfPCell(new Paragraph(p.routine[j][i]));
			                  table.addCell(timeCell);
		            	  }
		                  
		              }
	            }
	            document.add(table);
    		}
            document.close();
            pdfWriter.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
  }

	
	
	static void CourseAssign(Course cr  , String batch , String professor){
        cr.BatchName = batch;
        cr.CourseProfessorName = professor;
        AssignCourseList.add(cr.CourseName);
        RemainCourseList.remove(cr.CourseName);
        System.out.println("Course Assigned");
    }
	static void ProfessorAddCourse(Professor pr , String course,double cr){
        if(cr == 3.0) pr.Course.add(course);
        else pr.Lab.add(course);
    }
	static void ShowProfessorCourses(ArrayList<String> ProfessorList,Map<String,Professor> PR){
        for (String s : ProfessorList) {
            Professor pr = PR.get(s);
            pr.ShowCourses();
        }
    }
	public void clear() {
		ArrayList<String> professor=new ArrayList<String>();
    	Collections.shuffle(ProfessorList);
    	Collections.shuffle(AssociateProfessorList);
    	Collections.shuffle(AssistantProfessorList);
    	professor.addAll(ProfessorList);
		professor.addAll(AssociateProfessorList);
		professor.addAll(AssistantProfessorList);
		for (String s : BatchList) {
            Batch b = B.get(s);
            b.ClearRoutine();
            for(int i=0;i<5;i++) {
            	b.Track.get(i).clear();
            }
        }
        for (String s : professor) {
            Professor b = PR.get(s);
            b.ClearRoutine();
        }
        for(int i=0;i<5;i++){
            for(int j=0;j<6;j++){
                LABroutine[i][j]=null;
            }
        }
	}
	static void CreatePDF(){
		ArrayList<String> professor=new ArrayList<String>();
    	Collections.shuffle(ProfessorList);
    	Collections.shuffle(AssociateProfessorList);
    	Collections.shuffle(AssistantProfessorList);
    	professor.addAll(ProfessorList);
		professor.addAll(AssociateProfessorList);
		professor.addAll(AssistantProfessorList);
        for (String s : BatchList) {
            Batch b = B.get(s);
            b.GeneratePdf(s);
        }
        for (String s : professor) {
            Professor b = PR.get(s);
            b.GeneratePdf(s);
        }
    }
	static void ShowList(ArrayList<String> List){
        for(int i=0;i<List.size();i++){
            System.out.print( i+1 +") "+ List.get(i) +" ");
        }
    }
	static void ShowDetails(ArrayList<String> CourseList,Map<String,Course> CR){
        for (String s : CourseList) {
            Course cr = CR.get(s);
            cr.ShowDetails();
        }
    }
	static void ShowBatchRoutine(Map<String,Batch> B,ArrayList<String> batch){
        for(String b : batch){
            Batch BB = B.get(b);
            BB.ShowRoutine();
        }
    }
	///////////////////////////////////////////////////////////////////
	static boolean check(int i,int j) {
		if((LABroutine[i][j]==null || LABroutine[i][j]=="1") && (LABroutine[i][j+1]==null || LABroutine[i][j+1]=="1")  && (LABroutine[i][j+2]==null || LABroutine[i][j+2]=="1")) {
			return true;
		}
		else {
			return false;
		}
	}
	static ArrayList<Integer> GetSlotLab(Professor professor,Batch batch){
        ArrayList<Integer> code = new ArrayList<Integer>();
        for(int i=0;i<5;i++){
            for(int j=0;j<4;j++) {
                if(batch.Track.get(i).contains(professor.ProfessorName)){
                    System.out.println(professor.ProfessorName + " " + i);
                    break;
                }
                if((professor.routine[i][j]==null && batch.routine[i][j]==null) && (professor.routine[i][j+1]==null && batch.routine[i][j+1]==null) &&(professor.routine[i][j+2]==null && batch.routine[i][j+2]==null) && check(i,j)){
                    code.add((i*10) + j);
                }
            }
        }
        System.out.println(code);
        System.out.println(code.size());
        if(code.size() == 0) {
        	ArrayList<Integer> a = new ArrayList<Integer>();
            a.add(-1);
            a.add(-1);
            return a;
        }
        Random rand = new Random();
        int random = rand.nextInt(code.size());
        int x = code.get(random)/10 ;
        int y = code.get(random)-(x*10);
        System.out.println(x+" "+y);
        ArrayList<Integer> a = new ArrayList<Integer>();
        a.add(x);
        a.add(y);
        System.out.println(code);
        return a;
    }
	static ArrayList<Integer> GetSlot(Professor professor,Batch batch){
        ArrayList<Integer> code = new ArrayList<Integer>();
        for(int i=0;i<5;i++){
            for(int j=0;j<6;j++) {
                if(batch.Track.get(i).contains(professor.ProfessorName)){
                    System.out.println(professor.ProfessorName + " " + i);
                    break;
                }
                if(professor.routine[i][j]==null && batch.routine[i][j]==null){
                    code.add((i*10) + j);
                }
            }
        }
        if(code.size() == 0) {
        	ArrayList<Integer> a = new ArrayList<Integer>();
            a.add(-1);
            a.add(-1);
            return a;
        }
        System.out.println(code);
        System.out.println(code.size());
        Random rand = new Random();
        int random = rand.nextInt(code.size());
        int x = code.get(random)/10 ;
        int y = code.get(random)-(x*10);
        System.out.println(x+" "+y);
        ArrayList<Integer> a = new ArrayList<Integer>();
        a.add(x);
        a.add(y);
        System.out.println(code);
        return a;
    }
    public void GenerateRoutine(ActionEvent e) throws IOException{
    	clear();
    	ArrayList<String> professor=new ArrayList<String>();
    	Collections.shuffle(ProfessorList);
    	Collections.shuffle(AssociateProfessorList);
    	Collections.shuffle(AssistantProfessorList);
    	professor.addAll(ProfessorList);
		professor.addAll(AssociateProfessorList);
		professor.addAll(AssistantProfessorList);
		
        for(String s : professor){
            Professor P = PR.get(s);
            Collections.shuffle(P.Lab);
            Collections.shuffle(P.Course);
            for(String cc : P.Lab){
                int PerWeek = 1;
                while(PerWeek != 0)
                {
                    Course nowCourse = CR.get(cc);
                    Batch bb = B.get(nowCourse.BatchName);
                    ArrayList<Integer> slot = GetSlotLab(P, bb);
                    if(slot.get(0) == -1) break;
                    String lab = "LAB-1";
                    if(LABroutine[slot.get(0)][slot.get(1)]==null) {
                    	LABroutine[slot.get(0)][slot.get(1)]="1";
                    }
                    else {
                    	LABroutine[slot.get(0)][slot.get(1)]="2";
                    	lab = "LAB-2";
                    }
                    
                    if(LABroutine[slot.get(0)][slot.get(1)+1]==null) {
                    	LABroutine[slot.get(0)][slot.get(1)+1]="1";
                    }
                    else {
                    	LABroutine[slot.get(0)][slot.get(1)+1]="2";
                    	lab = "LAB-2";
                    }
                    
                    if(LABroutine[slot.get(0)][slot.get(1)+2]==null) {
                    	LABroutine[slot.get(0)][slot.get(1)+2]="1";
                    }
                    else {
                    	LABroutine[slot.get(0)][slot.get(1)+2]="2";
                    	lab = "LAB-2";
                    }
                    for(int i=0;i<5;i++){
                        for(int j=0;j<6;j++){
                            System.out.print(LABroutine[i][j] + " ");
                        }
                        System.out.println("");
                    }
                    String ForProf = nowCourse.CourseCode + "(" + bb.BatchName + ")"+"("+lab+")";
                    String ForBatch = nowCourse.CourseCode + "(" + P.ProfessorCode + ")"+"("+lab+")";
                    bb.routine[slot.get(0)][slot.get(1)] = ForBatch;
                    bb.routine[slot.get(0)][slot.get(1)+1] = ForBatch;
                    bb.routine[slot.get(0)][slot.get(1)+2] = ForBatch;
                    P.routine[slot.get(0)][slot.get(1)] = ForProf;
                    P.routine[slot.get(0)][slot.get(1)+1] = ForProf;
                    P.routine[slot.get(0)][slot.get(1)+2] = ForProf;
                    bb.Track.get(slot.get(0)).add(P.ProfessorName);
                    System.out.println(nowCourse.CourseName + " For " + bb.BatchName + " Assigned to " + P.ProfessorName);
                    System.out.println(bb.Track);
                    PerWeek = PerWeek - 1;
                }
            }
            for(String cc : P.Course){
                int PerWeek = 3;
                while(PerWeek != 0)
                {
                    Course nowCourse = CR.get(cc);
                    Batch bb = B.get(nowCourse.BatchName);
                    ArrayList<Integer> slot = GetSlot(P, bb);
                    if(slot.get(0) == -1) break;
                    String ForProf = nowCourse.CourseCode + "(" + bb.BatchName+")";
                    String ForBatch = nowCourse.CourseCode + "(" + P.ProfessorCode+")";
                    bb.routine[slot.get(0)][slot.get(1)] = ForBatch;
                    P.routine[slot.get(0)][slot.get(1)] = ForProf;
                    bb.Track.get(slot.get(0)).add(P.ProfessorName);
                    System.out.println(nowCourse.CourseName + " For " + bb.BatchName + " Assigned to " + P.ProfessorName);
                    System.out.println(bb.Track);
                    PerWeek = PerWeek - 1;
                }
            }
        }
        for(String Session_name : BatchList) {
        	Batch Ss = B.get(Session_name);
        	SessionRoutine.put(Session_name, Ss.routine);
        	
        }
        
        message("Successfully Generated Routine!");
    }
    
    public void individual() {
        CreatePDF();
        message("Individual Routine Is Created ! Check Your Directory !\nThank You !");
    }
    public void FullRoutine() {
    	DemoGeneratePdf();
    	message("Combined Routine Is Created ! Check Your Directory !\nThank You !");
    }
	
	private Parent root;
	private Stage stage;
	private Scene scene;
	public void message(String msg) {
		Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Successful");
		alert.setContentText(msg);
		ButtonType ok = new ButtonType("OK" , ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(ok);
		alert.showAndWait();
	}
	public boolean warnings(String msg) {
		Alert alert= new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Warnings!");
		alert.setContentText(msg);
		ButtonType ok = new ButtonType("OK");
		ButtonType cancel = new ButtonType("Cancel",ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(ok,cancel);
		Optional<ButtonType> result =alert.showAndWait();
		if(result.get() == ok) return false;
		else return true;
	}
	public boolean Wrong(String msg) {
		Alert alert= new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Warnings!");
		alert.setContentText(msg);
		ButtonType ok = new ButtonType("OK");
		alert.getButtonTypes().setAll(ok);
		Optional<ButtonType> result =alert.showAndWait();
		if(result.get() == ok) return false;
		else return true;
	}
	public void addSessionName(ActionEvent e) throws IOException {
		String SessionName = session.getText();
		if(SessionName.length()==0) {
			Wrong("Fill up the information!");
			return;
		}
		if(B.containsKey(SessionName)) {
			Wrong("Already added this session");
			return;
		}
		String wrnmsg = "Session : " + SessionName +"\nWant to add?";
		if(warnings(wrnmsg)) return;
		Batch batch = new Batch(SessionName);
		BatchList.add(SessionName);
		B.put(SessionName, batch);
		
		String msg = "Session " + SessionName + " is successfully added!";
//		Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
//		alert.setTitle("Successful");
//		System.out.print(msg);
//		alert.setContentText(msg);
//		ButtonType ok = new ButtonType("OK" , ButtonData.CANCEL_CLOSE);
//		alert.getButtonTypes().setAll(ok);
//		Optional<ButtonType> result = alert.showAndWait();
		message(msg);
		session.clear();
	}
	public void addCourseName(ActionEvent e) throws IOException {
		String CourseName = course.getText();
		System.out.println(CourseName.length());
		if(CourseName.length()==0) {
			Wrong("Course name is empty");
			return;
		}
		String Ss_name = SessionSelection.getValue();
		if(Ss_name==null) {
			Wrong("Select a session !");
			return;
		}
		double credit = 0;
		if(credit1.isSelected()) {
			credit = 1.5;
		}
		else if(credit2.isSelected()) {
			credit = 3.0;
		}
		else {
			Wrong("Select Credit And Try Again");
			return;
		}
		
		String CourseCode = coursecode.getText();
		if(CourseCode.length()==0) {
			Wrong("Course Code is empty ! ");
			return;
		}
		if(CR.containsKey(CourseName)) {
			Wrong("Course is already Added ! ");
			return;
		}
		String warnmsg = "Course Name : " + CourseName +"\nCredit : "+ credit + "\nCourse Code : " +CourseCode;
		if(warnings(warnmsg)) return;
		Course c = new Course(CourseName, credit , CourseCode,Ss_name);
		if(credit==1.5) {
			LabList.add(CourseName);
		}
		else {
			CourseList.add(CourseName);
		}
        CR.put(CourseName, c);
        String msg = CourseName+" is successfully Added !";
        RemainCourseList.add(CourseName);
        message(msg);
        credit1.setSelected(false);
		credit2.setSelected(false);
		course.clear();
		coursecode.clear();
		SessionSelection.setValue(null);
        c.ShowDetails();
	}
	public void addProfessorName(ActionEvent e) throws IOException {
		String ProfessorName = professorname.getText();
		String ProfessorNumber = professornumber.getText();
		String ProfessorCode = professorcode.getText();
		String Designation="";
		if(professor.isSelected()) {
			Designation ="Professor";
		}
		else if(associateprofessor.isSelected()) {
			Designation ="Associate Professor";
		}
		else if(assistantprofessor.isSelected()) {
			Designation ="Assistant Professor";
		}
		else if(lecturer.isSelected()) {
			Designation ="Lecturer";
		}
		else {
			Wrong("Select Designation!");
			return;
		}
		if(ProfessorName.length()==0) {
			Wrong("Fill The Professor Name !");
			return;
		}
		if(ProfessorCode.length()==0) {
			Wrong("Fill The Professor Code !");
			return;
		}
		if(PR.containsKey(ProfessorName)) {
			Wrong("Already Added !");
			return;
		}
		Professor p = new Professor(ProfessorName,ProfessorCode,ProfessorNumber,Designation);
		String warnmsg = "Course Name : " + ProfessorName +"\nDesignation : " + Designation;
		if(warnings(warnmsg)) return;
        if(professor.isSelected()) ProfessorList.add(ProfessorName);
        if(associateprofessor.isSelected()) AssociateProfessorList.add(ProfessorName);
        if(assistantprofessor.isSelected()) AssistantProfessorList.add(ProfessorName);
        if(lecturer.isSelected()) AssistantProfessorList.add(ProfessorName);
        PR.put(ProfessorName, p);
        String msg = ProfessorName+" is successfully Added !";
        message(msg);
        professorname.clear();
        professornumber.clear();
        professorcode.clear();
        professor.setSelected(false);
        associateprofessor.setSelected(false);
        assistantprofessor.setSelected(false);
        System.out.print(ProfessorName);
	}
	public void CourseAssignment(ActionEvent e) throws IOException {
		String course = courselist.getValue();
		if(AssignCourseList.contains(course)) {
			message("Already Assigned The Course !");
			return;
		}
		String professor = professorlist.getValue();
		String batch =  batchlist.getValue();
		String warnmsg = "Course Name : " + course +"\nProfessor : "+ professor + "\nSession : " +batch;
		if(warnings(warnmsg)) return;
		Course cr = CR.get(course);
        Professor pr = PR.get(professor);
        CourseAssign(cr,batch,professor);
        ProfessorAddCourse(pr,course,cr.credit);
        message("Succesfully Assigned Course!");
        RemainCourseList.remove(course);
        courselist.setValue(null);
        professorlist.setValue(null);
        batchlist.setValue(null);
	}
	public void Find(){
		ArrayList<String> Courses=new ArrayList<String>();
		String ses = batchlist.getValue();
		for(String lab : RemainCourseList) {
			Course CC = CR.get(lab);
			if(CC.BatchName == ses) {
				Courses.add(CC.CourseName);
			}
		}
		courselist.getItems().clear();
		courselist.getItems().addAll(Courses);
	}
	public void GetSession() {
		if(SessionSelection.getItems().size()!=0) return;
		SessionSelection.getItems().addAll(BatchList);
		System.out.print("Hello");
	}
	public void refresh(){
		ArrayList<String> Courses=new ArrayList<String>();
		ArrayList<String> Professors=new ArrayList<String>();
		Courses.addAll(LabList);
		Courses.addAll(CourseList);
		Professors.addAll(ProfessorList);
		Professors.addAll(AssociateProfessorList);
		Professors.addAll(AssistantProfessorList);
		if(batchlist.getItems().size()!=0) return;
		courselist.getItems().addAll(Courses);
		professorlist.getItems().addAll(Professors);
		batchlist.getItems().addAll(BatchList);
	}
	
	//Course Table
	 @FXML
	    private TableView<TableCourse> CourseTable;

	    @FXML
	    private ChoiceBox<String> EditSessionSelection;

	    @FXML
	    private TextField Editcourse;

	    @FXML
	    private TextField Editcoursecode;

	    @FXML
	    private CheckBox Editcredit1;

	    @FXML
	    private CheckBox Editcredit2;

	    @FXML
	    private TableColumn<TableCourse, String> e_coursecode;

	    @FXML
	    private TableColumn<TableCourse, String> e_coursename;

	    @FXML
	    private TableColumn<TableCourse, Double> e_credits;

	    @FXML
	    private TableColumn<TableCourse, String> e_session;

	    @FXML
	    public void DeleteCourseName() {
	    	if(CourseTable.getSelectionModel().getSelectedIndex() == -1) {
	    		message("Select From Course Table First Then Update ! ");
	    		return;
	    	}
	    	String Name = CourseTable.getSelectionModel().getSelectedItem().CourseName;
	    	String teacher = CR.get(CourseTable.getSelectionModel().getSelectedItem().CourseName).CourseProfessorName;
	    	if(teacher != null) {
	    		Professor p = PR.get(teacher);
	    		for(int i=0;i<p.Lab.size();i++) {
	    			if(p.Lab.get(i) == CourseTable.getSelectionModel().getSelectedItem().CourseName) {
	    				p.Lab.remove(i);
	    			}
	    		}
	    		for(int i=0;i<p.Course.size();i++) {
	    			if(p.Course.get(i) == CourseTable.getSelectionModel().getSelectedItem().CourseName) {
	    				p.Course.remove(i);
	    			}
	    		}
	    	}
	    	for(int i=0; i<CourseList.size() ; i++) {
	    		if(CourseList.get(i) == CourseTable.getSelectionModel().getSelectedItem().CourseName) {
	    			CourseList.remove(i);
	    		}
	    	}
	    	for(int i=0; i<LabList.size() ; i++) {
	    		if(LabList.get(i) == CourseTable.getSelectionModel().getSelectedItem().CourseName) {
	    			LabList.remove(i);
	    		}
	    	}
	    	CR.remove(CourseTable.getSelectionModel().getSelectedItem().CourseName);
	    }

	    @FXML
	    void ShowCourses(ActionEvent event) {
	    	ObservableList<TableCourse> l = FXCollections.observableArrayList();
	    	ArrayList<String> CList = new ArrayList<String>();
	    	CList.addAll(CourseList);
	    	CList.addAll(LabList);
			for(String course : CList) {
				System.out.println(course);
				Course CC = CR.get(course);
				TableCourse t = new TableCourse(CC.CourseName,CC.BatchName,CC.credit,CC.CourseCode);
				l.add(t);
			}
			e_coursename.setCellValueFactory(new PropertyValueFactory<TableCourse,String>("CourseName"));
			e_session.setCellValueFactory(new PropertyValueFactory<TableCourse,String>("Session"));
			e_credits.setCellValueFactory(new PropertyValueFactory<TableCourse,Double>("credits"));
			e_coursecode.setCellValueFactory(new PropertyValueFactory<TableCourse,String>("coursecode"));
			CourseTable.setItems(l);
	    }

	    @FXML
	    public void UpdateCourseName() {
	    	if(CourseTable.getSelectionModel().getSelectedIndex() == -1) {
	    		message("Select From Course Table First Then Update ! ");
	    		return;
	    	}
	    	String c_name = Editcourse.getText();
	    	String b_name = EditSessionSelection.getValue();
	    	double credit = Editcredit1.isSelected() ? 1.5 : 3.00 ;
	    	String c_code = Editcoursecode.getText();
	    	String teacher = CR.get(CourseTable.getSelectionModel().getSelectedItem().CourseName).CourseProfessorName;
	    	System.out.print(c_name+b_name+credit+c_code);
	    	if(warnings("Are You Sure?/n Course Name : "+c_name + "\nCourse Code : "+c_code+"\nCredit : " + credit + "\n Session Name : " + b_name)) return;
	    	CR.remove(CourseTable.getSelectionModel().getSelectedItem().CourseName);
	    	Course Cr = new Course(c_name,credit,c_code,b_name);
	    	if(teacher != null) {
	    		Professor p = PR.get(teacher);
	    		for(int i=0;i<p.Lab.size();i++) {
	    			if(p.Lab.get(i) == CourseTable.getSelectionModel().getSelectedItem().CourseName) {
	    				p.Lab.remove(i);
	    				if(credit == 1.5 )p.Lab.add(c_name);
	    				else p.Course.add(c_name);
	    			}
	    		}
	    		for(int i=0;i<p.Course.size();i++) {
	    			if(p.Course.get(i) == CourseTable.getSelectionModel().getSelectedItem().CourseName) {
	    				p.Course.remove(i);
	    				if(credit == 3.00)p.Course.add(c_name);
	    				else p.Lab.add(c_name);
	    			}
	    		}

	    		RemainCourseList.remove(CourseTable.getSelectionModel().getSelectedItem().CourseName);
	    		RemainCourseList.add(c_name);
	    		Cr.CourseProfessorName = teacher;
	    	}
	    	else {
	    		System.out.print("Null");
	    	}
	    	CR.put(c_name, Cr);
	    	for(int i=0; i<CourseList.size() ; i++) {
	    		if(CourseList.get(i) == CourseTable.getSelectionModel().getSelectedItem().CourseName) {
	    			CourseList.remove(i);
	    			CourseList.add(c_name);
	    		}
	    	}
	    	for(int i=0; i<LabList.size() ; i++) {
	    		if(LabList.get(i) == CourseTable.getSelectionModel().getSelectedItem().CourseName) {
	    			LabList.remove(i);
	    			LabList.add(c_name);
	    		}
	    	}
	    	message("Succefully Updated !! ");
	    }
	    public void GetSessionEdit() {
			if(EditSessionSelection.getItems().size()!=0) return;
			EditSessionSelection.getItems().addAll(BatchList);
			System.out.print("Hello");
		}
	    public void CourseCommit() {
	    	String course_name = CourseTable.getSelectionModel().getSelectedItem().CourseName;
	    	String Batch_name = CourseTable.getSelectionModel().getSelectedItem().Session;
	    	double credit_ = CourseTable.getSelectionModel().getSelectedItem().credits;
	    	String course_code = CourseTable.getSelectionModel().getSelectedItem().coursecode;
	    	Editcourse.setText(course_name);
	    	Editcoursecode.setText(course_code);
	    	if(credit_ == 1.5) {
	    		Editcredit1.setSelected(true);
	    		Editcredit2.setSelected(false);
	    	}
	    	else {
	    		Editcredit2.setSelected(true);
	    		Editcredit1.setSelected(false);
	    	}
	    	GetSessionEdit();
	    	EditSessionSelection.setValue(Batch_name);
	    }
	//
	    
	// Professor Table
	    @FXML
	    private TableView<TableProfessor> ProfessorTable;

	    @FXML
	    private TableColumn<TableProfessor, String> editdesignation;

	    @FXML
	    private TableColumn<TableProfessor, String> editnumber;

	    @FXML
	    private TableColumn<TableProfessor, String> editprofcode;

	    @FXML
	    private TableColumn<TableProfessor, String> editprofname;
	    @FXML
	    private CheckBox check_assistantprofessor;

	    @FXML
	    private CheckBox check_associateprofessor;

	    @FXML
	    private CheckBox check_lecturer;

	    @FXML
	    private CheckBox check_professor;

	    @FXML
	    private TextField edit_professorcode;

	    @FXML
	    private TextField edit_professorname;

	    @FXML
	    private TextField edit_professornumber;
	    public void showProfessor() {
			ObservableList<TableProfessor> l = FXCollections.observableArrayList();
			ArrayList<String> List = new ArrayList<String>();
			List.addAll(ProfessorList);
			List.addAll(AssociateProfessorList);
			List.addAll(AssistantProfessorList);
			for(String Sess : List) {
				System.out.println(Sess);
				Professor p = PR.get(Sess);
				TableProfessor t = new TableProfessor(p.ProfessorName,p.Designation,p.ProfessorNumber,p.ProfessorCode);
				l.add(t);
			}
			editprofname.setCellValueFactory(new PropertyValueFactory<TableProfessor,String>("ProfessorName"));
			editdesignation.setCellValueFactory(new PropertyValueFactory<TableProfessor,String>("Designation"));
			editnumber.setCellValueFactory(new PropertyValueFactory<TableProfessor,String>("ProfessorNumber"));
			editprofcode.setCellValueFactory(new PropertyValueFactory<TableProfessor,String>("ProfessorCode"));
			ProfessorTable.setItems(l);
		}
	    public void ProfessorCommit() {
	        edit_professorname.setText(ProfessorTable.getSelectionModel().getSelectedItem().ProfessorName);
	        edit_professornumber.setText(ProfessorTable.getSelectionModel().getSelectedItem().ProfessorNumber);
	        edit_professorcode.setText(ProfessorTable.getSelectionModel().getSelectedItem().ProfessorCode);
	        String des = ProfessorTable.getSelectionModel().getSelectedItem().Designation;
	        if(des == "Professor") {
	        	check_professor.setSelected(true);
	        	check_associateprofessor.setSelected(false);
	        	check_lecturer.setSelected(false);
	        	check_assistantprofessor.setSelected(false);
	        }
	        else if(des == "Assistant Professor") {
	        	check_professor.setSelected(false);
	        	check_associateprofessor.setSelected(false);
	        	check_lecturer.setSelected(false);
	        	check_assistantprofessor.setSelected(true);
	        }
	        else if(des == "Associate Professor") {
	        	check_professor.setSelected(false);
	        	check_associateprofessor.setSelected(true);
	        	check_lecturer.setSelected(false);
	        	check_assistantprofessor.setSelected(false);
	        }
	        else {
	        	check_professor.setSelected(false);
	        	check_associateprofessor.setSelected(false);
	        	check_lecturer.setSelected(true);
	        	check_assistantprofessor.setSelected(false);
	        }
		 }
	    public void updateProfessor() {
	    	if(ProfessorTable.getSelectionModel().getSelectedIndex() == -1) {
	    		message("Select From Course Table First Then Update ! ");
	    		return;
	    	}
	    	String Name = ProfessorTable.getSelectionModel().getSelectedItem().ProfessorName;
	    	String ChangeName = edit_professorname.getText();
	    	
	    	String designation = ProfessorTable.getSelectionModel().getSelectedItem().Designation;
	    	String ChangeDes = "";
	    	if(check_professor.isSelected()) {
	    		ChangeDes = "Professor";
	    	}
	    	else if (check_assistantprofessor.isSelected()) {
	    		ChangeDes = "Assistant Professor";
	    	}
	    	else if(check_associateprofessor.isSelected()) {
	    		ChangeDes = "Associate Professor";
	    	}
	    	else if(check_lecturer.isSelected()) {
	    		ChangeDes = "Lecturer" ;
	    	}
	    	else {
	    		warnings("Select Designation !");
	    		return;
	    	}
	    	String code = ProfessorTable.getSelectionModel().getSelectedItem().ProfessorCode;
	    	String ChangeCode = edit_professorcode.getText();
	    	
	    	String number =  ProfessorTable.getSelectionModel().getSelectedItem().ProfessorNumber;
	    	String ChangeNumber = edit_professornumber.getText();
    		Professor p = PR.get(Name);
    		Professor PP = new Professor(ChangeName,ChangeCode,ChangeNumber,ChangeDes);
    		PP.Course.addAll(p.Course);
    		PP.Lab.addAll(p.Lab);
    		PR.remove(Name);
    		for(int i=0;i<ProfessorList.size();i++) {
    			if(Name == ProfessorList.get(i)) {
    				ProfessorList.remove(i);
    				ProfessorList.add(ChangeName);
    			}
    		}
    		for(int i=0;i<AssistantProfessorList.size();i++) {
    			if(Name == AssistantProfessorList.get(i)) {
    				AssistantProfessorList.remove(i);
    				AssistantProfessorList.add(ChangeName);
    			}
    		}
    		for(int i=0;i<AssociateProfessorList.size();i++) {
    			if(Name == AssociateProfessorList.get(i)) {
    				AssociateProfessorList.remove(i);
    				AssociateProfessorList.add(ChangeName);
    			}
    		}
    		ArrayList<String> List = new ArrayList<String>();
    		List.addAll(CourseList);
    		List.addAll(LabList);
    		for(String cc : List) {
    			Course c = CR.get(cc);
    			if(c.CourseProfessorName==Name) {
    				c.CourseProfessorName = ChangeName;
    			}
    		}
    		
    		PR.put(ChangeName, PP);
	    }
	    
	    public void DeleteProfessor() {
	    	if(ProfessorTable.getSelectionModel().getSelectedIndex() == -1) {
	    		message("Select From Course Table First Then Delete ! ");
	    		return;
	    	}
	    	String Name = ProfessorTable.getSelectionModel().getSelectedItem().ProfessorName;
	    	for(int i=0;i<ProfessorList.size();i++) {
    			if(Name == ProfessorList.get(i)) {
    				ProfessorList.remove(i);
    			}
    		}
    		for(int i=0;i<AssistantProfessorList.size();i++) {
    			if(Name == AssistantProfessorList.get(i)) {
    				AssistantProfessorList.remove(i);
    			}
    		}
    		for(int i=0;i<AssociateProfessorList.size();i++) {
    			if(Name == AssociateProfessorList.get(i)) {
    				AssociateProfessorList.remove(i);
    			}
    		}
    		ArrayList<String> List = new ArrayList<String>();
    		List.addAll(CourseList);
    		List.addAll(LabList);
    		for(String cc : List) {
    			Course c = CR.get(cc);
    			if(c.CourseProfessorName==Name) {
    				c.CourseProfessorName = null;
    			}
    		}
    		PR.remove(Name);
	    }
	///
	    
	//Course Assignment Table
	    @FXML
	    private TableColumn<TableAssign, String> AssignCourseName;

	    @FXML
	    private TableColumn<TableAssign, String> AssignProfessorName;

	    @FXML
	    private TableColumn<TableAssign, String> AssignSession;

	    @FXML
	    private TableView<TableAssign> AssignTable;
	    
	    public void ShowAssign() {
	    	ObservableList<TableAssign> l = FXCollections.observableArrayList();
	    	ArrayList<String> List = new ArrayList<String>();
	    	List.addAll(CourseList);
	    	List.addAll(LabList);
			for(String Sess : List) {
				System.out.println(Sess);
				Course c = CR.get(Sess);
				TableAssign t = new TableAssign(c.BatchName,c.CourseName,c.CourseProfessorName);
				l.add(t);
			}
			AssignCourseName.setCellValueFactory(new PropertyValueFactory<TableAssign,String>("CourseName"));
			AssignSession.setCellValueFactory(new PropertyValueFactory<TableAssign,String>("SessionName"));
			AssignProfessorName.setCellValueFactory(new PropertyValueFactory<TableAssign,String>("ProfessorName"));
			AssignTable.setItems(l);
	    }
	    public void DeleteAssign() {
	    	String SName = AssignTable.getSelectionModel().getSelectedItem().SessionName;
	    	String CName = AssignTable.getSelectionModel().getSelectedItem().CourseName;
	    	String PName = AssignTable.getSelectionModel().getSelectedItem().ProfessorName;
	    	ArrayList<String> List = new ArrayList<String>();
			List.addAll(ProfessorList);
			List.addAll(AssociateProfessorList);
			List.addAll(AssistantProfessorList);
			for(String s : List) {
				if(PName == s) {
					Professor p = PR.get(PName);
					for(int i=0;i<p.Course.size();i++) {
						if(p.Course.get(i) == CName) {
							p.Course.remove(i);
						}
					}
					for(int i=0;i<p.Lab.size();i++) {
						if(p.Lab.get(i) == CName) {
							p.Lab.remove(i);
						}
					}
				}
			}
			ArrayList<String> CList = new ArrayList<String>();
			CList.addAll(CourseList);
			CList.addAll(LabList);
			for(String ss : CList) {
				Course cc = CR.get(ss);
				if(cc.CourseProfessorName == PName && cc.CourseName == CName) {
					cc.CourseProfessorName = null;
				}
			}
			AssignCourseList.remove(CName);
			RemainCourseList.add(CName);
	    }
	///
	public void DeleteSession() {
		String Name = SessionTable.getSelectionModel().getSelectedItem().name;
		int index = -1;
		for(int i=0;i< BatchList.size() ; i++) {
			if(Name == BatchList.get(i)){
				if(warnings("Are You Sure?("+BatchList.get(i) +" to " + Name +" )")) return;
				index = i;
				B.remove(Name);
				BatchList.remove(i);
				return;
			}
		}
		if(index!=-1) {
			for(String c : CourseList) {
				Course C = CR.get(c);
				if(C.BatchName == Name) {
					C.BatchName = null;
				}
			}
		}
	}
	public void UpdateSession(){
		System.out.print("Hello");
		String SS = SessionTable.getSelectionModel().getSelectedItem().name;
		String newSS = EditSession.getText();
		int index = -1;
		for(int i=0;i< BatchList.size() ; i++) {
			if(SS == BatchList.get(i)) {
				if(warnings("Are You Sure?("+BatchList.get(i) +" to " + newSS +" )")) return;
				index = i;
			}
		}
		if(index!=-1) {
			BatchList.set(index, newSS);
			Batch bbb = new Batch(newSS);
			B.remove(SS);
			B.put(newSS, bbb);
			for(String c : CourseList) {
				Course C = CR.get(c);
				if(C.BatchName == SS) {
					C.BatchName = newSS;
				}
			}
			for(String c : LabList) {
				Course C = CR.get(c);
				if(C.BatchName == SS) {
					C.BatchName = newSS;
				}
			}
		}
	}
	 @FXML
	 public void SessonCommit() {
		 EditSession.setText(SessionTable.getSelectionModel().getSelectedItem().name);
		 System.out.print(SessionTable.getSelectionModel().getSelectedItem().name);
	 }
	public void showSession() {
		ObservableList<TableBatch> l = FXCollections.observableArrayList();
		for(String Sess : BatchList) {
			System.out.println(Sess);
			TableBatch t = new TableBatch(B.get(Sess).BatchName);
			l.add(t);
		}
		SessionData.setCellValueFactory(new PropertyValueFactory<TableBatch,String>("name"));
		SessionTable.setItems(l);
	}
	//
	public void EditAssignScene(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("EditAssignCourse.fxml"));
		root = loader.load();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void EditProfessorScene(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("EditProfessor.fxml"));
		root = loader.load();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void EditCourseScene(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("EditCourse.fxml"));
		root = loader.load();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void EditSessionScene(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("EditBatch.fxml"));
		root = loader.load();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void EditScene(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("EditScene.fxml"));
		root = loader.load();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	 
	public void assignCourseScene(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AssignCourse.fxml"));
		root = loader.load();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void addProfessorScene(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("addProfessor.fxml"));
		root = loader.load();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void addSessionScene(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("addSession.fxml"));
		root = loader.load();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void addCourseScene(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("addCourse.fxml"));
		root = loader.load();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void MainScene(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
		root = loader.load();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	@Override
	public void start(Stage stage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene scene = new Scene(root,Color.ORANGE);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Image image = new Image("/icon.png");
		stage.getIcons().add(image);
		stage.setTitle("Automatic Routine Generator");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
		System.out.print(CourseList);
		//CreatePDF(BatchList,B);
        ShowDetails(CourseList,CR);
        ShowList(CourseList);
        ShowList(ProfessorList);
        ShowList(BatchList);
        ShowBatchRoutine(B,BatchList);
	}
}
