package org.agingpuzzle.web.filter;

import org.agingpuzzle.web.WebUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseFilter extends OncePerRequestFilter {

    private static List<String> ALLOWED_URLS = Arrays.stream(WebUtils.SUPPORTED_LANGUAGES)
            .map(s -> "/" + s)
            .collect(Collectors.toList());

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        boolean allowed = "/".equals(path) || ALLOWED_URLS.stream().anyMatch(s -> request.getServletPath().startsWith(s));

        return !allowed;
    }
}
