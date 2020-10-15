package work.lince.person

import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll
import work.lince.person.model.Person
import work.lince.person.model.PersonStatus
import work.lince.person.repository.PersonRepository
/**
 * @author izatta
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonFunctionalSpec extends Specification {

    @Shared
    RESTClient client

    @LocalServerPort
    int port;

    @Autowired
    PersonRepository personRepository

    def setup() {
        client = new RESTClient("http://localhost:${port}/")
        client.contentType = ContentType.JSON
    }

    @Unroll
    def "get Success"() {
        given:
            personRepository.save(new Person(name: name))

        when:
            def result = client.get(path: "people")

        then:
            result != null

        where:
            name     | _
            "Nome 1" | _

    }


    @Unroll
    def "Create Person #name"() {
        given:
            def body = [
                name: name,
                status : status
            ]


        when:
            def result = client.post(path: "people", body: body, headers: ["lince.user.name": userName])

        then:
            result != null
            result.data.id != null
            result.data.name == name
            result.data.status == PersonStatus.ACTIVE.toString()
            result.data.owner == expectedOwner

        where:
            name     | userName   | status                || expectedOwner
            "Nome 1" | null       | null                  || 'anonymous'
            "Nome 2" | 'x1324'    | PersonStatus.ACTIVE   || 'x1324'
            "Nome 3" | 'zxcvasdf' | PersonStatus.INACTIVE || 'zxcvasdf'


    }

}