package com.example.persistence;

import com.example.entity.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class UserPersistence {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();

        try {
            transaction.begin();

            Query selectUsers = entityManager.createNativeQuery("select * from User", User.class);
            users = (List<User>) selectUsers.getResultList();
            // LOGS
            System.out.println("LOG: UserPersistence::getUsers(): ");
            System.out.println(users);

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

    public User getUserById(int id) {
        User user = new User();

        try {
            transaction.begin();

            Query selectUser = entityManager.createNativeQuery("select * from User where id =:userId", User.class);
            selectUser.setParameter("userId", id);
            user = (User) selectUser.getSingleResult();
            // LOGS
            System.out.println("LOG: UserPersistence::getUserById(): ");
            System.out.println(user);

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

    public User getUserByUserName(String userName) {
        User user = new User();

        try {
            transaction.begin();

            Query selectUser = entityManager.createNativeQuery("select * from User where username =:userName",
                    User.class);
            selectUser.setParameter("userName", userName);
            user = (User) selectUser.getSingleResult();
            // LOGS
            System.out.println("LOG: UserPersistence::getUserByUserName(): ");
            System.out.println(user);

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

    public User addUser(User user) {
        try {
            transaction.begin();

            entityManager.createNativeQuery("insert into mydb.user (user.username, user.password)" +
                            "values (:userName, :password)", User.class)
                    .setParameter("userName", user.getUsername())
                    .setParameter("password", user.getPassword())
                    .executeUpdate();
            // retrieve the user from the database to get its ID
            // or find a way to avoid one extra transaction
            user = (User) entityManager.createNativeQuery("select * from User where username =:userName", User.class)
                    .setParameter("userName", user.getUsername())
                    .getSingleResult();

            // LOGS
            System.out.println("LOG: UserPersistence::addUser(): ");
            System.out.println(user);

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

    public User updateUser(User user) {
        try {
            transaction.begin();

            entityManager.createNativeQuery("update user d set username =:userName, password =:password where d.id =:userId", User.class)
                    .setParameter("userId", user.getId())
                    .setParameter("userName", user.getUsername())
                    .setParameter("password", user.getPassword())
                    .executeUpdate();

            // LOGS
            System.out.println("LOG: UserPersistence::updateUser(): ");
            System.out.println(user);

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

    public void removeUser(int id) {
        try {
            transaction.begin();

            entityManager.createNativeQuery("delete from User where id =:userId", User.class)
                    .setParameter("userId", id)
                    .executeUpdate();

            // LOGS
            System.out.println("LOG: UserPersistence::removeUser(): ");
            System.out.println(String.format("User with id: %d was removed.", id));

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