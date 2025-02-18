package com.tobiaslescano.services.mappers;

import com.tobiaslescano.models.DTOs.EnterpriseDTO;
import com.tobiaslescano.models.DTOs.TransactionsDTO;
import com.tobiaslescano.models.DTOs.responseDTOs.EnterpriseResponseDTO;
import com.tobiaslescano.models.entities.Enterprise;
import com.tobiaslescano.models.entities.Transactions;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface IEnterpriseMapper {

    IEnterpriseMapper INSTANCE = Mappers.getMapper(IEnterpriseMapper.class);

    @Mappings({
            @Mapping(source = "enterprise.transactions", target = "transactions", qualifiedByName = "mapTransactions")
    })
    EnterpriseResponseDTO fromEntityToResponseDTO(Enterprise enterprise);

    EnterpriseDTO fromEntityToDTO(Enterprise enterprise);


    @Named("mapTransactions")
    default List<TransactionsDTO> mapTransactions(Set<Transactions> transactions){
        return transactions.stream().map(ITransactionsMapper.INSTANCE::fromEntityToDTO).collect(Collectors.toList());
    }
}
