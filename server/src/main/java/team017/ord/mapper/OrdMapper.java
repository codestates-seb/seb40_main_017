package team017.ord.mapper;

import org.mapstruct.Mapper;
import team017.board.Entity.Board;
import team017.member.entity.Client;
import team017.member.entity.Seller;
import team017.ord.dto.OrdClientResponseDto;
import team017.ord.dto.OrdPostDto;
import team017.ord.dto.OrdResponseDto;
import team017.ord.dto.OrdSellerResponseDto;
import team017.ord.entity.Ord;
import team017.product.Entity.Product;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrdMapper {

    default Ord ordPostDtoToOrd(OrdPostDto ordPostDto){
        if( ordPostDto == null){
            return null;
        }
        Client.ClientBuilder client = Client.builder();
        client.clientId(ordPostDto.getClientId());

        Board board = new Board();
        board.setBoardId(ordPostDto.getBoardId());

        Ord ord = new Ord();
        ord.setClient(client.build());
        //ord.setClientName(ordPostDto.getClientName());
        ord.setAddress(ordPostDto.getAddress());
        ord.setPhone(ordPostDto.getPhone());
        ord.setTotalPrice(ordPostDto.getTotalPrice());
        ord.setQuantity(ordPostDto.getQuantity());

        return ord;
    }

    default OrdResponseDto ordToOrdResponseDto(Ord ord){
        if( ord == null){
            return null;
        }
        Client.ClientBuilder client = Client.builder();
        client.clientId(ord.getClient().getClientId());

        ord.setOrdId(ord.getOrdId());

        Product product = new Product();
        product.setProductId(ord.getProduct().getBoard().getBoardId());

        OrdResponseDto ordResponseDto = new OrdResponseDto();
        ordResponseDto.setOrdId(ord.getOrdId());
        ordResponseDto.setClientId(client.build().getClientId());
        ordResponseDto.setBoardId(product.getProductId());
        ordResponseDto.setName(ord.getClient().getMember().getName());
        ordResponseDto.setAddress(ord.getAddress());
        ordResponseDto.setPhone(ord.getPhone());
        ordResponseDto.setTotalPrice(ord.getTotalPrice());
        ordResponseDto.setQuantity(ord.getQuantity());
        ordResponseDto.setOrdStatus(ord.getStatus());
        ordResponseDto.setCreatedAt(ord.getCreatedAt());

        return ordResponseDto;

    }

    default OrdClientResponseDto ordToOrdClientResponseDto(Ord ord){
        if( ord == null){
            return null;
        }
        Client.ClientBuilder client = Client.builder();
        client.clientId(ord.getClient().getClientId());

        ord.setOrdId(ord.getOrdId());

        Product product = new Product();
        product.setProductId(ord.getProduct().getBoard().getBoardId());


        OrdClientResponseDto ordResponseDto = new OrdClientResponseDto();
        ordResponseDto.setOrdId(ord.getOrdId());
        ordResponseDto.setClientId(client.build().getClientId());
        ordResponseDto.setBoardId(product.getProductId());
        ordResponseDto.setTitle(ord.getProduct().getBoard().getTitle());
        ordResponseDto.setName(ord.getClient().getMember().getName());
        ordResponseDto.setAddress(ord.getAddress());
        ordResponseDto.setPhone(ord.getPhone());
        ordResponseDto.setQuantity(ord.getQuantity());

        return ordResponseDto;

    }
    List<OrdClientResponseDto> ordToOrdClientResponseDtos(List<Ord> ords);
}