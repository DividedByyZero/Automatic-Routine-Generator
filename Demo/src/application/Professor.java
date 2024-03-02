package application;

import java.util.ArrayList;
public class Professor extends Routine{
    Professor(String name,String Code,String Number,String des){
        this.ProfessorName = name;
        this.ProfessorNumber = Number;
        this.ProfessorCode = Code;
        this.Designation = des;
    }
    String ProfessorName;
    String Designation;
    String ProfessorNumber;
    String ProfessorCode;
    ArrayList<String> Course = new ArrayList<String>();
    ArrayList<String> Lab = new ArrayList<String>();
    void ShowCourses(){
        System.out.println(this.ProfessorName + " Courses : ");
        for(String c : this.Course){
            System.out.println(c);
        }
    }
}
