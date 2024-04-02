package org.fdifrison.webflux101.dto;

public record InputFailedValidationResponse(int errorCode, int input, String msg) {

}
