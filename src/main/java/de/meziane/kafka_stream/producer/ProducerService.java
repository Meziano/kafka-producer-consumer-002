package de.meziane.kafka_stream.producer;


import de.meziane.kafka_stream.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    @Autowired
    private KafkaTemplate<String, Person> kafkaTemplate;

    public void sendMessage(String topic, Person person) {
        kafkaTemplate.send(topic, person);
    }
}
