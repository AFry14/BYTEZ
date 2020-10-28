package com.VB2.BYTEZ_Backend.Controllers;

import com.VB2.BYTEZ_Backend.Domain.Report;
import com.VB2.BYTEZ_Backend.Domain.User;
import com.VB2.BYTEZ_Backend.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import java.util.List;

@Controller
@RequestMapping(path = "/report")
public class ReportController
{
    @Autowired
    private ReportService reportService;

    @GetMapping(path = "/")
    public @ResponseBody List<Report> getAllReports()
    {
        return reportService.getAllReports();
    }

    @GetMapping(path = "/{reportId}")
    public @ResponseBody Report getReport(@PathVariable("reportId") Long id)
    {
        return reportService.getReport(id);
    }

    @GetMapping(path = "/author/{reportId}")
    public @ResponseBody User getReportAuthor(@PathVariable("reportId")Long id)
    {
        return reportService.getAuthor(id);
    }

    @PostMapping(path = "/add/{userId}")
    public @ResponseBody Report addReport(@PathVariable("userId")Long id, @RequestBody Report report)
    {
        return reportService.addReport(id, report);
    }

    @DeleteMapping(path = "/delete/{reportId}")
    public @ResponseBody String removeReport(@PathVariable("reportId")Long id)
    {
        return reportService.removeReport(id);
    }
}
