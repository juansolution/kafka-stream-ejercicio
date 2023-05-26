package com.satrack.sourceinfo;


import com.satrack.crosshelpers.properties.KafkaStreamProperties;
import com.satrack.receiveravro.CanonicalEvent;
import com.satrack.receiveravro.VehicleStatus;
import com.satrack.shemaavro.serdes.SatrackGenericSerdes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueStore;
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

    private final SatrackGenericSerdes<VehicleStatus> serdeVehicle;

    @Autowired
    public void Entry(StreamsBuilder streamsBuilder){

        List<String> topics = Arrays.asList(
                properties.getVehiclesStatusInboundTopic()

        );

        streamsBuilder.stream(topics, Consumed.with(STRING_SERDE, getGenericSerdeVehicles()))
                .selectKey((key, value) -> value.getServiceCode())
                .peek((key, event) -> log.info("ReceiverEventSource::EventSource" + key + ", event=" + event.getServiceCode()));


    }

    public Serde<VehicleStatus> getGenericSerdeVehicles(){
        return serdeVehicle.serdes(VehicleStatus.class);
    }

}
