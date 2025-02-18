package com.hibernateJpa.ContactApp.DAO.contactDAO;

import com.hibernateJpa.ContactApp.DAO.contactDAO.contactDAO;
import com.hibernateJpa.ContactApp.Entity.contact;
import com.hibernateJpa.ContactApp.Entity.user;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class contactDAOImpl implements contactDAO {
    private final EntityManager entityManager;
    @Autowired
    public contactDAOImpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }
    @Override
    @Transactional
    public void addContact(contact newContact) {
        entityManager.persist(newContact);
    }

    @Override
    public List<contact> viewAllContact(user currentUser) {
        TypedQuery<contact> theQuery = entityManager.createQuery("SELECT u FROM contact u WHERE u.currentUser=:x", contact.class);
        theQuery.setParameter("x", currentUser);
        return theQuery.getResultList();
    }

    @Override
    public contact findContactByFirstName(String fname) {
        try {
            TypedQuery<contact> theQuery = entityManager.createQuery("SELECT u FROM contact u WHERE u.firstName=:x", contact.class);
            theQuery.setParameter("x", fname);
            return theQuery.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }
    @Override
    @Transactional
    public void deleteContactByFirstName(String fName){
        Query theQuery = entityManager.createQuery("DELETE FROM contact u WHERE u.firstName=:x");
        theQuery.setParameter("x", fName);
        theQuery.executeUpdate();
    }
}
