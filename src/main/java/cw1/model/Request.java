package cw1.model;

import cw1.exception.InvalidDataException;
import lombok.Data;

import java.util.List;

@Data
public class Request {
    private String attribute;
    private String operator;
    private String value;


    public static void validateRequestList(List<Request> requestList) throws InvalidDataException {
        if (requestList == null) {
            throw new InvalidDataException("RequestList cannot be null");
        }

        for (Request request : requestList) {
            validateRequest(request);
        }

    }

    private static void validateRequest(Request request) {
        if (request == null) {
            throw new InvalidDataException("Request cannot be null");
        }
        if (request.getAttribute() == null || request.getAttribute().isBlank()) {
            throw new InvalidDataException("Attribute cannot be null/empty");
        }
        if (request.getOperator() == null || request.getOperator().isBlank()) {
            throw new InvalidDataException("Operator cannot be null/empty");
        }
        if (request.getValue() == null || request.getValue().isBlank()) {
            throw new InvalidDataException("Value cannot be null/empty");
        }

        String op = request.getOperator().trim();
        if (!List.of("=", "!=", "<", ">").contains(op)) {
            throw new InvalidDataException("Invalid operator: " + op);
        }
    }
}
