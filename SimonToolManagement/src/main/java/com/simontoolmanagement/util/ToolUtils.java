package com.simontoolmanagement.util;

import com.simontoolmanagement.model.Tool;
import com.simontoolmanagement.model.ToolEnums;
import com.simontoolmanagement.model.ToolEnums.CostPreset;
import com.simontoolmanagement.model.ToolEnums.ToolPreset;

public final class ToolUtils {

    private ToolUtils() {
     /* No initialization necessary */
    }
    public static Tool makeToolFromCode(final String toolCode) {
        ToolPreset toolPreset = ToolEnums.ToolPreset.valueOf(toolCode);
        CostPreset costPreset = ToolEnums.CostPreset.valueOf(toolPreset.getToolType());
        return new Tool(toolPreset.getToolCode(), toolPreset.getToolType(), toolPreset.getToolBrand(), costPreset.getDailyCharge(),
                costPreset.getWeekdayCharge(), costPreset.getWeekendCharge(), costPreset.getHolidayCharge());
    }
}
