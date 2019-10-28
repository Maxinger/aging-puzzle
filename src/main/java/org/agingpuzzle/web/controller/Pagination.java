package org.agingpuzzle.web.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Pagination {

    public static int DEFAULT_ITEMS_PER_PAGE = 10;
    public static String PAGE_PARAMETER = "page";

    private int currentPage;
    private int itemsPerPage;
    private int totalPages;
    private UriComponentsBuilder urlTemplate;

    public Pagination(HttpServletRequest request, int totalItems) {
        this(request, totalItems, DEFAULT_ITEMS_PER_PAGE);
    }

    public Pagination(HttpServletRequest request, int totalItems, int itemsPerPage) {
        this.currentPage = Optional.ofNullable(request.getParameter(PAGE_PARAMETER)).map(Integer::parseInt).orElse(1);
        this.itemsPerPage = itemsPerPage;
        this.totalPages = Math.max(0, totalItems - 1) / itemsPerPage + 1;
        this.urlTemplate = UriComponentsBuilder
                .fromPath(request.getServletPath())
                .query(request.getQueryString())
                .queryParam(PAGE_PARAMETER, 1);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public boolean isFirstPage() {
        return currentPage == 1;
    }

    public boolean isLastPage() {
        return currentPage == totalPages;
    }

    public List<Integer> getPageNumbers() {
        return IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
    }

    public String getUrl(int page) {
        return urlTemplate.replaceQueryParam("page", page).toUriString();
    }

    public Pageable toPageable() {
        return PageRequest.of(currentPage - 1, itemsPerPage);
    }

    public Pageable toPageable(Sort sort) {
        return PageRequest.of(currentPage - 1, itemsPerPage, sort);
    }
}
