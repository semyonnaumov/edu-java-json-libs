package jackson.annotations;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import util.StringUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * {@link JsonAnyGetter} is used during serialization to extract map-filed entries as parent object fields.
 *
 * - can be only one in a class
 * - can relate only {@link Map} field
 * - 'enabled' argument rarely used
 */
public class JsonAnyGetterTest {
    @Test
    public void jsonAnyGetterAtGetter() throws JsonProcessingException {
        JsonAnyGetterAtGetter jsonAnyGetterAtGetter = new JsonAnyGetterAtGetter("Name", new HashMap<String, Integer>() {
            {
                put("one", 1);
                put("two", 2);
                put("three", 3);
            }
        });

        String expected =
                "{\n" +
                "  \"name\": \"Name\",\n" +
                "  \"one\": 1,\n" +
                "  \"two\": 2,\n" +
                "  \"three\": 3\n" +
                "}";
        expected = StringUtils.trimSpacesAndNewLines(expected);

        String json1 = new ObjectMapper().writeValueAsString(jsonAnyGetterAtGetter); // throws JsonProcessingException
        assertEquals(expected, json1);
    }

    @Test
    public void jsonAnyGetterAtField() throws JsonProcessingException {
        JsonAnyGetterAtField jsonAnyGetterAtField = new JsonAnyGetterAtField("Name", new HashMap<String, Integer>() {
            {
                put("one", 1);
                put("two", 2);
                put("three", 3);
            }
        });

        String expected =
                "{\n" +
                "  \"name\": \"Name\",\n" +
                "  \"numbers\": {\n" +
                "    \"one\": 1,\n" +
                "    \"two\": 2,\n" +
                "    \"three\": 3\n" +
                "  },\n" +
                "  \"one\": 1,\n" +
                "  \"two\": 2,\n" +
                "  \"three\": 3\n" +
                "}";
        expected = StringUtils.trimSpacesAndNewLines(expected);

        String json1 = new ObjectMapper().writeValueAsString(jsonAnyGetterAtField); // throws JsonProcessingException
        assertEquals(expected, json1);
    }

    private static class JsonAnyGetterAtGetter {
        private String name;
        private Map<String, Integer> numbers;

        public JsonAnyGetterAtGetter(String name, Map<String, Integer> numbers) {
            this.name = name;
            this.numbers = numbers;
        }

        public JsonAnyGetterAtGetter() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @JsonAnyGetter // Extracts map content as fields
        public Map<String, Integer> getNumbers() {
            return numbers;
        }

        public void setNumbers(Map<String, Integer> numbers) {
            this.numbers = numbers;
        }
    }

    private static class JsonAnyGetterAtField {
        private String name;
        @JsonAnyGetter // Retains map as a field but additionally extracts its content as fields
        private Map<String, Integer> numbers;

        public JsonAnyGetterAtField(String name, Map<String, Integer> numbers) {
            this.name = name;
            this.numbers = numbers;
        }

        public JsonAnyGetterAtField() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Map<String, Integer> getNumbers() {
            return numbers;
        }

        public void setNumbers(Map<String, Integer> numbers) {
            this.numbers = numbers;
        }
    }
}
