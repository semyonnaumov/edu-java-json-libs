package jackson.annotations;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static util.StringUtils.trimSpacesAndNewLines;

/**
 * {@link JsonRootName} is used in serialization to SUGGEST a wrapper name
 *
 * - only useful when wrapping is switched on in {@link ObjectMapper}
 * - optional argument 'namespace' is available to use with data formats such as XML
 */
public class JsonRootNameTest {
    @Test
    public void jsonRootNameTest() throws JsonProcessingException {
        Person person = new Person();
        person.setFirstName("Semyon");
        person.setSecondName("Naumov");
        person.setAge(25);

        String expected =
                "{\n" +
                "  \"person\": {\n" +
                "    \"firstName\": \"Semyon\",\n" +
                "    \"secondName\": \"Naumov\",\n" +
                "    \"age\": 25\n" +
                "  }\n" +
                "}";
        expected = trimSpacesAndNewLines(expected);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE); // must be enabled to see the effect
        String json = mapper.writeValueAsString(person);
        assertEquals(expected, json);
    }

    @JsonRootName("person")
    private static class Person {
        private String firstName;
        private String secondName;
        private Integer age;

        public Person() {
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getSecondName() {
            return secondName;
        }

        public void setSecondName(String secondName) {
            this.secondName = secondName;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
