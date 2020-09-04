package com.example.experimentDB.experimentDB.bootstrap;

import com.example.experimentDB.experimentDB.domain.Person;
import com.example.experimentDB.experimentDB.repositories.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {
    private final PersonRepository personRepository;

    public BootstrapData(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Person Alex = new Person("Alex", "Freiberg");
        Person Seth = new Person("Seth", "Gardner");
        Person Noah = new Person("Noah", "Kelleher");
        Person Zach = new Person("Zach", "Eisele");

        personRepository.save(Alex);
        personRepository.save(Seth);
        personRepository.save(Noah);
        personRepository.save(Zach);

        System.out.println("Started in BootStrap");
        System.out.println("Number of people:" + personRepository.count());
    }
}
