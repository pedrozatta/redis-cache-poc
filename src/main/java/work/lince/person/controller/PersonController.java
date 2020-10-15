package work.lince.person.controller;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import work.lince.person.model.Person;
import work.lince.person.service.PersonService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/people")
public class PersonController {

    @Autowired
    protected PersonService service;

    @Timed("lince.person.create")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person create(@RequestBody @Validated Person body) {
        return service.create(body);
    }

    @Timed("lince.person.find-all")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Person> findAll() {
        return service.findAll();
    }

    @Timed("lince.person.find-by-id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Person findById(@PathVariable("id") final Long id) {
        log.info("findById {}", id);
        return service.findById(id);
    }

    @Timed("lince.person.remove-by-id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable("id") final Long id) {
        service.remove(id);
    }

}