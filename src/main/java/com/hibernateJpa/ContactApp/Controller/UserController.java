package com.hibernateJpa.ContactApp.Controller;

import com.hibernateJpa.ContactApp.Entity.contact;
import com.hibernateJpa.ContactApp.Entity.user;
import com.hibernateJpa.ContactApp.DAO.contactDAO.contactDAO;
import com.hibernateJpa.ContactApp.DAO.userDAO.userDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Scanner;

@Controller
public class UserController {
    private final userDAO myUserDAO;
    private final contactDAO myContactDAO;
    private final Scanner scanner;
    private static boolean isloggedin = false;
    private user currentUser = null;

    @Autowired
    public UserController(userDAO theUserDAO, contactDAO theContactDAO) {
        this.myUserDAO = theUserDAO;
        this.myContactDAO = theContactDAO;
        this.scanner = new Scanner(System.in);
    }

    public void mainController() {
        boolean weContinue = true;
        while (weContinue) {
            if (isloggedin) {
                System.out.println("Currently Logged-in = " + currentUser.getUserName());
            } else {
                System.out.println("Please Login");
            }
            showCommand();
            boolean hasNextInt = scanner.hasNextInt();
            if (hasNextInt) {
                int action = scanner.nextInt();
                scanner.nextLine();
                switch (action) {
                    case 0 -> {
                        System.out.println("Application Closed.");
                        weContinue = false;
                        scanner.close();
                    }
                    case 1 -> {
                        createUser(myUserDAO);
                    }
                    case 2 -> {
                        currentUser = login(myUserDAO);
                    }
                    case 3 -> {
                        addContact(currentUser, myContactDAO);
                    }
                    case 4 -> {
                        viewAllContact(currentUser, myContactDAO);
                    }
                    case 5 -> {
                        findContactByFirstName(myContactDAO);
                    }
                    case 6 -> {
                        deleteContactByFirstName(myContactDAO);
                    }
                    case 7 -> {
                        deleteLoggedinUser(myUserDAO);
                    }
                    default -> {
                        System.out.println("Please enter the number show only.");
                    }
                }
            } else {
                scanner.nextLine();
                System.out.println("Enter Number Only.");
            }
        }
    }

    private void deleteLoggedinUser(userDAO myUserDAO) {
        if (isloggedin) {
            System.out.print("Are you sure you wanna delete the user? Type Yes/No? :");
            String confirmation = scanner.nextLine();
            if (confirmation.equalsIgnoreCase("yes")) {
                myUserDAO.deleteLoggedinUser(currentUser);
                System.out.println("Successfully deleted.");
                isloggedin = false;
            } else {
                System.out.println("Continue Working.");
            }
        } else {
            System.out.println("Please Login First.");
        }
    }

    public void deleteContactByFirstName(contactDAO myContactDAO) {
        if (isloggedin) {
            System.out.println("Enter a firstName: ");
            String firstName = scanner.nextLine();
            contact myContact = myContactDAO.findContactByFirstName(firstName);
            if (myContact != null) {
                myContactDAO.deleteContactByFirstName(firstName);
                System.out.println("Contact deleted for " + myContact.getFirstName());
            } else {
                System.out.println("No contact found with name " + firstName);
            }
        } else {
            System.out.println("Please Login First.");
        }
    }

    public void findContactByFirstName(contactDAO myContactDAO) {
        if (isloggedin) {
            System.out.println("Enter a firstName: ");
            String firstName = scanner.nextLine();
            contact myContact = myContactDAO.findContactByFirstName(firstName);
            if (myContact != null) {
                System.out.println("Contact found for " + myContact.getFirstName() + " -> " + myContact.getpNumber());
            } else {
                System.out.println("No contact found with name " + firstName);
            }
        } else {
            System.out.println("Please Login First.");
        }
    }

    public void viewAllContact(user currentUser, contactDAO myContactDAO) {
        if (isloggedin) {
            List<contact> myContactList = myContactDAO.viewAllContact(currentUser);
            if (!myContactList.isEmpty()) {
                for (contact x : myContactList) {
                    System.out.println(x.toString());
                }
            } else {
                System.out.println("No contact Added. Please add contact!");
            }
        } else {
            System.out.println("Please Login First.");
        }
    }

    public void addContact(user currentUser, contactDAO myContactDAO) {
        if (isloggedin) {
            System.out.println("Enter a firstName: ");
            String firstName = scanner.nextLine();
            System.out.println("Enter a secondName: ");
            String lastName = scanner.nextLine();
            System.out.println("Enter phone Number: ");
            int pNumber = scanner.nextInt();
            contact myContact = new contact(currentUser, pNumber, firstName, lastName);
            myContactDAO.addContact(myContact);
            System.out.println("Contact added successfully.");
        } else {
            System.out.println("Please Login First.");
        }
    }

    public user login(userDAO myUserDAO) {
        if (!isloggedin) {
            System.out.println("Enter a username: ");
            String username = scanner.nextLine();
            System.out.println("Enter a password: ");
            String password = scanner.nextLine();
            boolean correct = myUserDAO.login(username, password);
            if (correct) {
                isloggedin = true;
                System.out.println("Successfully Logged-in");
                return myUserDAO.findUserByUsername(username);
            } else {
                System.out.println("Unable to log-in. Incorrect username or password");
                return null;
            }
        } else {
            System.out.println("You are currently logged-in: " + currentUser.getUserName());
            return currentUser;
        }
    }

    public void createUser(userDAO myUserDAO) {
        System.out.println("Enter a username: ");
        String username = scanner.nextLine();
        System.out.println("Enter a email: ");
        String email = scanner.nextLine();
        System.out.println("Enter a password: ");
        String password = scanner.nextLine();
        try {
            user myUser = new user(username, email, password);
            myUserDAO.createUser(myUser);
            System.out.println("User created successfully." + myUser);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Error: Duplicate name found");
        }
    }

    public void showCommand() {
        System.out.println("""
            0. To Exit
            1. To create User
            2. To Login
            3. To add contact
            4. To view all contact
            5. To find a contact using first name
            6. To delete a contact
            7. To delete logged-in user
            """);
    }

}

