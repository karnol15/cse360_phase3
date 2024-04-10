import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class PatientDashboard {
	private Patient user;
    private Stage primaryStage;
    private String patientFirstName;
    private String patientLastName;
    private int patientAge;
    private String patientBirthday;

    public PatientDashboard(Stage primaryStage, User rlUser) {
    	user = (Patient)rlUser;
        this.primaryStage = primaryStage;
        this.patientFirstName = rlUser.getFName();
        this.patientLastName = rlUser.getLName();
        this.patientAge = rlUser.getAge();
        this.patientBirthday = rlUser.getbDay();
    }

    public void show() {
        primaryStage.setTitle("Patient Dashboard");
        
        // Label for the title
        Label titleLabel = new Label("Pediatric Doctor's Office");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        Label dashLabel = new Label("Patient Dashboard");
        dashLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        // Display patient information
        Label nameLabel = new Label("Name: " + patientFirstName + " " + patientLastName);
        Label ageLabel = new Label("Age: " + patientAge);
        Label birthdayLabel = new Label("Birthday: " + patientBirthday);

        // Buttons for various actions
        Button viewContactInfoButton = new Button("View Contact Info");
        Button updateContactInfoButton = new Button("Update Contact Info");
        Button viewVisitHistoryButton = new Button("View Visit History");
        Button sendMessageToStaffButton = new Button("Send Message to Staff");

        // Set button widths
        viewContactInfoButton.setPrefWidth(200);
        updateContactInfoButton.setPrefWidth(200);
        viewVisitHistoryButton.setPrefWidth(200);
        sendMessageToStaffButton.setPrefWidth(200);

        // Horizontal layout for buttons
        HBox buttonBox = new HBox(20);
        buttonBox.getChildren().addAll(viewContactInfoButton, updateContactInfoButton, viewVisitHistoryButton, sendMessageToStaffButton);
        buttonBox.setAlignment(Pos.CENTER);

        // Vertical layout for patient information and buttons
        VBox layout = new VBox(20);
        layout.getChildren().addAll(titleLabel, dashLabel, nameLabel, ageLabel, birthdayLabel, buttonBox);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        primaryStage.setScene(new Scene(layout, 850, 700));
        primaryStage.show();

        
        //handle events below here
        
        viewContactInfoButton.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	System.out.println(user.getContactInfo());
            }
        });
        
        //this changes the scene to update contact info
        updateContactInfoButton.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	UpdateContactInfo signUpPage = new UpdateContactInfo(primaryStage, user);
                signUpPage.show();
            }
        });
        
        viewVisitHistoryButton.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	SignInPage signInPage = new SignInPage(primaryStage);
                signInPage.show();
            }
        });
        
        //this changes the scene to update contact info
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
