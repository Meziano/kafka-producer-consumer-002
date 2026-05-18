package de.meziane.kafka_stream.serde;

import de.meziane.kafka_stream.model.Person;
import org.apache.kafka.common.serialization.Deserializer;
import tools.jackson.databind.ObjectMapper;

public class PersonDeserializer implements Deserializer<Person> {
    private final ObjectMapper mapper = new ObjectMapper();
    @Override
    public Person deserialize(String s, byte[] data) {
        try {
            return mapper.readValue(data, Person.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing Person", e);
        }
    }
}
