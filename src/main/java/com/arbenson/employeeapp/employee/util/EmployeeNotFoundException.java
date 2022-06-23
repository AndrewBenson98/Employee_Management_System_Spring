package com.arbenson.employeeapp.employee.util;

public class EmployeeNotFoundException extends RuntimeException{

    public EmployeeNotFoundException(String msg){
        super(msg);
    }

}
