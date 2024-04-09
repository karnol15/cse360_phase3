import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.DatePicker;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import java.time.LocalDate;
import java.util.Optional;

import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;


public class DoctorDashboard {
    private Stage primaryStage;
    private String patientFirstName;
    private String patientLastName;
    private int patientAge;
    private String patientBirthday;

    public DoctorDashboard(Stage primaryStage, String firstName, String lastName, int age, String birthday) {
        this.primaryStage = primaryStage;
        this.patientFirstName = firstName;
        this.patientLastName = lastName;
        this.patientAge = age;
        this.patientBirthday = birthday;
    }

    public void show() {
        primaryStage.setTitle("Doctor Dashboard");

        // Display patient information
        Label nameLabel = new Label("Name: " + patientFirstName + " " + patientLastName);
        Label ageLabel = new Label("Age: " + patientAge);
        Label birthdayLabel = new Label("Birthday: " + patientBirthday);

        // Buttons for various actions
        Button viewMedicalHistoryButton = new Button("View Medical History");
        Button makeAppointmentButton = new Button("Make Appointment");
        Button sendMessageToPatientButton = new Button("Send Message to Patient");

        // Set action handlers for buttons (if needed)
        viewMedicalHistoryButton.setOnAction(e -> viewMedicalHistory());
        makeAppointmentButton.setOnAction(e -> makeAppointment());
        sendMessageToPatientButton.setOnAction(e -> openSendMessagePopup());


        // Layout for patient dashboard
        VBox layout = new VBox(10);
        layout.getChildren().addAll(nameLabel, ageLabel, birthdayLabel, viewMedicalHistoryButton, makeAppointmentButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        primaryStage.setScene(new Scene(layout, 850, 700));
        primaryStage.show();


    }

    private void viewMedicalHistory() {
        // Assuming the medical history files are stored in a specific directory
        String medicalHistoryDirectory = "medical_history/";
        
        // Constructing the file path based on the patient's username
        String username = "patient_username"; // You should replace this with the actual patient's username
        String medicalHistoryFilePath = medicalHistoryDirectory + username + "_medical_history.txt";

        // Open the medical history file and display its content in a dialog window
        try {
            File file = new File(medicalHistoryFilePath);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            StringBuilder medicalHistoryContent = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                medicalHistoryContent.append(line).append("\n");
            }
            
            bufferedReader.close();

            // Show the medical history content in an alert dialog
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Medical History");
            alert.setHeaderText("Medical History for " + patientFirstName + " " + patientLastName);
            alert.setContentText(medicalHistoryContent.toString());
            alert.showAndWait();
            
        } catch (IOException e) {
            // Handle file not found or other IO exceptions
            e.printStackTrace();
            // Show an error dialog to the user
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error viewing medical history");
            alert.setContentText("Could not find medical history for the patient.");
            alert.showAndWait();
        }
    }

    private void makeAppointment() {
        // Create a DatePicker to select the appointment date
        DatePicker datePicker = new DatePicker();

        // Set the date picker to allow selection of dates only in the future
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });

        // Create a grid pane to layout the date picker
        GridPane gridPane = new GridPane();
        gridPane.add(datePicker, 0, 0);

        // Create an alert dialog to display the appointment form
        Alert alert = new Alert(AlertType.NONE);
        alert.setTitle("Make Appointment");
        alert.getDialogPane().setContent(gridPane);
        alert.getButtonTypes().add(ButtonType.OK);
        alert.getButtonTypes().add(ButtonType.CANCEL);

        // Show the alert dialog and handle the user's response
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            LocalDate selectedDate = datePicker.getValue();
            if (selectedDate != null) {
                // Perform appointment scheduling logic
                // Here you can implement the logic to save the appointment to a database or perform other actions
                System.out.println("Appointment scheduled for: " + selectedDate);
            } else {
                // No date selected, show an error message
                showError("Error", "Please select a future date for the appointment.");
            }
        }
    }

    // Helper method to show error messages
    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to open popup for sending message to patient
