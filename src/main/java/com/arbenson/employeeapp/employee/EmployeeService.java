package com.arbenson.employeeapp.employee;

import com.arbenson.employeeapp.employee.util.EmployeeAlreadyExistsException;
import com.arbenson.employeeapp.employee.util.EmployeeNotFoundException;

import java.util.List;

public interface EmployeeService {

    public  Employee saveEmployee(Employee employee) throws EmployeeAlreadyExistsException;
    public List<Employee> getEmployees();
    public Employee getEmployee(Long employeeId) throws EmployeeNotFoundException;
    public Employee updateEmployee(Employee employee, Long employeeId) throws EmployeeNotFoundException;
    public void deleteEmployee(Long employeeId) throws EmployeeNotFoundException;


}
