package jackson.annotations.serialization;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.assertEquals;

/**
 *  {@link JsonValue} is used in serialization to specify accessor that returns the resulting serialized value
 *  (not only {@link String} but all {@link Serializable) successors)
 *
 *  - also used in deserialization for enums
 */
public class JsonValueTest {
    @Test
    public void jsonValueEnumTest() throws JsonProcessingException {
        // serialization
        String json = new ObjectMapper().writeValueAsString(Currency.RUR); // throws JsonProcessingException
        assertEquals("\"Rubles\"", json);

        // deserialization
        Currency currency = new ObjectMapper().readValue("\"US Dollars\"", Currency.class);
        assertEquals(Currency.USD, currency);
    }

    @Test
    public void jsonValuePOJOTest() throws JsonProcessingException {
        Person person = new Person();
        person.setFirstName("Semyon");
        person.setSecondName("Naumov");
        person.setAge(25);

        String json = new ObjectMapper().writeValueAsString(person); // throws JsonProcessingException
        assertEquals("\"Semyon Naumov\"", json);
    }

    public enum Currency {
        RUR("Rubles"),
        USD("US Dollars");

        private final String value;

        Currency(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }
    }

    private static class Person {
        private String firstName;
        private String secondName;
        private Integer age;

        public Person() {
        }

        @JsonValue
        public String fullName() {
            return firstName + " " + secondName;
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
