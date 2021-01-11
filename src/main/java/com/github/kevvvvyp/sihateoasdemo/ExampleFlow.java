package com.github.kevvvvyp.sihateoasdemo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.http.dsl.Http;

@Slf4j
@Configuration
public class ExampleFlow {

    @Bean
    public IntegrationFlow myUserFlow() {
        return IntegrationFlows
                .from(Http.inboundGateway("/user")
                        .requestMapping(r -> r.methods(HttpMethod.GET))
                        .get()
                )
                .handle((payload, headers) -> new MyUser("Joe Blogs")
                        .add(Link.of("http://localhost:8080/account", LinkRelation.of("account"))))
                .get();
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @AllArgsConstructor
    static public class MyUser extends RepresentationModel<MyUser> {
        String name;
    }
}
