package com.arbenson.employeeapp.employee;

import com.arbenson.employeeapp.employee.util.EmployeeAlreadyExistsException;
import com.arbenson.employeeapp.employee.util.EmployeeNotFoundException;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



//http://localhost:8085/api/v1/employees
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService=employeeService;
    }

    @PostMapping("/employees")
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee){

        try{

            Employee emp = employeeService.saveEmployee(employee);

            return new ResponseEntity<Employee>(emp, HttpStatus.CREATED);

        }catch(EmployeeAlreadyExistsException e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @GetMapping("/employees")
    public ResponseEntity<?> getEmployees(){
        List<Employee> empList = employeeService.getEmployees();

        return new ResponseEntity<List<Employee>>(empList, HttpStatus.OK);
    }

    @GetMapping("/employees/{empId}")
    public ResponseEntity<?> getEmployee(@PathVariable("empId") String empId){

        try{

            Employee emp = employeeService.getEmployee(Long.parseLong(empId));
            return new ResponseEntity<Employee>(emp, HttpStatus.OK);


        }catch(EmployeeNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }


    @PutMapping("/employees/{empId}")
    public ResponseEntity<?> updateEmployee(@RequestBody Employee employee, @PathVariable("empId") String empId){

        try{
            Employee emp = employeeService.updateEmployee(employee,Long.parseLong(empId));
            return new ResponseEntity<Employee>(emp, HttpStatus.OK);

        }catch(EmployeeNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }


    @DeleteMapping("/employees/{empId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("empId") String empId){
        try{
            employeeService.deleteEmployee(Long.parseLong(empId));
            return new ResponseEntity<String>("Employee deleted successfully", HttpStatus.OK);

        }catch(EmployeeNotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
        }


    }























}
