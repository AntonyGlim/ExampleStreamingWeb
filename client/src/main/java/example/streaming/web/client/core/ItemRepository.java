package example.streaming.web.client.core;

import example.streaming.web.common.model.Item;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
 *
 * @author a.yatsenko
 * Created at 18.12.2020
 */
@Data
public class ItemRepository {
    private Map<Integer, Item> items = new ConcurrentHashMap<>();
}
