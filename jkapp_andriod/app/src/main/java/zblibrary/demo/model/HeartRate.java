package zblibrary.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author tj
 * @since 2021-04-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeartRate implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String userId;
    private Integer beatTimes;
    private Integer heartRate;
    private Integer heartType;
    private Date testTime;
}
