package com.satrack.crosshelpers.properties;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Configuration
@ConfigurationProperties("kafka-stream-properties")
@Getter
@Setter
@Validated
public class KafkaStreamProperties {
    @NotNull
    private String vehiclesStatusInboundTopic;

}
