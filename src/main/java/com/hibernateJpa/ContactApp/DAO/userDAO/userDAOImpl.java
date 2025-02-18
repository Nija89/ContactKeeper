package com.hibernateJpa.ContactApp.DAO.userDAO;

import com.hibernateJpa.ContactApp.Entity.user;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

@Repository
public class userDAOImpl implements com.hibernateJpa.ContactApp.DAO.userDAO.userDAO {

    private final EntityManager entityManager;

    @Autowired
    public userDAOImpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }

    @Override
    @Transactional
    public void createUser(user newUser){
        String hashedPassword = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
        newUser.setPassword(hashedPassword);
        entityManager.persist(newUser);
    }

    @Override
    public boolean login(String username, String password) {
        try {
            TypedQuery<user> theQuery = entityManager.createQuery("SELECT u FROM user u WHERE u.userName = :x", user.class);
            theQuery.setParameter("x", username);
            user result = theQuery.getSingleResult();
            return BCrypt.checkpw(password, result.getPassword());
        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public user findUserByUsername(String userName) {
        try{
            TypedQuery<user> theQuery = entityManager.createQuery("SELECT u FROM user u WHERE u.userName=:x", user.class);
            theQuery.setParameter("x",userName);
            return theQuery.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }

    @Override
    @Transactional
    public void deleteLoggedinUser(user thisUser){
        user userToDelete = entityManager.find(user.class, thisUser.getId());
        if (userToDelete != null) {
            entityManager.remove(userToDelete);
        }
    }
}
