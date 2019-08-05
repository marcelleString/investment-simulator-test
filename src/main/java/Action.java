import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Action {
    private WebDriver driver;
    private WebDriverWait wait;

    public Action(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public WebElement waitAndReturnElement(String elementSelector) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(elementSelector)));
        WebElement elementoRetornado = driver.findElement(By.cssSelector(elementSelector));
        return elementoRetornado;
    }

    public List<WebElement> waitAndReturnSubElements(WebElement element, String elementSelector) {
        List<WebElement> listaElementos = element.findElements(By.cssSelector(elementSelector));
        return listaElementos;
    }

    public void write(String elementSelector, String text) {
        waitAndReturnElement(elementSelector).sendKeys(text);
    }

    public void click(String elementSelector) {
        waitAndReturnElement(elementSelector).click();
    }

    public String getText(String elementSelector) {
        String text = waitAndReturnElement(elementSelector).getText();
        return text;
    }

    public void scrollToElement(String elementSelector) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.cssSelector(elementSelector)));
    }
}
