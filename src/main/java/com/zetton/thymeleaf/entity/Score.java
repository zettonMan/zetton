package com.zetton.thymeleaf.entity;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class Score {
    private Integer id;
    private Integer studentId;
    @Max(value = 100)
    @Min(value = 0)
    private double score;
    private String subject;
}
