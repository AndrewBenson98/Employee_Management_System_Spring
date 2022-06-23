package com.arbenson.employeeapp.employee;

import com.arbenson.employeeapp.employee.util.EmployeeAlreadyExistsException;
import com.arbenson.employeeapp.employee.util.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl  implements EmployeeService{

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository){
        this.employeeRepository=employeeRepository;
    }



    @Override
    public Employee saveEmployee(Employee employee) throws EmployeeAlreadyExistsException {

        Optional<Employee> optEmployee = employeeRepository.findById(employee.getEmpId());

        if(optEmployee.isEmpty()){
            return employeeRepository.save(employee);
        }else{
            throw new EmployeeAlreadyExistsException("Employee already exists");
        }

    }

    @Override
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployee(Long employeeId) throws EmployeeNotFoundException {

        Optional<Employee> optEmployee = employeeRepository.findById(employeeId);

        if(optEmployee.isPresent()){
            return optEmployee.get();
        }else{
            throw new EmployeeNotFoundException("Employee not found");
        }
    }

    @Override
    public Employee updateEmployee(Employee employee, Long employeeId) throws EmployeeNotFoundException {

        Optional<Employee> optEmployee = employeeRepository.findById(employeeId);

        if(optEmployee.isPresent() && employee.getEmpId() == employeeId){
            return employeeRepository.save(employee);
        }else{
            throw new EmployeeNotFoundException("Employee not found");
        }

    }

    @Override
    public void deleteEmployee(Long employeeId) throws EmployeeNotFoundException {

        Optional<Employee> optEmployee = employeeRepository.findById(employeeId);

        if(optEmployee.isPresent()){
            employeeRepository.deleteById(employeeId);
        }else{
            throw new EmployeeNotFoundException("Employee not found");
        }

    }
}
