package example.streaming.web.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/*
 *
 * @author a.yatsenko
 * Created at 17.12.2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private Integer id;

    private String uuid;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss.SSSX")
    private Date time;
}
