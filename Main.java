
import java.util.HashMap;
import java.util.Random;
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

import java.util.Random;

public class Main extends Application {
	
	public static HashMap<String, User> userMap = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	public void init() {
		
		try {
        	File f = new File("hash.txt");
        	if (!f.exists()) {userMap = new HashMap<String, User>(); return;}
        	FileInputStream fos = new FileInputStream("hash.txt");
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
		
  	File patientInfo = new File("hash.txt");
    	
    	try {
    	patientInfo.delete();
    	patientInfo.createNewFile();
    	
    	FileOutputStream fos = new FileOutputStream("hash.txt");
    	ObjectOutputStream oos = new ObjectOutputStream(fos);
    	oos.writeObject(userMap);
    	
   
    	fos.close();
    	oos.close();
    	} catch (IOException e) {
    		System.out.println("didn't work");
    	}
		
	}
	
	public static void newUser(String fName, String lName, LocalDate bday, String password, int type) {
		
		//logic to form the userName
		String formattedBirthday = DateTimeFormatter.ofPattern("MMddyy").format(bday);
		
		String patientID = fName.substring(0, 1) + lName + formattedBirthday.substring(0,4);
		
		switch (type) {
		case 0:
			userMap.putIfAbsent(patientID, new Patient(fName, lName, bday, password.hashCode()));
		case 1:
			userMap.putIfAbsent(patientID, new Nurse(fName, lName, bday, password.hashCode()));
		case 2:
			userMap.putIfAbsent(patientID, new Doctor(fName, lName, bday, password.hashCode()));
		}
		
	}
	
	private int genPatID() {
        Random rand = new Random();
        return rand.nextInt(90000) + 10000;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    

	
    
}
