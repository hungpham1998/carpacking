package com.carparking.core_exception.config;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class CustomLocalResolver extends AcceptHeaderLocaleResolver {

  private static final List<Locale> SUPPORTED_LOCALES =
      Collections.unmodifiableList(
          new ArrayList<Locale>() {
            {
              add(new Locale("en"));
              add(new Locale("vi", "VN"));
            }
          }
      );

  @Override
  public Locale resolveLocale(HttpServletRequest request) {
    String acceptLanguage = request.getHeader("Accept-Language");
    if (!StringUtils.hasText(acceptLanguage)) {
      return this.getDefaultLocale();
    }

    List<Locale.LanguageRange> list =
        Locale.LanguageRange.parse(acceptLanguage);
    Locale locale = Locale.lookup(list, SUPPORTED_LOCALES);

    return Objects.isNull(locale) ? this.getDefaultLocale() : locale;
  }
}
