package team017.ord.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team017.ord.dto.OrdPostDto;
import team017.ord.dto.OrdResponseDto;
import team017.ord.entity.Ord;
import team017.ord.mapper.OrdMapper;
import team017.ord.service.OrdService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@Slf4j
@Validated
public class OrdController {
    private final OrdService ordService;
    private final OrdMapper ordMapper;

    @PostMapping
    public ResponseEntity postOrd(@RequestBody @Valid OrdPostDto ordPostDto){

        OrdResponseDto response = ordService.createOrd(ordMapper.ordPostDtoToOrd(ordPostDto),ordPostDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{order_id}")
    public ResponseEntity deleteOrd(@PathVariable("order_id") @Positive Long ordId){

        ordService.deleteOrd(ordId);

        return new ResponseEntity<>("Success delete",HttpStatus.OK);

    }

    @GetMapping("/{order_id}")
    public ResponseEntity getOrd(@PathVariable("order_id") @Positive Long ordId){
      Ord ord= ordService.findVerifiedOrd(ordId);
      OrdResponseDto response = ordMapper.ordToOrdResponseDto(ord);

        log.info("redirect 가 전송됨");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
