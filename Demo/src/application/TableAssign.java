package application;

public class TableAssign {
	String SessionName;
	String CourseName;
	String ProfessorName;
	public TableAssign(String sessionName, String courseName, String professorName) {
		super();
		SessionName = sessionName;
		CourseName = courseName;
		ProfessorName = professorName;
	}
	public String getSessionName() {
		return SessionName;
	}
	public void setSessionName(String sessionName) {
		SessionName = sessionName;
	}
	public String getCourseName() {
		return CourseName;
	}
	public void setCourseName(String courseName) {
		CourseName = courseName;
	}
	public String getProfessorName() {
		return ProfessorName;
	}
	public void setProfessorName(String professorName) {
		ProfessorName = professorName;
	}
}
