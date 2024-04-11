import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class PatientDashboard {
    private Stage primaryStage;
    private Patient user;
    private String patientFirstName;
    private String patientLastName;
    private int patientAge;
    private String patientBirthday;
    private String userId;
    MessageSystem messageSystem;

    public PatientDashboard(Stage primaryStage, Patient rlUser) {
        this.primaryStage = primaryStage;
        user = rlUser;
        this.patientFirstName = rlUser.getFName();
        this.patientLastName = rlUser.getLName();
        
        // Format the birthday as mmdd
        LocalDate birthday = rlUser.getBirthday();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMdd");
        this.patientBirthday = birthday.format(formatter);	
        
        // Initialize MessageSystem
        userId = patientFirstName.substring(0, 1) + patientLastName + patientBirthday;
        messageSystem = new MessageSystem(userId);
        
        
    }

    public void show() {
        primaryStage.setTitle("Patient Dashboard");
        
        // Label for the title
        Label titleLabel = new Label("Pediatric Doctor's Office");
        titleLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
        
        
        // Create an ImageView with the image
        Image image = new Image("patientDashBanner.jpg"); 
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
        
        Label dashLabel = new Label("Patient Dashboard");
        dashLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        // Display patient information
        Label nameLabel = new Label("Hello " + patientFirstName + "!");
        nameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        // Display patient information
        Label actionLabel = new Label("What would you like to do?");
        actionLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        
        
        // Buttons for various actions
        Button viewContactInfoButton = new Button("View Contact Info");
        Button updateContactInfoButton = new Button("Update Contact Info");
        Button viewVisitHistoryButton = new Button("View Visit History");
        Button sendMessageToStaffButton = new Button("Send Message to Staff");
        Button logOutButton = new Button("Logout");

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
        layout.getChildren().addAll(titleLabel, logoBox, dashLabel, nameLabel,actionLabel, buttonBox,logOutButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        primaryStage.setScene(new Scene(layout, 850, 700));
        primaryStage.show();

        
        //handle events below here
        
        
        viewContactInfoButton.setOnAction(e -> viewContactInfo());
        updateContactInfoButton.setOnAction(e -> updateContactInfo());
        viewVisitHistoryButton.setOnAction(e -> viewVisitHistory());

        
        sendMessageToStaffButton.setOnAction(e -> openSendMessagePopup());
        
        logOutButton.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	WelcomePage home = new WelcomePage(primaryStage);
                home.show();
            }
        });
        
    }

    

	private void viewVisitHistory() {
		Stage pickDateStage = new Stage();
		pickDateStage.setTitle("Pick Visit Date");

        // Create labels and text fields for vitals input
        Label pickDateLabel = new Label("Please select the date of the visit you wish to view: ");
        DatePicker datePicker = new DatePicker();

        Button confirmButton = new Button("Confirm date");
        Button cancelButton = new Button("Cancel");
        
       

        // Create grid layout for the vitals input fields
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.addRow(0, pickDateLabel);
        gridPane.addRow(1, datePicker);
        gridPane.addRow(2, confirmButton);
        gridPane.addRow(3, cancelButton);
        
        GridPane.setHalignment(pickDateLabel, HPos.CENTER);
        GridPane.setHalignment(datePicker, HPos.CENTER);
        GridPane.setHalignment(confirmButton, HPos.CENTER);
        GridPane.setHalignment(cancelButton, HPos.CENTER);

        

        Scene scene = new Scene(gridPane, 400, 300);
        pickDateStage.setScene(scene);
        pickDateStage.show();
        
        
        //handle events below here
        confirmButton.setOnAction(e -> {
            // Validate input data
            
            
        	pickDateStage.close();
        });
        
        cancelButton.setOnAction(e -> {
            // close the pop-up
        	pickDateStage.close();
        });
	}

	private void updateContactInfo() {
		Stage contactInfoStage = new Stage();
		contactInfoStage.setTitle("Contact Information");

        // Create labels and text fields for vitals input
        Label addressLabel = new Label("Address: ");
        TextField addressField = new TextField();
        Label phoneNumberLabel = new Label("Phone Number: ");
        TextField phoneNumberField = new TextField();
        Label emailLabel = new Label("Email: ");
        TextField emailField = new TextField();


        Button saveButton = new Button("Update");
        Button cancelButton = new Button("Cancel");
        
        //make all the text areas non-editable
        addressField.setEditable(true);
        phoneNumberField.setEditable(true);
        emailField.setEditable(true);
        
        addressField.setPrefWidth(220);
        phoneNumberField.setPrefWidth(220);
        emailField.setPrefWidth(220);
        

        // Create grid layout for the vitals input fields
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.addRow(0, addressLabel, addressField);
        gridPane.addRow(1, phoneNumberLabel, phoneNumberField);
        gridPane.addRow(2, emailLabel, emailField);
        
        //set the alignment
        GridPane.setHalignment(saveButton, HPos.CENTER);
        GridPane.setColumnSpan(saveButton, 2);
        
        
        gridPane.addRow(3, saveButton);
        
        //set the alignment
        GridPane.setHalignment(cancelButton, HPos.CENTER);
        GridPane.setColumnSpan(cancelButton, 2);
        gridPane.addRow(4, cancelButton);

        

        Scene scene = new Scene(gridPane, 400, 300);
        contactInfoStage.setScene(scene);
        contactInfoStage.show();
        
        
        //handle events below here
        saveButton.setOnAction(e -> {
            // Validate input data
        	if (addressField.getText().isEmpty() || phoneNumberField.getText().isEmpty() || emailField.getText().isEmpty()) {
                showError("Error", "Please fill out all fields.");
                return; // Stop further execution
            }
        	
        	 // Validate phone number format
            long phoneNumber;
            try {
                phoneNumber = Long.parseLong(phoneNumberField.getText());
            } catch (NumberFormatException ex) {
                showError("Error", "Invalid phone number format.");
                return; // Stop further execution
            }
        	
        	user.setAddress(addressField.getText());
            user.setPhoneNumber(phoneNumber);
            user.setEmail(emailField.getText());
            
        	contactInfoStage.close();
        });
        
        cancelButton.setOnAction(e -> {
            // close the pop-up
        	contactInfoStage.close();
        });
	}

	private void viewContactInfo() {
		Stage contactInfoStage = new Stage();
		contactInfoStage.setTitle("Contact Information");

        // Create labels and text fields for vitals input
        Label addressLabel = new Label("Address: ");
        TextField addressField = new TextField();
        addressField.setText(user.getAddress());
        Label phoneNumberLabel = new Label("Phone Number: ");
        TextField phoneNumberField = new TextField();
        phoneNumberField.setText(""+user.getPhoneNumber());
        Label emailLabel = new Label("Email: ");
        TextField emailField = new TextField();
        emailField.setText(user.getEmail());


        Button returnButton = new Button("Go back");
        
        //make all the text areas non-editable
        addressField.setEditable(false);
        phoneNumberField.setEditable(false);
        emailField.setEditable(false);
        
        //adjust the width
        addressField.setPrefWidth(220);
        phoneNumberField.setPrefWidth(220);
        emailField.setPrefWidth(220);
        

        // Create grid layout for the vitals input fields
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.addRow(0, addressLabel, addressField);
        gridPane.addRow(1, phoneNumberLabel, phoneNumberField);
        gridPane.addRow(2, emailLabel, emailField);
        
        //set the alignment
        GridPane.setHalignment(returnButton, HPos.CENTER);
        GridPane.setColumnSpan(returnButton, 2);
        gridPane.addRow(3, returnButton);
        
        
        


        

        Scene scene = new Scene(gridPane, 400, 300);
        contactInfoStage.setScene(scene);
        contactInfoStage.show();
        
        
        //handle events below here
        returnButton.setOnAction(e -> {
            // Validate input data
            
            
        	contactInfoStage.close();
        });
        
        
	}
	
	private void openSendMessagePopup() {
        // Create a popup window to get the staff username
        Stage popupStage = new Stage();

        // Layout for the popup
        VBox popupLayout = new VBox(10);
        popupStage.setScene(new Scene(popupLayout, 400, 200));

        // Text field for entering the username
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter the username of the staff");

        // Button to confirm the username input
        Button confirmButton = new Button("Confirm");

        // Event handler for the confirm button
        confirmButton.setOnAction(event -> {
            String username = usernameField.getText();
            if (!username.isEmpty()) {
                // Show the message system with the provided username
                messageSystem.sendMessageToStaff(username);
                popupStage.close();
            } else {
                showError("Error", "Please enter the username.");
            }
        });

        // Add components to the layout
        popupLayout.getChildren().addAll(usernameField, confirmButton);

        // Show the popup stage
        popupStage.show();
    }


	private void viewMedicalHistory() {
        // Implement functionality to view medical history
        System.out.println("Viewing medical history...");
    }

   
    
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
