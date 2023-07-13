package com.simontoolmanagement.model;

public record Tool(String toolCode, String toolType, String toolBrand,
                   double dailyCharge, boolean weekdayCharge, boolean weekendCharge, boolean holidayCharge) {}
