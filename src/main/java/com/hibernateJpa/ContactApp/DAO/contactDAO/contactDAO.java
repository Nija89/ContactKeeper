package com.hibernateJpa.ContactApp.DAO.contactDAO;

import com.hibernateJpa.ContactApp.Entity.contact;
import com.hibernateJpa.ContactApp.Entity.user;

import java.util.List;

public interface contactDAO {
    void addContact(contact newContact);

    List<contact> viewAllContact(user currentUser);

    contact findContactByFirstName(String fname);

    void deleteContactByFirstName(String fName);
}
