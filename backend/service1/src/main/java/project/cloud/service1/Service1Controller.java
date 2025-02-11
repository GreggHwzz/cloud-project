package project.cloud.service1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Service1Controller {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World from Service 1!";
    }
}
