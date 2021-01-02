package net.example.springcloud.provider.web;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoController {
    @GetMapping(value = "/provider/echo/{string}")
    public String echo(@PathVariable String string) {
        return "this value form provider -> " + string;
    }
}
