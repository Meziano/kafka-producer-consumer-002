package de.meziane.kafka_stream.utilities;

import de.meziane.kafka_stream.model.Person;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public static List<Person> readPersons(Path csvPath) throws IOException {
        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .get();

        List<Person> persons = new ArrayList<>();

        try (Reader reader = Files.newBufferedReader(csvPath);
             CSVParser parser = CSVParser.parse(reader, format)) {

            for (CSVRecord record : parser) {
                String name = record.get("name");
                String address = record.get("address");
                int age = Integer.parseInt(record.get("age"));

                persons.add(new Person(name, address, age));
            }
        }

        return persons;
    }

}
