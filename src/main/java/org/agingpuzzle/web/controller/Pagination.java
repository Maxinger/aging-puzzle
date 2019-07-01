package org.agingpuzzle.web.controller;

import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Pagination {

    public static int DEFAULT_ITEMS_PER_PAGE = 10;

    private int currentPage;
    private int itemsPerPage;
    private int totalPages;
    private UriComponentsBuilder urlTemplate;

    public Pagination(HttpServletRequest request, int currentPage, int totalItems) {
        this(request, currentPage, totalItems, DEFAULT_ITEMS_PER_PAGE);
    }

    public Pagination(HttpServletRequest request, int currentPage, int totalItems, int itemsPerPage) {
        this.currentPage = currentPage;
        this.itemsPerPage = itemsPerPage;
        this.totalPages = Math.max(0, totalItems - 1) / itemsPerPage + 1;
        this.urlTemplate = UriComponentsBuilder
                .fromPath(request.getServletPath())
                .query(request.getQueryString())
                .queryParam("page", 1);
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
}
