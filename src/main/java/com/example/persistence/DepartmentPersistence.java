package com.example.persistence;

import com.example.entity.Department;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentPersistence {

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

    public Department getDepartmentById(int id) {
        Department department = new Department();
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();

            try {
                transaction.begin();

                Query selectDepartment = entityManager.createNativeQuery("select * from Department where id =:deptId", Department.class);
                selectDepartment.setParameter("deptId", id);
                department = (Department) selectDepartment.getSingleResult();
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

    public void removeDepartment(int id) throws Exception {
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
                System.out.println("LOG: DepartmentPersistence::removeDepartment(): ");
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
}