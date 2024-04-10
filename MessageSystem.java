import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessageSystem {
    private Stage primaryStage;
    private String userId;
    private String participant2;
    private ConversationHistory conversationHistory;
    private Conversation conversation;

    public MessageSystem(Stage primaryStage, String userId) {
        this.primaryStage = primaryStage;
        this.userId = userId;
        this.conversationHistory = new ConversationHistory(userId);
    }

    public void show() {
        // Left pane for conversation choices
        ListView<String> conversationList = new ListView<>();
        List<ConversationEntry> conversationEntries = conversationHistory.getConversationEntries();
        for (ConversationEntry conversationEntry : conversationEntries) {
            List<String> participants = conversationEntry.getParticipants();
            for (String participant : participants) {
                if (!participant.equals(userId)) {
                    conversationList.getItems().add(participant);
                } else {
                	participant2 = participant;
                }
            }
        }
        conversationList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                // Load conversation based on selected item
            	participant2 = newVal;
                loadConversation(newVal);
            }
        });

        // Right pane for conversation history and input
        VBox conversationPane = new VBox(10);
        conversationPane.setPadding(new Insets(10));

        // Text area to display conversation history
        TextArea conversationHistoryTextArea = new TextArea();
        conversationHistoryTextArea.setEditable(false);

        // Text field for typing new messages
        TextField messageField = new TextField();
        messageField.setPromptText("Type your message here");

        // Button to send messages
        Button sendButton = new Button("Send");
        sendButton.setOnAction(e -> {
            String message = messageField.getText().trim();
            if (!message.isEmpty()) {
                // Append message to conversation history
                appendMessage(conversationHistoryTextArea, userId + ": " + message, true);
                
                LocalDateTime timestamp = LocalDateTime.now();
                List<String> participants = new ArrayList<>();
                participants.add(userId);
                participants.add(participant2);
                ConversationEntry conversationEntry = new ConversationEntry(participants, timestamp);
                conversationHistory.addConversationEntry(conversationEntry);
                
                conversation.sendMessage(userId, message);
                
                messageField.clear();
            }
        });

        // Layout for conversation history, text field, and send button
        VBox messageLayout = new VBox(10);
        messageLayout.getChildren().addAll(conversationHistoryTextArea, messageField, sendButton);

        // Combine left and right panes
        BorderPane root = new BorderPane();
        root.setLeft(conversationList);
        root.setCenter(messageLayout);

        primaryStage.setScene(new Scene(root, 850, 700));
        primaryStage.show();
    }

    private void appendMessage(TextArea conversationHistoryTextArea, String message, boolean isSentByUser) {
        String backgroundColor = isSentByUser ? "-fx-control-inner-background: lightblue;" : "";
        conversationHistoryTextArea.appendText(message + "\n");
        conversationHistoryTextArea.setStyle(backgroundColor);
    }

    private void loadConversation(String participant2) {
        try {
        	int comparison = participant2.compareTo(userId);
            if (comparison < 0) {
            	conversation = new Conversation(participant2, userId);
            } else {
            	conversation = new Conversation(userId, participant2);
            }
            List<Message> messages = conversation.getConversation();

            // Display the loaded conversation in the conversation history text area
            TextArea conversationHistoryTextArea = new TextArea();
            conversationHistoryTextArea.setEditable(false);

            for (Message message : messages) {
                String formattedMessage = message.getSender() + ": " + message.getContent();
                appendMessage(conversationHistoryTextArea, formattedMessage, message.getSender().equals(userId));
            }

            // Update the conversation history text area
            BorderPane root = (BorderPane) primaryStage.getScene().getRoot();
            VBox messageLayout = (VBox) root.getCenter();
            messageLayout.getChildren().set(0, conversationHistoryTextArea);
        } catch (FileNotFoundException e) {
            // Handle conversation not found
            e.printStackTrace();
        }
    }
}
