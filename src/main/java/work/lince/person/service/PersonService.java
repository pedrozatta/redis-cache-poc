package work.lince.person.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import work.lince.commons.authentication.AuthenticationService;
import work.lince.commons.exception.NotFoundException;
import work.lince.person.model.Person;
import work.lince.person.model.PersonStatus;
import work.lince.person.repository.PersonRepository;

import java.util.List;

@Slf4j
@Service
public class PersonService {

    public static final String CACHE_NAME = "Person";

    @Autowired
    protected PersonRepository repository;

    @Autowired
    protected AuthenticationService authenticationService;

    public Person create(Person person) {
        person.setOwner(authenticationService.getAuthenticatedUser());
        person.setStatus(PersonStatus.ACTIVE);
        return repository.save(person);
    }
    
    public List<Person> findAll() {
        return repository.findAll();
    }


    @Cacheable(cacheNames = CACHE_NAME, key="#id")
    public Person findById(Long id) {
        log.info("findById {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException());
    }

    @CacheEvict(cacheNames = CACHE_NAME, key="#id")
    public void remove(Long id) {
        log.info("remove {}", id);
        Person person = repository.findById(id)
                .orElseThrow(() -> new NotFoundException());
        repository.delete(person);
    }

}