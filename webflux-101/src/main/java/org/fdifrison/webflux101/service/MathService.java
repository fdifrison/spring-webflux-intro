package org.fdifrison.webflux101.service;

import org.fdifrison.webflux101.dto.Response;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MathService {

    public Response findSquare(int input) {
        return new Response(input * input);
    }

    public List<Response> findMultiplicationTable(int input) {
        return IntStream.rangeClosed(1, 10)
                .peek(i -> SleepUtil.sleepSeconds(1)) // simulating a time-consuming task
                .peek(i -> System.out.println("mathService processing -> " + i))
                .mapToObj(i -> new Response(i * input))
                .collect(Collectors.toList());
    }

}
