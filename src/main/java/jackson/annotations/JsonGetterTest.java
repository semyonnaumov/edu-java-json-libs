package jackson.annotations;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static util.StringUtils.trimSpacesAndNewLines;

/**
 * {@link JsonGetter} is like {@link JsonProperty}, but used only during serialization
 */
public class JsonGetterTest {
    @Test
    public void jsonGetterTest() throws JsonProcessingException {
        Person person = new Person();
        person.setName("Semyon");
        person.setAge(25);

        String expeted =
                "{\n" +
                "  \"age\": 25,\n" +
                "  \"NAME\": \"Semyon\"\n" +
                "}";
        expeted = trimSpacesAndNewLines(expeted);

        String json = new ObjectMapper().writeValueAsString(person); // throws JsonProcessingException
        assertEquals(expeted, json);
    }

    private static class Person {
        private String name;
        private Integer age;

        public Person() {
        }

        @JsonGetter("NAME")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
