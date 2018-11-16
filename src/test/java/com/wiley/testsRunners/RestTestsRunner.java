package com.wiley.testsRunners;


import com.wiley.testsClasses.RestTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(RestTests.class)
public class RestTestsRunner {
}
