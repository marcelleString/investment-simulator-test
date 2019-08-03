import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInfo;

public class BaseTest {

    private Logger log = LogManager.getLogger(this.getClass());

    @BeforeAll
    public void BeforeAll() {
        String startedMessage = "\n\n###################   AUTOMATED TESTING STARTED   ###################";
        System.out.println(startedMessage);
        log.debug(startedMessage);
    }

    @AfterAll
    public void AfterAll() {
        String finishedMessage = "\n###################   AUTOMATED TESTING FINISHED   ###################";
        System.out.println(finishedMessage);
    }

    public void ScenarioStartedMessage(TestInfo testInfo){
        String ScenarioStartedMessage = "\n\n********** Test Scenario Started: " + testInfo.getDisplayName() + " **********\n";
        System.out.println(ScenarioStartedMessage);
    }

    public void ScenarioFinishedMessage(TestInfo testInfo){
        String ScenarioFinishedMessage = "\n********** Test Scenario Finished: " + testInfo.getDisplayName() + " **********\n\n";
        System.out.println(ScenarioFinishedMessage);
    }
}
