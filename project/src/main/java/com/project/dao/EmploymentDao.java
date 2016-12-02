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
 * Created by Brian on 11/30/2016.
 */
@Repository
public class EmploymentDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void addEmployment(Employment emp) {
        String sql = "insert into Employment(studentId, company, `position`, "
                + "skills, startDate, endDate, salary, currentJob, internship, willBehired";

        // Turns list of skills into string of skills "skill1, skill2, etc"
        Object[] parameters = {emp.getStudentId(), emp.getCompanyName(), emp.getPosition(),
                emp.skillsToString(), new java.sql.Date(emp.getStartDate().getTime()),
                new java.sql.Date(emp.getEndDate().getTime()), emp.getSalary(),
                emp.getIsCurrentJob(), emp.getInternship(), emp.getWillBeHired()};
        int[] types = {Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.DATE, Types.DATE, Types.DOUBLE, Types.BIT, Types.BIT, Types.BIT};
        jdbcTemplate.update(sql, parameters, types);
    }

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


    public List<String> getCompanys() {
        String sql = "select * from Company";
        List<String> companys = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            companys.add((String) row.get("companyName"));
        }
        return companys;
    }

    /**
     * Gets all employments from a student.
     *
     * @param student student to get employments from
     * @return list of employments
     */
    public List<Employment> getEmployments(Student student) {
        String sql = "select * from Employment where studentId = " + student.getId();
        List<Employment> employments = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            Employment employment = new Employment();
            employment.setCompanyName((String) row.get("company"));
            employment.setEmploymentId((Integer) row.get("employmentId"));
            employment.setPosition((String) row.get("position"));
            employment.setSkills(
                    new ArrayList<String>(Arrays.asList(((String) row.get("skills")).split(", "))));
        }
        return employments;
    }

}


class EmploymentRowMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int row) throws SQLException {
        Employment employment = new Employment();
        employment.setStudentId(rs.getInt("studentId"));
        employment.setEmploymentId(rs.getInt("employmentId"));
        employment.setCompanyName(rs.getString("companyName"));
        employment.setPosition(rs.getString("position"));
        employment.setSkills(new ArrayList<String>(Arrays.asList((
                (String) rs.getString("skills")).split(", "))));
        employment.setStartDate(rs.getDate("startDate"));
        employment.setEndDate(rs.getDate("endDate"));
        return employment;
    }
}