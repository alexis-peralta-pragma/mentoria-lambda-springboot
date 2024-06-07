package com.mentoria.update_card.controller;

import com.mentoria.update_card.dto.CreateCardDto;
import com.mentoria.update_card.dto.UpdateCardDto;
import com.mentoria.update_card.exceptions.BadRequesException;
import com.mentoria.update_card.exceptions.NotFoundException;
import com.mentoria.update_card.model.Card;
import com.mentoria.update_card.service.CreateCardService;
import com.mentoria.update_card.service.UpdatePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;

@RestController
@EnableWebMvc
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private UpdatePriceService updatePriceService;
    @Autowired
    private CreateCardService createCardService;

    @PostMapping("/updatePrice")
    public ResponseEntity<Card> updateCardPrice(@RequestBody UpdateCardDto updateCardDto) {
        try {
            Card updatedCard = updatePriceService.execute(updateCardDto.getCardId(), updateCardDto.getNewPrice());
            return ResponseEntity.ok(updatedCard);
        } catch (NotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @PostMapping
    public ResponseEntity<Card> createCard(@RequestBody CreateCardDto createCardDto) {
        try {
            Card card = createCardService.execute(createCardDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(card);
        } catch (IOException ex){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        } catch (BadRequesException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }


    }

}
