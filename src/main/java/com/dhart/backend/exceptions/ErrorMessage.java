package com.dhart.backend.exceptions;

import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.Date;

@Data
@ToString
public class ErrorMessage {

    private Integer statusCode;
    private Date timestamp;
    private String message;
    private String description;

    public ErrorMessage() {
        Date date = new Date();
        this.timestamp = new Timestamp(date.getTime());
    }
}
