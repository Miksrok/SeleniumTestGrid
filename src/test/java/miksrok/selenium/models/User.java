package miksrok.selenium.models;

import java.util.Random;

/**
 * Created by Залізний Мозок on 25.04.2017.
 */
public class User {

    private String name;
    private String surname;
    private String email;
    private int postcode;
    private String city;
    private String address;

    public User(String name, String surname, String email, int postcode, String city, String address) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.postcode = postcode;
        this.city = city;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public int getPostcode() {
        return postcode;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public static User generate(){

        return new User(
                "Ivan",
                "Ivanov",
                "newEmail"+System.currentTimeMillis()+"@gmailll.com",
                99999,
                "Kiev",
                "sdsdsds 12"
        );

    }

}
