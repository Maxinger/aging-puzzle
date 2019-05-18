package org.agingpuzzle.web.filter;

import org.agingpuzzle.service.UpdatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UpdatesFilter extends BaseFilter {

    @Autowired
    private UpdatesService updatesService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        request.setAttribute("updates", updatesService.getLastUpdates(3));

        filterChain.doFilter(request, response);
    }
}
