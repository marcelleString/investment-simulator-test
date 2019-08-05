
import java.io.File;
import java.io.PrintStream;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import io.restassured.filter.Filter;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.io.output.WriterOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class ServiceTest extends BaseTest {

    Util util = new Util();
    Evidence evidence = new Evidence();

    public static final String baseURI = "http://5b847b30db24a100142dce1b.mockapi.io/api/v1/";

    protected String headerName = "Content-Type";
    protected String headerValue = "application/json";

    private Response response;
    private File Jschema = util.getFileFromURL("jsonSchema/GetSimuladorResponse.json");


    @BeforeAll
    public void GetSimulator() {

        String callMessage = "\n***** GET /Simulador - Servico para Simulacao de Investimento de Poupanca *****";
        evidence.message(callMessage);

        try {
            response =
                    given()
                            .baseUri(baseURI)
                            .headers(headerName, headerValue)
                            .filters(evidence.getFilters())
                            .when()
                            .log().all()
                            .get("simulador")
                            .then()
                            .log().all()
                            .extract().response();

            evidence.generateSucessEvidence();
        } catch (Exception e) {
            evidence.generateErrorEvidence();
            evidence.log.error("Não Obteve Resposta do Serviço: " + e);
        }
    }


    @Test
    @DisplayName("GET/Simulador - Validacao de Status Code")
    public void ValidationStatusCode() {
        assertEquals(200, response.statusCode(), "Nao retornou status code 200!");
    }


    @Test
    @DisplayName("GET/Simulador - Validacao de Formato de Resposta: Campos e Tipos de Dados")
    public void ValidationResponseFormat() {
        //Pode-se validar com JsonSchema:
        response.then().assertThat().body(matchesJsonSchema(Jschema));

        //Ou pode-se validar cada campo desta maneira também:
        assertNotNull(response.path("meses[0]"), "O primeiro campo [meses] não foi retornado!");
        assertTrue(response.path("meses[0]") instanceof String, "O primeiro campo [meses] não retornou como String!");
    }


    @ParameterizedTest(name = "GET/Simulador - Validacao de Valores Fixos dos Campos")
    @CsvFileSource(resources = "/DataService.csv", numLinesToSkip = 1, delimiter = ';')
    @DisplayName("GET/Simulador - Validacao de Valores Fixos dos Campos")
    public void ValidationFixedFieldValues(ArgumentsAccessor arguments) {
        Integer id = arguments.getInteger(0);
        Integer currentId = response.path("id");

        assertEquals(id, currentId, "O campo id, não retornou o valor esperado: " + id);

        Integer csvColumn = 1;
        for (int i = 0; i < 3; i++) {
            String currentMonth = response.path("meses[" + i + "]");
            String currentValue = response.path("valor[" + i + "]");

            String expectedMonth = arguments.getString(csvColumn);
            csvColumn++;
            String expectedValue = arguments.getString(csvColumn);
            csvColumn++;

            String messageData =
                    "\nDados atuais: " + "Tempo(Meses): " + currentMonth + " - Valor: " + currentValue +
                            "\nDados esperados: " + "Tempo(Meses): " + expectedMonth + " - Valor: " + expectedValue;

            assertEquals(expectedMonth, currentMonth, "Não retornou o Tempo(Meses) esperado! " + messageData);
            assertEquals(expectedValue, currentValue, "Não retornou o Valor esperado! " + messageData);
        }
    }
}

