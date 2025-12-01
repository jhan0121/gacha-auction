package com.gacha_auction.item.service;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.gacha_auction.exception.NotFoundException;
import com.gacha_auction.item.domain.Item;
import com.gacha_auction.item.domain.ItemName;
import com.gacha_auction.item.domain.ItemType;
import com.gacha_auction.item.domain.Rarity;
import com.gacha_auction.item.repository.ItemRepository;
import com.gacha_auction.item.service.dto.input.ItemCreationInput;
import com.gacha_auction.item.service.dto.output.FindItemOutput;
import com.gacha_auction.item.service.dto.output.ItemCreationOutput;
import com.gacha_auction.user.service.dto.input.FindItemInput;
import java.lang.reflect.Field;
import java.util.Optional;
import org.assertj.core.api.Assertions;
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

    @Test
    @DisplayName("id를 기반으로 아이템을 탐색하여 아이템 정보를 제공한다")
    void findById() {
        // given
        final long targetId = 1L;
        final ItemName name = ItemName.from("name");
        final ItemType itemType = ItemType.NORMAL;
        final Rarity rarity = Rarity.R;
        final Item item = Item.withoutId(name, itemType, rarity);
        setItemId(item, targetId);
        final FindItemInput input = new FindItemInput(targetId);

        when(itemRepository.findById(targetId))
                .thenReturn(Optional.of(item));

        // when
        final FindItemOutput actual = itemService.findById(input);

        // then
        assertSoftly(softly -> {
            softly.assertThat(actual.id()).isEqualTo(targetId);
            softly.assertThat(actual.itemName()).isEqualTo(name);
            softly.assertThat(actual.itemType()).isEqualTo(itemType);
            softly.assertThat(actual.rarity()).isEqualTo(rarity);
        });
    }

    @Test
    @DisplayName("존재하지 않는 id를 기반으로 아이템 탐색 시도 시, 예외를 던진다")
    void findByNotExistedId() {
        // given
        final Long notSavedUserId = 100L;
        final FindItemInput input = new FindItemInput(notSavedUserId);
        when(itemRepository.findById(notSavedUserId))
                .thenReturn(Optional.empty());

        // when
        // then
        Assertions.assertThatThrownBy(() -> itemService.findById(input))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("item not found");
    }

    private void setItemId(final Item item, final Long id) {
        final Field idField = ReflectionUtils.findField(item.getClass().getSuperclass(), "id");
        if (idField != null) {
            idField.setAccessible(true);
            ReflectionUtils.setField(idField, item, id);
        }
    }
}
