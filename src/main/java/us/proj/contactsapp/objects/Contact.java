package us.proj.contactsapp.objects;

public class Contact {
    private int contactID;
    private String name;
    private String phoneNumber;
    private String address;
    private String email;
    private String groupName;

    // constructor to set initial values
    public Contact(int contactID, String name, String phoneNumber, String address, String email, String groupName) {
        this.contactID = contactID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
        this.groupName = groupName;
    }

    //setters & getters
    public int getContactID() {
        return contactID;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
