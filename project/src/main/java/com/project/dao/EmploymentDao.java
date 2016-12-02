package com.project.dao;

import com.project.Model.Employment;
import com.project.Model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * This class holds database queries and actions for the employment class/table.
 *
 * @author Brian Jorgenson
 */
@Repository
public class EmploymentDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * Adds an employment to the DB.
     *
     * @param emp the employment to add
     */
    public void addEmployment(Employment emp) {
        String sql = "insert into Employment(studentId, company, `position`, "
            + "skills, startDate, endDate";
        Object[] parameters = {emp.getStudentId(), emp.getCompanyName(), emp.getPosition(),
                                emp.skillsToString(), emp.getStartDate(), emp.getEndDate()};
        int[] types = {Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                        Types.DATE, Types.DATE};
        jdbcTemplate.update(sql, parameters, types);
    }
    
    /**
     * Updates a students employment.
     *
     * @param emp employment to update
     */
    public void updateEmployment(Employment emp) {
        String sql = "update Employment set companyName = ?, `position` = ?, skills = ? "
                + "startDate = ?, endDate = ? "
                + "where employmentId = ? and studentId = ?";
        Object[] parameters = {emp.getCompanyName(), emp.getPosition(), emp.skillsToString(),
                                emp.getStartDate(), emp.getEndDate(), emp.getEmploymentId(),
                                emp.getStudentId()};
        int[] types = {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.DATE,
                        Types.INTEGER, Types.INTEGER};
        jdbcTemplate.update(sql, parameters, types);
    }
    
    /**
     * Get an employment by its id.
     *
     * @param id id of the employment
     * @return the employment
     */
    public Employment getEmployment(int id) {
        String sql = "select * from Employment where employmentId = ?";
        return (Employment) jdbcTemplate.queryForObject(sql, new Object[]{id}, new EmploymentRowMapper());
    }
    
    
    /**
     * Gets all companies.
     *
     * @return list of all companies
     */
    public List<String> getCompanys() {
        String sql = "select * from Company";
        List<String> companies = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            companies.add((String) row.get("companyName"));
        }
        return companies;
    }
    
    /**
     * Gets all employments from a student.
     *
     * @param student student to get employments from
     * @return list of employments
     */
    public List<Employment> getEmployments(Student student){
        String sql = "select * from Employment where studentId = " + student.getId();
        List<Employment> employments = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            Employment employment = new Employment();
            employment.setCompanyName((String) row.get("company"));
            employment.setEmploymentId((Integer) row.get("employmentId"));
            employment.setPosition((String) row.get("position"));
            employment.setSkills(
                    new ArrayList<String>(Arrays.asList(((String)row.get("skills")).split(", "))));
        }
        return employments;
    }

}

/**
 * Gets data from row of MySQL data and maps it to an Employment.
 */
class EmploymentRowMapper implements RowMapper {
    
    @Override
    public Object mapRow(ResultSet rs, int row) throws SQLException {
        Employment employment = new Employment();
        employment.setStudentId(rs.getInt("studentId"));
        employment.setEmploymentId(rs.getInt("employmentId"));
        employment.setCompanyName(rs.getString("companyName"));
        employment.setPosition(rs.getString("position"));
        employment.setSkills(new ArrayList<String>(Arrays.asList((
                        (String)rs.getString("skills")).split(", "))));
        employment.setStartDate(rs.getDate("startDate"));
        employment.setEndDate(rs.getDate("endDate"));
        return employment;
    }
}