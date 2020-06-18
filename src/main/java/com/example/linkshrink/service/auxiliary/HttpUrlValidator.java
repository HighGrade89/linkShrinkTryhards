package com.example.linkshrink.service.auxiliary;

import com.example.linkshrink.service.interfaces.WeblinkValidator;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;

@Component
public class HttpUrlValidator implements WeblinkValidator {
    private final String[] schemes = {"http","https"};
    private UrlValidator urlValidator;

    public HttpUrlValidator() {
        this.urlValidator = new UrlValidator(schemes);
    }

    @Override
    public boolean isValid(String url) {
        return urlValidator.isValid(url);
    }
}
