package com.tobiaslescano.services;

import com.tobiaslescano.models.DTOs.EnterpriseDTO;
import com.tobiaslescano.models.DTOs.requestDTOs.EnterpriseRequestDTO;
import com.tobiaslescano.models.DTOs.responseDTOs.EnterpriseResponseDTO;

import java.util.List;

public interface IEnterpriseService {
    List<EnterpriseDTO> getLastMonthTransactions();

    List<EnterpriseResponseDTO> getLastMonthAdded();

    EnterpriseDTO createEnterprise(EnterpriseRequestDTO requestDTO);
}
