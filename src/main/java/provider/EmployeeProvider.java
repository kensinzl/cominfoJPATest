package provider;

import entity.Employee;
import entity.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class EmployeeProvider {

    EntityManager em = JPAUtil.getEntityManager();
    EntityTransaction transaction = null;

    public void saveEmployee(Employee employee) {
        transaction = em.getTransaction ();
        transaction.begin ();
        em.persist (employee);
        transaction.commit ();
    }

    public Employee saveEmployeeReturnAttachedEmployee(Employee employee) {
        transaction = em.getTransaction ();
        transaction.begin ();
        em.persist (employee);
        transaction.commit ();
        return employee;
    }

    public void deleteEmployee(Long id) {
        transaction = em.getTransaction ();
        transaction.begin ();
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
        TypedQuery<Employee> query = em.createQuery ("select e from Employee e left join fetch e.emails ee where e.id=:id", Employee.class);
        query.setParameter ("id", id);
        return query.getSingleResult ();
    }

    public void detachEmployeeById(Long id) {
        Employee employee = em.find (Employee.class, id);
        em.detach (employee);
    }

    public Employee detachEmployeeByIdThenReturnDetachedEmployee(Long id) {
        Employee employee = em.find (Employee.class, id);
        em.detach (employee);
        return employee;
    }
}
