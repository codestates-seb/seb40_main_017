// package team017.product.Entity;
//
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;
// import org.hibernate.annotations.ColumnDefault;
// import org.hibernate.annotations.DynamicInsert;
//
// import javax.persistence.*;
//
// @Getter
// @Setter
// @Entity
// @NoArgsConstructor
// @Table(name = "product")
// public class Product {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long productId;
//
//     @Column(nullable = false)
//     private int price;
//
//     @ColumnDefault("selling")
//     //@Column(columnDefinition = "")
//     @Column(nullable = true)
//     private String status; //판매 상태
//
//     @Column(nullable = false)
//     private int stock;
//
//     @Column(nullable = false)
//     private int category; //상품분류 (과일 :1 , 채소: 2, 곡물: 3,  견과류: 4)
//
// }
