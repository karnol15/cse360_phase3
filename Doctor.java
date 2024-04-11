import java.time.LocalDate;

//this class extends user and is used as more of a formality
public class Doctor extends User {
	public Doctor(String fName, String lName, LocalDate birthday, int pass) {
		super(fName, lName, birthday, pass);
	}

}