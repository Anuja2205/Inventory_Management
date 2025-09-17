package com.lcwd.Krushi_Seva_Kendra.Inventory.service;

import com.lcwd.Krushi_Seva_Kendra.Inventory.dto.PurchaseRequest;
import com.lcwd.Krushi_Seva_Kendra.Inventory.model.*;
import com.lcwd.Krushi_Seva_Kendra.Inventory.repository.CustomerRepository;
import com.lcwd.Krushi_Seva_Kendra.Inventory.repository.PurchaseRecordRepository;
import com.lcwd.Krushi_Seva_Kendra.Inventory.repository.SaleRecordRepository;
import com.lcwd.Krushi_Seva_Kendra.Inventory.repository.SeedRepository;
import com.lcwd.Krushi_Seva_Kendra.Inventory.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class InventoryService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);

    private final SeedRepository seedRepo;
    private final PurchaseRecordRepository purchaseRecordRepo;
    private final SaleRecordRepository saleRecordRepo;
    private final CustomerRepository customerRepo;

    public InventoryService(SeedRepository seedRepo, PurchaseRecordRepository purchaseRecordRepo,
                            SaleRecordRepository saleRecordRepo, CustomerRepository customerRepo) {
        this.seedRepo = seedRepo;
        this.purchaseRecordRepo = purchaseRecordRepo;
        this.saleRecordRepo = saleRecordRepo;
        this.customerRepo = customerRepo;
    }

    //  Purchase (increase stock)
    public Seed purchaseSeed(PurchaseRequest request, String invoiceNo) {

        logger.info("Processing purchase: item={}, company={}, qty={}, rate={}, invoice={}",
                request.getItemName(), request.getCompanyName(), request.getQty(), request.getRate(), invoiceNo);

        //find existing seed or create new
        Seed seed = seedRepo.findByItemNameAndCompanyName(request.getItemName(), request.getCompanyName())
                .orElse(new Seed());

        // update stock quantity
        seed.setQuantity(seed.getQuantity() + request.getQty());

        seed.setRate(request.getRate());

        //set other details
        seed.setItemName(request.getItemName());
        seed.setCompanyName(request.getCompanyName());
        seed.setBatchNo(request.getBatchNo());
        seed.setPacking(request.getPacking());
        seed.setExpiry(request.getExpiry());
        seed.setBillType(request.getBillType());
        seed.setInvoiceNumber(invoiceNo);

        // calculate total
        seed.setTotal(request.getQty() * request.getRate());

        seedRepo.save(seed);

        logger.info("Stock updated for {}-{}, new quantity={}, new rate={}",
                request.getItemName(), request.getCompanyName(), seed.getQuantity(), seed.getRate());

        // Save purchase record
         PurchaseRecord pr = new PurchaseRecord();
         pr.setSeedId(seed.getBillNo());
        pr.setItemName(request.getItemName());
        pr.setCompanyName(request.getCompanyName());
        pr.setPurchasedQty(request.getQty());
         pr.setPurchasedRate(request.getRate());
         pr.setDate(new Date().toString());
         pr.setInvoiceNumber(invoiceNo);
         purchaseRecordRepo.save(pr);

         logger.info("Purchase record saved successfully, invoice={}", invoiceNo, request.getItemName(), request.getCompanyName());

         return seed;
    }

    //  Sale (decrease stock)
    public String sellSeed(String itemName,
                           String companyName,
                           double qty,
                           double marketRate,     // dynamic market rate
                           String invoiceNo,
                           String customerName,
                           String billType) {
        logger.info("Processing sale: item={}, company={}, qty={}, marketrate={}, invoice={}, customer={}, billType={}",
                itemName, companyName, qty, marketRate, invoiceNo, customerName, billType);

        // allow multiple sales under same invoice
        logger.info("Processing sale with invoice={} (multiple sales allowed)", invoiceNo);

        // Find Seed from stock
        Seed seed = seedRepo.findByItemNameAndCompanyName(itemName, companyName)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Seed not found for item: " + itemName + " and company: " + companyName));

        // Stock check
        if (seed.getQuantity() < qty) {
            logger.warn("Insufficient stock for {}-{}, requested={}, available={}",
                    itemName, companyName, qty, seed.getQuantity());
            return "Not enough stock available!";
        }

        // Update stock (reduce quantity)
        seed.setQuantity(seed.getQuantity() - qty);
        seedRepo.save(seed);
        logger.info("Stock reduced for {}-{}, new quantity={}", itemName, companyName, seed.getQuantity());

        //total = qty * current market rate
        double total = qty * marketRate;

        // Save Sale Record
        SaleRecord sr = new SaleRecord();
        sr.setSeedId(seed.getBillNo());
        sr.setSoldQty(qty);
        sr.setDate(LocalDate.now().toString());
        sr.setInvoiceNumber(invoiceNo);
        sr.setBillType(billType);
        sr.setRate(marketRate);  // store market rate at time of sale
        sr.setTotal(total);

        saleRecordRepo.save(sr);
        logger.info("Sale record saved successfully, invoice={}, billType={}, rate={}", invoiceNo, billType, marketRate);

        // Handle Customer record
        Customer customer = customerRepo.findByCustomerName(customerName)
                .orElse(new Customer());

        if (customer.getId() == null) {
            logger.info("New customer created: {}", customerName);
            customer.setCustomerName(customerName);
        }


        if (customer.getPurchasedSeed() == null) {
            customer.setPurchasedSeed(new ArrayList<>());
        }

        PurchaseDetail detail = new PurchaseDetail();
        detail.setItemName(itemName);
        detail.setQty(qty);
        detail.setRate(marketRate);
        detail.setTotal(total);

        customer.getPurchasedSeed().add(detail);
        customerRepo.save(customer);

        logger.info("Customer record updated: {}, purchased {} qty={} at rate={} total={}",
                customerName, itemName, qty, marketRate, total);

        return "Sell success to " + customerName + " at rate " + marketRate;
    }

    public List<SaleRecord> getAllSales() {
        logger.info("Fetching all sale records");
        return saleRecordRepo.findAll();
    }

    public List<Seed> getAllSeeds() {
        logger.info("Fetching all seed records");
        return seedRepo.findAll();
    }
}
