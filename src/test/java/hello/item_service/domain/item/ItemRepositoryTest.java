package hello.item_service.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        // given
        Item item = new Item("itemA", 10000, 10);

        // when -> 검증하려는 것
        Item savedItem = itemRepository.save(item);

        // then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findAll() {
        // given
        Item item1 = new Item("itemA", 10000, 10);
        Item item2 = new Item("itemB", 20000, 20);
        itemRepository.save(item1);
        itemRepository.save(item2);

        // when
        List<Item> items = itemRepository.findAll();

        // then
        assertThat(items).hasSize(2);
        assertThat(items).contains(item1, item2);
    }

    @Test
    void update() {
        // given
        Item item = new Item("itemA", 10000, 10);
        Item savedItem = itemRepository.save(item);

        // when
        Item updateItem = new Item("updateItem", 333, 33);
        itemRepository.update(savedItem.getId(), updateItem);

        // then
        Item findItem = itemRepository.findById(savedItem.getId());
        assertThat(findItem.getItemName()).isEqualTo(updateItem.getItemName());
    }

}