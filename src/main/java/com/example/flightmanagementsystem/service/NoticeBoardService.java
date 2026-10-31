package com.example.flightmanagementsystem.service;

import com.example.flightmanagementsystem.model.NoticeBoard;
import java.time.LocalDate;
import java.util.List;

public interface NoticeBoardService {
    NoticeBoard createNoticeBoard(LocalDate date);
    List<NoticeBoard> getAllNoticeBoards();
    NoticeBoard getNoticeBoardById(String id);
    void deleteNoticeBoard(String id);
}
