package de.meziane.kafka_stream.serde;

import de.meziane.kafka_stream.model.Person;
import org.apache.kafka.common.serialization.Serializer;
import tools.jackson.databind.ObjectMapper;

public class PersonSerializer implements Serializer<Person> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, Person person) {
        try {
            return mapper.writeValueAsBytes(person);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing Person", e);
        }
    }
}
