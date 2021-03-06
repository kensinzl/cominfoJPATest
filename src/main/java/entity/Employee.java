package entity;

import javax.persistence.*;
import java.util.Set;

/**
 * Inverse Table(Main)
 */
@Entity
@Table(name = "EMPLOYEE")
public class Employee {

    @Id
    @GeneratedValue
    @Column(name = "employee_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "gender")
    private String gender;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Email> emails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Set<Email> getEmails() {
        return emails;
    }

    public void setEmails(Set<Email> emails) {
        this.emails = emails;
    }

}
