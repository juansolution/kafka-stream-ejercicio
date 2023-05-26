package com.satrack.shemaavro.serdes;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.stereotype.Component;

@Component
public class SatrackGenericSerdes<T> extends Serdes.WrapperSerde<T>{

    public SatrackGenericSerdes() {
        super(new JsonSerializer<>(), new JsonDeserializer<>());
    }

    public Serde<T> serdes(Class<T> tClass) {
        JsonSerializer<T> serializer = new JsonSerializer<>();
        JsonDeserializer<T> deserializer = new JsonDeserializer<>(tClass);
        return Serdes.serdeFrom(serializer, deserializer);
    }
}
