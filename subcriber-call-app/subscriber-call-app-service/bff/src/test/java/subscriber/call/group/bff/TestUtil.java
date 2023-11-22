package subscriber.call.group.bff;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String readFile(Class className, String fileName) throws URISyntaxException, IOException {
        Path path = Paths.get(className.getClassLoader().getResource(fileName).toURI());
        Stream<String> lines = Files.lines(path);
        String data = lines.collect(Collectors.joining("\n"));
        lines.close();
        return data;
    }

    public static String convertToJson(Object object) {
        var mapper = objectMapper.writer().withDefaultPrettyPrinter();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            return "";
        }
    }
}
