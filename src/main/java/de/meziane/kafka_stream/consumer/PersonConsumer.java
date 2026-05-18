package de.meziane.kafka_stream.consumer;

import de.meziane.kafka_stream.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PersonConsumer {
    @KafkaListener(topics = "persons", groupId = "persons-group")
    public void listen(Person person) {
        log.info("{} lives in '{}' and is {} years old. ", person.name(), person.address(), person.age());
    }
}
