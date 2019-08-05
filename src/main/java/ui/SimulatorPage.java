package ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SimulatorPage extends Action {
    public SimulatorPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    private final String profileRadioButton = ".formGeraInvestimento .campoObrigatorio input[type*='radio'][value*='#PROFILE#']";

    private final String amountToApplyField = "#valorAplicar";
    private final String amountToApplyErrorLabel = "#valorAplicar-error";

    private final String amountToInvestField = "#valorInvestir";
    private final String amountToInvestErrorLabel = "#valorInvestir-error";

    private final String timeField = "#tempo";

    private final String periodDropDownList = ".btSelect";
    private final String periodOption = ".listaSelect li a[rel*='#PERIOD#']";


    private final String simulateButton = ".btnSimular";

    private final String periodResultText = ".blocoResultadoSimulacao .texto strong";
    private final String amountResultText = ".blocoResultadoSimulacao .valor";

    private final String valuesTable = ".blocoResultadoSimulacao .maisOpcoes table";
    private final String valuesRow = "tbody tr";
    private final String valuesColumn = "td";


    public void simulateInvestment(String profile, String amountToApply, String amountToInvest, String time, String period) {
        scrollToElement(amountToApplyField);
        click(profileRadioButton.replace("#PROFILE#", profile));
        write(amountToApplyField, amountToApply);
        write(amountToInvestField, amountToInvest);
        write(timeField, time);
        click(periodDropDownList);
        click(periodOption.replace("#PERIOD#", period));
        click(simulateButton);

        System.out.println("SIMULACAO: \n" + "Perfil - " + profile + "\n" + "ValorAplicar - " + amountToApply + "\n" +
                "ValorInvestir - " + amountToInvest + "\n" + "Tempo - " + time + period);
    }

    public String getPeriodResultText() {
        return getText(periodResultText);
    }

    public String getAmountResultText() {
        return getText(amountResultText);
    }

    public WebElement getResultsTable() {
        WebElement tableValues = waitAndReturnElement(valuesTable);
        return tableValues;
    }

    public List<WebElement> getTableRowValues(WebElement tableValues) {
        List<WebElement> rowValues = waitAndReturnSubElements(tableValues, valuesRow);
        return rowValues;
    }

    public List<WebElement> getValuesFromTableColumns(WebElement tabelaValores) {
        List<WebElement> valoresColuna = waitAndReturnSubElements(tabelaValores, valuesColumn);
        return valoresColuna;
    }

    public String getAmountToApplyMessageError() {
        return getText(amountToApplyErrorLabel);
    }

    public String getAmountToInvestMessageError() {
        return getText(amountToInvestErrorLabel);
    }
}
