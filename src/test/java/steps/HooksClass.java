package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class HooksClass {

    // Code to set up before each scenario
    @Before
    public void setUp(Scenario scenario){
        System.out.println("Scenario is Starting: "+ scenario.getName());
    }

    @After
    public void TearDown(Scenario scenario){
        if(scenario.isFailed()){
            System.out.println("Scenario FAILED: "+ scenario.getName());
        }
        else {
            System.out.println("Scenario Passed:"+ scenario.getName());
        }
    }


}
