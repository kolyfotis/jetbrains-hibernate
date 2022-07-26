/*
* Run tests that require connection to the database
* */
package com.example.other;

import com.example.entity.User;
import com.example.persistence.UserPersistence;

import javax.persistence.*;

public class Persistence {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = javax.persistence.Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
//        DepartmentPersistence departmentPersistence = new DepartmentPersistence();
//        DepartmentService departmentService = new DepartmentService();
        UserPersistence userPersistence = new UserPersistence();

        try {
            transaction.begin();
//          ------------------ TESTS BELOW --------------------

//            javax.persistence.NoResultException: No entity found for query
//            User user2 = userPersistence.getUserByUserName("NOT_IN_DB");
//            System.out.println("RETURN VALUE FOR NOT FOUND: " + user2);

//            User user2 = userPersistence.getUserByUserName("user2");
//            user2.setPassword("secret");
//            userPersistence.updateUser(user2);
//
//            user2 = userPersistence.getUserByUserName("user2");
//            System.out.println(user2);
//
//            User user1 = userPersistence.getUserByUserName("user1");
//            user1.setPassword("secret");
//            userPersistence.updateUser(user1);
//
//            user1 = userPersistence.getUserByUserName("user1");
//            System.out.println(user1);

//            Department department = new Department();
//            department.setName("Data Analytics Department");
//            entityManager.persist(department);

//            Employee fotis = new Employee();
//            fotis.setFirstName("Fotis");
//            fotis.setLastName("Kolytoumpas");
//            fotis.setDepartmentId(1);
//            entityManager.persist(fotis);

//            TypedQuery<Employee> empByDeptQuery = entityManager.createNamedQuery("Employee.byDept", Employee.class);
//            empByDeptQuery.setParameter(1, "Software Department");
//            for (Employee employee : empByDeptQuery.getResultList()) {
//                System.out.println(employee);
//            }

//            Query countEmpByDept = entityManager.createNativeQuery("SELECT COUNT(*) FROM Employee INNER JOIN Department D on Employee.department_id = D.id WHERE D.name=:deptName");
//            countEmpByDept.setParameter("deptName", "Data Analytics Department");
//            System.out.println(String.format("There are %d employees in the department of Data Analytics.", countEmpByDept.getSingleResult()));

            // TEST: DepartmentService::getDepartments
//            List<Department> departments = departmentService.getDepartments();
//            System.out.println("LOG: MAIN: DepartmentService::getDepartments:");
//            System.out.println(departments);

//            Query selectDepartment = entityManager.createNativeQuery("select * from Department where id =:deptId", Department.class);
//            selectDepartment.setParameter("deptId", 2);
//            Department department = (Department) selectDepartment.getSingleResult();
//            System.out.println("LOG: MAIN: Select Department:");
//            System.out.println(department);

//            Department department = new Department();
//            department.setName("Frontend Development");
//            entityManager.createNativeQuery("insert into mydb.department (department.name) values (:deptName)")
//                    .setParameter("deptName", department.getName())
//                    .executeUpdate();

//            entityManager.createNativeQuery("delete from Department where id =:deptId", Department.class)
//                    .setParameter("deptId", 7)
//                    .executeUpdate();

//            Department department = new Department();
//            department.setId(9);
//            department.setName("User Experience");
//
//            entityManager.createNativeQuery("update department d set name =:deptName where d.id =:deptId", Department.class)
//                    .setParameter("deptName", department.getName())
//                    .setParameter("deptId", department.getId())
//                    .executeUpdate();

//          ------------------ TESTS ABOVE --------------------
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
