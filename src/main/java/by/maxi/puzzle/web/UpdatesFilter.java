package by.maxi.puzzle.web;

import by.maxi.puzzle.service.UpdatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class UpdatesFilter implements Filter {

    @Autowired
    private UpdatesService updatesService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setAttribute("updates", updatesService.getLastUpdates(3));

        chain.doFilter(request, response);
    }
}
