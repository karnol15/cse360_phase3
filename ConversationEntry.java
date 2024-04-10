import java.time.LocalDateTime;
import java.util.List;

public class ConversationEntry {
    private List<String> participants;
    private LocalDateTime timestamp;

    public ConversationEntry(List<String> participants, LocalDateTime timestamp) {
        this.participants = participants;
        this.timestamp = timestamp;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
