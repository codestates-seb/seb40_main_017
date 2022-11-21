package team017.board.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team017.board.Dto.BoardForSellerMyPageDto;
import team017.board.Entity.Board;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findBoardsByProduct_Category(PageRequest pageRequest , int category );

    @Query("SELECT new team017.board.Dto.BoardForSellerMyPageDto( b.title, b.createdAt, b.product.stock, b.soldStock) FROM Board as b  WHERE b.seller.sellerId = :sellerId")
    List<BoardForSellerMyPageDto> sellerBoard(@Param("sellerId") Long sellerId);

}