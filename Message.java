import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Message {
    private String messageContent;
    private User sender;
    private User recipient;
    private String subject;
    private int date;
    private int time;

    public Message(User sender, User recipient, String subject, String messageContent) {
        this.sender = sender;
        this.recipient = recipient;
        this.subject = subject;
        this.messageContent = messageContent;
        // Assuming date and time are initialized at the time of creation
        this.date = LocalDate.now().getDayOfMonth();
        this.time = LocalTime.now().getHour() * 100 + LocalTime.now().getMinute();
    }

    // Method to read patient information from a file and return a list of patients
    public static List<Patient> readPatientsFromFile(String filePath) {
        List<Patient> patients = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Assuming patient information is stored in CSV format: firstName,lastName,age,birthday
                String[] parts = line.split(",");
                String firstName = parts[0];
                String lastName = parts[1];
                int age = Integer.parseInt(parts[2]);
                String birthday = parts[3];

                // Create a Patient object and add it to the list
                patients.add(new Patient(firstName, lastName, age, birthday));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return patients;
    }

    // Method to send a message to a patient
    public void sendMessageToPatient(String patientFirstName, String patientLastName, String filePath) {
        // Read patient information from file
        List<Patient> patients = readPatientsFromFile(filePath);

        // Find the patient from the list
        User recipient = null;
        for (Patient patient : patients) {
            if (patient.getFirstName().equals(patientFirstName) && patient.getLastName().equals(patientLastName)) {
                recipient = patient;
                break;
            }
        }

        if (recipient != null) {
            // Create a message and send it to the patient
            Message message = new Message(sender, recipient, subject, messageContent);
            System.out.println("Message sent to patient: " + message.getSubject());
        } else {
            System.out.println("Patient not found.");
        }
    }

    public String getMessageContent() {
        return messageContent;
    }

    public User getSender() {
        return sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public String getSubject() {
        return subject;
    }

    public int getDate() {
        return date;
    }

    public int getTime() {
        return time;
    }
}

