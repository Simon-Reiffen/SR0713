package com.simontoolmanagement.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record RentalAgreement(String toolCode, String toolType, String toolBrand, int rentalDays,LocalDate checkoutDate,
                              LocalDate dueDate, double dailyRentalCharge, int chargeDays, double preDiscountCharge,
                              double discount, double discountAmount, double finalCharge) {

    public String generateReceipt() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");

        return String.format("""
            Tool code: %s
            Tool type: %s
            Tool brand: %s
            Rental days: %d
            Check out date: %s
            Due date: %s
            Daily rental charge: $%.2f
            Charge days: %d
            Pre-discount charge: $%.2f
            Discount percent: %.0f%%
            Discount amount: $%.2f
            Final charge: $%.2f
            """,
                this.toolCode,
                this.toolType,
                this.toolBrand,
                this.rentalDays,
                dateFormatter.format(this.checkoutDate),
                dateFormatter.format(this.dueDate),
                Double.valueOf(this.dailyRentalCharge),
                Integer.valueOf(this.chargeDays),
                Double.valueOf(this.preDiscountCharge),
                Double.valueOf(this.discount),
                Double.valueOf(this.discountAmount),
                Double.valueOf(this.finalCharge));
    }
}
