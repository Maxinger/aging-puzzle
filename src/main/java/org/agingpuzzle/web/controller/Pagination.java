package org.agingpuzzle.web.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Pagination {

    private int currentPage;
    private int totalItems;
    private int itemsPerPage;
    private int totalPages;

    public Pagination(int currentPage, int totalItems) {
        this(currentPage, totalItems, 10);
    }

    public Pagination(int currentPage, int totalItems, int itemsPerPage) {
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.itemsPerPage = itemsPerPage;
        this.totalPages = totalItems / itemsPerPage + 1;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public List<Integer> getPageNumbers() {
        return IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
    }
}
