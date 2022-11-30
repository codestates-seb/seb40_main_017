package team017.ord.mapper;

import org.mapstruct.Mapper;
import team017.board.Entity.Board;
import team017.member.entity.Client;
import team017.ord.dto.OrdClientResponseDto;
import team017.ord.dto.OrdPostDto;
import team017.ord.dto.OrdResponseDto;
import team017.ord.entity.Ord;
import team017.product.Entity.Product;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrdMapper {

    default Ord ordPostDtoToOrd(Client client, Product product, OrdPostDto ordPostDto){
        if( ordPostDto == null){
            return null;
        }

        Ord ord = new Ord();
        ord.setClient(client);
        ord.setProduct(product);
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

        OrdResponseDto ordResponseDto = new OrdResponseDto();
        ordResponseDto.setOrdId(ord.getOrdId());
        ordResponseDto.setClientId(ord.getClient().getClientId());
        ordResponseDto.setBoardId(ord.getProduct().getBoard().getBoardId());
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

        OrdClientResponseDto ordResponseDto = new OrdClientResponseDto();
        ordResponseDto.setOrdId(ord.getOrdId());
        ordResponseDto.setClientId(ord.getClient().getClientId());
        ordResponseDto.setBoardId(ord.getProduct().getBoard().getBoardId());
        ordResponseDto.setTitle(ord.getProduct().getBoard().getTitle());
        ordResponseDto.setName(ord.getClient().getMember().getName());
        ordResponseDto.setAddress(ord.getAddress());
        ordResponseDto.setPhone(ord.getPhone());
        ordResponseDto.setQuantity(ord.getQuantity());

        return ordResponseDto;

    }
    List<OrdClientResponseDto> ordToOrdClientResponseDtos(List<Ord> ords);
}