import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

public class BaseTest {

    private Logger log = LogManager.getLogger(this.getClass());

    @BeforeAll
    public void BeforeAll() {
        String startedMessage = "\n###################   AUTOMATED TESTING STARTED   ###################\n";
        System.out.println(startedMessage);
        log.debug(startedMessage);
    }

    @AfterAll
    public void AfterAll() {
        String finishedMessage = "\n###################   AUTOMATED TESTING FINISHED   ###################\n";
        System.out.println(finishedMessage);
        log.debug(finishedMessage);
    }

    @BeforeEach
    public void BeforeEach(TestInfo testInfo) {
        ScenarioStartedMessage(testInfo);
    }

    @AfterEach
    public void AfterEach(TestInfo testInfo) {
        ScenarioFinishedMessage(testInfo);
    }

    public void ScenarioStartedMessage(TestInfo testInfo){
        String ScenarioStartedMessage = "\n********** Test Scenario Started: " + testInfo.getDisplayName() + " **********\n";
        System.out.println(ScenarioStartedMessage);
        log.debug(ScenarioStartedMessage);
    }

    public void ScenarioFinishedMessage(TestInfo testInfo){
        String ScenarioFinishedMessage = "\n********** Test Scenario Finished: " + testInfo.getDisplayName() + " **********\n";
        System.out.println(ScenarioFinishedMessage);
        log.debug(ScenarioFinishedMessage);
    }
}
