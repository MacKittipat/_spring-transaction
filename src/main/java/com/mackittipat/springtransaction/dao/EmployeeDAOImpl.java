package com.mackittipat.springtransaction.dao;

import com.mackittipat.springtransaction.model.EmployeeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean insert(EmployeeModel employeeModel) {
        String sql = "INSERT INTO employee(first_name, last_name, age) VALUES(?,?,?)";
        KeyHolder holder = new GeneratedKeyHolder();
        int rowInsert = jdbcTemplate.update(connection -> {
            int count = 1;
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(count++, employeeModel.getFirstName());
            ps.setString(count++, employeeModel.getLastName());
            ps.setInt(count, employeeModel.getAge());
            return ps;
        }, holder);
        employeeModel.setId(holder.getKey().intValue());
        return rowInsert == 1;
    }

    @Override
    public List<EmployeeModel> findAll() {
        return jdbcTemplate.query("SELECT * FROM employee", new EmployeeRowMapper());
    }

    @Override
    public EmployeeModel findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM employee WHERE id=?",
                new Object[] {id}, new EmployeeRowMapper());
    }

    class EmployeeRowMapper implements RowMapper<EmployeeModel> {

        @Override
        public EmployeeModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            EmployeeModel employeeModel = new EmployeeModel();
            employeeModel.setId(rs.getInt("id"));
            employeeModel.setFirstName(rs.getString("first_name"));
            employeeModel.setLastName(rs.getString("last_name"));
            employeeModel.setAge(rs.getInt("age"));
            return employeeModel;
        }
    }
}
