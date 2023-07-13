package com.simontoolmanagement.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.simontoolmanagement.model.RentalAgreement;
import com.simontoolmanagement.model.Tool;
import com.simontoolmanagement.model.ToolEnums.ToolPreset;
import com.simontoolmanagement.util.ToolUtils;

@SpringBootTest
@RunWith(org.springframework.test.context.junit4.SpringRunner.class)
public class ToolServiceTest {

    private static final double DELTA = 0.001;
    @Autowired
    private ToolService toolService;

    @Test
    public void test1() {
        Tool tool = ToolUtils.makeToolFromCode(ToolPreset.JAKR.getToolCode());
        assertThrows(IllegalArgumentException.class,
                () -> this.toolService.rentTool(tool,  5, LocalDate.of(2015, 3, 15), 101));
    }

    @Test
    public void test2() {
        Tool tool = ToolUtils.makeToolFromCode(ToolPreset.LADW.getToolCode());
        RentalAgreement rentalAgreement = this.toolService.rentTool(tool,  3, LocalDate.of(2020, 7, 2), 10);
        double expected = 3.98;
        assertEquals(expected, rentalAgreement.preDiscountCharge(), DELTA);
        assertEquals(expected - (expected * .1), rentalAgreement.finalCharge(), DELTA);
    }

    @Test
    public void test3() {
        Tool tool = ToolUtils.makeToolFromCode(ToolPreset.CHNS.getToolCode());
        RentalAgreement rentalAgreement = this.toolService.rentTool(tool,  5, LocalDate.of(2015, 7, 2), 25);
        double expected = 4.47;
        assertEquals(expected, rentalAgreement.preDiscountCharge(), DELTA);
        assertEquals(expected - (expected * .25), rentalAgreement.finalCharge(), DELTA);
    }

    @Test
    public void test4() {
        Tool tool = ToolUtils.makeToolFromCode(ToolPreset.JAKD.getToolCode());
        RentalAgreement rentalAgreement = this.toolService.rentTool(tool,  6, LocalDate.of(2015, 9, 3), 0);
        double expected = 8.97;
        assertEquals(expected, rentalAgreement.preDiscountCharge(), DELTA);
        assertEquals(expected, rentalAgreement.finalCharge(), DELTA);
    }

    @Test
    public void test5() {
        Tool tool = ToolUtils.makeToolFromCode(ToolPreset.JAKR.getToolCode());
        RentalAgreement rentalAgreement = this.toolService.rentTool(tool,  9, LocalDate.of(2015, 7, 2), 0);
        double expected = 17.94;
        assertEquals(expected, rentalAgreement.preDiscountCharge(), DELTA);
        assertEquals(expected, rentalAgreement.finalCharge(), DELTA);
    }

    @Test
    public void test6() {
        Tool tool = ToolUtils.makeToolFromCode(ToolPreset.JAKR.getToolCode());
        RentalAgreement rentalAgreement = this.toolService.rentTool(tool,  4, LocalDate.of(2020, 7, 2), 50);
        double expected = 2.99;
        assertEquals(expected, rentalAgreement.preDiscountCharge(), DELTA);
        assertEquals(expected - (expected * .5), rentalAgreement.finalCharge(), DELTA);
    }

}
