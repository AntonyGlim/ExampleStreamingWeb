package glim.antony.example.streaming.web.controllers.rest.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/*
 *
 * @author a.yatsenko
 * Created at 17.12.2020
 */
@Slf4j
@RestController
@RequestMapping("/")
public class TestController {
    @GetMapping("/test")
    public String testEndpoint(HttpServletRequest req) {
        log.info("<-<- from '{}'", req.getRequestURL());
        return "successful test connection";
    }
}
