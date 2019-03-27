package by.maxi.puzzle.repo;

import by.maxi.puzzle.TestConfiguration;
import by.maxi.puzzle.model.Person;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = TestConfiguration.class)
@RunWith(SpringRunner.class)
public class RepositoryTests {

    @Autowired
    PersonRepository personRepository;

    @Test
    public void testPerson() {
        Person person = new Person();
        person.setName("Max");
        person.setDescription("Aging Puzzle creator");
        personRepository.save(person);

        Person found = personRepository.findById(person.getId()).get();
        Assert.assertEquals("Max", found.getName());
    }
}

