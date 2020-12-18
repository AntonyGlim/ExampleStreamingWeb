package example.streaming.web.server.controllers.rest.v1;

import example.streaming.web.common.model.Item;
import example.streaming.web.server.repository.in.memory.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/*
 *
 * @author a.yatsenko
 * Created at 17.12.2020
 */
@Slf4j
@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/get/{id}")
    public Item getItem(HttpServletRequest req, @PathVariable Integer id) {
        log.info("<-<- from '{}', id = {}", req.getRequestURL(), id);
        return itemRepository.getItemById(id);
    }

    //FIXME асинхронная отдача
    @GetMapping("/get/all")
    public Map<Integer, Item> getAllItems(HttpServletRequest req) {
        log.info("<-<- from '{}'", req.getRequestURL());
        return itemRepository.getAllItems();
    }

}
