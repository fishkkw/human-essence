package core.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class I18Config {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String s){
        return this.getMessage(s,null,null);
    }

    public String getMessage(String s, Locale Locale){
        return this.getMessage(s,null,Locale);
    }

    public String getMessage(String s, Object[] objects, Locale Locale){
        return messageSource.getMessage(s,objects,Locale);
    }
}
