package com.project.dao;

import com.project.Model.Degree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class holds database queries and actions for the degree class/table.
 *
 * @author Brian Jorgenson
 * @author Edgard Solorzano
 * @author Adam Waldron
 */
@Repository
public class DegreeDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * Adds a degree that is associated to a student by studentId.
     *
     * @param degree the degree to add
     */
    public void addStudentDegree(Degree degree) {
        String sql = "insert into StudentDegree(studentId,  degree, `program`, "
                + "graduationTerm, graduationYear, gpa) "
                + "values (?, ?, ?, ?, ?, ?)";
        Object[] parameters = {degree.getStudentId(), degree.getDegreeLevel(),
                degree.getProgram(), degree.getGraduationTerm(),
                degree.getGraduationYear(), degree.getGpa()};
        int[] types = {Types.INTEGER, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.INTEGER, Types.DOUBLE};
        jdbcTemplate.update(sql, parameters, types);
    }

    /**
     * Gets the all the degrees of a student by id.
     *
     * @param id id of student
     * @return list of degrees
     */
    public ArrayList<Degree> getStudentDegrees(int id) {
        String sql = "select * from StudentDegree where studentId = " + id;
        ArrayList<Degree> degrees = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            Degree degree = new Degree();
            degree.setStudentId((Integer) row.get("studentId"));
            degree.setDegreeId((Integer) row.get("degreeId"));
            degree.setGpa((Double) row.get("gpa"));
            degree.setGraduationYear((Integer) row.get("graduationYear"));
            degree.setGraduationTerm((String) row.get("graduationTerm"));
            degree.setProgram((String) row.get("program"));
            degree.setDegreeLevel((String) row.get("degree"));
            degrees.add(degree);
        }
        return degrees;
    }

    /**
     * Gets all degrees.
     * For use in combo box.
     *
     * @return list of degrees
     */
    public List<Degree> getDegrees() {
        String sql = "select * from Degree";
        return makeListDegree(sql);
    }

    /**
     * Gets a list of degrees by level.
     *
     * @param level level to get by
     * @return list of degrees
     */
    public List<Degree> getDegreeByLevel(Degree level) {
        String sql = "select * from Degree where degree = " + level.getDegreeLevel();
        return makeListDegree(sql);
    }

    /**
     * Gets a list of degrees by program.
     *
     * @param program program to get by
     * @return list of degrees
     */
    public List<Degree> getDegreeByProgram(Degree program) {
        String sql = "select * from Degree where program = " + program.getProgram();
        return makeListDegree(sql);
    }

    /**
     * Makes a list of degrees from an sql query.
     *
     * @param sql sql query
     * @return list of degrees
     */
    private List<Degree> makeListDegree(String sql) {
        List<Degree> degrees = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            Degree degree = new Degree();
            degree.setProgram((String) row.get("program"));
            degree.setDegreeLevel((String) row.get("degree"));
        }
        return degrees;
    }

    /**
     * Gets a specific degree by id from a student.
     *
     * @param degreeId  degree id to get
     * @param studentId student to get from
     * @return degree from DB
     */
    public Degree getStudentDegree(int degreeId, int studentId) {
        String sql = "select * from StudentDegree where degreeId = ? and studentId = ?";
        Object[] parameters = new Object[]{degreeId, studentId};
        return (Degree) jdbcTemplate.queryForObject(sql, parameters, new DegreeRowMapper());

    }

    /**
     * Gets a list of degrees by program.
     *
     * @return list of degrees
     */
    public Set<String> getDegreePrograms() {
        String sql = "select program from Degree";
        List<String> list = (List<String>) jdbcTemplate.queryForList(sql, String.class);
        return list.stream().collect(Collectors.toSet());
    }

    /**
     * Gets a list of degrees by level.
     *
     * @return list of degrees
     */
    public Set<String> getDegreeLevels() {
        String sql = "select degree from Degree";
        List<String> list = (List<String>) jdbcTemplate.queryForList(sql, String.class);
        return list.stream().collect(Collectors.toSet());
    }

    /**
     * Gets a list of transferColleges
     *
     * @return list of transfer colleges
     */
    public List<String> getTransferColleges() {
        String query = "select * from TransferCollege";
        return (List<String>) jdbcTemplate.queryForList(query, String.class);
    }

    /**
     * Adds a transfer college to the DB
     *
     * @param id
     * @param transferSchools
     */
    public void addStudentTransferCollege(Integer id, String transferSchools) {
        String sql = "insert into StudentTransferCollege(studentId, collegeName)" +
                "values('" + id + "', '" + transferSchools + "');";

        jdbcTemplate.update(sql);
    }
}

/**
 * Gets data from row of MySQL data and maps it to a Degree.
 */
class DegreeRowMapper implements RowMapper {

    @Override
    public Object mapRow(ResultSet rs, int row) throws SQLException {
        Degree degree = new Degree();
        degree.setDegreeLevel(rs.getString("degree"));
        degree.setProgram(rs.getString("program"));
        degree.setGraduationTerm(rs.getString("graduationTerm"));
        degree.setGraduationYear(rs.getInt("graduationYear"));
        degree.setGpa(rs.getDouble("gpa"));
        return degree;
    }
}