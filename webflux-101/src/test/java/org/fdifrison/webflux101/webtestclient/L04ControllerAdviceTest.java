package org.fdifrison.webflux101.webtestclient;

import org.fdifrison.webflux101.controller.ReactiveMathValidationController;
import org.fdifrison.webflux101.dto.Response;
import org.fdifrison.webflux101.service.ReactiveMathService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(ReactiveMathValidationController.class)
public class L04ControllerAdviceTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private ReactiveMathService service;

    @Test
    public void errorHandling() {

        Mockito.when(service.findSquare(Mockito.anyInt()))
                .thenReturn(Mono.just(new Response(1)));

        // we want to simulate the error in the controller layer
        this.client
                .get()
                .uri("/r-math/square/{input}/throw", 5) // should be in range 10-20
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.msg").isEqualTo("allowed range is 10- 20")
                .jsonPath("$.errorCode").isEqualTo("100");



    }


}
