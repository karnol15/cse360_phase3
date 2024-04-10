import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConversationHistory {
    private List<ConversationEntry> conversationEntries;
    private File historyFile;

    public ConversationHistory(String user) {
    	conversationEntries = new ArrayList<>();
        historyFile = new File("message" + File.separator + "history" + File.separator + user + ".txt");
        if (!historyFile.exists()) {
            try {
                historyFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
        	loadHistory();
        }
    }

    private void loadHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader(historyFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String participant1 = parts[0];
                    String participant2 = parts[1];
                    LocalDateTime timestamp = LocalDateTime.parse(parts[2]);
                    
                    List<String> participants = new ArrayList<>();
                    participants.add(participant1);
                    participants.add(participant2);
                    
                    conversationEntries.add(new ConversationEntry(participants, timestamp));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void addConversationEntry(ConversationEntry conversationEntry) {
        // Check if a conversation entry with the same participants already exists
        ConversationEntry existingEntry = findConversationEntryByParticipants(conversationEntry.getParticipants());
        if (existingEntry != null) {
            // If exists, remove the old entry
            conversationEntries.remove(existingEntry);
        }

        // Add the new conversation entry
        conversationEntries.add(conversationEntry);
        
        // Save the updated history
        saveHistory();
    }

    private ConversationEntry findConversationEntryByParticipants(List<String> participants) {
        for (ConversationEntry entry : conversationEntries) {
            if (entry.getParticipants().equals(participants)) {
                return entry;
            }
        }
        return null; // If no matching entry found
    }

    private void saveHistory() {
    	try (BufferedWriter writer = new BufferedWriter(new FileWriter(historyFile))) {
            for (ConversationEntry entry : conversationEntries) {
                writer.write(String.join(",", entry.getParticipants()) + ",");
                writer.write(entry.getTimestamp().toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<ConversationEntry> getConversationEntries() {
        return conversationEntries;
    }
}
