package application;

import java.util.ArrayList;
public class Professor extends Routine{
    Professor(String name){
        this.ProfessorName = name;
    }
    String ProfessorName;
    ArrayList<String> Course = new ArrayList<String>();
    ArrayList<String> Lab = new ArrayList<String>();
    void ShowCourses(){
        System.out.println(this.ProfessorName + " Courses : ");
        for(String c : this.Course){
            System.out.println(c);
        }
    }
}
