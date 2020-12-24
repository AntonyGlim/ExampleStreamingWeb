package example.streaming.web.server.services;

import example.streaming.web.common.model.Item;
import example.streaming.web.server.repository.in.memory.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/*
 *
 * @author a.yatsenko
 * Created at 17.12.2020
 */
@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public Item getItemById(Integer id) {
        return itemRepository.getItemById(id);
    }

    public Map<Integer, Item> getAllItems() {
        return itemRepository.getAllItems();
    }
}
