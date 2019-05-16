package com.javadeveloperzone.controller;

import com.javadeveloperzone.model.Employee;
import com.javadeveloperzone.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
/**
 * Created by JavaDeveloperZone on 19-07-2017.
 */
@RestController     // for rest response
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // to add new employee
    @RequestMapping(value = "save",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)     // or user @GetMapping
    public Employee save(@RequestBody Employee employee){
    	System.out.println(employee.getEmployeeEmail());
    	System.out.println(employee.getEmployeeId());
    	System.out.println(employee.getEmployeeName());
    	System.out.println(employee.getEmployeeRole());
        return employeeService.save(employee);
    }

    // list of all employee
    @RequestMapping(value = "listEmployee",method = RequestMethod.GET)   // or use @GetMapping
    public java.util.List<Employee> listEmployee() {
        return employeeService.findAll();
    }

    // delete specific employee using employee id
    @RequestMapping(value = "delete", method = RequestMethod.DELETE)        // or use @DeleteMapping
    public void delete(@RequestParam("id")long id){
         employeeService.delete(id);
    }

    // search employee start with name
    @RequestMapping(value = "startWithName/{name}")
    public java.util.List<Employee> findByName(@PathVariable("name")String name){
        return employeeService.findEmployeeByEmployeeNameStartingWith(name);
    }
    
    @RequestMapping(value="greaterThan/{id}")
    public java.util.List<Employee> findByGreaterthanId(@PathVariable("id")Integer id){
    	return employeeService.findByGreaterthanId(id);
    }

    // search employee by role
    @RequestMapping(value = "findByRole/{role}")
    public java.util.List<Employee> findByRole(@PathVariable("role")String role){
        return employeeService.findEmployeeByEmployeeRole(role);
    }

    @RequestMapping(value = "/query")
    public java.util.List<Employee> test(){
        //return employeeService.findByCriteria("Sowjanya");
        return employeeService.findByCriteria("Sowws","Technology Analyst");
        // return  employeeService.findByLikeCriteria("info");
         //return employeeService.findByLikeAndBetweenCriteria(null,0,0);
       // return  employeeService.findByPagingCriteria("Jone", new PageRequest(0,10));
    }
}
