package application;


import java.time.LocalDate;  
import java.time.format.DateTimeFormatter;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.application.Platform;





public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Main Window");

        Button signInButton = new Button("Sign In");
        signInButton.setOnAction(e -> openSignIn());

        Button signUpButton = new Button("Sign Up");
        signUpButton.setOnAction(e -> openSignUp());

        HBox headerLayout = new HBox(10);
        headerLayout.getChildren().addAll(signInButton, signUpButton);
        headerLayout.setAlignment(Pos.CENTER);
        headerLayout.setPadding(new Insets(10));

        VBox mainLayout = new VBox(10);
        mainLayout.getChildren().addAll(headerLayout);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20));

        primaryStage.setScene(new Scene(mainLayout, 300, 200));
        primaryStage.show();
    }

    
    
    
    
    
    private void openSignIn() {
        Stage selectRoleWindow = new Stage();
        selectRoleWindow.initModality(Modality.APPLICATION_MODAL);
        selectRoleWindow.setTitle("Select Role");

        Label roleLabel = new Label("I am a:");

        Button nurseButton = new Button("Nurse");
        nurseButton.setOnAction(e -> openSignInPopup("Nurse"));

        Button doctorButton = new Button("Doctor");
        doctorButton.setOnAction(e -> openSignInPopup("Doctor"));

        Button patientButton = new Button("Patient");
        patientButton.setOnAction(e -> openSignInPopup("Patient"));

        VBox layout = new VBox(10);
        layout.getChildren().addAll(roleLabel, nurseButton, doctorButton, patientButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        selectRoleWindow.setScene(new Scene(layout, 250, 200));
        selectRoleWindow.showAndWait();
    }

    private boolean validateFields(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            // Display error for blank fields
            showError("Error", "Please fill out all fields.");
            return false;
        }
        // You can add more validation logic here if needed
        return true;
    }
 // Example method to validate credentials
    private boolean isValidCredentials(String username, String password, String role) {
        // Perform authentication logic based on role
        if (role.equals("Nurse")) {
            // Check if username and password match nurse credentials
            if (username.equals("1") && password.equals("1")) {
                // Open nurse dashboard
                openNurseDashboard();
                return true;
            }
        } else if (role.equals("Doctor")) {
            // Check if username and password match doctor credentials
            if (username.equals("1") && password.equals("1")) {
                // Open doctor dashboard
                openDoctorDashboard();
                return true;
            }
        } else if (role.equals("Patient")) {
            // Check if username and password match patient credentials
            if (username.equals("1") && password.equals("1")) {
                // Open patient dashboard
                openPatientDashboard(patient);
                return true;
            }
        }
        // Default case: return false if role is unknown or credentials are incorrect
        return false;
    }


