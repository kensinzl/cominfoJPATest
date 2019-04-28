package provider;

import entity.Employee;
import entity.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class EmployeeProvider {

    EntityManager em = JPAUtil.getEntityManager();
    EntityTransaction transaction = null;

    public Employee saveEmployee(Employee employee) {
        transaction = em.getTransaction ();
        transaction.begin ();
        em.persist (employee);
        transaction.commit ();
        return employee;
    }

    public void deleteEmployee(Long id) {
        transaction = em.getTransaction ();
        transaction.begin ();
//        https://www.codejava.net/frameworks/hibernate/hibernate-basics-3-ways-to-delete-an-entity-from-the-datastore
//        https://thoughts-on-java.org/avoid-cascadetype-delete-many-assocations/
//        Query query1 = em.createQuery("delete Email e where e.id > :id");
//        query1.setParameter("id", 5L);
//        query1.executeUpdate();
//
//        Query query2 = em.createQuery("delete Employee e where e.id=:id");
//        query2.setParameter("id", id);
//        query2.executeUpdate();

        Employee employee = em.find (Employee.class, 5L);
        em.remove (employee);

        transaction.commit();
    }

    public void updateEmployee(Employee newEmployee) {
        transaction = em.getTransaction ();
        transaction.begin ();
        em.merge (newEmployee);
        transaction.commit();
    }

    public Employee findEmployeeById(Long id) {
        return em.find (Employee.class, id);
    }

    public Employee findEmployeeByIdWithJPQL(Long id) {
        TypedQuery<Employee> query = em.createQuery ("select e from Employee e where e.id=:id", Employee.class);
        query.setParameter ("id", id);
        return query.getSingleResult ();
    }

    public void detachEmployeeById(Long id) {
        Employee employee = em.find (Employee.class, id);
        em.detach (employee);
    }
}
