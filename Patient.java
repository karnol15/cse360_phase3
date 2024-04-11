import java.io.Serializable;
import java.time.LocalDate;

public class Patient implements Serializable {
    private String fName;
    private String lName;
    private LocalDate birthday;
    private int hashPword;
    private String address;
    private long phoneNumber;
    private String email;

    // Constructor
    public Patient(String fName, String lName, LocalDate birthday, int hashPword, String address, long phoneNumber, String email) {
        this.fName = fName;
        this.lName = lName;
        this.birthday = birthday;
        this.hashPword = hashPword;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Getters and setters
    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public int getHashPword() {
        return hashPword;
    }

    public void setHashPword(int hashPword) {
        this.hashPword = hashPword;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Additional methods as needed
    public int getHash() {
		return hashPword;
	}
}
