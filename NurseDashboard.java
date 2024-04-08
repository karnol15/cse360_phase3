import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
        Label ageLabel = new Label("Age: " + patientAge);
        Label birthdayLabel = new Label("Birthday: " + patientBirthday);

        // Buttons for various actions
        Button viewMedicalHistoryButton = new Button("View Medical History");
        Button makeAppointmentButton = new Button("Make Appointment");

        // Set action handlers for buttons (if needed)
        viewMedicalHistoryButton.setOnAction(e -> viewMedicalHistory());
        makeAppointmentButton.setOnAction(e -> makeAppointment());

        // Layout for patient dashboard
        VBox layout = new VBox(10);
        layout.getChildren().addAll(nameLabel, ageLabel, birthdayLabel, viewMedicalHistoryButton, makeAppointmentButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        primaryStage.setScene(new Scene(layout, 850, 700));
        primaryStage.show();
    }

    private void viewMedicalHistory() {
        // Implement functionality to view medical history
        System.out.println("Viewing medical history...");
    }

    private void makeAppointment() {
        // Implement functionality to make appointment
        System.out.println("Making appointment...");
    }
}
