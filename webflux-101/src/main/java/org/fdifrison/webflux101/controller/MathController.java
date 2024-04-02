package org.fdifrison.webflux101.controller;

import org.fdifrison.webflux101.dto.Response;
import org.fdifrison.webflux101.service.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("math")
public class MathController {

    private final MathService service;

    @Autowired
    public MathController(MathService service) {
        this.service = service;
    }

    @GetMapping("/square/{input}")
    public Response findSquare(@PathVariable int input) {
        return this.service.findSquare(input);
    }

    @GetMapping("/table/{input}")
    public List<Response> findMultiplicationTable(@PathVariable int input) {
        return this.service.findMultiplicationTable(input);
    }

}
