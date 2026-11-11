package com.example.flightmanagementsystem.repository;

import com.example.flightmanagementsystem.model.NoticeBoard;

public class NoticeBoardRepository extends InFileRepository<NoticeBoard, String> {
    public NoticeBoardRepository() {
        super("src/main/resources/data/noticeboards.json", NoticeBoard.class, NoticeBoard::getId);
    }
}