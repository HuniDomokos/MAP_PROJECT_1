package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.model.*;
import com.example.flightmanagementsystem.repository.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.time.*;

@Service
public class NoticeBoardService {
    private final CrudRepository<NoticeBoard, String> noticeBoardRepository;

    public NoticeBoardService(CrudRepository<NoticeBoard, String> noticeBoardRepository) {
        this.noticeBoardRepository = noticeBoardRepository;
    }

    public NoticeBoard createNoticeBoard(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date must not be null");
        }
        NoticeBoard board = new NoticeBoard(UUID.randomUUID().toString(), date);
        return noticeBoardRepository.save(board);
    }

    public List<NoticeBoard> getAllNoticeBoards() {
        return noticeBoardRepository.findAll();
    }

    public NoticeBoard getNoticeBoardById(String id) {
        return noticeBoardRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Notice board not found: " + id));
    }

    public void deleteNoticeBoard(String id) {
        NoticeBoard board = getNoticeBoardById(id);
        if (!board.getFlightsOfTheDay().isEmpty()) {
            throw new IllegalStateException("Notice board still has flights");
        }
        noticeBoardRepository.deleteById(id);
    }
}
