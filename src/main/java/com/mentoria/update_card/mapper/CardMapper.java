package com.mentoria.update_card.mapper;

import com.mentoria.update_card.dto.CreateCardDto;
import com.mentoria.update_card.model.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CardMapper {
    CardMapper INSTANCE = Mappers.getMapper(CardMapper.class);

    @Mapping(target = "partitionKey", source = "set")
    @Mapping(target = "sortKey", source = "number")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "powers", source = "powers")
    @Mapping(target = "priceHistorial", ignore = true)
    Card dtoToModel(CreateCardDto createCardDto);
}
