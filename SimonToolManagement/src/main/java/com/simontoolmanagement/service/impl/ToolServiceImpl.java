package com.simontoolmanagement.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import com.simontoolmanagement.model.RentalAgreement;
import com.simontoolmanagement.model.Tool;
import com.simontoolmanagement.service.ToolService;

@Service
public class ToolServiceImpl implements ToolService {

    private final List<DayOfWeek> WEEKEND_DAYS = Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    @Override
    public RentalAgreement rentTool(final Tool tool, final int rentalDays, final LocalDate checkoutDate, final int discountPercent) {

        if (discountPercent > 100 || discountPercent < 0) {
            throw new IllegalArgumentException("Discount must be between 0 and 100");
        }
        if (rentalDays < 1) {
            throw new IllegalArgumentException("Rental days must be greater than 0");
        }

        int billableDays = this.calculateBillableDays(checkoutDate, rentalDays, tool.weekdayCharge(), tool.weekendCharge(), tool.holidayCharge());
        double preDiscountCharge = tool.dailyCharge() * billableDays;
        double discountAmount = preDiscountCharge * (discountPercent / 100.0);
        double finalCharge = preDiscountCharge - discountAmount;

        return new RentalAgreement(tool.toolCode(), tool.toolType(), tool.toolBrand(), rentalDays, checkoutDate, checkoutDate.plusDays(rentalDays),
                tool.dailyCharge(), billableDays, preDiscountCharge, discountPercent, discountAmount, finalCharge);
    }

    public int calculateBillableDays(final LocalDate startDate, final int numberOfDays,
            final boolean chargeWeekdays, final boolean chargeWeekends, final boolean chargeHolidays) {
        int billableDays = 0;
        LocalDate currentDate;



        for (int i = 0; i < numberOfDays; i++) {
            currentDate = startDate.plusDays(i);

            if (this.isHoliday(currentDate)) {
                if (!chargeHolidays) {
                    continue;
                }
            }

            if (this.isWorkday(currentDate)) {
                if (!chargeWeekdays) {
                    continue;
                }
            }
            else {
                if (!chargeWeekends) {
                    continue;
                }
            }
            billableDays++;

        }

        return billableDays;
    }

    private boolean isWorkday(final LocalDate date) {
        return !WEEKEND_DAYS.contains(date.getDayOfWeek());
    }

    private boolean isHoliday(final LocalDate date) {
        return isIndependenceDay(date) || isLaborDay(date);
    }

    private boolean isIndependenceDay(final LocalDate date) {
        if (date.getMonth() == Month.JULY) {
            if (date.getDayOfMonth() == 3) {
                return date.getDayOfWeek() == DayOfWeek.FRIDAY;
            }
            if (date.getDayOfMonth() == 5) {
                return date.getDayOfWeek() == DayOfWeek.MONDAY;
            }
            if (isWorkday(date)) {
               return date.getDayOfMonth() == 4;
            }
        }
        return false;
    }

    private boolean isLaborDay(final LocalDate date) {
        return date.getMonth() == Month.SEPTEMBER
                && date.getDayOfWeek() == DayOfWeek.MONDAY
                && date.getDayOfMonth() <= 7;
    }
}
