import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MessageSystem extends Application {
  private String userId;

  public MessageSystem(String userId) {
    this.userId = userId;
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Messaging Portal");

    GridPane grid = new GridPane();
    grid.setPadding(new Insets(20, 20, 20, 20));
    grid.setVgap(10);
    grid.setHgap(10);

    TextField staffUsernameField = new TextField();
    staffUsernameField.setPromptText("Enter staff username");

    Button sendButton = new Button("Send Message");
    Button viewConversationButton = new Button("View Conversation");
    grid.add(staffUsernameField, 0, 0);
    grid.add(sendButton, 1, 0);
    grid.add(viewConversationButton, 0, 1);

    sendButton.setOnAction(e -> {
      String staffUsername = staffUsernameField.getText();
      if (!staffUsername.isEmpty()) {
        sendMessage(primaryStage, staffUsername);
      } else {
        showAlert("Error", "Please enter staff username.");
      }
    });

    viewConversationButton.setOnAction(e -> viewConversation(primaryStage));

    Scene scene = new Scene(grid, 400, 200);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void sendMessage(Stage primaryStage, String staffUsername) {
	    try {
	        TextInputDialog dialog = new TextInputDialog();
	        dialog.setTitle("Message");
	        dialog.setHeaderText("Compose your message to " + staffUsername);
	        dialog.setContentText("Enter your message:");
	        Optional<String> result = dialog.showAndWait();

	        result.ifPresent(message -> {
	            String folderPath = "Messages/" + userId;
	            String filePath = folderPath + "/" + staffUsername + ".txt";

	            try {
	                FileUtils.ensureDirectoryExists(folderPath);
	                File file = FileUtils.createFile(filePath);
	                FileWriter writer = new FileWriter(file, true);
	                writer.write(LocalDate.now() + ": " + userId + ": " + message + "\n"); // Write message with timestamp
	                writer.close();

	                showAlert("Success", "Message sent successfully to " + staffUsername);

	                // Refresh conversation view after sending message
	                viewConversation(primaryStage);
	            } catch (IOException e) {
	                showAlert("Error", "Failed to send message. Please try again.");
	            }
	        });
	    } catch (Exception e) {
	        showAlert("Error", "An unexpected error occurred.");
	    }
	}


  public void sendMessageToStaff(String staffUsername) {
    Stage primaryStage = new Stage();
    sendMessage(primaryStage, staffUsername);
  }

  public void replyToPatient(String patientUsername) {
    Stage primaryStage = new Stage();
    sendMessage(primaryStage, patientUsername);
  }

  private void viewConversation(Stage primaryStage) {
	    try {
	        String folderPath = "Messages/" + userId;
	        File folder = new File(folderPath);
	        File[] files = folder.listFiles();

	        if (files != null && files.length > 0) {
	            VBox conversationBox = new VBox();
	            TextArea conversationArea = new TextArea();
	            conversationArea.setEditable(false); // Set TextArea to non-editable
	            
	            // Create a list to hold messages
	            List<String> messages = new ArrayList<>();
	            
	            // Read messages from all files and add them to the list
	            for (File file : files) {
	                BufferedReader reader = new BufferedReader(new FileReader(file));
	                String line;
	                while ((line = reader.readLine()) != null) {
	                    messages.add(line);
	                }
	                reader.close();
	            }
	            
	            // Sort messages chronologically based on timestamp
	            Collections.sort(messages);
	            
	            // Display messages in the correct order
	            for (String message : messages) {
	                String[] parts = message.split(": ", 3);
	                if (parts.length == 3) {
	                    String sender = parts[1];
	                    String messageContent = parts[2];
	                    conversationArea.appendText("From: " + sender + "\nMessage: " + messageContent + "\n\n");
	                }
	            }
	            
	            conversationBox.getChildren().add(conversationArea);

	            ScrollPane scrollPane = new ScrollPane(conversationBox);
	            scrollPane.setFitToWidth(true);

	            Stage conversationStage = new Stage();
	            conversationStage.setTitle("Conversation");
	            conversationStage.setScene(new Scene(scrollPane, 400, 300));
	            conversationStage.show();
	        } else {
	            showAlert("Info", "No conversation found.");
	        }
	    } catch (IOException e) {
	        showAlert("Error", "Failed to load conversation. Please try again.");
	    }
	}


  private void showAlert(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
  }
}