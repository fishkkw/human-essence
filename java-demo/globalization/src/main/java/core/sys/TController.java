package core.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@RestController
@RequestMapping("/v1")
public class TController {

    @Autowired
    private I18Config i18Config;

    @RequestMapping(value = "/testGlob", method = RequestMethod.GET)
    public Object testGlob() {
        //LocaleResolver
        return i18Config.getMessage("login.username",Locale.CHINA);
    }
}