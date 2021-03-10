package jackson.annotations;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static util.StringUtils.trimSpacesAndNewLines;

/**
 * {@link JsonPropertyOrder} is used in serialization to specify property order in JSON
 */
public class JsonPropertyOrderTest {
    @Test
    public void explicitly() throws JsonProcessingException {
        PersonOne personOne = new PersonOne();
        personOne.setFirstName("Semyon");
        personOne.setSecondName("Naumov");
        personOne.setAge(25);

        String expected =
                "{\n" +
                "  \"secondName\": \"Naumov\",\n" +
                "  \"firstName\": \"Semyon\",\n" +
                "  \"age\": 25\n" +
                "}";
        expected = trimSpacesAndNewLines(expected);

        String json = new ObjectMapper().writeValueAsString(personOne); // throws JsonProcessingException
        assertEquals(expected, json);
    }

    @Test
    public void alphabetic() throws JsonProcessingException {
        PersonTwo personTwo = new PersonTwo();
        personTwo.setFirstName("Semyon");
        personTwo.setSecondName("Naumov");
        personTwo.setAge(25);

        String expected =
                "{\n" +
                "  \"age\": 25,\n" +
                "  \"firstName\": \"Semyon\",\n" +
                "  \"secondName\": \"Naumov\"\n" +
                "}";
        expected = trimSpacesAndNewLines(expected);

        String json = new ObjectMapper().writeValueAsString(personTwo); // throws JsonProcessingException
        assertEquals(expected, json);
    }

    @Test
    public void combined() throws JsonProcessingException {
        PersonThree personThree = new PersonThree();
        personThree.setFirstName("Semyon");
        personThree.setSecondName("Naumov");
        personThree.setAge(25);

        String expected =
                "{\n" +
                "  \"secondName\": \"Naumov\",\n" +
                "  \"age\": 25,\n" +
                "  \"firstName\": \"Semyon\"\n" +
                "}";
        expected = trimSpacesAndNewLines(expected);

        String json = new ObjectMapper().writeValueAsString(personThree); // throws JsonProcessingException
        assertEquals(expected, json);
    }

    @JsonPropertyOrder({"secondName", "firstName", "age"})
    private static class PersonOne {
        private String firstName;
        private String secondName;
        private Integer age;

        public PersonOne() {
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

    @JsonPropertyOrder(alphabetic = true)
    private static class PersonTwo {
        private String firstName;
        private String secondName;
        private Integer age;

        public PersonTwo() {
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

    @JsonPropertyOrder(value = {"secondName"}, alphabetic = true) // first fields in 'value', the rest alphabetically
    private static class PersonThree {
        private String firstName;
        private String secondName;
        private Integer age;

        public PersonThree() {
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
