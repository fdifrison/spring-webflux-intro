package org.fdifrison.webflux101.webtestclient;

import org.assertj.core.api.Assertions;
import org.fdifrison.webflux101.controller.ParamsController;
import org.fdifrison.webflux101.controller.ReactiveMathController;
import org.fdifrison.webflux101.dto.Response;
import org.fdifrison.webflux101.service.ReactiveMathService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@WebFluxTest(controllers = {ReactiveMathController.class, ParamsController.class})
// all we need instead of the heavy @SpringBootTest
public class L02ControllerGetTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private ReactiveMathService mathService;

    @Test
    public void singleResponse() {

        Mockito
                .when(mathService.findSquare(Mockito.anyInt()))
                .thenReturn(Mono.just(new Response(25)));

        this.webClient
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

    @Test
    public void listResponse() {

        Flux<Response> flux = Flux.range(1, 3).map(Response::new);

        Mockito.when(mathService.findMultiplicationTable(Mockito.anyInt()))
                .thenReturn(flux);

        this.webClient
                .get()
                .uri("/r-math/table/{input}", 5)
                .exchange()
                .expectStatus()
                .is2xxSuccessful() // any 2xx is ok
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Response.class)
                .hasSize(3);


    }

    @Test
    public void streamResponse() {

        Flux<Response> flux = Flux.range(1, 3)
                .map(Response::new)
                .delayElements(Duration.ofMillis(100));

        Mockito.when(mathService.findMultiplicationTable(Mockito.anyInt()))
                .thenReturn(flux);

        this.webClient
                .get()
                .uri("/r-math/table/{input}/stream", 5)
                .exchange()
                .expectStatus()
                .is2xxSuccessful() // any 2xx is ok
                .expectHeader()
                .contentType(MediaType.TEXT_EVENT_STREAM_VALUE)
                .expectBodyList(Response.class)
                .hasSize(3);

    }

    @Test
    public void queryParamsMapsTest() {

        Map<String, Integer> params = Map.of(
                "count", 10,
                "page", 20);


        this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/jobs/search")
                        .query("count={count}&page={page}")
                        .build(params))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Integer.class)
                .hasSize(2).contains(10, 20);

    }
}
