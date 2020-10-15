package work.lince.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import work.lince.person.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}

