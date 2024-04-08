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
        Label welcomeLabel1 = new Label("Staff Login");
        
        //
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        
        //create a toggle group that allows the user to pick their role
        ToggleGroup loginOptions = new ToggleGroup();
        doctor = new RadioButton("Doctor");
        nurse = new RadioButton("Nurse");
        doctor.setToggleGroup(loginOptions);
        nurse.setToggleGroup(loginOptions);
        
        
        // Create an ImageView with the image
        Image image = new Image("stafflogin.jpeg"); 
        ImageView imageView = new ImageView(image);
        
        // Set preserve ratio to true so that image won't stretch disproportionately
        imageView.setPreserveRatio(true);
        

        // Bind the fitWidth property of ImageView to the width of the VBox
        //imageView.fitWidthProperty().bind(primaryStage.widthProperty());

        // Create a VBox and add the ImageView to it
        VBox logoBox = new VBox();
        logoBox.getChildren().add(welcomeLabel1);
        logoBox.setAlignment(Pos.CENTER);

        // Set preferred width for the VBox
        logoBox.setPrefWidth(500);

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button signInButton = new Button("Sign In");

        VBox layout = new VBox(10);
        layout.getChildren().addAll(welcomeLabel,usernameLabel, usernameField, passwordLabel, passwordField, signInButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setPrefWidth(300);
        
        HBox middleLayout = new HBox(10);
        middleLayout.getChildren().addAll(logoBox, layout);
        
        

        primaryStage.setScene(new Scene(middleLayout, 850, 700));
        primaryStage.show();
        
        //handle all events below here
        
        signInButton.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
            	 // Get username and password from input fields
                String username = usernameField.getText();
                String password = passwordField.getText();

                // Perform authentication logic
                boolean isAuthenticated = authenticate(username, password);

                if (isAuthenticated) {
                    // Authentication successful, open dashboard based on role
                    openNurseDashboard(username);
                } else {
                    // Authentication failed, show error message
                    showError("Error", "Incorrect username or password. Please try again.");
                }
            }
        });
        
    }

    private boolean authenticate(String username, String password) {
        // Perform authentication logic here
        // Will need to implement file checking logic
        // right now its a hardcoded check
        return username.equals("admin") && password.equals("admin");
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
