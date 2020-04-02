package com.example.lombokimmutableconfigpropsexample;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

enum MyEnum {
  OLOLO,
  TROLOLO
}

@Value
@ConstructorBinding
@ConfigurationProperties(prefix = "my-props")
class MyProps {
  String ololo;
  MyEnum type;
}

@SpringBootApplication
@RequiredArgsConstructor
@EnableConfigurationProperties(MyProps.class)
public class LombokImmutableConfigPropsExampleApplication {

  private final MyProps myProps;

  @Bean
  RouterFunction<ServerResponse> routes() {
    return RouterFunctions.route()
                          .path("/**",
                                builder -> builder.GET("/", this::fallback)
                                                  .build())
                          .build();
  }

  private Mono<ServerResponse> fallback(ServerRequest serverRequest) {
    var response = Map.of("myProps", this.myProps);
    return ServerResponse.ok().body(Mono.just(response), Map.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(LombokImmutableConfigPropsExampleApplication.class, args);
  }
}
