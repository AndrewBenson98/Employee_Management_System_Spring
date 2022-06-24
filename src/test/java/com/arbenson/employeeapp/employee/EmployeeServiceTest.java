package com.arbenson.employeeapp.employee;

import com.arbenson.employeeapp.employee.util.EmployeeAlreadyExistsException;
import com.arbenson.employeeapp.employee.util.EmployeeNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class EmployeeServiceTest {


    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;


    private Employee employee;
    private List<Employee> employeeList;


    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        employeeList=new ArrayList<>();
        employee = new Employee(1, "Andrew", "Benson", "a.benson@company.com");

        Employee emp1 = new Employee(2, "John", "Doe", "j.doe@company.com");
        Employee emp2 = new Employee(3, "Charlie", "Marshal", "c.marshal@company.com");
        employeeList.add(emp1);
        employeeList.add(emp2);

    }

    @AfterEach
    public void tearDown(){
        employee = null;
        employeeList=null;
    }


    //get all employees
    @Test
    public void givenGetAllEmployeesThenShouldReturnListOfEmployees(){
        when(employeeRepository.findAll()).thenReturn(employeeList);

        assertEquals(employeeList,employeeService.getEmployees());

        verify(employeeRepository,times(1)).findAll();

    }

    //create new employee
    @Test
    public void givenCreateNewEmployeeThenShouldReturnNewEmployee(){
        when(employeeRepository.findById(any())).thenReturn(Optional.empty());
        when(employeeRepository.save(any())).thenReturn(employee);

        assertEquals(employee, employeeService.saveEmployee(employee));

        verify(employeeRepository,times(1)).findById(any());
        verify(employeeRepository,times(1)).save(any());

    }

    //create new employee that already exists
    @Test
    public void givenCreateEmployeeThatExistsThenShouldThrowEmployeeAlreadyExistsException(){
        when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));

        assertThrows(EmployeeAlreadyExistsException.class,()->employeeService.saveEmployee(employee));

        verify(employeeRepository,times(1)).findById(any());
        verify(employeeRepository,times(0)).save(any());

    }

    //get single employee
    @Test
    public void givenGetEmployeeThenShouldReturnEmployee(){
        when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));

        assertEquals(employee,employeeService.getEmployee(employee.getEmpId()));

        verify(employeeRepository,times(1)).findById(any());
    }


    //get single employee not found
    @Test
    public void givenGetNonExistentEmployeeThenShouldThrowEmployeeNotFoundException(){
        when(employeeRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, ()-> employeeService.getEmployee(employee.getEmpId()));

        verify(employeeRepository,times(1)).findById(any());

    }


    //update employee
    @Test
    public void givenEmployeeToUpdateThenShouldReturnUpdatedEmployee(){

        when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any())).thenReturn(employee);

        assertEquals(employee,employeeService.updateEmployee(employee,1L));

        verify(employeeRepository,times(1)).findById(any());
        verify(employeeRepository,times(1)).save(any());
    }

    //update employee not found
    @Test
    public void givenNonExistentEmployeeToUpdateThenShouldThrowEmployeeNotFoundException(){
        when(employeeRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, ()-> employeeService.updateEmployee(employee,employee.getEmpId()));

        verify(employeeRepository,times(1)).findById(any());
    }

    //update employee but IDs are not the same


    //delete employee
    public void givenEmployeeToDeleteThenShouldDeleteEmployee(){

       when(employeeRepository.findById(any())).thenReturn(Optional.of(employee));

        employeeRepository.deleteById(employee.getEmpId());

        verify(employeeRepository,times(1)).deleteById(any());


    }

    //delete employee not found
    @Test
    public void givenNonExistentEmployeeToDeleteThenShouldThrowEmployeeNotFoundException(){

        when(employeeRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, ()-> employeeService.deleteEmployee(employee.getEmpId()));

        verify(employeeRepository,times(1)).findById(any());
    }




}
