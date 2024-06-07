package com.mentoria.update_card.repository.dynamo;

import com.mentoria.update_card.model.Card;
import com.mentoria.update_card.repository.CardRepository;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;

@Repository
public class DynamoDbCardRepository implements CardRepository {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

    private final DynamoDbTable<Card> cardTable;

    public DynamoDbCardRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
        this.cardTable = dynamoDbEnhancedClient
                .table("Cards", TableSchema.fromBean(Card.class));
    }

    @Override
    public Card getCard(String partitionKey, String sortKey) {
        return cardTable.getItem(Key.builder().partitionValue(partitionKey).sortValue(sortKey).build());
    }

    @Override
    public Card saveCard(Card card) {
        cardTable.putItem(card);
        return card;
    }
}
