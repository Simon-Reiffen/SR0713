package com.simontoolmanagement.model;

public class ToolEnums {
    public enum ToolPreset {
        CHNS("CHNS", "Chainsaw", "Stihl"),
        LADW("LADW", "Ladder", "Werner"),
        JAKD("JAKD","Jackhammer", "DeWalt"),
        JAKR("JAKR", "Jackhammer", "Ridgid");

        private final String toolCode;
        private final String toolType;
        private final String toolBrand;

        ToolPreset(final String toolCode, final String toolType, final String toolBrand) {
            this.toolCode = toolCode;
            this.toolType = toolType;
            this.toolBrand = toolBrand;
        }

        public String getToolCode() {
            return this.toolCode;
        }

        public String getToolType() {
            return this.toolType;
        }

        public String getToolBrand() {
            return this.toolBrand;
        }
    }
    public enum CostPreset {
        Ladder(1.99, true, true, false),
        Chainsaw(1.49, true, false, true),
        Jackhammer(2.99, true, false, false);

        private final double dailyCharge;
        private final boolean weekdayCharge;
        private final boolean weekendCharge;
        private final boolean holidayCharge;

        CostPreset(final double dailyCharge, final boolean weekdayCharge, final boolean weekendCharge, final boolean holidayCharge) {
            this.dailyCharge = dailyCharge;
            this.weekdayCharge = weekdayCharge;
            this.weekendCharge = weekendCharge;
            this.holidayCharge = holidayCharge;
        }

        public double getDailyCharge() {
            return this.dailyCharge;
        }

        public boolean getWeekdayCharge() {
            return this.weekdayCharge;
        }

        public boolean getWeekendCharge() {
            return this.weekendCharge;
        }

        public boolean getHolidayCharge() {
            return this.holidayCharge;
        }
    }
}
