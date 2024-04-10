import java.time.LocalDate;

public class Patient extends User{
	String address;
	int pNumber;
	String email;

        // Constructor
        public Patient(String fName, String lName, LocalDate birthday, int pass) {
    		super(fName, lName, birthday, pass);
    	}
        
        public void addContactInfo(String addy, int pNum, String emmail) {
        	address = addy;
        	pNumber = pNum;
        	email = emmail;
        }
        
        public String getContactInfo() {
        	return address;
        }

        
    } 
    