private void openSendMessagePopup() {
    // Create a new stage for the popup
    Stage popupStage = new Stage();
    popupStage.setTitle("Send Message to Patient");

    // Create text area for inputting message
    TextArea messageTextArea = new TextArea();
    messageTextArea.setWrapText(true);
    messageTextArea.setPromptText("Enter your message here");

    // Create button for sending message
    Button sendButton = new Button("Send");
    sendButton.setOnAction(e -> {
        String messageContent = messageTextArea.getText();
        if (!messageContent.isEmpty()) {
            sendMessageToPatient(messageContent);
            popupStage.close();
        } else {
            // Show error message if message content is empty
            showError("Error", "Message content cannot be empty");
        }
    });

    // Layout for popup
    VBox popupLayout = new VBox(10);
    popupLayout.getChildren().addAll(messageTextArea, sendButton);
    popupLayout.setAlignment(Pos.CENTER);
    popupLayout.setPadding(new Insets(20));

    popupStage.setScene(new Scene(popupLayout, 400, 200));
    popupStage.show();
}


// Helper method to send message to patient
private void sendMessageToPatient(String messageContent) {
    // Logic to send message to patient
    System.out.println("Message sent to patient: " + messageContent);
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
        VBox inputLayout = new VBox(10);
        inputLayout.getChildren().addAll(findingsLabel, findingsTextArea, saveButton);
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
    
    // Helper method to display error message
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void prescribeMedication() {
        // Create labels and text fields for medication details
        Label medicationNameLabel = new Label("Medication Name:");
        TextField medicationNameField = new TextField();
    
        Label dosageLabel = new Label("Dosage:");
        TextField dosageField = new TextField();
    
        Label frequencyLabel = new Label("Frequency:");
        TextField frequencyField = new TextField();
    
        Label instructionsLabel = new Label("Instructions:");
        TextField instructionsField = new TextField();
    
        // Create a button to prescribe medication
        Button prescribeButton = new Button("Prescribe Medication");
        prescribeButton.setOnAction(e -> {
            // Retrieve the entered medication details
            String medicationName = medicationNameField.getText();
            String dosage = dosageField.getText();
            String frequency = frequencyField.getText();
            String instructions = instructionsField.getText();
    
            // Validate the entered data
            if (!medicationName.isEmpty() && !dosage.isEmpty() && !frequency.isEmpty()) {
                // Perform logic to save the prescribed medication (e.g., save to database)
                // Display a confirmation message
                showConfirmation("Medication Prescribed", "Medication has been successfully prescribed.");
            } else {
                // Display an error message if any essential field is empty
                showError("Error", "Please fill out all required fields before prescribing medication.");
            }
        });
    
        // Create a layout to arrange the input components
        VBox inputLayout = new VBox(10);
        inputLayout.getChildren().addAll(medicationNameLabel, medicationNameField, dosageLabel, dosageField,
                frequencyLabel, frequencyField, instructionsLabel, instructionsField, prescribeButton);
        inputLayout.setAlignment(Pos.CENTER);
        inputLayout.setPadding(new Insets(20));
    
        // Create a new stage for prescribing medication
        Stage prescribeMedicationStage = new Stage();
        prescribeMedicationStage.setTitle("Prescribe Medication");
        prescribeMedicationStage.setScene(new Scene(inputLayout));
        prescribeMedicationStage.show();
    }

    

    private void viewHealthHistory() {
        // Retrieve the patient's health history from the database or data structure
        String patientUsername = "patient_username"; // Replace with actual patient's username
        String healthHistory = retrieveHealthHistory(patientUsername);
    
        // Display the health history in an alert dialog
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Health History");
        alert.setHeaderText("Health History for " + patientFirstName + " " + patientLastName);
        alert.setContentText(healthHistory);
        alert.showAndWait();
    }
    
    // Method to retrieve health history from the database or data structure
    private String retrieveHealthHistory(String patientUsername) {
        // Placeholder method to simulate retrieving health history
        // Replace this with actual logic to retrieve health history from files .txt
        // For demonstration purposes, returning dummy health history
        return "Patient's health history goes here...";
    }
    
}