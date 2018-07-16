import spock.lang.Specification

class AppTest extends Specification {
    def "simple test test"(){
        given:
        def map = new HashMap()
        when:
        map.put("key", "value")
        then:
        map.size() == 1
    }
}