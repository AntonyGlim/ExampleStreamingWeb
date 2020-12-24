package example.streaming.web.server.repository.in.memory;

import example.streaming.web.common.model.Itemiarium;
import example.streaming.web.common.model.Item;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/*
 *
 * @author a.yatsenko
 * Created at 17.12.2020
 */
@Data
@Repository
public class ItemRepository {

    private Itemiarium itemiarium = fillItemiarium();

    private Itemiarium fillItemiarium() {
        Itemiarium itemiarium = new Itemiarium();
        for (int i = 0; i < 200; i++) {
            itemiarium.getItems().put(i, new Item(i,UUID.randomUUID().toString()/*, new Date(System.currentTimeMillis())*/));
        }
        return itemiarium;
    }

    public Item getItemById(Integer id) {
        return itemiarium.getItems().get(id);
    }

    public Map<Integer, Item> getAllItems() {
        return itemiarium.getItems();
    }
}
