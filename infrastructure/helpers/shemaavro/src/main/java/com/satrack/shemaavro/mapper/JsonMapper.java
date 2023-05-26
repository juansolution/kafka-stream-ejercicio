package com.satrack.shemaavro.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonMapper {

    private static final com.fasterxml.jackson.databind.json.JsonMapper objectMapper = newObjectMapper();//new ObjectMapper();

    public static com.fasterxml.jackson.databind.json.JsonMapper newObjectMapper() {
        return com.fasterxml.jackson.databind.json.JsonMapper.builder()
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false)
                .findAndAddModules()
                .addMixIn(org.apache.avro.specific.SpecificRecord.class, // Interface implemented by all generated Avro-Classes
                        JacksonIgnoreAvroPropertiesMixIn.class)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .build();
    }


    /**
     * Map the given JSON String to the required class type.
     */
    public static <T> T readFromJson(String json, Class<T> clazz) throws MappingException {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new MappingException(e);
        }
    }

    /**
     * Map the given Object to a JSON String.
     */
    public static String writeToJson(Object obj) throws MappingException {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new MappingException(e);
        }
    }
}
