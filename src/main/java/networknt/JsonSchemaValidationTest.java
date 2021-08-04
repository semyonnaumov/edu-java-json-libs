package networknt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class JsonSchemaValidationTest {
    @Test
    public void test() throws JsonProcessingException {
        String jsonString = """
                {
                    "id": 1,
                    "name": "Lampshade",
                    "price": 0
                }""";

        String schemaString = """
                {
                    "$schema": "http://json-schema.org/draft-04/schema#",
                    "title": "Product",
                    "description": "A product from the catalog",
                    "type": "object",
                    "properties": {
                        "id": {
                            "description": "The unique identifier for a product",
                            "type": "integer"
                        },
                        "name": {
                            "description": "Name of the product",
                            "type": "string"
                        },
                        "price": {
                            "type": "number",
                            "minimum": 0,
                            "exclusiveMinimum": true
                        },
                        "type": {
                            "type": "string"
                        }
                    },
                    "required": ["id", "name", "price", "type"]
                }""";

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonString);// throws JsonProcessingException

        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        JsonSchema schema = schemaFactory.getSchema(schemaString);

        Set<ValidationMessage> validationMessages = schema.validate(jsonNode);
        Assert.assertEquals(2, validationMessages.size()); // absent "type" and wrong value of "price"
    }
}
