package de.dhbw.plugins.rest.controller.financialledger.csv;

import de.dhbw.ems.adapter.application.financialledger.FinancialLedgerApplicationAdapter;
import de.dhbw.ems.application.archive.core.TmpFile;
import de.dhbw.ems.domain.financialledger.entity.FinancialLedger;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/user/{userId}/financialledger/{financialLedgerAggregateId}/archive/", produces = "application/octet-stream")
@AllArgsConstructor

public class FinancialLedgerCSVController {

    private final FinancialLedgerApplicationAdapter financialLedgerApplicationAdapter;

    @GetMapping
    public ResponseEntity<Resource> findOne(@PathVariable("userId") UUID userId, @PathVariable("financialLedgerAggregateId") UUID financialLedgerAggregateId) throws IOException {
        Optional<FinancialLedger> optionalFinancialLedgerAggregate = financialLedgerApplicationAdapter.find(userId, financialLedgerAggregateId);
        if (!optionalFinancialLedgerAggregate.isPresent()) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        try (TmpFile tmpFile = financialLedgerApplicationAdapter.createTmpZipArchive(optionalFinancialLedgerAggregate.get())){

            HttpHeaders header = new HttpHeaders();
            header.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=financialLedger-%s.zip", financialLedgerAggregateId));
            header.add("Cache-Control", "no-cache, no-store, must-revalidate");
            header.add("Pragma", "no-cache");
            header.add("Expires", "0");
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(tmpFile.getFile().toPath()));

            return ResponseEntity.ok()
                    .headers(header)
                    .contentLength(tmpFile.getFile().length())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(resource);
        }
    }

}
