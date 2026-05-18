package de.meziane.kafka_stream.controller;


import de.meziane.kafka_stream.model.Person;
import de.meziane.kafka_stream.producer.ProducerService;
import de.meziane.kafka_stream.utilities.CsvReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {

    @Autowired
    private ProducerService service;

    @PostMapping
    public void sendPersons() throws IOException {
        log.info("Current working directory: {}", Path.of("").toAbsolutePath());
        log.info("CSV path: {}", Path.of("src/main/resources/data/persons.csv").toAbsolutePath());
        List<Person> persons = CsvReader.readPersons(Path.of("src/main/resources/data/persons.csv"));
        /* persons.forEach(p -> log.info("'{}' is {} years old.", p.name(), p.age())); */
        persons.forEach(p -> service.sendMessage("persons", p));
    }
}
