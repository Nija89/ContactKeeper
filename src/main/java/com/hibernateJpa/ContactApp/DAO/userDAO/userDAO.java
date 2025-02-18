package com.hibernateJpa.ContactApp.DAO.userDAO;

import com.hibernateJpa.ContactApp.Entity.user;

public interface userDAO {
    void createUser(user newUser);
    boolean login(String username, String password);
    user findUserByUsername(String fname);
    void deleteLoggedinUser(user thisUser);
}
