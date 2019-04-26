package ru.CRMEntities;

public class UserEntity {
    public String login;
    public String password;
    public String post;
    public String lastName;
    public String firstName;
    public String middleName;
    public String apartmentNumber;
    public String phoneNumber;

    public UserEntity(String login, String password, String post,
               String lastName, String firstName, String middleName,
               String apartmentNumber, String phoneNumber) {
        this.login = login;
        this.password = password;
        this.post = post;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.apartmentNumber = apartmentNumber;
        this.phoneNumber = phoneNumber;
    }
}
