package com.lcwd.Krushi_Seva_Kendra.Inventory.service;

import com.lcwd.Krushi_Seva_Kendra.Inventory.repository.CustomerRepository;
import com.lcwd.Krushi_Seva_Kendra.Inventory.repository.PurchaseRecordRepository;
import com.lcwd.Krushi_Seva_Kendra.Inventory.repository.SaleRecordRepository;
import com.lcwd.Krushi_Seva_Kendra.Inventory.repository.SeedRepository;
import com.lcwd.Krushi_Seva_Kendra.Inventory.exception.ResourceNotFoundException;
import com.lcwd.Krushi_Seva_Kendra.Inventory.model.Customer;
import com.lcwd.Krushi_Seva_Kendra.Inventory.model.PurchaseRecord;
import com.lcwd.Krushi_Seva_Kendra.Inventory.model.SaleRecord;
import com.lcwd.Krushi_Seva_Kendra.Inventory.model.Seed;
import lombok.RequiredArgsConstructor;
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
    public Seed purchaseSeed(String itemName, String companyName, double qty, String invoiceNo) {

        logger.info("Processing purchase: item={}, company={}, qty={}, invoice={}", itemName, companyName, qty, invoiceNo);

        Seed seed = seedRepo.findByItemNameAndCompanyName(itemName, companyName)
                .orElse(new Seed());

                seed.setQuantity(seed.getQuantity() + qty);
                seed.setItemName(itemName);
                seed.setCompanyName(companyName);
                seed.setInvoiceNumber(invoiceNo);
                seedRepo.save(seed);

                logger.info("Stock updated for {}-{}, new quantity={}", itemName, companyName, seed.getQuantity());

        // Save purchase record
         PurchaseRecord pr = new PurchaseRecord();
         pr.setSeedId(seed.getBillNo());
         pr.setPurchasedQty(qty);
         pr.setDate(new Date().toString());
         pr.setInvoiceNumber(invoiceNo);
         purchaseRecordRepo.save(pr);

         logger.info("Purchase record saved successfully, invoice={}", invoiceNo);

         return seed;
    }

    //  Sale (decrease stock)
    public String sellSeed(String itemName, String companyName, double qty,
                           String invoiceNo, String customerName) {
        logger.info("Processing sale: item={}, company={}, qty={}, invoice={}, customer={}",
                itemName, companyName, qty, invoiceNo, customerName);

        // Find Seed
        Seed seed = seedRepo.findByItemNameAndCompanyName(itemName, companyName)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Seed not found for item: " + itemName + " and company: " + companyName));

        // Stock check
        if (seed.getQuantity() < qty) {
            logger.warn("Insufficient stock for {}-{}, requested={}, available={}",
                    itemName, companyName, qty, seed.getQuantity());
            return "Not enough stock available!";
        }

        // Update stock
        seed.setQuantity(seed.getQuantity() - qty);
        seedRepo.save(seed);
        logger.info("Stock reduced for {}-{}, new quantity={}", itemName, companyName, seed.getQuantity());

        // Save Sale Record
        SaleRecord sr = new SaleRecord();
        sr.setSeedId(seed.getBillNo());
        sr.setSoldQty(qty);
        sr.setDate(LocalDate.now().toString());
        sr.setInvoiceNumber(invoiceNo);
        saleRecordRepo.save(sr);
        logger.info("Sale record saved successfully, invoice={}", invoiceNo);

        // Handle Customer
        Customer customer = customerRepo.findByCustomerName(customerName)
                .orElse(new Customer());

        if (customer.getId() == null) {
            logger.info("New customer created: {}", customerName);
            customer.setCustomerName(customerName);
        }

        if (customer.getPurchasedSeed() == null) {
            customer.setPurchasedSeed(new ArrayList<>());
        }

        customer.getPurchasedSeed().add(itemName + " (" + qty + ")");
        customerRepo.save(customer);

        logger.info("Customer record updated: {}", customerName);

        return "Sell success to " + customerName;
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
