package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin={"html:target/reports/cucumber.html","json:target/reports/cucumber.json"},
        glue="steps",
        features="src/test/resources/features",
        tags = "@regression",
        dryRun = false
)

public class RunnerClass {

}

