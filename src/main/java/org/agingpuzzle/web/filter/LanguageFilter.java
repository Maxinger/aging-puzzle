package org.agingpuzzle.web.filter;

import org.agingpuzzle.web.WebUtils;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class LanguageFilter extends BaseFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();
        String query = request.getQueryString();

        Optional<String> langOpt = WebUtils.getLanguageFromUrl(path);
        langOpt.ifPresent(s -> request.getSession().setAttribute("lang", s));

        String lang = langOpt.orElse(WebUtils.SUPPORTED_LANGUAGES[0]);
        request.setAttribute("lang", lang);

        request.setAttribute("switchLinks", Arrays.stream(WebUtils.SUPPORTED_LANGUAGES)
                .filter(s -> !lang.equals(s))
                .collect(Collectors.toMap(
                        Function.identity(),
                        s -> WebUtils.replaceLanguage(path, s) + (query != null ? "?" + query : ""))));

        filterChain.doFilter(request, response);
    }
}
