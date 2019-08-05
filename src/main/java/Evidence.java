import io.restassured.filter.Filter;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.commons.io.output.WriterOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Evidence {

    Logger log = LogManager.getLogger(this.getClass());

    protected StringWriter requestWriter;
    protected StringWriter responseWriter;
    protected StringWriter errorWriter;

    protected PrintStream requestStream;
    protected PrintStream responseStream;
    protected PrintStream erroreStream;


    public List<Filter> getFilters() {
        resetStreams();

        List<Filter> filters;

        filters = new ArrayList<>();
        filters.add(new RequestLoggingFilter(requestStream));
        filters.add(new ResponseLoggingFilter(responseStream));
        filters.add(new ErrorLoggingFilter(erroreStream));

        return filters;
    }


    public void resetStreams() {

        requestWriter = new StringWriter();
        responseWriter = new StringWriter();
        errorWriter = new StringWriter();

        requestStream = new PrintStream(new WriterOutputStream(requestWriter, Charset.defaultCharset()), true);
        responseStream = new PrintStream(new WriterOutputStream(responseWriter, Charset.defaultCharset()), true);
        erroreStream = new PrintStream(new WriterOutputStream(errorWriter, Charset.defaultCharset()), true);
    }

    public void generateEvidence(StringWriter... writers) {
        for (StringWriter w : writers) {
            log.debug(w.toString());
        }
    }

    public void generateSucessEvidence() {
        generateEvidence(requestWriter, responseWriter);
    }

    public void generateErrorEvidence() {
        generateEvidence(requestWriter, responseWriter, errorWriter);
    }

    public void message(String message) {
        System.out.println(message);
        log.info(message);
    }
}
