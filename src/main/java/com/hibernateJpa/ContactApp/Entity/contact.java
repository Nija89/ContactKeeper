package com.hibernateJpa.ContactApp.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "contacts")
public class contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private int id;
    @Column(name = "phone_number")
    private int pNumber;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private user currentUser;

    public contact(){}

    public contact(user currentUser,int pNumber, String firstName, String lastName) {
        this.pNumber = pNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.currentUser = currentUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getpNumber() {
        return pNumber;
    }

    public void setpNumber(int pNumber) {
        this.pNumber = pNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "contact{" +
                "id=" + id +
                ", pNumber=" + pNumber +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
