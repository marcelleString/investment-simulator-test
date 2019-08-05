import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

public class BaseTest {

    Evidence evidence = new Evidence();


    @BeforeAll
    public void BeforeAll() {
        String startedMessage = "\n###################   AUTOMATED TESTING STARTED   ###################";
        evidence.message(startedMessage);
    }


    @AfterAll
    public void AfterAll() {
        String finishedMessage = "###################   AUTOMATED TESTING FINISHED   ###################\n";
        evidence.message(finishedMessage);
    }


    @BeforeEach
    public void BeforeEach(TestInfo testInfo) {
        ScenarioStartedMessage(testInfo);
    }


    @AfterEach
    public void AfterEach(TestInfo testInfo) {
        ScenarioFinishedMessage(testInfo);
    }


    public void ScenarioStartedMessage(TestInfo testInfo) {
        String ScenarioStartedMessage = "\n********** Test Scenario Started: " + testInfo.getDisplayName() + " **********";
        evidence.message(ScenarioStartedMessage);
    }


    public void ScenarioFinishedMessage(TestInfo testInfo) {
        String ScenarioFinishedMessage = "********** Test Scenario Finished: " + testInfo.getDisplayName() + " **********\n";
        evidence.message(ScenarioFinishedMessage);
    }
}
