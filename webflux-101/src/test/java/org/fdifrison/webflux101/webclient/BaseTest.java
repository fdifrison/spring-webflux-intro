package org.fdifrison.webflux101.webclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

// Start the application on the same port each time we run test
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class BaseTest {

    @Autowired
    protected WebClient webClient;


}
