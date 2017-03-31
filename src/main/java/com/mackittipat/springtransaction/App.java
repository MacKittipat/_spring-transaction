package com.mackittipat.springtransaction;

import com.mackittipat.springtransaction.dao.EmployeeDAO;
import com.mackittipat.springtransaction.manager.EmployeeManager;
import com.mackittipat.springtransaction.model.EmployeeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class App implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(App.class);

    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EmployeeManager employeeManager;

    @Autowired
    private EmployeeDAO employeeDAO;

    @Override
    public void run(String... strings) throws Exception {
        // Create Table
        log.debug(">>> Creating Table");
        jdbcTemplate.execute("CREATE TABLE employee (\n" +
                "            id INT IDENTITY PRIMARY KEY,\n" +
                "            first_name VARCHAR(10) NOT NULL,\n" +
                "            last_name VARCHAR(10) NOT NULL,\n" +
                "            age INT NOT NULL\n" +
                "        )");

        List<EmployeeModel> employeeModelList = new ArrayList<>();

        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setFirstName("Mac");
        employeeModel.setLastName("Kittipat");
        employeeModel.setAge(27);
        employeeModelList.add(employeeModel);

        EmployeeModel employeeModel2 = new EmployeeModel();
        employeeModel2.setFirstName("Mac22222222222");
        employeeModel2.setLastName("Kittipat2");
        employeeModel2.setAge(28);
        employeeModelList.add(employeeModel2);

        EmployeeModel employeeModel3 = new EmployeeModel();
        employeeModel3.setFirstName("Mac3");
        employeeModel3.setLastName("Kittipat3");
        employeeModel3.setAge(29);
        employeeModelList.add(employeeModel3);

        try {
            employeeManager.insertMultiple(employeeModelList);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        log.debug(">>> Searching all employee");
        employeeModelList = employeeDAO.findAll();
        employeeModelList.forEach(e -> {
            log.debug(">>> {}", e.toString());
        });
    }
}
