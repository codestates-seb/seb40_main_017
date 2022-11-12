package team017.member.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import team017.member.dto.MemberDto;
import team017.member.entity.Client;
import team017.member.entity.Member;
import team017.member.entity.Seller;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-12T21:14:05+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.16.1 (Azul Systems, Inc.)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public Member memberDtoToMember(MemberDto.Post post) {
        if ( post == null ) {
            return null;
        }

        Member member = new Member();

        member.setName( post.getName() );
        member.setEmail( post.getEmail() );
        member.setPassword( post.getPassword() );
        member.setPhone( post.getPhone() );
        member.setAddress( post.getAddress() );
        member.setRole( post.getRole() );

        return member;
    }

    @Override
    public MemberDto.Response memberToMemberResponseDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDto.Response response = new MemberDto.Response();

        if ( member.getMemberId() != null ) {
            response.setMemberId( member.getMemberId() );
        }
        response.setEmail( member.getEmail() );
        response.setName( member.getName() );
        response.setPhone( member.getPhone() );
        response.setAddress( member.getAddress() );
        response.setRole( member.getRole() );

        return response;
    }

    @Override
    public MemberDto.ClientDto memberToClientDto(Member member, Client client) {
        if ( member == null && client == null ) {
            return null;
        }

        MemberDto.ClientDto clientDto = new MemberDto.ClientDto();

        if ( member != null ) {
            clientDto.setEmail( member.getEmail() );
            clientDto.setName( member.getName() );
            clientDto.setPhone( member.getPhone() );
            clientDto.setAddress( member.getAddress() );
            clientDto.setRole( member.getRole() );
        }
        if ( client != null ) {
            if ( client.getClientId() != null ) {
                clientDto.setClientId( client.getClientId() );
            }
        }

        return clientDto;
    }

    @Override
    public MemberDto.SellerDto memberToSellerDto(Member member, Seller seller) {
        if ( member == null && seller == null ) {
            return null;
        }

        MemberDto.SellerDto sellerDto = new MemberDto.SellerDto();

        if ( member != null ) {
            sellerDto.setEmail( member.getEmail() );
            sellerDto.setName( member.getName() );
            sellerDto.setPhone( member.getPhone() );
            sellerDto.setAddress( member.getAddress() );
            sellerDto.setRole( member.getRole() );
        }
        if ( seller != null ) {
            if ( seller.getSellerId() != null ) {
                sellerDto.setSellerId( seller.getSellerId() );
            }
            sellerDto.setIntroduce( seller.getIntroduce() );
        }

        return sellerDto;
    }
}
