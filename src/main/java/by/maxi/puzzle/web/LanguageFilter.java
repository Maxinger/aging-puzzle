package by.maxi.puzzle.web;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Pattern;

@Component
public class LanguageFilter implements Filter {

    private static final Pattern PATTERN = Pattern.compile("^/([a-z]{2})/");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String path = ((HttpServletRequest) request).getServletPath();

        var matcher = PATTERN.matcher(path);
        String lang = matcher.find() ? matcher.group(1) : WebApplication.SUPPORTED_LANGUAGES[0];
        request.setAttribute("lang", lang);

        chain.doFilter(request, response);
    }
}
