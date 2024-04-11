
import java.util.HashMap;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static HashMap<String, Patient> patientMap = new HashMap<>(); // Store Patient objects
    private static final String PATIENT_FILE_NAME = "userHash.txt"; // New file name
    
    public static HashMap<String, User> userMap = new HashMap<>(); //store Staff objects
    private static final String STAFF_FILE_NAME = "staffHash.txt"; // New file name
	
	public void init() {
		
		//initialize Patient file
		try {
        	File f = new File(PATIENT_FILE_NAME);
        	if (!f.exists()) {
        		patientMap = new HashMap<String, Patient>(); // Store Patient objects
        		return;
        	}
        	FileInputStream fos = new FileInputStream(PATIENT_FILE_NAME);
        	ObjectInputStream oos = new ObjectInputStream(fos);
        	patientMap = (HashMap <String, Patient>)oos.readObject();        	
       
        	fos.close();
        	oos.close();
        	
        	
        	if (patientMap == null) {
        		System.out.println("null");
        	}
        	
        	} catch (Exception e1) {System.out.println("didn't work");}
		
		try {
        	File f = new File(STAFF_FILE_NAME);
        	if (!f.exists()) {userMap = new HashMap<String, User>(); return;}
        	FileInputStream fos = new FileInputStream(STAFF_FILE_NAME);
        	ObjectInputStream oos = new ObjectInputStream(fos);
        	userMap = (HashMap <String, User>)oos.readObject();        	
       
        	fos.close();
        	oos.close();
        	
        	
        	if (userMap == null) {
        		System.out.println("null");
        	}
        	
        	} catch (Exception e1) {System.out.println("Files are corrupted. Delete old files and try again.");}
		
	}

	
	@Override
    public void start(Stage primaryStage) {
        // Create and display the welcome page
        WelcomePage welcomePage = new WelcomePage(primaryStage);
        welcomePage.show();
    }
	
	public void stop() {
		
		File patientInfo = new File(PATIENT_FILE_NAME);
		
		File staffInfo = new File(STAFF_FILE_NAME);
    	
    	try {
    	patientInfo.delete();
    	patientInfo.createNewFile();
    	
    	FileOutputStream fos = new FileOutputStream(PATIENT_FILE_NAME);
    	ObjectOutputStream oos = new ObjectOutputStream(fos);
    	oos.writeObject(patientMap);
    	
   
    	fos.close();
    	oos.close();
    	} catch (IOException e) {
    		System.out.println("didn't work");
    	}
    	
    	try {
        	staffInfo.delete();
        	staffInfo.createNewFile();
        	
        	FileOutputStream fos = new FileOutputStream(STAFF_FILE_NAME);
        	ObjectOutputStream oos = new ObjectOutputStream(fos);
        	oos.writeObject(userMap);
        	
       
        	fos.close();
        	oos.close();
        	} catch (IOException e) {
        		System.out.println("didn't work");
        	}
		
	}
	
	// Create a new patient and add to the patient map
    public static Patient newPatient(String fName, String lName, LocalDate birthday, String password, String address, long phoneNumber, String email) {
        String formattedBirthday = DateTimeFormatter.ofPattern("MMddyy").format(birthday);
        String patientID = fName.substring(0, 1) + lName + formattedBirthday.substring(0, 4);
        Patient newPatient = new Patient(fName, lName, birthday, password.hashCode(), address, phoneNumber, email);
        patientMap.putIfAbsent(patientID, newPatient);
        return newPatient;
    }
    
	public static void newUser(String fName, String lName, LocalDate bday, String password, int type) {
			
			//logic to form the userName
			String formattedBirthday = DateTimeFormatter.ofPattern("MMddyy").format(bday);
			
			String patientID = fName.substring(0, 1) + lName + formattedBirthday.substring(0,4);
			
			switch (type) {
			case 0:
				userMap.putIfAbsent(patientID, new Doctor(fName, lName, bday, password.hashCode()));
			case 1:
				userMap.putIfAbsent(patientID, new Nurse(fName, lName, bday, password.hashCode()));
			
				
			}
			
		}

    public static void main(String[] args) {
        launch(args);
    }
    
    

	
    
}
