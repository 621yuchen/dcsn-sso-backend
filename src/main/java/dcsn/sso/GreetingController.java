package dcsn.sso;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

        private static final String template = "Hello,%s!";
        private final AtomicLong counter = new AtomicLong();

       @GetMapping("/greeting")
        public Greeting greeting() {
            return new Greeting("DSCN","SSO");
        }
    }

class Greeting {
    private final String id;
    private final String content;

    public Greeting(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId(){
        return this.id;
    }

    public String getContent(){
        return this.content;
    }
}





