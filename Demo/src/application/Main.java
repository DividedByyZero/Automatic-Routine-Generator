package application;
	
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;


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
	@FXML
	TextField session;
	@FXML
	TextField course;
	@FXML
	CheckBox credit1;
	@FXML
	CheckBox credit2;
	@FXML
	TextField coursecode;
	@FXML
	TextField professorname;
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
	
	static void CourseAssign(Course cr  , String batch , String professor){
        cr.BatchName = batch;
        cr.CourseProfessorName = professor;
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
	static void CreatePDF(ArrayList<String> BatchList,Map<String,Batch> B){
        for (String s : BatchList) {
            Batch b = B.get(s);
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
	static ArrayList<Integer> GetSlotLab(Professor professor,Batch batch){
        ArrayList<Integer> code = new ArrayList<Integer>();
        for(int i=0;i<5;i++){
            for(int j=0;j<4;j++) {
                if(batch.Track.get(i).contains(professor.ProfessorName)){
                    System.out.println(professor.ProfessorName + " " + i);
                    break;
                }
                if((professor.routine[i][j]==null && batch.routine[i][j]==null) && (professor.routine[i][j+1]==null && batch.routine[i][j+1]==null) &&(professor.routine[i][j+2]==null && batch.routine[i][j+2]==null)){
                    code.add((i*10) + j);
                }
            }
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
                    String ForProf = nowCourse.CourseName + "," + bb.BatchName;
                    String ForBatch = nowCourse.CourseName + "," + P.ProfessorName;
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
                    String ForProf = nowCourse.CourseName + "," + bb.BatchName;
                    String ForBatch = nowCourse.CourseName + "," + P.ProfessorName;
                    bb.routine[slot.get(0)][slot.get(1)] = ForBatch;
                    P.routine[slot.get(0)][slot.get(1)] = ForProf;
                    bb.Track.get(slot.get(0)).add(P.ProfessorName);
                    System.out.println(nowCourse.CourseName + " For " + bb.BatchName + " Assigned to " + P.ProfessorName);
                    System.out.println(bb.Track);
                    PerWeek = PerWeek - 1;
                }
            }
        }
    }
	
	private Parent root;
	private Stage stage;
	private Scene scene;
	public void addSessionName(ActionEvent e) throws IOException {
		String SessionName = session.getText();
		Batch batch = new Batch(SessionName);
		BatchList.add(SessionName);
		B.put(SessionName, batch);
	}
	public void addCourseName(ActionEvent e) throws IOException {
		String CourseName = course.getText();
		double credit = 0;
		if(credit1.isSelected()) {
			credit = 1.5;
		}
		if(credit2.isSelected()) {
			credit = 3.0;
		}
		String CourseCode = coursecode.getText();
		
		Course c = new Course(CourseName, credit , CourseCode);
		if(credit==1.5) {
			LabList.add(CourseName);
		}
		else {
			CourseList.add(CourseName);
		}
        CR.put(CourseName, c);
        c.ShowDetails();
	}
	public void addProfessorName(ActionEvent e) throws IOException {
		String ProfessorName = professorname.getText();
		Professor p = new Professor(ProfessorName);
        if(professor.isSelected()) ProfessorList.add(ProfessorName);
        if(associateprofessor.isSelected()) AssociateProfessorList.add(ProfessorName);
        if(assistantprofessor.isSelected()) AssistantProfessorList.add(ProfessorName);
        PR.put(ProfessorName, p);
        System.out.print(ProfessorName);
	}
	public void CourseAssignment(ActionEvent e) throws IOException {
		String course = courselist.getValue();
		String professor = professorlist.getValue();
		String batch =  batchlist.getValue();
		Course cr = CR.get(course);
        Professor pr = PR.get(professor);
        CourseAssign(cr,batch,professor);
        ProfessorAddCourse(pr,course,cr.credit);
	}
	public void refreshAssignCourse(ActionEvent e) throws IOException {
		ArrayList<String> Courses=new ArrayList<String>();
		ArrayList<String> Professors=new ArrayList<String>();
		Courses.addAll(LabList);
		Courses.addAll(CourseList);
		Professors.addAll(ProfessorList);
		Professors.addAll(AssociateProfessorList);
		Professors.addAll(AssistantProfessorList);
		courselist.getItems().addAll(Courses);
		professorlist.getItems().addAll(Professors);
		batchlist.getItems().addAll(BatchList);
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
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
		System.out.print(CourseList);
		CreatePDF(BatchList,B);
        ShowDetails(CourseList,CR);
        ShowList(CourseList);
        ShowList(ProfessorList);
        ShowList(BatchList);
        ShowBatchRoutine(B,BatchList);
	}
}
