# Sending and Reading object rather than String
In this project, we will send not only the names of the persons from the `persons.csv` file but the complete Java-record Person. The producer and consumer clients send and receive bytes, respectively.   
This means they need a way to serialize and deserialize the bytes they send and read.
We therefore introduce a **Serializer**: 
```java

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
```
and a **Deserializer**:
```java
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

```
classes which obviously implements `Serializer` and `Deserializer` from the `org.apache.kafka.common.serialization` package.
We configure Kafka to use them for the `Producer` and `Consumer`:
```yaml
spring:    
  kafka:    
    consumer:
      value-deserializer: de.meziane.kafka_stream.serde.PersonDeserializer
    producer:
      value-serializer: de.meziane.kafka_stream.serde.PersonSerializer
```
Having started the Kafka broker from `docker-compose.yml`, let's start the Application.
Once the application started, we send a Post-Request to the `RestController`: http://localhost:8080/api/persons
Our consumer prints out the information it got 
```shell
...
026-05-18T22:03:17.300+02:00  INFO 97236 --- [kafka-producer-consumer] [ntainer#0-0-C-1] d.m.k.consumer.PersonConsumer            : Laura Fischer lives in 'Hauptstraße 25, 55116 Mainz, Germany' and is 29 years old. 
2026-05-18T22:03:17.301+02:00  INFO 97236 --- [kafka-producer-consumer] [ntainer#0-0-C-1] d.m.k.consumer.PersonConsumer            : Michael Weber lives in 'Rheinallee 77, 56068 Koblenz, Germany' and is 52 years old. 
2026-05-18T22:03:17.301+02:00  INFO 97236 --- [kafka-producer-consumer] [ntainer#0-0-C-1] d.m.k.consumer.PersonConsumer            : Sophie Wagner lives in 'Lindenweg 3, 64283 Darmstadt, Germany' and is 24 years old.  
...
```

