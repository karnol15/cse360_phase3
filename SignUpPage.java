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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SignUpPage {
    private Stage primaryStage;

    public SignUpPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void show() {
        primaryStage.setTitle("Sign Up");

        Label firstNameLabel = new Label("First Name:");
        TextField firstNameField = new TextField();

        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameField = new TextField();

        Label birthdayLabel = new Label("Birthday:");
        DatePicker birthdayPicker = new DatePicker();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

       
       

        Button signUpButton = new Button("Sign Up");
        

        Button cancelButton = new Button("Cancel");
        

        HBox buttonLayout = new HBox(10);
        buttonLayout.getChildren().addAll(signUpButton, cancelButton);
        buttonLayout.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(firstNameLabel, firstNameField, lastNameLabel, lastNameField,
                birthdayLabel, birthdayPicker, passwordLabel, passwordField, buttonLayout);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        primaryStage.setScene(new Scene(layout, 500, 450));
        primaryStage.show();
        
        //handle all events below here
        
        signUpButton.setOnAction(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            LocalDate birthday = birthdayPicker.getValue();
            String password = passwordField.getText();

            if (validateFields(firstName, lastName, birthday, password)) {
                // Perform sign-up logic here (e.g., save user to database)
                // For simplicity, we'll just display a confirmation message
            	Main.newUser(firstName, lastName, birthday, password);
            	
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

    private boolean validateFields(String firstName, String lastName, LocalDate birthday, String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || birthday == null || password.isEmpty()) {
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
