package cd.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import flexjson.JSONSerializer;

/**
 * Created by chendong on 16/5/3.
 */
public class JSONSerializerUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        // Output class name in serialized result, support polymorphism.
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T>  String toJson(T obj, String opt_fields) {
        JSONSerializer serializer = getSerializer(opt_fields);
        return serializer.serialize(obj);
    }

    public static <T> String toJson(List<T> list, String opt_fields) {
        if (list == null) {
            return null;
        }
        if (list.isEmpty()) {
            return "[]";
        }
        JSONSerializer serializer = getSerializer(opt_fields);

        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (T obj: list) {
            String json = serializer.serialize(obj);
            if (!first) {
                sb.append(",").append(json);
            } else {
                sb.append(json);
                first = false;
            }
        }
        sb.append("]");
        return sb.toString();
    }

    protected static JSONSerializer getSerializer(String opt_fields) {
        JSONSerializer serializer = new JSONSerializer();
        if (StringUtils.isBlank(opt_fields)) {
            return serializer.exclude("*.class").include("*");
        }
        return serializer.exclude("*.class")
                .include(StringUtils.split(opt_fields, ","))
                .exclude("*");
    }

    public static String toJson(Object obj) throws IOException {
        return mapper.writeValueAsString(obj);
    }

    public static void writeValue(Object obj, OutputStream out) throws IOException {
        mapper.writeValue(out, obj);
    }

    public static <T> T fromJson(String json, Class<T> valueType) throws IOException {
        return (T) mapper.readValue(json, valueType);
    }

    public static <T> T fromJson(InputStream input, Class<T> valueType) throws IOException {
        return (T) mapper.readValue(input, valueType);
    }
}
