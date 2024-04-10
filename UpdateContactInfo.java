import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.RadioButton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UpdateContactInfo {
    private Stage primaryStage;
    private Patient user;

    public UpdateContactInfo(Stage primaryStage, Patient user) {
        this.primaryStage = primaryStage;
        this.user = user;
    }

    public void show() {
    	
    	
        primaryStage.setTitle("Sign Up");

        Label firstNameLabel = new Label("Address:");
        TextField firstNameField = new TextField();

        Label lastNameLabel = new Label("Phone Number:");
        TextField lastNameField = new TextField();

        Label birthdayLabel = new Label("Email Address:");
        TextField birthdayPicker = new TextField();


       
       

        Button signUpButton = new Button("Save");
        

        Button cancelButton = new Button("Cancel");
        
        

        HBox buttonLayout = new HBox(10);
        buttonLayout.getChildren().addAll(signUpButton, cancelButton);
        buttonLayout.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(firstNameLabel, firstNameField, lastNameLabel, lastNameField,
                birthdayLabel, birthdayPicker, buttonLayout);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        primaryStage.setScene(new Scene(layout, 500, 450));
        primaryStage.show();
        
        //handle all events below here
        
        signUpButton.setOnAction(e -> {
        	int lastName = 0;
            String firstName = firstNameField.getText();
            try {
            	lastName = Integer.parseInt(firstNameField.getText());
            } catch (NumberFormatException e1) {
            	showError("Error", "Phone Number must be a number.");
            }
            String birthday = birthdayPicker.getText();

            if (firstName.trim().isEmpty() || birthday.trim().isEmpty()) {
                // Perform sign-up logic here (e.g., save user to database)
                // For simplicity, we'll just display a confirmation message
            	showError("Error", "Please fill out all fields.");
                
              
            }
            else {
            	user.addContactInfo(firstName, lastName, birthday);
            	showConfirmation();
            }
        });
        
        //take the patient to the welcome screen
        cancelButton.setOnAction(e -> {
        	WelcomePage home = new WelcomePage(primaryStage);
            home.show();
        });
        
        
    }

    private boolean validateFields(String firstName, String lastName, LocalDate birthday) {
        if (firstName.isEmpty() || lastName.isEmpty() || birthday == null) {
            showError("Error", "Please fill out all fields.");
            return false;
        }
        // Additional validation logic can be added here if needed
        return true;
    }

    private void showConfirmation() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sign Up Successful");
        alert.setHeaderText(null);
        alert.setContentText("Welcome, " + user.getFName() + "! You have successfully updated your contact info.");
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