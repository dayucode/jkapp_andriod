package zblibrary.demo.model;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Obesity{
    private String id;
    private String userId;
    private Date obesityDatetime;
    private Double obesityValue;
    private Double bmi;
    private Integer obesityType;
    private String testTime;


}
