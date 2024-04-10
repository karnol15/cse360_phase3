import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Conversation {
	private List<String> participants;
    private List<Message> conversation;
    private File conversationFile;

    public Conversation(String participant1, String participant2) throws FileNotFoundException {
    	participants = new ArrayList<>();
        conversation = new ArrayList<>();
        conversationFile = new File("message" + File.separator + participant1 + "_" + participant2 + ".txt");
        
        participants.add(participant1);
        participants.add(participant2); // Remove the '.txt' extension
        
        if (conversationFile.exists()) {
        	loadConversation();
        }
    }

    private void loadConversation() {
        try (BufferedReader reader = new BufferedReader(new FileReader(conversationFile))) {
            String line;
            LocalDateTime timestamp = null;
            String sender = null;
            StringBuilder contentBuilder = new StringBuilder();
            
            while ((line = reader.readLine()) != null) {
                if (timestamp == null) {
                    timestamp = LocalDateTime.parse(line);
                } else if (sender == null) {
                    sender = line;
                } else if (line.charAt(0) == '@') {
                //Extracting information based on the first character
                	String contentLine = line.substring(1); // Remove type indicator
                	contentBuilder.append(contentLine);
                	contentBuilder.append("\n");
                } else {
                    conversation.add(new Message(timestamp, sender, contentBuilder.toString()));
                    timestamp = null;
                    sender = null;
                    contentBuilder.setLength(0); // Clear StringBuilder
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Message> getConversation() {
        return conversation;
    }

    public void sendMessage(String sender, String content) {
        LocalDateTime timestamp = LocalDateTime.now();
        Message message = new Message(timestamp, sender, content);
        conversation.add(message);
        saveMessage(message);
    }
    
    private void saveMessage(Message message) {
        try {
            if (!conversationFile.exists()) {
                conversationFile.createNewFile();
            }

            // Append the message to the conversation file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(conversationFile, true))) {
            	writer.write(message.getTimestamp().toString());
                writer.newLine();
                writer.write(message.getSender());
                writer.newLine();
                
                // Write content lines starting with "@"
                String[] contentLines = message.getContent().split("\n");
                for (int i = 0; i < contentLines.length; i++) {
                	if (i == contentLines.length - 1) {
                        writer.write(contentLines[i]); // Last line without "@"
                    } else {
                        writer.write("@" + contentLines[i]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
