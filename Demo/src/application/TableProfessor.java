package application;

public class TableProfessor {
	String ProfessorName;
    String Designation;
    String ProfessorNumber;
    String ProfessorCode;
	public TableProfessor(String professorName, String designation, String professorNumber, String professorCode) {
		super();
		ProfessorName = professorName;
		Designation = designation;
		ProfessorNumber = professorNumber;
		ProfessorCode = professorCode;
	}
	public String getProfessorName() {
		return ProfessorName;
	}
	public void setProfessorName(String professorName) {
		ProfessorName = professorName;
	}
	public String getDesignation() {
		return Designation;
	}
	public void setDesignation(String designation) {
		Designation = designation;
	}
	public String getProfessorNumber() {
		return ProfessorNumber;
	}
	public void setProfessorNumber(String professorNumber) {
		ProfessorNumber = professorNumber;
	}
	public String getProfessorCode() {
		return ProfessorCode;
	}
	public void setProfessorCode(String professorCode) {
		ProfessorCode = professorCode;
	}
	
}
