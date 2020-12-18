package example.streaming.web.server.controllers.rest.v1;

import example.streaming.web.common.model.Item;
import example.streaming.web.server.services.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/*
 *
 * @author a.yatsenko
 * Created at 17.12.2020
 */
@Slf4j
@RestController
@RequestMapping("/item/stream")
public class ItemStreamController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/get/all")
    public ResponseEntity<StreamingResponseBody> getAllItems(HttpServletRequest req, final HttpServletResponse resp) {
        log.info("<-<- from '{}'", req.getRequestURL());

        resp.setContentType("application/text");

        final Map<Integer, Item> items = itemService.getAllItems();

        StreamingResponseBody stream = out -> {
            try {
                PrintWriter printWriter = new PrintWriter(resp.getOutputStream());
                for (Item item : items.values()) {
                    Thread.sleep(100L);
                    printWriter.println(item.toString());
                }
                printWriter.close();

            } catch (final IOException | InterruptedException e) {
                log.error("Exception while reading and streaming data, message : '{}' ", e.getMessage());
            }
        };
        log.info("steaming response {} ", stream);
        return new ResponseEntity(stream, HttpStatus.OK);
    }

}
