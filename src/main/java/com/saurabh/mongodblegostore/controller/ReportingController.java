package com.saurabh.mongodblegostore.controller;

import com.saurabh.mongodblegostore.model.projections.AvgRatingModel;
import com.saurabh.mongodblegostore.persistance.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/legoStore/reports")
public class ReportingController {
    private ReportService reportService;

    public ReportingController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/avgRatingsReport")
    public List<AvgRatingModel> avgRatingReport() {
        return this.reportService.getAvgRatingReport();
    }
}
