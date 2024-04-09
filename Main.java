
import java.util.HashMap;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static HashMap<String, User> userMap = new HashMap<>();
	
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
        	
        	} catch (Exception e1) {System.out.println("didn't work");}
		
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
	
	public static void newUser(String fName, String lName, LocalDate bday, String password) {
		userMap.putIfAbsent(fName + lName, new User(fName, lName, bday, password.hashCode()));
	}

    public static void main(String[] args) {
        launch(args);
    }
    
    

	
    
}

