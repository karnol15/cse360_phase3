import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SignUpPage {
    private Stage primaryStage;

    public SignUpPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void show() {
        primaryStage.setTitle("Sign Up");
        
        Label welcomeLabel= new Label("Welcome! Thank you for picking our Pediatric Office.");
        
        Label welcomeLabel2 = new Label("\n We look forward to providing you with the utmost care and attention.");
        
        welcomeLabel.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 32));
        welcomeLabel2.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 24));

        Label firstNameLabel = new Label("First Name:");
        TextField firstNameField = new TextField();

        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameField = new TextField();

        Label birthdayLabel = new Label("Birthday:");
        DatePicker birthdayPicker = new DatePicker();
        
        // Create labels and text fields for vitals input
        Label addressLabel = new Label("Address: ");
        TextField addressField = new TextField();
        Label phoneNumberLabel = new Label("Phone Number: ");
        TextField phoneNumberField = new TextField();
        Label emailLabel = new Label("Email: ");
        TextField emailField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button signUpButton = new Button("Sign Up");
        

        Button cancelButton = new Button("Cancel");
        

        HBox buttonLayout = new HBox(10);
        buttonLayout.getChildren().addAll(signUpButton, cancelButton);
        buttonLayout.setAlignment(Pos.CENTER);

        
        //make the textareas wider
        firstNameField.setPrefWidth(220);
        lastNameField.setPrefWidth(220);
        emailField.setPrefWidth(220);
        birthdayPicker.setPrefWidth(220);
        addressField.setPrefWidth(220);
        phoneNumberField.setPrefWidth(220);
        passwordField.setPrefWidth(220);
        
        // Create grid layout for the vitals input fields
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.addRow(0, firstNameLabel, firstNameField);
        gridPane.addRow(1, lastNameLabel, lastNameField);
        gridPane.addRow(2, emailLabel, emailField);
        gridPane.addRow(3, birthdayLabel, birthdayPicker);
        gridPane.addRow(4, addressLabel, addressField);
        gridPane.addRow(5, phoneNumberLabel, phoneNumberField);
        gridPane.addRow(6, passwordLabel, passwordField);
        //set the alignment
        GridPane.setHalignment(buttonLayout, HPos.CENTER);
        GridPane.setColumnSpan(buttonLayout, 2);
        gridPane.addRow(7,buttonLayout);

        VBox finalLayout = new VBox(10);
        finalLayout.getChildren().addAll(welcomeLabel, welcomeLabel2, gridPane);
        finalLayout.setAlignment(Pos.CENTER);

        primaryStage.setScene(new Scene(finalLayout, 850, 700));
        primaryStage.show();
        
        //handle all events below here
        
        signUpButton.setOnAction(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            LocalDate birthday = birthdayPicker.getValue();
            String address = addressField.getText();
            String phoneNumber = phoneNumberField.getText();
            
            long parsedPhoneNumber = 0;
            try {
                parsedPhoneNumber = Long.parseLong(phoneNumber);
            } catch (NumberFormatException ex) {
                showError("Error", "Invalid phone number format.");
                return;
            }
            
            String email = emailField.getText();
            String password = passwordField.getText();

            if (validateFields(firstName, lastName, birthday, address, phoneNumber, email, password)) {
            	
            	// Create a new patient
                Patient patient = Main.newPatient(firstName, lastName, birthday, password, address, parsedPhoneNumber, email);
                
                // Generate patient ID
                String formattedBirthday = DateTimeFormatter.ofPattern("MMddyy").format(birthday);
                String username = generatePatientID(firstName, lastName, formattedBirthday);
            	showConfirmation(firstName, username);
            }
        });
        
        //take the patient to the welcome screen
        cancelButton.setOnAction(e -> {
        	WelcomePage home = new WelcomePage(primaryStage);
            home.show();
        });
        
        
    }

    private boolean validateFields(String firstName, String lastName, LocalDate birthday,
        String address, String phoneNumber, String email, String password) {
		if (firstName.isEmpty() || lastName.isEmpty() || birthday == null || address.isEmpty() ||
					phoneNumber.isEmpty() || email.isEmpty() || password.isEmpty()) {
			showError("Error", "Please fill out all fields.");
			return false;
		}
		// Additional validation logic can be added here if needed
		return true;
	}

    private void showConfirmation(String firstName, String username) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sign Up Successful");
        alert.setHeaderText(null);
        alert.setContentText("Welcome, " + firstName + "! You have successfully signed up.\nYour username is: "+username);
        alert.showAndWait();
        WelcomePage home = new WelcomePage(primaryStage);
        home.show();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private String generatePatientID(String firstName, String lastName, String formattedBirthday) {
        
        // Example: '2MJones080701'
        // We'll assume here that you have access to the number of patients with the same name.
        // For simplicity, we'll use '2' for the example.
        String patientID = firstName.substring(0, 1) + lastName + formattedBirthday.substring(0,4);
        return patientID;
    }
    
    
}
