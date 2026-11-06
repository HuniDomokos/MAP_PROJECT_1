package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.service.NoticeBoardService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/notice-boards")
public class NoticeBoardController {
    private final NoticeBoardService noticeBoardService;

    public NoticeBoardController(NoticeBoardService noticeBoardService) {
        this.noticeBoardService = noticeBoardService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("noticeBoards", noticeBoardService.getAllNoticeBoards());
        return "noticeboard/index";
    }

    @GetMapping("/new")
    public String form() {
        return "noticeboard/form";
    }

    @PostMapping
    public String create(@RequestParam("date")
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        noticeBoardService.createNoticeBoard(date);
        return "redirect:/notice-boards";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") String id) {
        noticeBoardService.deleteNoticeBoard(id);
        return "redirect:/notice-boards";
    }
}
