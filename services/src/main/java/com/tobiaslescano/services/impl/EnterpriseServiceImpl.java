package com.tobiaslescano.services.impl;

import com.tobiaslescano.models.DTOs.EnterpriseDTO;
import com.tobiaslescano.models.DTOs.requestDTOs.EnterpriseRequestDTO;
import com.tobiaslescano.models.DTOs.responseDTOs.EnterpriseResponseDTO;
import com.tobiaslescano.models.entities.Enterprise;
import com.tobiaslescano.models.entities.Transactions;
import com.tobiaslescano.repository.repositories.IEnterpriseRepository;
import com.tobiaslescano.repository.repositories.ITransactionRepository;
import com.tobiaslescano.services.IEnterpriseService;
import com.tobiaslescano.services.exceptions.NotFoundException;
import com.tobiaslescano.services.mappers.IEnterpriseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnterpriseServiceImpl implements IEnterpriseService {

    private final IEnterpriseRepository enterpriseRepository;
    private final ITransactionRepository transactionRepository;


    @Override
    public List<EnterpriseResponseDTO> getLastMonthTransactions() {
        List<Enterprise> enterprises = enterpriseRepository.findAllEnterprisesWithLastMonthTransactions();

        checkIfEmpty(enterprises);

        return enterprises.stream().map(IEnterpriseMapper.INSTANCE::fromEntityToResponseDTO).collect(Collectors.toList());
    }

    @Override
    public List<EnterpriseDTO> getLastMonthAdded() {
        LocalDate date = LocalDate.now().minusMonths(1);
        List<Enterprise> enterprises = enterpriseRepository.getEnterprisesByJoinedDateGreaterThanEqual(Date.valueOf(date));

        checkIfEmpty(enterprises);

        return enterprises.stream().map(IEnterpriseMapper.INSTANCE::fromEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public EnterpriseDTO createEnterprise(EnterpriseRequestDTO requestDTO) {
        Enterprise createdEnterprise = Enterprise.builder()
                .legalName(requestDTO.getLegalName())
                .cuit(requestDTO.getCuit())
                .joinedDate(requestDTO.getJoinedDate())
                .build();

        createdEnterprise = enterpriseRepository.save(createdEnterprise);

        return IEnterpriseMapper.INSTANCE.fromEntityToDTO(createdEnterprise);
    }


    private void checkIfEmpty(List<Enterprise> enterprises) {
        if (enterprises.isEmpty()) throw new NotFoundException("No enterprises found with last month transactions");
    }
}
