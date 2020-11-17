package com.VB2.BYTEZ_Backend.Service;

import com.VB2.BYTEZ_Backend.Domain.Report;
import com.VB2.BYTEZ_Backend.Domain.User;
import com.VB2.BYTEZ_Backend.Repositories.ReportRepository;
import com.VB2.BYTEZ_Backend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService
{
    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Report> getAllReports()
    {
        return reportRepository.findAll();
    }

    public Report getReport(Long id)
    {
        return reportRepository.findById(id).isPresent() ? reportRepository.findById(id).get() : null;
    }

    public User getAuthor(Long id)
    {
        Long userId = reportRepository.findById(id).get().getAuthor().getId();
        return userRepository.findById(userId).isPresent() ? userRepository.findById(userId).get() : null;
    }

    public Report addReport(Long id, Report report)
    {
        return userRepository.findById(id)
                .map(user -> {
                    report.setAuthor(user);
                    return reportRepository.save(report);
                })
                .orElse(null);
    }

    public String removeReport(Long id)
    {
        reportRepository.deleteById(id);
        return "{\"Status\":\"Success\"}";
    }
}
