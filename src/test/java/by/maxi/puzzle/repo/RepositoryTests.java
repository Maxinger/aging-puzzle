package by.maxi.puzzle.repo;

import by.maxi.puzzle.model.Person;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;

@SpringBootTest
@SpringJUnitConfig
public class RepositoryTests {

    @Autowired
    PersonRepository personRepository;

    @Test
    public void testPerson() {
        Person person = new Person();
        person.setName("Max");
        person.setDescription("Aging Puzzle creator");
        person.setUrls(Arrays.asList("url1", "url2"));
        personRepository.save(person);

        Person found = personRepository.findById(person.getId()).get();
        Assert.assertEquals("Max", found.getName());
    }
}

