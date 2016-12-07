package com.project.Tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * This is a test suite, it contains all the tests.
 *
 * @author Brian Jorgenson
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({DegreeDaoTest.class, EmploymentDaoTest.class, ReportDaoTest.class,
                    TransferCollegeDaoTest.class, StudentDaoImplTest.class})
public class AllTests {
}
