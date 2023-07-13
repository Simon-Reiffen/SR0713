package com.simontoolmanagement.controller;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.simontoolmanagement.model.Tool;
import com.simontoolmanagement.model.ToolEnums.ToolPreset;
import com.simontoolmanagement.service.ToolService;
import com.simontoolmanagement.util.ToolUtils;

@RestController
@RequestMapping("/tools")
public class ToolController {

    private final ToolService toolService;

    public ToolController(final ToolService toolService) {
        this.toolService = toolService;
    }
    @GetMapping
    public String getTools() {

        //TODO: If there were a persistence layer, it should be called here, and return all the tools

        //For now, return an ENUM
        return Arrays.toString(ToolPreset.values());
    }

    //API endpoint for renting a tool with Self Checkout (ie: no cashier)
    @PostMapping("/rent")
    public ResponseEntity<String> rentToolCustomer(
            @RequestParam final String toolCode,
            @RequestParam final int rentalDays
    ) {
        return this.rentTool(toolCode, rentalDays, 0);
    }
    // API endpoint for renting a tool with a cashierE
    @Secured("ROLE_CASHIER") // Restrict access to users with "cashier" privilege
    @PostMapping("/rentCashier")
    public ResponseEntity<String> rentToolCashier(
            @RequestParam final String toolCode,
            @RequestParam final int rentalDays,
            @RequestParam(defaultValue = "0") final int discountPercent
    ) {
        return this.rentTool(toolCode, rentalDays, discountPercent);
    }

    private ResponseEntity<String> rentTool(final String toolCode, final int rentalDays, final int discountPercent) {
        Tool tool = ToolUtils.makeToolFromCode(toolCode);
        try {
            String response = this.toolService.rentTool(tool, rentalDays, LocalDate.now(), discountPercent)
                    .generateReceipt();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
