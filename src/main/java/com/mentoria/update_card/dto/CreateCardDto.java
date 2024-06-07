package com.mentoria.update_card.dto;

import com.mentoria.update_card.model.Power;
import com.mentoria.update_card.model.Price;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.util.List;

@Data
public class CreateCardDto {

    private String set;
    private String number;
    private String title;
    private Double price;
    private String image;
    List<PowerDto> powers;
}
