package com.gacha_auction.item.service;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.gacha_auction.item.domain.Item;
import com.gacha_auction.item.domain.ItemName;
import com.gacha_auction.item.domain.ItemType;
import com.gacha_auction.item.domain.Rarity;
import com.gacha_auction.item.repository.ItemRepository;
import com.gacha_auction.item.service.dto.input.ItemCreationInput;
import com.gacha_auction.item.service.dto.output.ItemCreationOutput;
import java.lang.reflect.Field;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.ReflectionUtils;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    ItemRepository itemRepository;

    @InjectMocks
    ItemService itemService;

    @Test
    @DisplayName("아이템을 저장한다")
    void save() {
        // given
        final ItemName name = ItemName.from("name");
        final ItemType itemType = ItemType.NORMAL;
        final Rarity rarity = Rarity.SSR;
        final ItemCreationInput input = new ItemCreationInput(name, itemType, rarity);
        final Item item = Item.withoutId(name, itemType, rarity);
        setItemId(item, 1L);

        when(itemRepository.save(any(Item.class)))
                .thenReturn(item);

        // when
        final ItemCreationOutput actual = itemService.save(input);

        // then
        assertSoftly(softly -> {
            softly.assertThat(actual.id()).isNotNull();
            softly.assertThat(actual.itemName()).isEqualTo(name);
            softly.assertThat(actual.itemType()).isEqualTo(itemType);
            softly.assertThat(actual.rarity()).isEqualTo(rarity);
        });
    }

    private void setItemId(final Item item, final Long id) {
        final Field idField = ReflectionUtils.findField(item.getClass().getSuperclass(), "id");
        if (idField != null) {
            idField.setAccessible(true);
            ReflectionUtils.setField(idField, item, id);
        }
    }
}
