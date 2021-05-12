package zblibrary.demo.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sugar{
    private String id;
    private String userId;
    private Date sugarDatetime;
    private Double sugarValue;
    private Integer sugarType;
    private String testTime;


}
