package hello;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String designation;
    private Date joiningDate;

    public Employee(String firstName, String lastName, String designation, Date joiningDate){
        this.firstName = firstName;
        this.lastName = lastName;
        this.designation = designation;
        this.joiningDate = joiningDate;
    }
}
