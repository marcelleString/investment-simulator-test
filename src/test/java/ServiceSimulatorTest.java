
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
import io.restassured.response.Response;
import org.apache.commons.io.output.WriterOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class ServiceSimulatorTest extends BaseTest {

    private Logger log = LogManager.getLogger(this.getClass());

    public static final String baseURI = "http://5b847b30db24a100142dce1b.mockapi.io/api/v1/";

    protected String headerName = "Content-Type";
    protected String headerValue = "application/json";

    private Response response;
    private File Jschema = getFileFromURL("jsonSchema/GetSimuladorResponse.json");

    protected StringWriter requestWriter;
    protected StringWriter responseWriter;
    protected StringWriter errorWriter;

    protected PrintStream requestStream;
    protected PrintStream responseStream;
    protected PrintStream erroreStream;




    @BeforeAll
    public void GetSimulador() {

        String callMessage = "\n***** GET /Simulador - Servico para Simulacao de Investimento de Poupanca *****";
        System.out.println(callMessage);
        log.info(callMessage);

        try {
            response =
                    given()
                            .baseUri(baseURI)
                            .headers(headerName, headerValue)
                            .filters(getFilters())
                            .when()
                            .log().all()
                            .get("simulador")
                            .then()
                            .log().all()
                            .body(matchesJsonSchema(Jschema))
                            .extract().response();

            generateSucessEvidence();
        } catch (Exception e) {
            generateErrorEvidence();
            log.error("Não Obteve Resposta do Serviço: " + e);
        }
    }

    @Test
    @DisplayName("Validacao de Status Code")
    public void StatusCode() {
        assertEquals(200, response.statusCode(), "Nao retornou status code 200!");
            log.debug("ResponseCode: " + response.getStatusCode());
        }


    public File getFileFromURL(String filePath) {
        URL url = getClass().getClassLoader().getResource(filePath);
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            file = new File(url.getPath());
        } finally {
            return file;
        }
    }

    public void resetStreams() {
        requestWriter = new StringWriter();
        responseWriter = new StringWriter();
        errorWriter = new StringWriter();

        requestStream = new PrintStream(new WriterOutputStream(requestWriter, Charset.defaultCharset()), true);
        responseStream = new PrintStream(new WriterOutputStream(responseWriter, Charset.defaultCharset()), true);
        erroreStream = new PrintStream(new WriterOutputStream(errorWriter, Charset.defaultCharset()), true);
    }

    public List<Filter> getFilters() {
        resetStreams();

        List<Filter> filters;

        filters = new ArrayList<>();
        filters.add(new RequestLoggingFilter(requestStream));
        filters.add(new ResponseLoggingFilter(responseStream));
        filters.add(new ErrorLoggingFilter(erroreStream));

        return filters;
    }

    public void generateSucessEvidence() {
        generateEvidence(requestWriter, responseWriter);
    }

    public void generateErrorEvidence() {
        generateEvidence(requestWriter, responseWriter, errorWriter);
    }

    public void generateEvidence(StringWriter... writers) {
        for (StringWriter w : writers) {
            log.debug(w.toString());
        }
    }
}

