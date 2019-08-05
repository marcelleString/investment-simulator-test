import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class Util {

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
}
