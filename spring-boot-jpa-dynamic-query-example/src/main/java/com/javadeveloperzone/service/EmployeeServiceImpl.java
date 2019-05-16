package com.javadeveloperzone.service;

import com.javadeveloperzone.dao.EmployeeDAO;
import com.javadeveloperzone.model.Employee;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 04-04-2018.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
	 Logger x = Logger.getLogger("EmployeeServiceImpl.class");

    @Autowired
    private EmployeeDAO employeeDAO;

    @Override
    public List<Employee> findAll() {
        return employeeDAO.findAll(new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	//System.out.println("Here 1");
            	x.info("Here 1");

                return null;
            }
        });
    }

    public List<Employee> findByCriteria(String employeeName){
        return employeeDAO.findAll(new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            	//System.out.println("Here 2");
            	x.info("Here 2");
                List<Predicate> predicates = new ArrayList<>();
                if(employeeName!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("employeeName"), employeeName)));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }

    public List<Employee> findByPagingCriteria(String employeeName,Pageable pageable){
        Page page = employeeDAO.findAll(new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            //	System.out.println("Here 3");
            	
            	x.info("Here 3");
                List<Predicate> predicates = new ArrayList<>();
                if(employeeName!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("employeeName"), employeeName)));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);

        page.getTotalElements();        // get total elements
        page.getTotalPages();           // get total pages
        return page.getContent();       // get List of Employee
    }

    
    //use this
    public List<Employee> findByCriteria(String employeeName,String employeeRole){
        return employeeDAO.findAll(new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            	//System.out.println("Here 4");
            	x.info("Here 4");
                List<Predicate> predicates = new ArrayList<>();
                if(employeeName!=null) {
                    predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get("employeeName"), "%"+employeeName+"%")));
                }
                if(employeeRole!=null){
                    predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("employeeRole"), employeeRole)));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }

    public List<Employee> findByLikeCriteria(String text){
        return employeeDAO.findAll(new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            	//System.out.println("Here 5 ");
            	x.info("Here 5");
                List<Predicate> predicates = new ArrayList<>();
                if(text!=null) {
                    predicates.add(criteriaBuilder.or(criteriaBuilder.like(root.get("employeeName"), "%" + text + "%"),
                            criteriaBuilder.like(root.get("employeeEmail"), "%" + text + "%")));
                   
                    
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }
    
    //For Join Queires
    
	/*
	 * public static Specification<Employee> getEmployeesByPhoneTypeSpec(PhoneType
	 * phoneType) { return new Specification<Employee>() {
	 * 
	 * @Override public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?>
	 * query, CriteriaBuilder criteriaBuilder) {
	 * 
	 *  ListJoin<Employee, Phone> phoneJoin	  = root.join(Employee_.phones); 
	 *  Predicate equalPredicate =
	 * criteriaBuilder.equal(phoneJoin.get(Phone_.type), phoneType); return
	 * equalPredicate; } }; }
	 */
    @Override
	public List<Employee> findByGreaterthanId(Integer id) {
		
		return employeeDAO.findAll(new Specification<Employee>() {
			
			@Override
			public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if(id>0) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.greaterThan(root.get("employeeId"), 1)));
					
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
				
			}
			
			
			
			
			
			
			
			
		});
	}
    public List<Employee> findByLikeAndBetweenCriteria(String text,int employeeIdStart, int employeeIdEnd){
        return employeeDAO.findAll(new Specification<Employee>() {
        	
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            	//System.out.println("Here 6");
            	x.info("Here 6");
                List<Predicate> predicates = new ArrayList<>();
                if(text!=null) {
                    predicates.add(criteriaBuilder.or(criteriaBuilder.like(root.get("employeeName"), "%" + text + "%"),
                            criteriaBuilder.like(root.get("employeeEmail"), "%" + text + "%")));
                }
                if(employeeIdStart!=0 && employeeIdEnd!=0){
                    predicates.add(criteriaBuilder.between(root.get("employeeId"),employeeIdStart,employeeIdEnd));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }
    

    @Override
    public List<Employee> findEmployeeByEmployeeNameStartingWith(String name) {
        return employeeDAO.findEmployeeByEmployeeNameStartingWith(name);
    }

    @Override
    public List<Employee> findEmployeeByEmployeeRole(String role) {
        return employeeDAO.findEmployeeByEmployeeRole(role);
    }

    @Override
    public void delete(long employeeId) {
        Employee employee = employeeDAO.findOne(employeeId);
        employeeDAO.delete(employee);
    }

    @Override
    public Employee save(Employee employee) {
        return employeeDAO.save(employee);
    }

	
}
