package team017.payment;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Amount {
    private int total;
    private int tax_free;
    private int vat;
    private int point;
    private int discount;
}
