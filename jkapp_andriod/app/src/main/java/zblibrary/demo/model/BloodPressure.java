package zblibrary.demo.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class BloodPressure implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String userId;
    private Integer heightPressure;
    private Integer lowPressure;
    private Integer pressureType;
    private String testTime;
}
