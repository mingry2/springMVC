package hello.item_service.web.validation;

import hello.item_service.domain.item.*;
import hello.item_service.web.validation.form.ItemSaveForm;
import hello.item_service.web.validation.form.ItemUpdateForm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/validation/v4/items")
@RequiredArgsConstructor
public class ValidationItemControllerV4 {

    private final ItemRepository itemRepository;

    @ModelAttribute("regions")
    public Map<String, String> region() {
        Map<String, String> regions = new LinkedHashMap<>();
        regions.put("SEOUL", "서울");
        regions.put("BUSAN", "부산");
        regions.put("JEJU", "제주");
        return regions;
    }

    @ModelAttribute("itemTypes")
    public ItemType[] itemType() {
        return ItemType.values();
    }

    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes() {
        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
        deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
        deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));

        return deliveryCodes;
    }


    // 목록 조회
    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v4/items";
    }

    // 상세 조회
    @GetMapping("/{itemId}")
    public String item(Model model, @PathVariable("itemId") Long itemId) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "validation/v4/item";
    }

    // 등록폼
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());

        return "validation/v4/addForm";
    }

    // 등록
    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute("item") ItemSaveForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (form.getPrice() != null && form.getQuantity() != null) {
            int resultPrice = form.getPrice() * form.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);

            return "validation/v4/addForm";
        }

        // 성공 로직
        Item updateItem = new Item(form.getItemName(), form.getPrice(), form.getQuantity());

        Item savedItem = itemRepository.save(updateItem);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v4/items/{itemId}";
    }

    // 수정폼
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item findItem = itemRepository.findById(itemId);
        model.addAttribute("item", findItem);

        return "validation/v4/editForm";

    }

    // 수정
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId,
                       @Validated
                       @ModelAttribute("item") ItemUpdateForm form,
                       BindingResult bindingResult) {

        if (form.getPrice() != null && form.getQuantity() != null) {
            int resultPrice = form.getPrice() * form.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            return "validation/v4/editForm";
        }

        Item updateItem = new Item(form.getItemName(), form.getPrice(), form.getQuantity());
        itemRepository.update(itemId, updateItem);

        return "redirect:/validation/v4/items/{itemId}";

    }

    // 삭제
    @PostMapping("/{itemId}/delete")
    public String delete(@PathVariable Long itemId) {
        itemRepository.delete(itemId);
        return "redirect:/validation/v4/items";
    }

    /**
     * 테스트용 더미 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }

}
