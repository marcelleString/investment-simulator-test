import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ui.SimulatorPage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UITest extends BaseTest {

    private WebDriver driver;
    private WebDriverWait wait;
    SimulatorPage simulatorPage;

    @BeforeEach
    public void BeforeEach(TestInfo testInfo) {
        ScenarioStartedMessage(testInfo);

        System.setProperty("webdriver.chrome.driver", "./browserDrives/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(WebSiteURI);

        wait = new WebDriverWait(driver, 20);

        simulatorPage = new SimulatorPage(driver, wait);
    }

    @AfterEach
    public void AfterEach(TestInfo testInfo) {
        driver.quit();

        ScenarioFinishedMessage(testInfo);
    }

    @ParameterizedTest(name = "Validar Valores de Simulacao de Investimento de Poupanca : Perfil: {0}, ValorAplicar: {1}, ValorInvestir: {2}, Tempo: {3}{4}")
    @CsvFileSource(resources = "/data/UIData.csv", numLinesToSkip = 1, delimiter = ';')
    @DisplayName("Validar Valores de Simulacao de Investimento de Poupanca")
    public void validateSavingsInvestmentSimulation(ArgumentsAccessor arguments) {
        String profile = arguments.getString(0);
        String amountToApply = arguments.getString(1);
        String amountToInvest = arguments.getString(2);
        String time = arguments.getString(3);
        String period = arguments.getString(4);
        String expectedPeriodResult = arguments.getString(5);
        String expectedAmountResult = arguments.getString(6);

        simulatorPage.simulateInvestment(profile, amountToApply, amountToInvest, time, period);

        String currentPeriodResult = simulatorPage.getPeriodResultText();
        String currentAmountResult = simulatorPage.getAmountResultText();

        assertEquals(expectedPeriodResult, currentPeriodResult, "No Resultado da simulacao não retornou o período esperado!");
        assertEquals(expectedAmountResult, currentAmountResult, "No Resultado da simulacao não retornou o valor esperado!");

        evidence.message("\nRESULTADO: " + expectedAmountResult + " em: " + expectedPeriodResult);

        WebElement moreOptionsTable = simulatorPage.getResultsTable();
        List<WebElement> tableRows = simulatorPage.getTableRowValues(moreOptionsTable);

        Integer csvColumn = 7;
        for (WebElement currentRow : tableRows) {
            List<WebElement> values = simulatorPage.getValuesFromTableColumns(currentRow);

            WebElement timeElement = values.get(0);
            WebElement valueElement = values.get(1);

            String currentTime = timeElement.getText();
            String currentValue = valueElement.getText();

            evidence.message("Tempo(Meses): " + currentTime + " - Valor: " + currentValue);


            String expectedTime = arguments.getString(csvColumn);
            csvColumn++;
            String expectedValue = arguments.getString(csvColumn);
            csvColumn++;

            String messageData =
                    "\nDados atuais: " + "Tempo(Meses): " + currentTime + " - Valor: " + currentValue +
                            "\nDados esperados: " + "Tempo(Meses): " + expectedTime + " - Valor: " + expectedValue;

            assertEquals(expectedTime, currentTime, "Não retornou o Tempo(Meses) esperado! " + messageData);
            assertEquals(expectedValue, currentValue, "Não retornou o Valor esperado! " + messageData);
        }
    }


    @ParameterizedTest(name = "Validar mensagem de erro quando informado valor menor que R$20.00 em ValorAplicar e ValorInvestir")
    @CsvFileSource(resources = "/data/UIErrorData.csv", numLinesToSkip = 1, delimiter = ';')
    @DisplayName("Validar mensagem de erro quando informado valor menor que R$20.00 em ValorAplicar e ValorInvestir")
    public void validateValueErrorMessage(ArgumentsAccessor arguments) {
        String profile = arguments.getString(0);
        String amountToApply = arguments.getString(1);
        String amountToInvest = arguments.getString(2);
        String time = arguments.getString(3);
        String period = arguments.getString(4);
        String expectedErrorMessage = arguments.getString(5);

        simulatorPage.simulateInvestment(profile, amountToApply, amountToInvest, time, period);

        String currentAmountToApplyErrorMessage = simulatorPage.getAmountToApplyMessageError();
        String currentAmountToInvestErrorMessage = simulatorPage.getAmountToInvestMessageError();

        evidence.message("Mensagem de erro ValorAplicar: " + currentAmountToApplyErrorMessage);
        evidence.message("Mensagem de erro ValorInvestir: " + currentAmountToInvestErrorMessage);

        assertEquals(expectedErrorMessage, currentAmountToApplyErrorMessage, "Não retornou a Mensagem de Erro esperada!");
        assertEquals(expectedErrorMessage, currentAmountToInvestErrorMessage, "Não retornou a Mensagem de Erro esperada!");
    }
}