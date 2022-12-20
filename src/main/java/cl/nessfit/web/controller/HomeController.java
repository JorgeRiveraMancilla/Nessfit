package cl.nessfit.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    /**
     * Method who control the redirection to the home page.  
     * @return the form from the home page. 
     */
    @GetMapping ({"/home", "/", ""})
    public String home() {
        return "home";
    }
}