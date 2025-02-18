package com.tobiaslescano.services.mappers;

import com.tobiaslescano.models.DTOs.TransactionsDTO;
import com.tobiaslescano.models.entities.Transactions;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ITransactionsMapper {

    ITransactionsMapper INSTANCE = Mappers.getMapper(ITransactionsMapper.class);

    TransactionsDTO fromEntityToDTO(Transactions transactions);

}
