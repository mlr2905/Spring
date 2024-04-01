package full.mypostgresql.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    @GetMapping("/page1")
    public String redirectToStaticPage() {
        return "redirect:/page1.html";
    }

    @PostMapping(value ="/post_demo")
    public String createUserPost(@RequestParam(defaultValue = "") String firstname,
                                         @RequestParam(defaultValue = "") String lastname,
                                         @RequestParam(defaultValue = "") String email) {
        // decide where to send the client ...
        return "redirect:/thank_you.html";
    }
}
