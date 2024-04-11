import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class DoctorDashboard {
    private Stage primaryStage;
    private String doctorFirstName;
    private String doctorLastName;
    
    private String doctorBirthday;

    public DoctorDashboard(Stage primaryStage, User rlUser) {
        this.primaryStage = primaryStage;
        this.doctorFirstName = rlUser.getFName();
        this.doctorLastName = rlUser.getLName();
        
        this.doctorBirthday = rlUser.getbDay();
    }

    public void show() {
    	
    	//create all the UI components first
        primaryStage.setTitle("Doctor Dashboard");

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
        
        Label dashLabel = new Label("Doctor Dashboard");
        dashLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
        
        // Display patient information
        Label welcomeLabel = new Label("Welcome back, Doctor");
        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        // Display patient information
        Label actionLabel = new Label("What would you like to do?");
        actionLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
       

        // Buttons for various actions
        Button viewMedicalHistoryButton = new Button("View Medical History");
        
        Button sendMessageToPatientButton = new Button("Send Message to Patient");
        Button prescribeMedicineButton = new Button("Prescribe Medicine");
        Button performPhysical = new Button("Perform Physical");
        
        
        Button logOutButton = new Button("Logout");

        


        // Layout for patient dashboard
        
        HBox buttonsLayout = new HBox(10);
        buttonsLayout.getChildren().addAll(viewMedicalHistoryButton, prescribeMedicineButton,performPhysical, sendMessageToPatientButton);
        buttonsLayout.setAlignment(Pos.CENTER);
        VBox layout = new VBox(10);
        layout.getChildren().addAll(dashLabel, logoBox, welcomeLabel, actionLabel, buttonsLayout, logOutButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        primaryStage.setScene(new Scene(layout, 850, 700));
        primaryStage.show();
        
        // Set action handlers for buttons (if needed)
        viewMedicalHistoryButton.setOnAction(e -> viewMedicalHistory());
       
        sendMessageToPatientButton.setOnAction(e -> openReplyToPatientPopup());
        performPhysical.setOnAction(e->inputPhysicalData());
        prescribeMedicineButton.setOnAction(e->prescribeMedication());
        
        
        logOutButton.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	WelcomePage home = new WelcomePage(primaryStage);
                home.show();
            }
        });


    }
    
    
    
    //this is called when the button is clicked
    private void viewMedicalHistory() {
        // Create labels and text fields for medication details
        Label patientUsername = new Label("Enter the Patient's username whose history you wish to view:");
        TextField usernameField = new TextField();
        HBox fieldBox = new HBox(usernameField);
        fieldBox.setPrefWidth(250);
        fieldBox.setAlignment(Pos.CENTER);

        // Create a button to search for the users medical history
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
            healthIssuesTextArea.setPrefHeight(80); 
            prescriptionsTextArea.setPrefHeight(80);
            immunizationsTextArea.setPrefHeight(80);
            recommendationsTextArea.setPrefHeight(80);

            // Read the medical history from the file and fill in the text areas
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
            // when the doctor saves, the existing file is overwritten
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



    // Method to open popup for sending message to patient
 	
    private void openReplyToPatientPopup() {
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

        
        //need the doctor to input the patients username
        viewMessagesButton.setOnAction(e -> {
            String patientUsername = usernameField.getText().trim();
            if (!patientUsername.isEmpty()) {
                // Initialize MessageSystem with patient's username
                MessageSystem messageSystem = new MessageSystem(patientUsername);
                messageSystem.replyToPatient(patientUsername);
            } else {
                showError("Error", "Please enter the patient's username.");
            }
        });
    }
	
 // Helper method to show alert messages
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    
	// Helper method to show error messages
	private void showError(String title, String message) {
	    Alert alert = new Alert(Alert.AlertType.ERROR);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	    }

	
    private void inputPhysicalData() {
    	// Create labels and text fields for medication details
    	Label patientUsername = new Label("Enter Patients username:");
        TextField usernameField = new TextField();
        Label findingsLabel = new Label("New Findings:");
        TextArea findingsTextArea = new TextArea();
        findingsTextArea.setWrapText(true);
        findingsTextArea.setPromptText("Enter new findings here");
    
        // Create a button to save the entered findings
        Button saveButton = new Button("Save Findings");
        saveButton.setOnAction(e -> {
            // Retrieve the entered findings
            String findings = findingsTextArea.getText();
    
            // Validate the entered findings
            if (!findings.isEmpty()) {
                // Perform saving logic here (e.g., save findings to database)
                // Display a confirmation message
                showConfirmation("Findings Saved", "New findings have been successfully recorded.");
            } else {
                // Display an error message if the findings are empty
                showError("Error", "Please enter new findings before saving.");
            }
        });
    
        // Create a layout to arrange the input components
        HBox patientBox = new HBox(10);
        patientBox.getChildren().addAll(patientUsername, usernameField);
        patientBox.setAlignment(Pos.CENTER_LEFT);
        VBox inputLayout = new VBox(10);
        inputLayout.getChildren().addAll(patientBox,findingsLabel, findingsTextArea, saveButton);
        inputLayout.setAlignment(Pos.CENTER);
        inputLayout.setPadding(new Insets(20));
    
        // Create a new stage for recording findings
        Stage findingsStage = new Stage();
        findingsStage.setTitle("Record Findings");
        findingsStage.setScene(new Scene(inputLayout));
        findingsStage.show();
    }
    
    // Helper method to display confirmation message
    private void showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    

    
    private void prescribeMedication() {
        // Create labels and text fields for medication details
    	Label patientUsername = new Label("Enter Patients username:");
        TextField usernameField = new TextField();

        Label medicationNameLabel = new Label("Medication Name:");
        TextField medicationNameField = new TextField();
    
        Label dosageLabel = new Label("Dosage:");
        TextField dosageField = new TextField();
    
        Label frequencyLabel = new Label("Frequency:");
        TextField frequencyField = new TextField();
    
        Label instructionsLabel = new Label("Instructions:");
        TextField instructionsField = new TextField();
        
        Label datePick = new Label("Appt. Date:");
        DatePicker dater = new DatePicker();
        
    
        // Create a button to prescribe medication
        Button prescribeButton = new Button("Prescribe Medication");
        prescribeButton.setOnAction(e -> {
            // Retrieve the entered medication details
            String medicationName = medicationNameField.getText();
            String dosage = dosageField.getText();
            String frequency = frequencyField.getText();
            String instructions = instructionsField.getText();
            String username = usernameField.getText().trim();
    
            // Validate the entered data
            if (!username.isEmpty() && !medicationName.isEmpty() && !dosage.isEmpty() && !frequency.isEmpty() && dater.getValue() != null) {
                // Perform logic to save the prescribed medication (e.g., save to database)
                // Display a confirmation message
            	LocalDate date = dater.getValue();
            	
            	
            	String medicalHistoryDirectory = "medical_history/";
                String medicalHistoryFilePath = medicalHistoryDirectory + username + "_" + date + "_prescriptions.txt";
                
                
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
                    
                    bufferedWriter.write(medicationName + "\n");
                    bufferedWriter.write(dosage + "\n");
                    bufferedWriter.write(frequency + "\n");
                    bufferedWriter.write(instructions + "\n");

                    bufferedWriter.close();
                    
                    
        	  
                } catch (IOException e3) {
                	showError("Error", "Something went wrong!");
                }
                showConfirmation("Medication Prescribed", "Medication has been successfully prescribed.");
            } else {
                // Display an error message if any essential field is empty
                showError("Error", "Please fill out all required fields before prescribing medication.");
            }
        });
    
        // Create a layout to arrange the input components
        
        // Create grid layout for the vitals input fields
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.addRow(0, patientUsername, usernameField);
        gridPane.addRow(1, medicationNameLabel, medicationNameField);
        gridPane.addRow(2, dosageLabel, dosageField);
        gridPane.addRow(3, frequencyLabel, frequencyField);
        gridPane.addRow(4, instructionsLabel, instructionsField);
        gridPane.addRow(5,  datePick, dater);
        
        //set the alignment
        GridPane.setHalignment(prescribeButton, HPos.CENTER);
        GridPane.setColumnSpan(prescribeButton, 2);
        gridPane.addRow(6,prescribeButton);
        
    
        // Create a new stage for prescribing medication
        Stage prescribeMedicationStage = new Stage();
        prescribeMedicationStage.setTitle("Prescribe Medication");
        prescribeMedicationStage.setScene(new Scene(gridPane));
        prescribeMedicationStage.show();
    }

    
    
}