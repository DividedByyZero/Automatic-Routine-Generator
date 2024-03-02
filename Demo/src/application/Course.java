package application;

public class Course {
    String CourseName;
    String CourseCode;
    double credit;
    String BatchName;
    String CourseProfessorName;
    public Course(String s,double cr,String code,String b){
        this.CourseName = s;
        this.credit = cr;
        this.CourseCode = code;
        this.BatchName = b;
    }
    void ShowDetails(){
        System.out.println("Course Name : " + this.CourseName);
        System.out.println("Batch Name : " + this.BatchName);
        System.out.println("Professor Name : " + this.CourseProfessorName);
    }
}
