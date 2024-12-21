package hello.item_service.domain.item;

import lombok.Data;

import java.util.List;

@Data // 사용 시 항상 확인 필요(getter,setter만 사용 권장)
public class Item {

    private Long id;
    private String itemName;
    private Integer price; // null 허용
    private Integer quantity; // null 허용

    private Boolean open; // 판매 여부
    private List<String> regions; // 등록 지역
    private ItemType itemType; // 상품 종류
    private String deliveryCode; // 배송 방식

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
