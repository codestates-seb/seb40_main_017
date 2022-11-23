package team017.payment;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReadyResponseDto {

    private String tid;                     //결제 고유 번호, 20자
    private String next_redirect_pc_url;    //사용자 정보 입력 화면 Redirect URL
    //추후 삭제 예정
    private String partner_order_id;        //가맹점 주문번호, 최대 100자

}
