package com.simontoolmanagement.service;

import com.simontoolmanagement.model.RentalAgreement;
import com.simontoolmanagement.model.Tool;

import java.time.LocalDate;

public interface ToolService {

    // TODO: Remove Customer Name
    RentalAgreement rentTool(Tool tool, int rentalDays, LocalDate checkoutDate, int discountPercent);


}
