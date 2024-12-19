package hello.item_service.domain.item;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    // DB 연동 X -> id 생성을 위해
    private static final Map<Long, Item> store = new HashMap<>();
    private static long sequence = 0L;

    // 등록
    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    // 조회
    public Item findById(Long id) {
        return store.get(id);
    }

    // 전체 조회
    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    // 수정
    public void update(Long id, Item updateParam) {
        // 기존 item 조회
        Item findItem = findById(id);

        // item 업데이트
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    // 삭제
    public void clearStore() {
        store.clear();
    }
}
