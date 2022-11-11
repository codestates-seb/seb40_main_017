package team017.board.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team017.member.entity.Seller;
import team017.product.Entity.Product;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardPostDto {

    private Long sellerId;

    private String title;

    private String content;

    private int price;

    private int stock;

    private String category;

}
