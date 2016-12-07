package com.project.dao;

import com.project.Model.Employment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

/**
 * This class holds database queries and actions for the employment class/table.
 *
 * @author Brian Jorgenson
 * @author Edgard Solorzano
 * @author Adam Waldron
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
                + "skills, startDate, endDate, salary, currentJob, "
                + "internship, willBehired) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] parameters = {
                emp.getStudentId(), emp.getCompanyName(), emp.getPosition(),
                emp.skillsToString(), emp.getStartDate(), emp.getEndDate(),
                emp.getSalary(), emp.getIsCurrentJob(), emp.getInternship(),
                emp.getWillBeHired()};
        int[] types = {
                Types.INTEGER, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.DATE, Types.DATE,
                Types.DOUBLE, Types.BIT, Types.BIT,
                Types.BIT};
        jdbcTemplate.update(sql, parameters, types);
    }

    /**
     * Updates a students employment.
     *
     * @param emp employment to update
     */
    public void updateEmployment(Employment emp) {
        String sql = "update Employment set company = ?, `position` = ?, skills = ?, "
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
        String sql = "select * from Employment where employmentId = " + id;
        return (Employment) jdbcTemplate.queryForObject(sql, new EmploymentRowMapper());
    }


    /**
     * Gets all companies.
     *
     * @return list of all companies
     */
    public List<String> getCompanies() {
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
     * @param id student to get employments from
     * @return list of employments
     */
    public ArrayList<Employment> getEmployments(int id) {
        String sql = "select * from Employment where studentId = " + id;
        ArrayList<Employment> employments = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            Employment employment = new Employment();
            employment.setCompanyName((String) row.get("company"));
            employment.setEmploymentId((Integer) row.get("employmentId"));
            employment.setPosition((String) row.get("position"));
            employment.setStartDate((Date) row.get("startDate"));
            employment.setEndDate((Date) row.get("endDate"));
            BigDecimal salary = (BigDecimal) row.get("salary");
            employment.setSalary(salary.doubleValue());
            employment.setSkills(new ArrayList<String>(Arrays.asList(((String) row.get("skills")).split(", "))));
            employments.add(employment);
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
        employment.setCompanyName(rs.getString("company"));
        employment.setPosition(rs.getString("position"));
        employment.setSkills(new ArrayList<String>(Arrays.asList((
                (String) rs.getString("skills")).split(", "))));
        employment.setStartDate(rs.getDate("startDate"));
        employment.setEndDate(rs.getDate("endDate"));
        return employment;
    }
}