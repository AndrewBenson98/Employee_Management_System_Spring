package com.arbenson.employeeapp.employee.util;

public class EmployeeAlreadyExistsException extends RuntimeException{
    public EmployeeAlreadyExistsException(String msg){
        super(msg);
    }

}
