/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeRepositoryTests {
    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private EmployeeService service;


    @Autowired
    private EntityManager entityManager;

//    @Autowired
//    private JpaEntityInformation entityInformation;


    @Test
    public void test() {
        repository.save(new Employee("Jack", "Huang", "MANAGER", new Date()));
        repository.save(new Employee("Jack", "Joe", "worker", new Date()));
        repository.save(new Employee("JJ", "Joe", "MANAGER", new Date()));

        Employee managersCriteria = new Employee();
        managersCriteria.setDesignation("MANAGER");

        //Fetches all the employees whose designation is 'MANAGER'
        List<Employee> list1 = service.retrieveEmployees(managersCriteria );
        System.out.println(list1);

        Employee employeeWhoHasLastNameJoe = new Employee();
        employeeWhoHasLastNameJoe.setLastName("jo");

        //Fetches all the employees whose has joe in there last name
        List<Employee> list2 = service.retrieveEmployees(employeeWhoHasLastNameJoe );
        System.out.println(list2);
    }

    @Test
    public void testQuery() {
        repository.save(new Employee("Jack", "Huang", "MANAGER", new Date()));
        repository.save(new Employee("Jack", "Joe", "worker", new Date()));
        repository.save(new Employee("JJ", "Joe", "MANAGER", new Date()));

//        String jpql = "FROM Customer WHERE lastName='Bauer'";
//        String jpql = "FROM Customer WHERE lastName=:ln";
        String jpql = "FROM Customer WHERE lastName=:lastName and age>=50";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("lastName", "Bauer");
        List<Customer> list = query.getResultList();
        list.forEach(System.out::println);
    }

    @Test
    public void testOrdering() {
        String jpql = "FROM Customer where age>=40 order by age desc";
        Query query = entityManager.createQuery(jpql);
        List<Customer> list = query.getResultList();
        list.forEach(System.out::println);
    }
}