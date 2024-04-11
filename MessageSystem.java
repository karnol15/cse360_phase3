import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    grid.add(viewConversationButton, 1, 1);

    sendButton.setOnAction(e -> {
      String staffUsername = staffUsernameField.getText();
      if (!staffUsername.isEmpty()) {
        sendMessage(primaryStage, staffUsername);
      } else {
        showAlert("Error", "Please enter staff username.");
      }
    });

    viewConversationButton.setOnAction(e -> viewConversation(primaryStage, "", "", true));

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
                viewConversation(primaryStage, staffUsername, message, true);
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

  private void viewConversation(Stage primaryStage, String receiver, String pendingMessage, boolean isSending) {
	    try {
	        String folderPath = "Messages/" + receiver;
	        File folder = new File(folderPath);
	        FileUtils.ensureDirectoryExists(folderPath);
	        File[] files = folder.listFiles();
	        
            VBox conversationBox = new VBox();
            TextArea conversationArea = new TextArea();
            conversationArea.setEditable(false); // Set TextArea to non-editable
            
            if (files != null && files.length > 0) {
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
            }
            conversationBox.getChildren().add(conversationArea);
            
            if (isSending) {
            
	            // Confirmation message
	            Label confirmationLabel = new Label();
	            confirmationLabel.setText("Are you sure you want to send this message to " + receiver + ":\n\n" + pendingMessage);
	            confirmationLabel.setAlignment(Pos.CENTER);
	            
	            // Confirmation controls
	            Button confirmButton = new Button("Confirm");
	            Button cancelButton = new Button("Cancel");

	            HBox confirmationBox = new HBox(confirmButton, cancelButton);
	            confirmationBox.setAlignment(Pos.CENTER);
	            
	            confirmationBox.setSpacing(10);
	            confirmationBox.setPadding(new Insets(10, 0, 0, 0));
	            
	            conversationBox.getChildren().addAll(confirmationLabel, confirmationBox);

	            confirmButton.setOnAction(e -> {
	            	String folderPath1 = "Messages/" + userId;
    	            String filePath1 = folderPath1 + "/" + userId + ".txt";
    	            
    	            String folderPath2 = "Messages/" + receiver;
    	            String filePath2 = folderPath2 + "/" + userId + ".txt";

    	            try {
    	                FileUtils.ensureDirectoryExists(folderPath1);
    	                File file1 = FileUtils.createFile(filePath1);
    	                FileWriter writer1 = new FileWriter(file1, true);
    	                writer1.write(LocalDate.now() + ": " + userId + ": " + pendingMessage + "\n"); // Write message with timestamp
    	                writer1.close();
    	                
    	                FileUtils.ensureDirectoryExists(folderPath2);
    	                File file2 = FileUtils.createFile(filePath2);
    	                FileWriter writer2 = new FileWriter(file2, true);
    	                writer2.write(LocalDate.now() + ": " + userId + ": " + pendingMessage + "\n"); // Write message with timestamp
    	                writer2.close();

    	                showAlert("Success", "Message sent successfully to " + receiver);
    	                viewConversation(primaryStage, receiver, pendingMessage, false);
    	                //conversationStage.close();
    	            } catch (IOException error) {
    	                showAlert("Error", "Failed to send message. Please try again.");
    	            }
	            	confirmationBox.getChildren().clear();
	                confirmationLabel.setText("");
	            });

	            cancelButton.setOnAction(e -> {
	                // Implement cancellation action here
	                // This will be executed when the user cancels
	            	confirmationBox.getChildren().clear();
	                confirmationLabel.setText("");
	            });
            } else {
            	Button sendButton = new Button("Send Message");
            	
	            HBox sendBox = new HBox(sendButton);
	            sendBox.setAlignment(Pos.CENTER);
	            conversationBox.getChildren().add(sendBox);

	            sendButton.setOnAction(e -> {
	            	sendMessage(primaryStage, receiver);
	            	sendBox.getChildren().clear();
	            });
            }
            
            ScrollPane scrollPane = new ScrollPane(conversationBox);
            scrollPane.setFitToWidth(true);

            Stage conversationStage = new Stage();
            conversationStage.setTitle("Conversation");
            conversationStage.setScene(new Scene(scrollPane, 400, 300));
            conversationStage.show();
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