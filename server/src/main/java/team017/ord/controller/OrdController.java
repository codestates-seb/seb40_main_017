package team017.ord.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team017.member.service.ClientService;
import team017.ord.dto.OrdPostDto;
import team017.ord.entity.Ord;
import team017.ord.mapper.OrdMapper;
import team017.ord.service.OrdService;
import team017.product.Service.ProductService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@Slf4j
@Validated
public class OrdController {
    private final OrdService ordService;
    private final ProductService productService;
    private final ClientService clientService;
    private final OrdMapper ordMapper;

    @PostMapping
    public ResponseEntity postOrd(@RequestBody @Valid OrdPostDto ordPostDto){

        Ord ord = ordService.createOrd(ordMapper.ordPostDtoToOrd(ordPostDto), ordPostDto.getClientId(), ordPostDto.getBoardId());

        return new ResponseEntity<>((ordMapper.ordToOrdResponseDto(ord)),HttpStatus.CREATED);
    }

    @DeleteMapping("/{order-id}")
    public ResponseEntity deleteOrd(@PathVariable("order-id") @Positive Long ordId
                                    ){

        ordService.deleteOrd(ordId);

        String message = "Success!";

        return ResponseEntity.ok(message);

    }
}
