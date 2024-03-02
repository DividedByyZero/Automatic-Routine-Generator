package application;

public class TableCourse {
	String CourseName;
	String Session;
	double credits;
	String coursecode;
	public String getCoursecode() {
		return coursecode;
	}
	public void setCoursecode(String coursecode) {
		this.coursecode = coursecode;
	}
	public String getCourseName() {
		return CourseName;
	}
	public TableCourse(String courseName, String session, double credits, String coursecode) {
		super();
		CourseName = courseName;
		Session = session;
		this.credits = credits;
		this.coursecode = coursecode;
	}
	public TableCourse(String courseName, String session, double credits) {
		super();
		CourseName = courseName;
		Session = session;
		this.credits = credits;
	}
	public void setCourseName(String courseName) {
		CourseName = courseName;
	}
	public String getSession() {
		return Session;
	}
	public void setSession(String session) {
		Session = session;
	}
	public double getCredits() {
		return credits;
	}
	public void setCredits(double credits) {
		this.credits = credits;
	}
	
}
