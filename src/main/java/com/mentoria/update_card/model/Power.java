package com.mentoria.update_card.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbBean
public class Power {
    private String name;
    private Integer points;
}
