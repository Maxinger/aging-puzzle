package org.agingpuzzle.web.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Pagination {

    private int currentPage;
    private int totalItems;
    private int itemsPerPage;
    private int totalPages;
    private String urlTemplate;

    public Pagination(String baseUrl, int currentPage, int totalItems) {
        this(baseUrl, currentPage, totalItems, 10);
    }

    public Pagination(String baseUrl, int currentPage, int totalItems, int itemsPerPage) {
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.itemsPerPage = itemsPerPage;
        this.totalPages = totalItems / itemsPerPage + 1;
        this.urlTemplate = baseUrl + (baseUrl.indexOf("?") < 0 ? "?" : "&") + "page=%d";
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
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
        return String.format(urlTemplate, page);
    }
}
