package org.fdifrison.webflux101.webtestclient;

import org.assertj.core.api.Assertions;
import org.fdifrison.webflux101.dto.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
@AutoConfigureWebTestClient
public class L01SimpleWebTestClientTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void test() {

        Flux<Response> response = this.webTestClient
                .get()
                .uri("/r-math/square/{input}", 5)
                .exchange()
                .expectStatus()
                .is2xxSuccessful() // any 2xx is ok
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .returnResult(Response.class)
                .getResponseBody();


        StepVerifier.create(response)
                .expectNextMatches(i -> i.output() == 25)
                .verifyComplete();

    }

    @Test
    public void fluentAssertionTest() {

        this.webTestClient
                .get()
                .uri("/r-math/square/{input}", 5)
                .exchange()
                .expectStatus()
                .is2xxSuccessful() // any 2xx is ok
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody(Response.class)
                .value(response -> {
                    Assertions.assertThat(response.output()).isEqualTo(25);
                });


    }
}
