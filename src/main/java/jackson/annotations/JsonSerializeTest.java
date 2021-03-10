package jackson.annotations;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import static util.StringUtils.trimSpacesAndNewLines;

/**
 * {@link JsonSerialize} is used during serialization to specify custom serializer for a field or a class
 */
public class JsonSerializeTest {
    @Test
    public void jsonRootNameFieldLevelTest() throws JsonProcessingException {
        PersonOne personOne = new PersonOne();
        personOne.setFirstName("Semyon");
        personOne.setSecondName("Naumov");
        personOne.setBirthDate(LocalDate.of(1996, 3, 5));

        String expected =
                "{\n" +
                "  \"firstName\": \"Semyon\",\n" +
                "  \"secondName\": \"Naumov\",\n" +
                "  \"birthDate\": \"05-03-1996\"\n" +
                "}";
        expected = trimSpacesAndNewLines(expected);

        String json = new ObjectMapper().writeValueAsString(personOne);
        assertEquals(expected, json);
    }

    @Test
    public void jsonRootNameClassLevelTest() throws JsonProcessingException {
        PersonTwo personTwo = new PersonTwo();
        personTwo.setFirstName("Semyon");
        personTwo.setSecondName("Naumov");
        personTwo.setBirthDate(LocalDate.of(1996, 3, 5));

        String expected =
                "{\n" +
                "  \"person\": [\n" +
                "    \"Semyon\",\n" +
                "    \"Naumov\",\n" +
                "    \"05-03-1996\"\n" +
                "  ]\n" +
                "}";
        expected = trimSpacesAndNewLines(expected);

        String json = new ObjectMapper().writeValueAsString(personTwo);
        assertEquals(expected, json);
    }

    private static class PersonOne {
        private String firstName;
        private String secondName;
        @JsonSerialize(using = CustomLocalDateSerializer.class) // can be put over a getter as well
        private LocalDate birthDate;

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

        public LocalDate getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
        }
    }

    @JsonSerialize(using = PersonTwoSerializer.class)
    private static class PersonTwo {
        private String firstName;
        private String secondName;
        private LocalDate birthDate;

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

        public LocalDate getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
        }
    }

    private static class CustomLocalDateSerializer extends JsonSerializer<LocalDate> {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        @Override
        public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeString(value.format(formatter));
        }
    }

    private static class PersonTwoSerializer extends JsonSerializer<PersonTwo> {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        @Override
        public void serialize(PersonTwo value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            // some crazy stuff here
            gen.writeStartObject();
            gen.writeArrayFieldStart("person");
            gen.writeString(value.getFirstName());
            gen.writeString(value.getSecondName());
            gen.writeString(value.getBirthDate().format(formatter));
            gen.writeEndArray();
            gen.writeEndObject();
        }
    }
}
