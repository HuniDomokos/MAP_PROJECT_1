package com.example.flightmanagementsystem.repository;

import com.example.flightmanagementsystem.model.Staff;

public class StaffRepository extends InFileRepository<Staff, String> {
    public StaffRepository() {
        super("src/main/resources/data/staff.json", Staff.class, Staff::getId);
    }
}