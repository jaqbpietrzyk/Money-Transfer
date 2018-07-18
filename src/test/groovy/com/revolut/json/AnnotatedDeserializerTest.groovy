package com.revolut.json

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParseException
import com.revolut.dto.TransferDto
import spock.lang.Specification

class AnnotatedDeserializerTest extends Specification {

    Gson underTest

    class SimpleDto {
        @JsonRequired
        private final String s1
        private final String s2

        SimpleDto(String s1, String s2) {
            this.s1 = s1
            this.s2 = s2
        }
    }

    def setup() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(SimpleDto.class, new AnnotatedDeserializer<TransferDto>());
        underTest = gsonBuilder.create();
    }

    def "test missing required field"() {
        given:
            def simpleJson = "{\"s2\" : \"testt\"}"

        when:
            underTest.fromJson(simpleJson, SimpleDto.class)

        then:
            thrown(JsonParseException.class)

    }

    def "test object with required field only"() {
        given:
            def simpleJson = "{\"s1\" : \"test\"}"

        when:
            def simpleDto = underTest.fromJson(simpleJson, SimpleDto.class)

        then:
            simpleDto.s1 == "test"
            simpleDto.s2 == null

    }

    def "test object with all fields "() {
        given:
            def simpleJson = "{\"s1\" : \"test1\",\"s2\" : \"test2\"}"

        when:
            def simpleDto = underTest.fromJson(simpleJson, SimpleDto.class)

        then:
            simpleDto.s1 == "test1"
            simpleDto.s2 == "test2"

    }
}
