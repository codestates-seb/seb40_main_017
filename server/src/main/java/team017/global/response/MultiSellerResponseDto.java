package team017.global.response;

import lombok.Getter;

import java.util.List;
import team017.member.dto.MemberDto;

@Getter
public class MultiSellerResponseDto<T> {
    private List<T> dataList;
    private MemberDto.SelleResponseDto data;

    public MultiSellerResponseDto(MemberDto.SelleResponseDto data ,List<T> dataList ){
        this.dataList = dataList;
        this.data = data;
    }
}