////// THIS NEEDS TO BE CHANGED BASED ON THE USER INPUTS AND GERNEATED ID FROM SIGNUP IF NOT DOCTOR PATIENT 
    // DO THIS
    /*
     * THIS MF
     * NEEDS
     * TO 
     * GET
     * RE
     * DID
     * BABES
     * SINCE
     * IT
     * RUNS
     * NOW
     * DONT
     * FUCK
     * W
     * IT
     * CURRENTLY
     * */
    
    /*
     * GUES
     * WHO
     * FUCKED
     * IT
     * UP
     * */


    // Function to display error messages
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void openSignInPopup(String role) {
        Stage popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Sign In as " + role);

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button signInButton = new Button("Sign In");
        signInButton.setOnAction(e -> {
            // Get username and password from input fields
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Validate input fields
            if (!validateFields(username, password)) {
                return;
            }

            // Perform sign-in logic here
            if (isValidCredentials(username, password, role)) {
                System.out.println("Successfully signed in as " + role);
                Platform.runLater(() -> {
                    popupWindow.close();
                    if (role.equals("Nurse")) {
                        openNurseDashboard();
                    } else if (role.equals("Patient")) {
                        openPatientDashboard(patient); // Pass patient to openPatientDashboard
                    }
                    else if(role.equals("Doctor")) {
                    	openDoctorDashboard();
                    }
                });
            } else {
                // Display error for incorrect credentials
                showError("Error", "Incorrect username or password. Please try again.");
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, signInButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        popupWindow.setScene(new Scene(layout, 250, 200));
        popupWindow.showAndWait();
    }
    
    

    
    
    
    
    
    
    
    
    
    private void openSignUp() {
        Stage popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Sign Up");

        Label firstNameLabel = new Label("First Name:");
        TextField firstNameField = new TextField();

        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameField = new TextField();

        Label birthdayLabel = new Label("Birthday:");
        DatePicker birthdayPicker = new DatePicker();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button signUpButton = new Button("Sign Up");
        signUpButton.setOnAction(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            LocalDate birthday = birthdayPicker.getValue();
            String password = passwordField.getText();
            
            // Check if any field is empty
            if (firstName.isEmpty() || lastName.isEmpty() || birthday == null || password.isEmpty()) {
                // Display error popup
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill out all fields.");
                alert.showAndWait();
                return;
            }

            // Generate patient ID
            String formattedBirthday = DateTimeFormatter.ofPattern("MMddyy").format(birthday);
            String patientID = generatePatientID(firstName, lastName, formattedBirthday);
            
            // Display patient ID in popup
            Alert patientIDAlert = new Alert(Alert.AlertType.INFORMATION);
            patientIDAlert.setTitle("Patient ID Generated");
            patientIDAlert.setHeaderText(null);
            patientIDAlert.setContentText("Patient ID: " + patientID);
            patientIDAlert.showAndWait();
            
            
            popupWindow.close();

            // You can proceed with further sign-up logic here
            // For example, saving the user information to a database
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> popupWindow.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(firstNameLabel, firstNameField, lastNameLabel, lastNameField, birthdayLabel, birthdayPicker, passwordLabel, passwordField, signUpButton, cancelButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        HBox buttonLayout = new HBox(10);
        buttonLayout.getChildren().addAll(cancelButton);
        buttonLayout.setAlignment(Pos.BOTTOM_RIGHT);
        buttonLayout.setPadding(new Insets(10));

        VBox mainLayout = new VBox(10);
        mainLayout.getChildren().addAll(layout, buttonLayout);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20));

        popupWindow.setScene(new Scene(mainLayout, 250, 350));
        popupWindow.showAndWait();
    }

    private String generatePatientID(String firstName, String lastName, String formattedBirthday) {
        // You can implement your own logic for generating patient ID here
        // Example: '2MJones080701'
        // We'll assume here that you have access to the number of patients with the same name.
        // For simplicity, we'll use '2' for the example.
        String patientID = firstName.substring(0, 1) + lastName + formattedBirthday;
        return patientID;
    }
    
    
    private Nurse currentNurse;
    // IF SIGNED IN AS NURSE 
    private void openNurseDashboard() {
        System.out.println("Opening Nurse Dashboard...");

        // Create buttons for nurse dashboard functionalities
        Button viewPatientsButton = new Button("View Patients");
        Button createVisitButton = new Button("Create Visit");
        Button viewPatientHistoryButton = new Button("View Patient History");
        Button recordVitalsButton = new Button("Record Vitals");
        Button makeAppointmentButton = new Button("Make Appointment");

        // Set action handlers for buttons (if needed)
        viewPatientsButton.setOnAction(e -> viewPatients());
        createVisitButton.setOnAction(e -> createVisit());
        viewPatientHistoryButton.setOnAction(e -> viewPatientHistory());
        recordVitalsButton.setOnAction(e -> recordVitals());
        makeAppointmentButton.setOnAction(e -> makeAppointment());

        // Add buttons to a VBox
        VBox dashboardLayout = new VBox(10);
        dashboardLayout.getChildren().addAll(
            viewPatientsButton,
            createVisitButton,
            viewPatientHistoryButton,
            recordVitalsButton,
            makeAppointmentButton
        );

        // Set alignment and padding for the VBox
        dashboardLayout.setAlignment(Pos.CENTER);
        dashboardLayout.setPadding(new Insets(20));

        // Create a scene with the VBox as the root node
        Scene scene = new Scene(dashboardLayout, 300, 200);

        // Create a new stage for the nurse dashboard and set the scene
        Stage nurseDashboard = new Stage();
        nurseDashboard.setTitle("Nurse Dashboard");
        nurseDashboard.setScene(scene);

        // Show the nurse dashboard stage
        nurseDashboard.show();
    }
    // Method to view patient list
    private void viewPatients() {
        // Implement functionality to view patients
    }

    // Method to create a visit
    private void createVisit() {
        // Implement functionality to create a visit
    }

    // Method to view patient history
    private void viewPatientHistory() {
        // Implement functionality to view patient history
    }

    // Method to record vitals
    private void recordVitals() {
        // Implement functionality to record vitals
    }

    // Method to make an appointment
    private void makeAppointment() {
        // Implement functionality to make an appointment
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    private Doctor currentDoctor;
    
    
    private void openDoctorDashboard() {
        System.out.println("Opening Doctor Dashboard...");
   //     try {
   //         if (currentDoctor != null) {
                // Create and display the doctor dashboard UI
                Stage doctorDashboard = new Stage();

                VBox doctorDashboardLayout = new VBox(10);

                Button viewPatientsButton = new Button("View Patients");
                viewPatientsButton.setOnAction(e -> viewPatients());

                Button prescribeMedicineButton = new Button("Prescribe Medicine");
                prescribeMedicineButton.setOnAction(e -> prescribeMedicine());

                Button viewMedicalHistoryButton = new Button("View Medical History");
                viewMedicalHistoryButton.setOnAction(e -> viewMedicalHistory());

                Button makeRecommendationsButton = new Button("Make Recommendations");
                makeRecommendationsButton.setOnAction(e -> makeRecommendations());

                Button replyToPatientButton = new Button("Reply to Patient");
                replyToPatientButton.setOnAction(e -> replyToPatient());

                Button callPatientButton = new Button("Call Patient");
                callPatientButton.setOnAction(e -> callPatient());

                doctorDashboardLayout.getChildren().addAll(
                    viewPatientsButton,
                    prescribeMedicineButton,
                    viewMedicalHistoryButton,
                    makeRecommendationsButton,
                    replyToPatientButton,
                    callPatientButton
                );
                doctorDashboardLayout.setAlignment(Pos.CENTER);
                doctorDashboardLayout.setPadding(new Insets(20));

                Scene scene = new Scene(doctorDashboardLayout, 600, 400);
                doctorDashboard.setScene(scene);

                doctorDashboard.setTitle("Doctor Dashboard");
                doctorDashboard.show();
     //       } //else {
                // Handle case where no doctor is logged in
 //               System.out.println("No doctor is logged in.");
           // }
        //} 
     //   catch (Exception e) {
        //    e.printStackTrace();
      //  }
    }


    private void inputPhysicalInfo() {
        // Implement functionality to input physical info
    }

    private void prescribeMedicine() {
        // Implement functionality to prescribe medicine
    }

    private void viewMedicalHistory() {
        // Implement functionality to view medical history
    }

    private void makeRecommendations() {
        // Implement functionality to make recommendations
    }

    private void replyToPatient() {
        // Implement functionality to reply to patient
    }

    private void callPatient() {
        // Implement functionality to call patient
    }

    
    
    
    
    
    
    
    
    public class Patient {
        private String firstName;
        private String lastName;
        private int age;
        private String birthday;

        // Constructor
        public Patient(String firstName, String lastName, int age, String birthday) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
            this.birthday = birthday;
        }

        // Getters for the fields
        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public int getAge() {
            return age;
        }

        public String getBirthday() {
            return birthday;
        }
    } 
    
    
    
    
    
    
   // private Patient currentPatient;
    Patient patient = new Patient(firstName, lastName, age, birthday);

    
    private void openPatientDashboard(Patient patient) {
        System.out.println("Opening Patient Dashboard...");

        // Create UI elements for patient dashboard
        // Example: Label to display patient's information
        Label nameLabel = new Label("Name: " + patient.getFirstName() + " " + patient.getLastName());
        Label ageLabel = new Label("Age: " + patient.getAge());
        Label birthdayLabel = new Label("Birthday: " + patient.getBirthday());
        // Add more labels for other patient information...

        // Example: Button to view contact info
        Button viewContactInfoButton = new Button("View Contact Info");
        viewContactInfoButton.setOnAction(e -> viewContactInfo());

        // Example: Button to update contact info
        Button updateContactInfoButton = new Button("Update Contact Info");
        updateContactInfoButton.setOnAction(e -> updateContactInfo());

        // Example: Button to view visit history
        Button viewVisitHistoryButton = new Button("View Visit History");
        viewVisitHistoryButton.setOnAction(e -> viewVisitHistory());

        // Example: Button to send messages to staff
        Button sendMessageToStaffButton = new Button("Send Message to Staff");
        sendMessageToStaffButton.setOnAction(e -> sendMessageToStaff());

        // Add UI elements to layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(nameLabel, ageLabel, birthdayLabel, viewContactInfoButton, updateContactInfoButton, viewVisitHistoryButton, sendMessageToStaffButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        // Create and display patient dashboard window
        Stage patientDashboard = new Stage();
        patientDashboard.setTitle("Patient Dashboard");
        patientDashboard.setScene(new Scene(layout, 300, 400));
        patientDashboard.show();
    }

    private void viewContactInfo() {
        // Implement functionality to view contact info
    }

    private void updateContactInfo() {
        // Implement functionality to update contact info
    }

    private void viewVisitHistory() {
        // Implement functionality to view visit history
    }

    private void sendMessageToStaff() {
        // Implement functionality to send message to staff
    }

    
    
    public static void main(String[] args) {
        launch(args);
    }
    
}

