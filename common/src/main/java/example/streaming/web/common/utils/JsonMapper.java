package example.streaming.web.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/*
 *
 * @author a.yatsenko
 * Created at 18.06.2020
 */
@Slf4j
public class JsonMapper {

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        DateFormat dateFormat = new SimpleDateFormat();
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        mapper.setDateFormat(dateFormat);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static String serializeObject(Object object) {
        String json = null;
        try {
            json = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.warn("Serialization error. Error cause: {}, message: {}", e.getCause(), e.getMessage());
        }
        return json;
    }

    public static <T> T deserializeObject(String json, Class<T> clazz) {
        T object = null;
        try (StringReader reader = new StringReader(json)) {
            object = mapper.readValue(reader, clazz);
        } catch (IOException e) {
            log.warn("Deserialization error. Error cause: {}, message: {}", e.getCause(), e.getMessage());
        }
        return object;
    }

    public static JsonNode readTree(String message) {
        JsonNode root = null;
        try {
            root = mapper.readTree(message);
        } catch (JsonProcessingException e) {
            log.warn("Deserialization error. Error cause: {}, message: {}", e.getCause(), e.getMessage());
        }
        return root;
    }
}
