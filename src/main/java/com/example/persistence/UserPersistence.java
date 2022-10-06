/*
 * Implements basic CRUD functionality for User entity, making transactions to the database.
 * */
package com.example.persistence;

import com.example.entity.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class UserPersistence {

    private final String PERSISTENCE_UNIT_NAME = "hibernate";
//    private final String PERSISTENCE_UNIT_NAME = "glassfish";

    /*
     * Returns a list of all Users in the User table including
     * all the table's fields.
     * */
    public List<User> getUsers() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        List<User> users = new ArrayList<>();

        try {
            transaction.begin();

            Query selectUsers = entityManager.createNativeQuery("select * from User", User.class);
            users = (List<User>) selectUsers.getResultList();
            // LOGS
//            System.out.println("LOG: UserPersistence::getUsers(): ");
//            System.out.println(users);

            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return users;
    }

    /*
     * Receives an integer and returns a User if the id is valid.
     * Otherwise, throws an exception.
     * */
    public User getUserById(int id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        User user = new User();

        try {
            transaction.begin();

            Query selectUser = entityManager.createNativeQuery("select * from User where id =:userId", User.class);
            selectUser.setParameter("userId", id);
            user = (User) selectUser.getSingleResult();
            // LOGS
//            System.out.println("LOG: UserPersistence::getUserById(): ");
//            System.out.println(user);

            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return user;
    }

    /*
     * Receives a String, retrieves a User from the database and returns this User.
     * Does not handle "Entity not found" errors.
     * */
    public User getUserByUserName(String userName) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        User user = null;

        try {
            transaction.begin();

            Query selectUser = entityManager.createNativeQuery("select * from User where username =:userName",
                    User.class);
            selectUser.setParameter("userName", userName);
            user = (User) selectUser.getSingleResult();
            // LOGS
//            System.out.println("LOG: UserPersistence::getUserByUserName(): ");
//            System.out.println(user);

            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return user;
    }

    /*
     * Receives a User object, stores it in the database and returns this User.
     * */
    public User addUser(User user) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            entityManager.createNativeQuery("insert into mydb.user (user.username, user.password, user.role)" +
                            "values (:userName, :password, :role)", User.class)
                    .setParameter("userName", user.getUsername())
                    .setParameter("password", user.getPassword())
                    .setParameter("role", user.getRole())
                    .executeUpdate();
            // retrieve the user from the database to get its ID
            // or find a way to avoid one extra transaction
            user = (User) entityManager.createNativeQuery("select * from User where username =:userName", User.class)
                    .setParameter("userName", user.getUsername())
                    .getSingleResult();

            // LOGS
//            System.out.println("LOG: UserPersistence::addUser(): ");
//            System.out.println(user);

            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return user;
    }

    /*
     * Receives a User object, updates and returns it if the id is valid.
     * Does not handle "Entity not found" errors.
     * */
    public User updateUser(User user) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            entityManager.createNativeQuery("update user d set username =:userName, password =:password where d.id =:userId", User.class)
                    .setParameter("userId", user.getId())
                    .setParameter("userName", user.getUsername())
                    .setParameter("password", user.getPassword())
                    .executeUpdate();

            // LOGS
//            System.out.println("LOG: UserPersistence::updateUser(): ");
//            System.out.println(user);

            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return user;
    }

    /*
     * Receives an integer, deletes the Department, and returns a Message if the id is valid.
     * Does not handle "Entity not found" errors.
     * */
    public void removeUser(int id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            entityManager.createNativeQuery("delete from User where id =:userId", User.class)
                    .setParameter("userId", id)
                    .executeUpdate();

            // LOGS
//            System.out.println("LOG: UserPersistence::removeUser(): ");
//            System.out.println(String.format("User with id: %d was removed.", id));

            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}