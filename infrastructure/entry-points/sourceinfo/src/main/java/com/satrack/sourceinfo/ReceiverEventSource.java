package com.satrack.sourceinfo;


import com.satrack.crosshelpers.properties.KafkaStreamProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReceiverEventSource {

    private static final Serde<String> STRING_SERDE = Serdes.String();
    private final KafkaStreamProperties properties;

    @Autowired
    public void Entry(StreamsBuilder streamsBuilder){

        List<String> topics = Arrays.asList(
                properties.getVehiclesStatusInboundTopic()

        );

        streamsBuilder.stream(topics, Consumed.with(STRING_SERDE, STRING_SERDE))
                .peek((key, event) -> log.info("ReceiverEventSource::EventSource" + key + ", event=" + event));


    }

}
