import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javafx.scene.control.DatePicker;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import java.time.LocalDate;
import java.util.Optional;

import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;

import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



public class NurseDashboard {
    private Stage primaryStage;
    private String patientFirstName;
    private String patientLastName;
    private int patientAge;
    private String patientBirthday;

    public NurseDashboard(Stage primaryStage, String firstName, String lastName, int age, String birthday) {
        this.primaryStage = primaryStage;
        this.patientFirstName = firstName;
        this.patientLastName = lastName;
        this.patientAge = age;
        this.patientBirthday = birthday;
    }

    public void show() {
        primaryStage.setTitle("Nurse Dashboard");

        // Create an ImageView with the image
        Image image = new Image("nurseDashBanner.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(500);

        // Set preserve ratio to true so that image won't stretch disproportionately
        imageView.setPreserveRatio(true);

        // Create a VBox and add the ImageView to it
        HBox logoBox = new HBox();
        logoBox.getChildren().add(imageView);
        logoBox.setAlignment(Pos.CENTER);

        // Set preferred width for the VBox
        logoBox.setPrefWidth(750);

        Label dashLabel = new Label("Nurse Dashboard");
        dashLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Display nurse information
        Label welcomeLabel = new Label("Welcome back, Nurse");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Display patient information
        Label actionLabel = new Label("What would you like to do?");
        actionLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Buttons for various actions
        Button viewMedicalHistoryButton = new Button("View Medical History");
        Button sendMessageToPatientButton = new Button("Send Message to Patient");
        Button takeVitalsButton = new Button("Take Vitals");

        Button logOutButton = new Button("Logout");

        // Layout for nurse dashboard
        HBox buttonsLayout = new HBox(10);
        buttonsLayout.getChildren().addAll(viewMedicalHistoryButton, sendMessageToPatientButton, takeVitalsButton);
        buttonsLayout.setAlignment(Pos.CENTER);
        VBox layout = new VBox(10);
        layout.getChildren().addAll(dashLabel, logoBox, welcomeLabel, actionLabel, buttonsLayout, logOutButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        primaryStage.setScene(new Scene(layout, 850, 700));
        primaryStage.show();

        // Set action handlers for buttons (if needed)
        viewMedicalHistoryButton.setOnAction(e -> viewMedicalHistory());
        sendMessageToPatientButton.setOnAction(e -> openSendMessagePopup());
        takeVitalsButton.setOnAction(e -> takeVitals());

        logOutButton.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	WelcomePage home = new WelcomePage(primaryStage);
                home.show();
            }
        });
    }


    private void viewMedicalHistory() {
        // Create labels and text fields for medication details
        Label patientUsername = new Label("Enter the Patient's username whose history you wish to view:");
        TextField usernameField = new TextField();
        HBox fieldBox = new HBox(usernameField);
        fieldBox.setPrefWidth(250);
        fieldBox.setAlignment(Pos.CENTER);

        // Create a button to save the entered findings
        Button confirmButton = new Button("Retrieve Medical History");

        VBox confirmBox = new VBox(10);
        confirmBox.getChildren().addAll(patientUsername, fieldBox, confirmButton);
        confirmBox.setAlignment(Pos.CENTER);

        // Create a new stage for recording findings
        Stage findingsStage = new Stage();
        findingsStage.setTitle("View Medical History");
        findingsStage.setScene(new Scene(confirmBox, 400, 250));
        findingsStage.show();

        confirmButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            if (!username.isEmpty()) {
                displayMedicalHistory(username);
                findingsStage.close();
            } else {
                showAlert("Error", "Please enter the patient's username.");
            }
        });
    }


    private void displayMedicalHistory(String username) {
        // Logic to read medical history from the file and display it in the text areas
        String medicalHistoryDirectory = "medical_history/";
        String medicalHistoryFilePath = medicalHistoryDirectory + username + "_medical_history.txt";

        try {
            // Open the medical history file
            File file = new File(medicalHistoryFilePath);
            if (!file.exists()) {
                // If the file does not exist, create it
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs(); // Create parent directories if they don't exist
                }
                file.createNewFile(); // Create the file
            }

            // Create text areas for medical history data
            TextArea healthIssuesTextArea = new TextArea();
            TextArea prescriptionsTextArea = new TextArea();
            TextArea immunizationsTextArea = new TextArea();
            TextArea recommendationsTextArea = new TextArea();
            // Set  height for each text area
            healthIssuesTextArea.setPrefHeight(80); // Adjust the height as needed
            prescriptionsTextArea.setPrefHeight(80);
            immunizationsTextArea.setPrefHeight(80);
            recommendationsTextArea.setPrefHeight(80);

            // Read the medical history from the file and populate the text areas
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Read each line and set the text of corresponding text areas
            healthIssuesTextArea.setText(bufferedReader.readLine());
            prescriptionsTextArea.setText(bufferedReader.readLine());
            immunizationsTextArea.setText(bufferedReader.readLine());
            recommendationsTextArea.setText(bufferedReader.readLine());

            bufferedReader.close();

            // Create a GridPane to display the medical history
            GridPane gridPane = new GridPane();
            gridPane.setPadding(new Insets(10));
            gridPane.setVgap(10);
            gridPane.setHgap(10);

            // Add labels and text areas to the grid pane
            gridPane.add(new Label("Health Issues:"), 0, 0);
            gridPane.add(healthIssuesTextArea, 1, 0);
            gridPane.add(new Label("Prescriptions:"), 0, 1);
            gridPane.add(prescriptionsTextArea, 1, 1);
            gridPane.add(new Label("Immunizations:"), 0, 2);
            gridPane.add(immunizationsTextArea, 1, 2);
            gridPane.add(new Label("Recommendations:"), 0, 3);
            gridPane.add(recommendationsTextArea, 1, 3);

            // Add save button to the grid pane
            Button saveButton = new Button("Save");
            gridPane.add(saveButton, 1, 4);

            // Action handler for the save button
            saveButton.setOnAction(event -> {
                try {
                    FileWriter fileWriter = new FileWriter(file);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                    // Write the text from text areas to the file
                    bufferedWriter.write(healthIssuesTextArea.getText() + "\n");
                    bufferedWriter.write(prescriptionsTextArea.getText() + "\n");
                    bufferedWriter.write(immunizationsTextArea.getText() + "\n");
                    bufferedWriter.write(recommendationsTextArea.getText() + "\n");

                    bufferedWriter.close();

                    // Show a confirmation message
                    showAlert("Success", "Medical history saved successfully.");
                } catch (IOException e) {
                    // Handle file writing errors
                    e.printStackTrace();
                    showAlert("Error", "Failed to save medical history.");
                }
            });

            // Show the medical history UI containing the GridPane
            Stage medicalHistoryStage = new Stage();
            medicalHistoryStage.setTitle("Medical History");
            medicalHistoryStage.setScene(new Scene(gridPane));
            medicalHistoryStage.show();

        } catch (IOException e) {
            // Handle file creation or reading errors
            e.printStackTrace();
            // Show an error dialog to the user
            showAlert("Error", "Failed to read medical history.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Helper method to show error messages
    /*private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }*/
    
    
	private void openSendMessagePopup() {
		// Create a new stage for the popup
        Stage popupStage = new Stage();

        // Create labels and text field for patient username input
        Label usernameLabel = new Label("Enter patient's username:");
        TextField usernameField = new TextField();
        Button viewMessagesButton = new Button("View Messages");

        // Layout for popup
        VBox popupLayout = new VBox(10);
        popupLayout.getChildren().addAll(usernameLabel, usernameField, viewMessagesButton);
        popupLayout.setAlignment(Pos.CENTER);
        popupStage.setScene(new Scene(popupLayout, 400, 200));
        popupStage.show();

        viewMessagesButton.setOnAction(e -> {
            String patientUsername = usernameField.getText().trim();
            if (!patientUsername.isEmpty()) {
                // Initialize MessageSystem with patient's username
                MessageSystem messageSystem = new MessageSystem(patientUsername);
                messageSystem.sendMessageToStaff(patientUsername);
            } else {
                showError("Error", "Please enter the patient's username.");
            }
        });
    }
    

    // Helper method to show error messages
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void takeVitals() {
        Stage vitalsStage = new Stage();
        vitalsStage.setTitle("Take Vitals");

        // Create labels and text fields for vitals input
        Label patientLabel = new Label("Enter patients username:");
        TextField patientUsernameField = new TextField();
        Label weightLabel = new Label("Weight (kg):");
        TextField weightField = new TextField();

        Label heightLabel = new Label("Height (cm):");
        TextField heightField = new TextField();

        Label temperatureLabel = new Label("Body Temperature (°C):");
        TextField temperatureField = new TextField();

        Label bloodPressureLabel = new Label("Blood Pressure (mmHg):");
        TextField bloodPressureField = new TextField();

        Label oxygenLevelLabel = new Label("Oxygen Level (%):");
        TextField oxygenLevelField = new TextField();

        Button saveButton = new Button("Save");
        
        DatePicker dater = new DatePicker();
        
        saveButton.setOnAction(e -> {
            // Validate input data
        	try {
        	
        		String username = patientUsernameField.getText();
	            double weight = Double.parseDouble(weightField.getText());
	            double height = Double.parseDouble(heightField.getText());
	            double temperature = Double.parseDouble(temperatureField.getText());
	            String bloodPressure = bloodPressureField.getText();
	            double oxygenLevel = Double.parseDouble(oxygenLevelField.getText());
	            
	            LocalDate date = dater.getValue();
	         
	            
	            saveVitals(weight, height, temperature, bloodPressure, oxygenLevel, date, username);
	            vitalsStage.close();

        	} catch (Exception e1)
        	{
        		showError("Error", "Please fill out all fields.");
        	}

            // Save the inputted vital signs to the system or database
            
        });

        // Create grid layout for the vitals input fields
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.addRow(0, patientLabel, patientUsernameField);
        gridPane.addRow(1, weightLabel, weightField);
        gridPane.addRow(2, heightLabel, heightField);
        gridPane.addRow(3, temperatureLabel, temperatureField);
        gridPane.addRow(4, bloodPressureLabel, bloodPressureField);
        gridPane.addRow(5, oxygenLevelLabel, oxygenLevelField);
        gridPane.addRow(6,  new Label("Appt. Date:"), dater);
        gridPane.addRow(7, saveButton);

        Scene scene = new Scene(gridPane, 400, 320);
        vitalsStage.setScene(scene);
        vitalsStage.show();
    }

    private void saveVitals(double weight, double height, double temperature, String bloodPressure, double oxygenLevel, LocalDate date, String username) {
        // Implement logic to save the inputted vital signs to the system or database
        
        String medicalHistoryDirectory = "medical_history/";
        String medicalHistoryFilePath = medicalHistoryDirectory + username + "_" + date + "_medical_history.txt";
        
        
        try {
	        File file = new File(medicalHistoryFilePath);
	        if (!file.exists()) {
	            // If the file does not exist, create it
	            if (!file.getParentFile().exists()) {
	                file.getParentFile().mkdirs(); // Create parent directories if they don't exist
	            }
	            file.createNewFile(); // Create the file
	        }
	        
	        FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            
            bufferedWriter.write(weight + "\n");
            bufferedWriter.write(height + "\n");
            bufferedWriter.write(temperature + "\n");
            bufferedWriter.write(bloodPressure + "\n");
            bufferedWriter.write(oxygenLevel + "\n");

            bufferedWriter.close();
            
            
	  
        } catch (IOException e3) {
        	showError("Error", "Something went wrong!");
        }
        
        
    }
}