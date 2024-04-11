import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class SignInPage {
    private Stage primaryStage;
    private RadioButton doctor, nurse;

    public SignInPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void show() {
        primaryStage.setTitle("Healthcare Provider Sign In");
        
        Label welcomeLabel = new Label("Staff Login");
        
        
        //
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));


        Label quote = new Label("\"Pediatrics: Making a big difference to little people\"");
        quote.setFont(Font.font("Arial", FontWeight.LIGHT, 24));
        Label definition = new Label("Did you know \"paediatrics\" translates to \"healer of children\" in Greek? ");
        definition.setFont(Font.font("Arial", FontWeight.LIGHT, 18));
        
        
        //create a toggle group that allows the user to pick their role
        ToggleGroup loginOptions = new ToggleGroup();
        doctor = new RadioButton("Doctor");
        nurse = new RadioButton("Nurse");
        doctor.setToggleGroup(loginOptions);
        nurse.setToggleGroup(loginOptions);
        
        //adjust the font size of the buttons to match their corresponding label
        doctor.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 16));
        nurse.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 16));

        
        Label staffOption = new Label("I am a :");
        staffOption.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        VBox options = new VBox(10);
        options.getChildren().addAll(staffOption, doctor, nurse);
        
        // Create an ImageView with the image
        Image image = new Image("stafflogin.jpeg"); 
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(500);
        
        // Set preserve ratio to true so that image won't stretch disproportionately
        imageView.setPreserveRatio(true);

        // Create a VBox and add the ImageView to it
        VBox logoBox = new VBox();
        logoBox.getChildren().add(imageView);
        logoBox.setAlignment(Pos.CENTER_LEFT);

        // Set preferred width for the VBox
        logoBox.setPrefWidth(500);

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button signInButton = new Button("Sign In");
        Button signUpButton = new Button("Sign Up");
        Button goBackBtn = new Button("Go back");
        
        //allow staff to create accounts
        Label newStaffLabel = new Label("New staff? Register with our office below!");
        newStaffLabel.setFont(Font.font("Arial", 14));

        VBox layout = new VBox(10);
        layout.getChildren().addAll(options, usernameLabel, usernameField, passwordLabel, passwordField, signInButton, newStaffLabel, signUpButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setPrefWidth(300);
        
        HBox middleLayout = new HBox(10);
        middleLayout.getChildren().addAll(layout, logoBox);
        middleLayout.setAlignment(Pos.CENTER_LEFT);
        
        VBox finalLayout = new VBox(10);
        finalLayout.getChildren().addAll(welcomeLabel, quote, middleLayout, definition, goBackBtn);
        finalLayout.setAlignment(Pos.CENTER);
        
        

        primaryStage.setScene(new Scene(finalLayout, 850, 700));
        primaryStage.show();
        
        //handle all events below here
        
        signInButton.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	//check that a role has been selected
            	if(loginOptions.getSelectedToggle() == null) {
            		showError("Error", "Please select a role and try again.");
            		return;
            	}
            	
            	//check that the fields arent empty
            	if(usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            		showError("Error", "Please fill out all fields and try again.");
            		return;
            	}
            	 // Get username and password from input fields
                String username = usernameField.getText();
                String password = passwordField.getText();

                // Perform authentication logic
                User userType = authenticate(username, password);
                
                if (userType instanceof Nurse) {
                    // Authentication successful, open dashboard based on role
                    openNurseDashboard(username);
                } else if (userType instanceof Doctor) {
                	
                	openDoctorDashboard(username);
                    // Authentication failed, show error message
                }
                else {showError("Error", "Incorrect username or password. Please try again.");}
            }
        });
        
        //this changes the scene to the signUp page
        signUpButton.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	staffSignUp signUpPage = new staffSignUp(primaryStage);
                signUpPage.show();
            }
        });
        
        goBackBtn.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	WelcomePage home = new WelcomePage(primaryStage);
                home.show();
            }
        });
        
    }

    private User authenticate(String username, String password) {
        // Perform authentication logic here
        // Will need to implement file checking logic
        // right now its a hardcoded check
    	if (Main.userMap.containsKey(username)) {
    		if (Main.userMap.get(username).getHash() == password.hashCode()) {
    			return Main.userMap.get(username);
    		}
    	}
        return null;
    }

    private void openNurseDashboard(String username) {
        // Open dashboard based on the role of the user
        // For demonstration purposes, let's assume there is only one dashboard for all users
        // Replace "DashboardPage" with the appropriate dashboard class based on the user's role
        NurseDashboard dashboardPage = new NurseDashboard(primaryStage, "john", "doe", 30, "today");
        dashboardPage.show();
    }
    
    private void openDoctorDashboard(String username) {
        // Open dashboard based on the role of the user
        // For demonstration purposes, let's assume there is only one dashboard for all users
        // Replace "DashboardPage" with the appropriate dashboard class based on the user's role
        DoctorDashboard dashboardPage = new DoctorDashboard(primaryStage, "john", "doe", 30, "today");
        dashboardPage.show();
    }
    
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
