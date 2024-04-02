package org.fdifrison.webflux101.dto;

import java.util.Date;

public record Response(Date date, int output) {

    public Response(int output) {
        this(new Date(), output);
    }
}
