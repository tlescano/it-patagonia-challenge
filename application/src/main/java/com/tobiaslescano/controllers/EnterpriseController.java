package com.tobiaslescano.controllers;

import com.tobiaslescano.models.DTOs.EnterpriseDTO;
import com.tobiaslescano.models.DTOs.requestDTOs.EnterpriseRequestDTO;
import com.tobiaslescano.models.DTOs.responseDTOs.EnterpriseResponseDTO;
import com.tobiaslescano.services.IEnterpriseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enterprises")
@RequiredArgsConstructor
public class EnterpriseController {

    private final IEnterpriseService enterpriseService;

    @GetMapping("/lastMonthTransactions")
    public ResponseEntity<List<EnterpriseDTO>> getLastMonthTransactions() {
        return new ResponseEntity<>(enterpriseService.getLastMonthTransactions(), HttpStatus.OK);
    }

    @GetMapping("/lastMonthAddedEnterprises")
    public ResponseEntity<List<EnterpriseResponseDTO>> getLastMonthAddedEnterprises() {
        return new ResponseEntity<>(enterpriseService.getLastMonthAdded(), HttpStatus.OK);
    }

    @PostMapping("/addEnterprise")
    public ResponseEntity<EnterpriseDTO> addEnterprise(@RequestBody EnterpriseRequestDTO requestDTO) {
        return new ResponseEntity<>(enterpriseService.createEnterprise(requestDTO), HttpStatus.CREATED);
    }
}
