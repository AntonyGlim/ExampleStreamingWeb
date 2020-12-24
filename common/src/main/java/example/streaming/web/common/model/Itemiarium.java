package example.streaming.web.common.model;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
 *
 * @author a.yatsenko
 * Created at 17.12.2020
 */
@Data
public class Itemiarium {
    private Map<Integer, Item> items = new ConcurrentHashMap<>();
}
