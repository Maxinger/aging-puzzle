package org.agingpuzzle.web.filter;

import org.agingpuzzle.web.WebUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class LanguageFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String path = ((HttpServletRequest) request).getServletPath();
        request.setAttribute("lang", WebUtils.getLanguageFromUrl(path));

        chain.doFilter(request, response);
    }
}
