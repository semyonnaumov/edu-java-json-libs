package jackson.annotations.serialization;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static util.StringUtils.trimSpacesAndNewLines;

/**
 * {@link JsonRawValue} is used in serialization to serialize value as is, just put the field value in JSON
 */
public class JsonRawValueTest {
    @Test
    public void jsonRawValueTest() throws JsonProcessingException {
        Request request = new Request();
        request.setId(1L);
        request.setBody("{\"data\":{\"values\":[12,15]}}");

        String expected =
                "{\n" +
                "  \"id\": 1,\n" +
                "  \"body\": {\n" +
                "    \"data\": {\n" +
                "      \"values\": [\n" +
                "        12,\n" +
                "        15\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "}";
        expected = trimSpacesAndNewLines(expected);

        String json = new ObjectMapper().writeValueAsString(request); // throws JsonProcessingException
        assertEquals(expected, json);
    }

    private static class Request {
        private Long id;
        @JsonRawValue // can be used at getter as well
        private String body;

        public Request() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }
}
