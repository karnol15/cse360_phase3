import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WelcomePage {
    private Stage primaryStage;
    
   

    public WelcomePage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void show() {
        primaryStage.setTitle("Welcome");
        
        //Welcome label
        Label title = new Label("Welcome to the Pediatric Doctor's Office System");
        
        //
        title.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        
        //login
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        
        VBox fieldsBox = new VBox(10);
        fieldsBox.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField);
        fieldsBox.setAlignment(Pos.CENTER_LEFT);
        
        
        Button signInButton = new Button("Sign In");
        Button signUpButton = new Button("Sign Up");
        
        
        // Create an ImageView with the image
        Image image = new Image("logo.png"); 
        ImageView imageView = new ImageView(image);
        
        // Adjust the size of the ImageView
        imageView.setFitWidth(350); // Set width as needed
        imageView.setPreserveRatio(true); // Maintain aspect ratio

        // Create a VBox and add the ImageView to it
        VBox logoBox = new VBox();
        logoBox.getChildren().add(imageView);
        logoBox.setAlignment(Pos.CENTER);
        logoBox.setPrefWidth(400);
        
        //healthcare provider label to redirect to a different login page
        Button HCBtn = new Button("Healthcare Provider? Sign in here");
        

        VBox signInBox = new VBox(10);
        signInBox.getChildren().addAll(fieldsBox, signInButton, signUpButton);
        signInBox.setAlignment(Pos.CENTER);
        signInBox.setPadding(new Insets(20));
        signInBox.setPrefWidth(400);
        
        HBox layout = new HBox(10);
        layout.getChildren().addAll(logoBox, signInBox);
        layout.setAlignment(Pos.CENTER);
        
        VBox finalLayout = new VBox(10);
        
        finalLayout.getChildren().addAll(title, layout, HCBtn);
        finalLayout.setAlignment(Pos.CENTER);


        primaryStage.setScene(new Scene(finalLayout, 850, 700));
        primaryStage.show();
        
        //handle all events below here
        
        signInButton.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	 // Get username and password from input fields
                String username = usernameField.getText();
                String password = passwordField.getText();

                // Perform authentication logic
                User rlUser = authenticate(username, password);

                if (rlUser!=null) {
                    // Authentication successful, open dashboard based on role
                    openDashboard(rlUser);
                } else {
                    // Authentication failed, show error message
                    showError("Error", "Incorrect username or password. Please try again.");
                }
            }
        });
        
        //this changes the scene to the signUp page
        signUpButton.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	SignUpPage signUpPage = new SignUpPage(primaryStage);
                signUpPage.show();
            }
        });
        
        //take the healthcare provider to their separate login page
         HCBtn.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	SignInPage signInPage = new SignInPage(primaryStage);
                signInPage.show();
            }
        });
        
    }
    
    private User authenticate(String username, String password) {
        if (Main.userMap.containsKey(username)) {
        	if (password.hashCode() == Main.userMap.get(username).getHash()) {
        		return Main.userMap.get(username);
        	}
        }
        return null;
    }

    private void openDashboard(User rlUser) {
        // Open dashboard based on the role of the user
        // For demonstration purposes, let's assume there is only one dashboard for all users
        // Replace "DashboardPage" with the appropriate dashboard class based on the user's role
    	PatientDashboard dashboardPage = new PatientDashboard(primaryStage, rlUser);
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

