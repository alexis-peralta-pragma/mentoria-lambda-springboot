package com.mentoria.update_card.model;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbBean
public class Card {

    @Getter(onMethod = @__({@DynamoDbPartitionKey, @DynamoDbAttribute("set")}))
    private String partitionKey;
    @Getter(onMethod = @__({@DynamoDbSortKey, @DynamoDbAttribute("number")}))
    private String sortKey;
    private String title;
    private Double price;
    private String image;
    List<Price> priceHistorial;
    List<Power> powers;
}
