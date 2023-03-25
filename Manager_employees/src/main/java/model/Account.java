package model;

public class Account {
    private String user_name;
    private String pass_word;

    public Account() {
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPass_word() {
        return pass_word;
    }

    public void setPass_word(String pass_word) {
        this.pass_word = pass_word;
    }

    @Override
    public String toString() {
        return "Account{" +
                "user_name='" + user_name + '\'' +
                ", pass_word='" + pass_word + '\'' +
                '}';
    }
}
