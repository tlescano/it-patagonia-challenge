package com.tobiaslescano.controllers;

import com.tobiaslescano.models.DTOs.EnterpriseDTO;
import com.tobiaslescano.models.DTOs.ErrorDTO;
import com.tobiaslescano.models.DTOs.requestDTOs.EnterpriseRequestDTO;
import com.tobiaslescano.models.DTOs.responseDTOs.EnterpriseResponseDTO;
import com.tobiaslescano.services.IEnterpriseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enterprises")
@RequiredArgsConstructor
public class EnterpriseController {

    private final IEnterpriseService enterpriseService;

    @Operation(summary = "Get all enterprises with last month made transactions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Enterprises found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EnterpriseDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Enterprises not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))
            ),
    })
    @GetMapping("/lastMonthTransactions")
    public ResponseEntity<List<EnterpriseResponseDTO>> getLastMonthTransactions() {
        return new ResponseEntity<>(enterpriseService.getLastMonthTransactions(), HttpStatus.OK);
    }

    @Operation(summary = "Get all enterprises created in the last month")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Enterprises found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EnterpriseResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Enterprises not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorDTO.class))
            ),
    })
    @GetMapping("/lastMonthAddedEnterprises")
    public ResponseEntity<List<EnterpriseResponseDTO>> getLastMonthAddedEnterprises() {
        return new ResponseEntity<>(enterpriseService.getLastMonthAdded(), HttpStatus.OK);
    }

    @Operation(summary = "Create an enterprise")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Enterprise created",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EnterpriseDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad request for enterprise creation",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDTO.class))
                    }),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDTO.class))
                    }),
    })
    @PostMapping("/addEnterprise")
    public ResponseEntity<EnterpriseDTO> addEnterprise(@Valid @RequestBody EnterpriseRequestDTO requestDTO) {
        return new ResponseEntity<>(enterpriseService.createEnterprise(requestDTO), HttpStatus.CREATED);
    }
}
