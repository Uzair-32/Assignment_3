package com.example.User_Management;


// base class of all the other classes of package
public abstract class User {
    private String userID;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private String address;
    private int age;
    private String gender;
    private boolean accountStatus;

    // a default constructor 
    User() {

    }
    // Constructor to initialize all attributes
    public User(String userID, String name, String email, String phoneNumber, String password,
                String address, int age, String gender, boolean accountStatus) {
        setUserID(userID);
        setName(name);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setPassword(password);
        setAddress(address);
        setAge(age);
        setGender(gender);
        setAccountStatus(accountStatus);
    }

    // Setters with Validation
    public void setUserID(String userID) {
        if (userID == null || !userID.matches("[UPAD]\\d{5}")) { // this will ensure that the pattern of the user id should be of this type.
            throw new IllegalArgumentException("Invalid User ID! It must start with 'U','P','A' or 'D' followed by 5 digits.");
        }
        this.userID = userID;
    }

    public void setName(String name) {
        if (name == null || !name.matches("[A-Za-z ]+")) {
            throw new IllegalArgumentException("Invalid Name! It should contain only alphabets.");
        }
        this.name = name;
    }

    public void setEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid Email format! Example: abc@gmail.com");
        }
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches("\\d{10,15}")) {
            throw new IllegalArgumentException("Invalid Phone Number! Must be 10-15 digits.");
        }
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        if (password == null || password.length() < 8 || !password.matches(".*[0-9].*") || !password.matches(".*[A-Za-z].*")) {
            throw new IllegalArgumentException("Password must be at least 8 characters long with letters and numbers.");
        }
        this.password = password;
    }

    public void setAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty.");
        }
        this.address = address;
    }

    public void setAge(int age) {
        if (age < 1 || age > 120) {
            throw new IllegalArgumentException("Age must be between 1 and 120.");
        }
        this.age = age;
    }

    public void setGender(String gender) {
        if (!gender.equals("Male") && !gender.equals("Female") && !gender.equals("Others")) {  
            throw new IllegalArgumentException("Invalid Gender! Use 'Male', 'Female', or 'Others'.");
        }
        this.gender = gender;
    }

    public void setAccountStatus(boolean accountStatus) {
        this.accountStatus = accountStatus;
    }

    // Getters
    public String getUserID() { return userID; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getPassword() { return password; }
    public String getAddress() { return address; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public boolean isAccountStatus() { return accountStatus; }

    
    @Override
    public String toString() {
        return String.format("User ID: %s\nName: %s\nEmail: %s\nPhone: %s\nAge: %d\nGender: %c\nAccount Status: %s",
                userID, name, email, phoneNumber, age, gender,
                accountStatus ? "Active" : "Inactive");
    }
}
