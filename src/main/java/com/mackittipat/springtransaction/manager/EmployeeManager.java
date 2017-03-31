package com.mackittipat.springtransaction.manager;

import com.mackittipat.springtransaction.dao.EmployeeDAO;
import com.mackittipat.springtransaction.model.EmployeeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeManager {

    private final Logger log = LoggerFactory.getLogger(EmployeeManager.class);

    @Autowired
    private EmployeeDAO employeeDAO;

    @Transactional
    public void insertMultiple(List<EmployeeModel> employeeModelList) {
        for(int i=0; i<employeeModelList.size(); i++) {

            log.debug("Inserting employee {}", i);
            employeeDAO.insert(employeeModelList.get(i));

        }
    }
}
