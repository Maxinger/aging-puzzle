package org.agingpuzzle.web.filter;

import org.agingpuzzle.web.LanguageUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public abstract class BaseFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        boolean allowed = "/".equals(path) || LanguageUtils.isLanguageUrl(path);

        return !allowed;
    }
}
