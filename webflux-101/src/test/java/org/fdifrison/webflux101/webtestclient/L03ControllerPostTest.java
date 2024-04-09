package org.fdifrison.webflux101.webtestclient;

import org.fdifrison.webflux101.controller.ReactiveMathController;
import org.fdifrison.webflux101.dto.MultiplyRequest;
import org.fdifrison.webflux101.dto.Response;
import org.fdifrison.webflux101.service.ReactiveMathService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(ReactiveMathController.class)
public class L03ControllerPostTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private ReactiveMathService service;

    @Test
    public void postTest() {

        Mockito.when(service.multiply(Mockito.any())).thenReturn(Mono.just(new Response(10)));

        this.client
                .post()
                .uri("/r-math/multiply")
                .accept(MediaType.APPLICATION_JSON)
                .headers(headers -> headers.setBasicAuth("user", "pass"))
                .bodyValue(new MultiplyRequest(2,5))
                .exchange()
                .expectStatus().is2xxSuccessful();


    }

}
