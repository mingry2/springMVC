package hello.item_service.domain.item;

import lombok.Data;

@Data // 사용 시 항상 확인 필요(getter,setter만 사용 권장)
public class Item {

    private Long id;
    private String itemName;
    private Integer price; // null 허용
    private Integer quantity; // null 허용

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
