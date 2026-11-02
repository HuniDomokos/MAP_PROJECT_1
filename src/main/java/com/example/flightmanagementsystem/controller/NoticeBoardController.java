package com.example.flightmanagementsystem.controller;

import com.example.flightmanagementsystem.model.NoticeBoard;
import com.example.flightmanagementsystem.service.NoticeBoardService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/notice-boards")
public class NoticeBoardController {
    private final NoticeBoardService noticeBoardService;

    public NoticeBoardController(NoticeBoardService noticeBoardService) {
        this.noticeBoardService = noticeBoardService;
    }

    public record NoticeBoardRequest(LocalDate date) {}

    @GetMapping
    public List<NoticeBoard> getAll() {
        return noticeBoardService.getAllNoticeBoards();
    }

    @GetMapping("/{id}")
    public NoticeBoard getById(@PathVariable("id") String id) {
        return noticeBoardService.getNoticeBoardById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NoticeBoard create(@RequestBody NoticeBoardRequest request) {
        return noticeBoardService.createNoticeBoard(request.date());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        noticeBoardService.deleteNoticeBoard(id);
    }
}
