package com.mentoria.update_card.service;

import com.mentoria.update_card.dto.CreateCardDto;
import com.mentoria.update_card.exceptions.BadRequesException;
import com.mentoria.update_card.mapper.CardMapper;
import com.mentoria.update_card.model.Card;
import com.mentoria.update_card.model.Price;
import com.mentoria.update_card.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class CreateCardService {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardMapper cardMapper;

    @Autowired
    private S3Client s3Client;

    private final String BUCKET_NAME = "mentoria-card-bucket";

    public Card execute(CreateCardDto createCardDto) throws BadRequesException, IOException {

        if (createCardDto.getPowers() == null || createCardDto.getPowers().isEmpty()) {
            throw new BadRequesException();
        }

        Card newCard = cardMapper.dtoToModel(createCardDto);
        if (createCardDto.getPrice() != null) {
            Price price = Price.builder().price(createCardDto.getPrice()).date(new Date().getTime()).build();
            newCard.setPrice(createCardDto.getPrice());
            newCard.setPriceHistorial(List.of(price));
        } else {
            newCard.setPrice(0D);
            newCard.setPriceHistorial(new ArrayList<>());
        }

        if (createCardDto.getImage() != null) {
            uploadImage(createCardDto.getImage(), createCardDto.getSet() + '-' + createCardDto.getNumber());
            newCard.setImage("https://" + BUCKET_NAME + ".s3.amazonaws.com/" + createCardDto.getSet() + '-' + createCardDto.getNumber());
        }


        return cardRepository.saveCard(newCard);

    }

    public void uploadImage(String base64Image, String fileName) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(fileName)
                .contentType("image/png") // Cambia esto al tipo de tu imagen
                //.acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        try {
            var result = s3Client.putObject(putObjectRequest, RequestBody.fromBytes(imageBytes));
            System.out.println(result.toString());
        } catch (S3Exception e) {
            System.out.println(e.getMessage());
            throw new IOException("Failed to upload image to S3", e);
        }
    }

}
