package com.lcwd.Krushi_Seva_Kendra.Inventory.controller;

import com.lcwd.Krushi_Seva_Kendra.Inventory.dto.PurchaseRequest;
import com.lcwd.Krushi_Seva_Kendra.Inventory.dto.SeedResponse;
import com.lcwd.Krushi_Seva_Kendra.Inventory.service.InventoryService;
import com.lcwd.Krushi_Seva_Kendra.Inventory.dto.SellRequest;
import com.lcwd.Krushi_Seva_Kendra.Inventory.dto.ApiResponse;
import com.lcwd.Krushi_Seva_Kendra.Inventory.model.SaleRecord;
import com.lcwd.Krushi_Seva_Kendra.Inventory.model.Seed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    // Company Purchase API
    @PostMapping("/purchase")
    public ResponseEntity<ApiResponse<SeedResponse>> purchase(@RequestParam String invoiceNo,
                                                      @RequestBody PurchaseRequest request) {
        logger.info("Received purchase request: item={}, company={}, qty={}, rate={}, invoice={}",
                request.getItemName(), request.getCompanyName(), request.getQty(), request.getRate(), invoiceNo);

        // Pass full request instead of individual fields
        SeedResponse seedResponse = service.purchaseSeed(request, invoiceNo);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Purchase successful", seedResponse));
    }

    // Customer Sell API
    @PostMapping("/sell")
    public ResponseEntity<ApiResponse<String>> sell(@RequestBody SellRequest request,
                                                    @RequestParam String billType) {
        logger.info("Received sell request: {}", request, billType);

        String result = service.sellSeed(
                request.getItemName(),
                request.getCompanyName(),
                request.getQty(),
                request.getRate(),
                request.getInvoiceNo(),
                request.getCustomerName(),
                request.getBillType()   //pass extra param to service
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "Sale processed", result));
    }

    //Get all Seeds
    @GetMapping("/seeds")
    public ResponseEntity<ApiResponse<List<SeedResponse>>> getAllSeeds() {
        logger.info("Fetching all seeds");

        List<SeedResponse> seeds = service.getAllSeeds();
        return ResponseEntity
                .ok(new ApiResponse<>(true, "All seeds fetched", seeds));
    }

    //Get all Sales
    @GetMapping("/sales")
    public ResponseEntity<ApiResponse<List<SaleRecord>>> getAllSales() {
        logger.info("Fetching all sales records");

        List<SaleRecord> sales = service.getAllSales();
        return ResponseEntity
                .ok(new ApiResponse<>(true, "All sales fetched", sales));
    }
}

