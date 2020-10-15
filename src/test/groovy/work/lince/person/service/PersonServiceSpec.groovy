package work.lince.person.service

import spock.lang.Specification
import spock.lang.Unroll
import work.lince.commons.authentication.AuthenticationService
import work.lince.person.model.Person
import work.lince.person.model.PersonStatus
import work.lince.person.repository.PersonRepository
/**
 * @author pzatta
 */

class PersonServiceSpec extends Specification {

    PersonService service;

    def setup() {
        service = Spy(PersonService)
        service.repository = Mock(PersonRepository)
        service.authenticationService = Mock(AuthenticationService)

    }

    @Unroll
    def "verify with #name"() {
        given:
            1 * service.repository.save(_) >> { Person value ->
                value.id = id
                return value
            }
            1 * service.authenticationService.getAuthenticatedUser() >> { user }
            def person = new Person(
                name: name,
                status: status,
                owner: owner
            )
        when:
            def result = service.create(person)

        then:
            result != null
            result.id == id
            result.name == name
            result.owner == user
            result.status == PersonStatus.ACTIVE

        where:
            name       | status                | owner      | user   | id
            "Nome 1"   | null                  | "asdfasdf" | "asdf" | 1L
            "Nome 2"   | PersonStatus.INACTIVE | null       | "qwer" | 2L
            "Nome 3"   | null                  | null       | "asdf" | 3L
            "Nome 4"   | PersonStatus.INACTIVE | "asdfasdf" | "qwer" | 4L


    }

}