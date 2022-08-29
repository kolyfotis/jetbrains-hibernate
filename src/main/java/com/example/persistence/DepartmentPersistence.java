/*
* Implements basic CRUD functionality for Department entity, making transactions to the database.
* */
package com.example.persistence;

import com.example.entity.Department;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentPersistence {

    /*
    * Returns a list of all Departments in the Department table including
    * all the table's fields.
    * */
    public List<Department> getDepartments() {
        List<Department> departments = new ArrayList<>();

        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();

            try {
                transaction.begin();

                Query selectDepartments = entityManager.createNativeQuery("select * from Department", Department.class);
                departments = (List<Department>) selectDepartments.getResultList();
                // LOGS
                System.out.println("LOG: DepartmentPersistence::getDepartments(): ");
                System.out.println(departments);

                transaction.commit();
            } finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                entityManager.close();
                entityManagerFactory.close();
            }
        } catch (Exception e) {
            throw e;
        }
        return departments;
    }

    /*
     * Receives an integer and returns a Department if the id is valid.
     * Otherwise, throws an exception.
     * */
    public Department getDepartmentById(int id) throws Exception {
        Department department = new Department();
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();

            try {
                transaction.begin();

                try {
                    Query selectDepartment = entityManager.createNativeQuery("select * from Department where id =:deptId", Department.class);
                    selectDepartment.setParameter("deptId", id);
                    department = (Department) selectDepartment.getSingleResult();
                } catch (Exception e) {
                    throw new Exception("Invalid id.");
                }
                // LOGS
                System.out.println("LOG: DepartmentPersistence::getDepartmentById(): ");
                System.out.println(department);

                transaction.commit();
            } catch (Exception e) {
                throw e;
            } finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                entityManager.close();
                entityManagerFactory.close();
            }
        } catch (Exception e) {
            throw e;
        }
        return department;
    }

    /*
     * Receives a Department, stores it in the database and returns this Department.
     * */
    public Department addDepartment(Department department) {
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();

                entityManager.createNativeQuery("insert into mydb.department (department.name)" +
                                "values (:deptName)", Department.class)
                        .setParameter("deptName", department.getName())
                        .executeUpdate();
                // retrieve the department from the database to get its ID
                // or find a way to avoid one extra transaction
                department = (Department) entityManager.createNativeQuery("select * from Department where name =:deptName", Department.class)
                        .setParameter("deptName", department.getName())
                        .getSingleResult();

                // LOGS
                System.out.println("LOG: DepartmentPersistence::addDepartment(): ");
                System.out.println(department);

                transaction.commit();
            } catch (Exception e) {
                throw e;
            } finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                entityManager.close();
                entityManagerFactory.close();
            }
        } catch (Exception e) {
            throw e;
        }
        return department;
    }

    /*
     * Receives a Department, updates and returns it if the id is valid.
     * Otherwise, throws an exception.
     * */
    public Department updateDepartment(Department department) throws Exception {
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();

                int lines = entityManager.createNativeQuery("update department d set name =:deptName where d.id =:deptId", Department.class)
                        .setParameter("deptName", department.getName())
                        .setParameter("deptId", department.getId())
                        .executeUpdate();

                if (lines < 1) {
                    throw new Exception("Invalid id.");
                }

                // LOGS
                System.out.println("LOG: DepartmentPersistence::updateDepartment(): ");
                System.out.println(department);
//                System.out.println("Lines affected: " + lines);

                transaction.commit();
            } catch (Exception e) {
                throw e;
            }
            finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                entityManager.close();
                entityManagerFactory.close();
            }
        } catch (Exception e) {
            throw e;
        }
        return department;
    }

    /*
     * Receives an integer, deletes the Department, and returns a Message if the id is valid.
     * Otherwise, throws an exception.
     * */
    public void removeDepartmentById(int id) throws Exception {
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();

                int lines = entityManager.createNativeQuery("delete from Department where id =:deptId", Department.class)
                        .setParameter("deptId", id)
                        .executeUpdate();

                if (lines < 1) {
                    throw new Exception("Invalid id.");
                }

                // LOGS
                System.out.println("LOG: DepartmentPersistence::removeDepartmentById(): ");
                System.out.println(String.format("Department with id: %d was removed.", id));

                transaction.commit();
            } catch (Exception e) {
                throw e;
            }
            finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                entityManager.close();
                entityManagerFactory.close();
            }
        } catch (Exception e) {
            throw e;
        }
    }
/*
    public void removeDepartmentByName(String name) throws Exception {
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();

                int lines = entityManager.createNativeQuery("delete from Department where name =:deptName", Department.class)
                        .setParameter("deptName", name)
                        .executeUpdate();

                if (lines < 1) {
                    throw new Exception("Invalid name.");
                }

                // LOGS
                System.out.println("LOG: DepartmentPersistence::removeDepartmentByName(): ");
                System.out.println(String.format("Department |%s| was removed.", name));

                transaction.commit();
            } catch (Exception e) {
                throw e;
            }
            finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                entityManager.close();
                entityManagerFactory.close();
            }
        } catch (Exception e) {
            throw e;
        }
    }
*/
    public void removeDepartmentsInRange(int fromId, int toId) throws Exception {
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();

                int lines = entityManager.createNativeQuery("delete from Department where id between :fromId and :toId", Department.class)
                        .setParameter("fromId", fromId)
                        .setParameter("toId", toId)
                        .executeUpdate();

                if (lines < 1) {
                    throw new Exception("Invalid id.");
                }

                // LOGS
                System.out.println("LOG: DepartmentPersistence::removeDepartmentById(): ");
                System.out.printf("Departments from id %d to id %d were removed.", fromId, toId);

                transaction.commit();
            } catch (Exception e) {
                throw e;
            }
            finally {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                entityManager.close();
                entityManagerFactory.close();
            }
        } catch (Exception e) {
            throw e;
        }
    }
}