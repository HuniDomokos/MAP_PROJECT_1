package com.example.flightmanagementsystem.repository;

import com.example.flightmanagementsystem.model.NoticeBoard;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class NoticeBoardRepository implements CrudRepository<NoticeBoard, String> {

    private final Map<String, NoticeBoard> storage = new HashMap<>();

    @Override
    public NoticeBoard save(NoticeBoard noticeBoard) {
        if (noticeBoard != null && noticeBoard.getId() != null) {
            storage.put(noticeBoard.getId(), noticeBoard);
        }
        return noticeBoard;
    }

    @Override
    public Optional<NoticeBoard> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<NoticeBoard> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public boolean deleteById(String id) {
        return storage.remove(id) != null;
    }
}