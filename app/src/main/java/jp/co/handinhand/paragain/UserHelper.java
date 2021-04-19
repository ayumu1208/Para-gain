package jp.co.handinhand.paragain;

public class UserHelper {
    String username, email, phoneNunmber,password;

    public UserHelper() {
    }

    public UserHelper(String username, String email, String phoneNunmber, String password) {
        this.username = username;
        this.email = email;
        this.phoneNunmber = phoneNunmber;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNunmber() {
        return phoneNunmber;
    }

    public void setPhoneNunmber(String phoneNunmber) {
        this.phoneNunmber = phoneNunmber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
