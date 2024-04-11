import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
import javafx.scene.control.DatePicker;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import java.time.LocalDate;
import java.util.Optional;

import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;

import javafx.scene.control.TextField;



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

        // Display patient information
        Label nameLabel = new Label("Name: " + patientFirstName + " " + patientLastName);
        

        // Buttons for various actions
        Button viewMedicalHistoryButton = new Button("View Medical History");
        Button makeAppointmentButton = new Button("Make Appointment");
        Button sendMessageToPatientButton = new Button("Send Message to Patient");
        Button takeVitalsButton = new Button("Take Vitals"); // Added button for taking vitals


        // Layout for patient dashboard
        VBox layout = new VBox(10);
        layout.getChildren().addAll(nameLabel, viewMedicalHistoryButton, makeAppointmentButton, sendMessageToPatientButton, takeVitalsButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        primaryStage.setScene(new Scene(layout, 850, 700));
        primaryStage.show();

        //sendMessageToPatientButton.setOnAction(e -> sendMessageToPatient());

        
        // Set action handlers for buttons (if needed)
        viewMedicalHistoryButton.setOnAction(e -> viewMedicalHistory());
        makeAppointmentButton.setOnAction(e -> makeAppointment());
        sendMessageToPatientButton.setOnAction(e -> openSendMessagePopup());
        takeVitalsButton.setOnAction(e -> takeVitals()); // Action handler for taking vitals
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
    /*private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }*/
    
    
    // Method to open popup for sending message to patient
    private void openSendMessagePopup() {
    	// Create a new stage for the popup
	    Stage popupStage = new Stage();

	    // Layout for popup
	    VBox popupLayout = new VBox(10);
	    popupStage.setScene(new Scene(popupLayout, 400, 200));
	    popupStage.show();

        String userId = patientFirstName.substring(0, 1) + patientLastName + patientBirthday;

        MessageSystem messageSystem = new MessageSystem(popupStage, userId);
        messageSystem.show();
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
        
        Label ageLabel = new Label("Enter patients age:");
        TextField ageField = new TextField();
        
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
        saveButton.setOnAction(e -> {
            // Validate input data
        	double age = Double.parseDouble(ageField.getText());
        	
        	if (age > 12) {
            double weight = Double.parseDouble(weightField.getText());
            double height = Double.parseDouble(heightField.getText());
            double temperature = Double.parseDouble(temperatureField.getText());
            String bloodPressure = bloodPressureField.getText();
            double oxygenLevel = Double.parseDouble(oxygenLevelField.getText());
            
            // Save the inputted vital signs to the system or database
            saveVitals(weight, height, temperature, bloodPressure, oxygenLevel);
        	}
        	
        	else {
        		showError("Error", "Vitals can only be recorded for patients aged 12 or older, Please have parents approval.");
        	}
            
            
            vitalsStage.close();
        });

        // Create grid layout for the vitals input fields
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.addRow(0, patientLabel, patientUsernameField);
        gridPane.addRow(1, ageLabel, ageField);
        gridPane.addRow(2, weightLabel, weightField);
        gridPane.addRow(3, heightLabel, heightField);
        gridPane.addRow(4, temperatureLabel, temperatureField);
        gridPane.addRow(5, bloodPressureLabel, bloodPressureField);
        gridPane.addRow(6, oxygenLevelLabel, oxygenLevelField);
        gridPane.addRow(7, saveButton);

        Scene scene = new Scene(gridPane, 400, 300);
        vitalsStage.setScene(scene);
        vitalsStage.show();
    }

    private void saveVitals(double weight, double height, double temperature, String bloodPressure, double oxygenLevel) {
        // Implement logic to save the inputted vital signs to the system or database
        System.out.println("Vitals saved:");
        System.out.println("Weight: " + weight + " lbs");
        System.out.println("Height: " + height + " cm");
        System.out.println("Temperature: " + temperature + " °F");
        System.out.println("Blood Pressure: " + bloodPressure + " mmHg");
        System.out.println("Oxygen Level: " + oxygenLevel + " %");
        
        
    }
}