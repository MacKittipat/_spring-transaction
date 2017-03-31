package com.mackittipat.springtransaction.dao;

import com.mackittipat.springtransaction.model.EmployeeModel;

import java.util.List;

public interface EmployeeDAO {

    boolean insert(EmployeeModel employeeModel);

    List<EmployeeModel> findAll();

    EmployeeModel findById(int id);
}
