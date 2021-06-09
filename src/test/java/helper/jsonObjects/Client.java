package helper.jsonObjects;

public class Client {
    private String email;
    private String phone;
    private String mobile;
    private String sessionToken;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String fullName;
    private int id;

    public void setAttribute(String attribute, String value) {
        if (attribute.contains("firstName")) {
            setFirstName(value);
        } else if (attribute.contains("lastName")) {
            setLastName(value);
        } else if (attribute.contains("email")) {
            setEmail(value);
        } else if (attribute.contains("phone")) {
            setPhone(value);
        } else if (attribute.contains("mobile")) {
            setMobile(value);
        }
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getMobile() { return mobile; }

    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFullName() { return fullName; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getSessionToken() { return sessionToken; }

    public void setSessionToken(String sessionCookie) { this.sessionToken = sessionCookie; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Client(String firstName, String lastName, String email, String phone, String mobile) {
        super();
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.mobile = mobile;
    }

    public Client() {
        super();
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.mobile = mobile;
    }
}
