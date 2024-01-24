package application;

public class Course {
    String CourseName;
    String CourseCode;
    double credit;
    String BatchName;
    String CourseTeacherName;

    String CourseProfessorName;
    public Course(String s,double cr,String code){
        this.CourseName = s;
        this.credit = cr;
        this.CourseCode = code;
    }
    void ShowDetails(){
        System.out.println("Course Name : " + this.CourseName);
        System.out.println("Batch Name : " + this.BatchName);
        System.out.println("Professor Name : " + this.CourseProfessorName);
    }
}
