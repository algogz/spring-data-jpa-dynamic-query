package hello;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> retrieveEmployees(Employee filter) {

        List<Employee> employees = employeeRepository.findAll(new Specification<Employee>() {

            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery< ?> query, CriteriaBuilder cb) {

                List<Predicate> predicates = new ArrayList<>();

                // If designation is specified in filter, add equal where clause
                if (filter.getDesignation() != null) {
                    predicates.add(cb.equal(root.get("designation"), filter.getDesignation()));
                }

                // If firstName is specified in filter, add contains (lile)
                // filter to where clause with ignore case
                if (filter.getFirstName() != null) {
                    predicates.add(cb.like(cb.lower(root.get("firstName")),
                            "%" + filter.getFirstName().toLowerCase() + "%"));
                }

                // If lastName is specified in filter, add contains (lile)
                // filter to where clause with ignore case
                if (filter.getLastName() != null) {
                    predicates.add(cb.like(cb.lower(root.get("lastName")),
                            "%" + filter.getLastName().toLowerCase() + "%"));
                }

                return cb.and(predicates.toArray(new Predicate[0]));
            }
        });

        return employees;
    }
}
