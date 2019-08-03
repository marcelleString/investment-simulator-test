import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UISimulatorTest extends BaseTest {

    private Logger log = LogManager.getLogger(this.getClass());

    WebDriver driver;
    WebDriverWait wait;

    public UISimulatorTest() {
        System.setProperty("webdriver.chrome.driver", "./browserDrives/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @BeforeEach
    public void BeforeEach(TestInfo testInfo) {
        ScenarioStartedMessage(testInfo);

        driver.manage().window().maximize();
        driver.get("https://www.sicredi.com.br/html/ferramenta/simulador-investimento-poupanca/");
    }

    @AfterEach
    public void AfterEach(TestInfo testInfo) {
        driver.quit();

        ScenarioFinishedMessage(testInfo);
    }

    @Test
    @DisplayName("UI - Simulador de Investimento - Poupanca")
    public void investSavings() {

        scrollToElement(driver.findElement(By.id("valorAplicar")));

        WebElement valorAplicarInputText = driver.findElement(By.id("valorAplicar"));
        valorAplicarInputText.sendKeys("50,00");

        WebElement valorInvestirInputText = driver.findElement(By.id("valorInvestir"));
        valorInvestirInputText.sendKeys("30,00");

        WebElement tempoInputText = driver.findElement(By.id("tempo"));
        tempoInputText.sendKeys("1");

        WebElement periodoSelect = driver.findElement(By.className("btSelect"));
        periodoSelect.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".listaSelect li a[rel*='A']")));
        WebElement periodoAno = driver.findElement(By.cssSelector(".listaSelect li a[rel*='A']"));
        periodoAno.click();

        WebElement simularBtn = driver.findElement(By.className("btnSimular"));
        simularBtn.click();

        WebElement tabelaValores = driver.findElement(By.cssSelector(".blocoResultadoSimulacao .maisOpcoes table"));
        List<WebElement> listaValores = tabelaValores.findElements(By.cssSelector("tbody tr"));
        for (WebElement trItem : listaValores) {
            List<WebElement> tdItens = trItem.findElements(By.cssSelector("td"));

            WebElement tempoElement = tdItens.get(0);
            String tempo = tempoElement.getText();

            WebElement valorElement = tdItens.get(1);
            String valor = valorElement.getText();

            System.out.println("Tempo(Meses): " + tempo + " - Valor: " + valor);
        }
    }

    public void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", element);
    }
}