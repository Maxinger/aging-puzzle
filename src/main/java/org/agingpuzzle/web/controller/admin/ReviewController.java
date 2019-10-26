package org.agingpuzzle.web.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.agingpuzzle.model.Review;
import org.agingpuzzle.repo.*;
import org.agingpuzzle.web.controller.AbstractController;
import org.agingpuzzle.web.controller.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Slf4j
@Controller
@RequestMapping("/{lang}/admin/reviews")
public class ReviewController extends AbstractController {

    @Autowired
    private BaseProjectRepository baseProjectRepository;

    @Autowired
    private BaseOrganizationRepository baseOrganizationRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewSummaryRepository reviewSummaryRepository;

    @GetMapping
    public String listPage(HttpServletRequest request, Model model) {
        int count = (int) reviewSummaryRepository.count();
        var pagination = new Pagination(request, count);
        model.addAttribute("pagination", pagination);

        model.addAttribute("reviews", reviewSummaryRepository.findAll(pagination.toPageable()));
        return "admin/reviews";
    }

    @PostMapping("/save")
    public String saveReview(@PathVariable String lang, @RequestParam Long entityId, @RequestParam String entityType) {
        Review review = new Review();
        if ("Project".equals(entityType)) {
            review.setBaseProject(baseProjectRepository.getOne(entityId));
        } else {
            review.setBaseOrganization(baseOrganizationRepository.getOne(entityId));
        }
        review.setDate(LocalDate.now());
        reviewRepository.save(review);


        log.info("Saved review with id={} for {} with id={}", review.getId(), entityType, entityId);

        return String.format("redirect:/%s/admin/reviews", lang);
    }
}
