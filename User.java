import java.io.Serializable;
import java.time.LocalDate;

public class User implements Serializable {
	
	private String fName;
	private String lName;
	private LocalDate birthday;
	private int hashPword;
	
	
	public User(String fName, String lName, LocalDate birthday, int pass) {
		this.fName = fName;
		this.lName = lName;
		this.birthday = birthday;
		hashPword = pass;
	}
	
	public String getFName() {return fName;}
	public String getLName() {return lName;}
	public String getbDay() {return birthday.toString();}
	public int getAge() {
		
		if (LocalDate.now().compareTo(birthday) - 1 <= 0) {
			return 0;
		}
		return LocalDate.now().compareTo(birthday) - 1;
	}

	
	public int getHash() {
		return hashPword;
	}

}