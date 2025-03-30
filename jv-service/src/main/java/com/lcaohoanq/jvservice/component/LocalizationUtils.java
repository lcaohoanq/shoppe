package com.lcaohoanq.jvservice.component;

import com.lcaohoanq.jvservice.util.WebUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

@Component
@RequiredArgsConstructor
public class LocalizationUtils {

    private final MessageSource messageSource;
    private final LocaleResolver customLocaleResolver;

    public String getLocalizedMessage(String messageKey, Object ...params){
        HttpServletRequest request = WebUtil.getCurrentRequest();
        Locale locale = customLocaleResolver.resolveLocale(request);
        return messageSource.getMessage(messageKey, params, locale);
    }

}
