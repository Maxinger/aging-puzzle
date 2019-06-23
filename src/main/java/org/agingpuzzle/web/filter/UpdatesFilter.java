package org.agingpuzzle.web.filter;

import org.agingpuzzle.repo.UpdateRepository;
import org.agingpuzzle.web.LanguageUtils;
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
    private UpdateRepository updateRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        LanguageUtils.getLanguageFromUrl(request.getServletPath()).ifPresent(lang -> {
            request.setAttribute("updates", updateRepository.findAllByLanguage(lang, updateRepository.page(0, 3)));
        });
        filterChain.doFilter(request, response);
    }
}
