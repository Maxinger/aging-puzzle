package org.agingpuzzle.repo;

import org.agingpuzzle.model.*;
import org.agingpuzzle.TestConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = TestConfiguration.class)
@RunWith(SpringRunner.class)
public class RepositoryTests {

    @Autowired
    BasePersonRepository basePersonRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    BaseOrganizationRepository baseOrganizationRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BaseUpdateRepository baseUpdateRepository;

    @Autowired
    UpdateRepository updateRepository;

    @Test
    public void createUpdates() {
        IntStream.rangeClosed(1, 40).forEach(i -> {
            BaseUpdate baseUpdate = new BaseUpdate();
            baseUpdate.setDate(LocalDate.now());
            baseUpdateRepository.save(baseUpdate);

            Update update = new Update();
            update.setBaseEntity(baseUpdate);
            update.setTitle("Title " + i);
            update.setPreview("Preview " + i);
            update.setFullText("Long long full description " + i);
            update.setLanguage("en");
            updateRepository.save(update);
        });
    }

    @Test
    public void testPerson() {
        Person person = new Person();
        person.setName("Max");
        person.setDescription("Puzzle of Aging creator");
        personRepository.save(person);

        Person found = personRepository.findById(person.getId()).get();
        assertEquals("Max", found.getName());

        personRepository.delete(found);
    }

    @Test
    public void testMembership() {
        BaseOrganization baseOrganization = baseOrganizationRepository.save(new BaseOrganization());

        Organization organization = new Organization();
        organization.setBaseEntity(baseOrganization);
        organization.setLanguage("en");
        organization.setName("Org");
        organization.setDescription("Organization description");
        organizationRepository.save(organization);

        BasePerson basePerson = basePersonRepository.save(new BasePerson());

        Person person1 = new Person();
        person1.setBaseEntity(basePerson);
        person1.setLanguage("en");
        person1.setName("Max");
        person1.setDescription("Puzzle of Aging creator");
        personRepository.save(person1);

        Person person2 = new Person();
        person2.setBaseEntity(basePerson);
        person2.setLanguage("ru");
        person2.setName("Макс");
        person2.setDescription("Создатель Puzzle of Aging");
        personRepository.save(person2);


        Member member = new Member();
        member.setBaseOrganization(baseOrganization);
        member.setBasePerson(basePerson);
        member.setRole("founder");
        memberRepository.save(member);

        var members = memberRepository.findPersonsByOrganization(baseOrganization.getId(), "en");
        assertEquals(1, members.size());
        assertEquals("Max", members.get(0).getEntity().getName());
        assertEquals("founder", members.get(0).getRole());

        memberRepository.delete(member);

        organizationRepository.delete(organization);
        baseOrganizationRepository.delete(baseOrganization);

        personRepository.delete(person1);
        personRepository.delete(person2);
        basePersonRepository.delete(basePerson);
    }
}

