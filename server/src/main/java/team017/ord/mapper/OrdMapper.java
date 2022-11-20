package team017.ord.mapper;

import org.mapstruct.Mapper;
import team017.ord.dto.OrdPostDto;
import team017.ord.dto.OrdResponseDto;
import team017.ord.entity.Ord;

@Mapper(componentModel = "spring")
public interface OrdMapper {

    Ord ordPostDtoToOrd(OrdPostDto ordPostDto);

    OrdResponseDto ordToOrdResponseDto(Ord ord);
}
