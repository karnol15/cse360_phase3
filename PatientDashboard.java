import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PatientDashboard {
    private Stage primaryStage;
    private String patientFirstName;
    private String patientLastName;
    private int patientAge;
    private String patientBirthday;

    public PatientDashboard(Stage primaryStage, String firstName, String lastName, int age, String birthday) {
        this.primaryStage = primaryStage;
        this.patientFirstName = firstName;
        this.patientLastName = lastName;
        this.patientAge = age;
        this.patientBirthday = birthday;
    }

    public void show() {
        primaryStage.setTitle("Patient Dashboard");
        
        


        
        // Display patient information
        Label nameLabel = new Label("Name: " + patientFirstName + " " + patientLastName);
        Label ageLabel = new Label("Age: " + patientAge);
        Label birthdayLabel = new Label("Birthday: " + patientBirthday);

        // Buttons for various actions

        // Example: Button to view contact info
        Button viewContactInfoButton = new Button("View Contact Info");

        // Example: Button to update contact info
        Button updateContactInfoButton = new Button("Update Contact Info");

        // Example: Button to view visit history
        Button viewVisitHistoryButton = new Button("View Visit History");

        // Example: Button to send messages to staff
        Button sendMessageToStaffButton = new Button("Send Message to Staff");
        
        

        // Layout for patient dashboard
        VBox layout = new VBox(10);
        layout.getChildren().addAll(nameLabel, ageLabel, birthdayLabel, viewContactInfoButton, updateContactInfoButton, viewVisitHistoryButton, sendMessageToStaffButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        primaryStage.setScene(new Scene(layout, 850, 700));
        primaryStage.show();
        
        //handle events below here
        
        viewContactInfoButton.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	SignInPage signInPage = new SignInPage(primaryStage);
                signInPage.show();
            }
        });
        
        //this changes the scene to update contact info
        updateContactInfoButton.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	SignUpPage signUpPage = new SignUpPage(primaryStage);
                signUpPage.show();
            }
        });
        
        viewVisitHistoryButton.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	SignInPage signInPage = new SignInPage(primaryStage);
                signInPage.show();
            }
        });
        
        //this changes the scenne to update contact info
        sendMessageToStaffButton.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	SignUpPage signUpPage = new SignUpPage(primaryStage);
                signUpPage.show();
            }
        });
        
        
    }

    private Object sendMessageToStaff() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object viewVisitHistory() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object updateContactInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object viewContactInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	private void viewMedicalHistory() {
        // Implement functionality to view medical history
        System.out.println("Viewing medical history...");
    }

    private void makeAppointment() {
        // Implement functionality to make appointment
        System.out.println("Making appointment...");
    }
}
