package com.example.webflux;


import com.example.webflux.entity.Item;
import com.example.webflux.entity.SubItem;
import com.example.webflux.model.ItemModel;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class ItemControllerTest extends AbstractTest {

    @Test
    public void whenGetAllItems_thenReturnListOfItemsFromDatabase(){
        var expectedData = List.of (
                new Item(FIRST_ITEM_ID, "Name 1", 10, Collections.emptyList()),
                new Item(SECOND_ITEM_ID, "Name 2", 20, List.of(
                        new SubItem("SubItem 1", BigDecimal.valueOf(1001)),
                        new SubItem("SubItem 2", BigDecimal.valueOf(2001))
                )
                )
        );

        webTestClient.get().uri("/api/v1/items")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ItemModel.class)
                .hasSize(2)
                .contains(expectedData.toArray(ItemModel[]::new));
    }
    @Test
    public void whenGetItemById_thenReturnItemById(){
        var expectedData = new Item(FIRST_ITEM_ID, "Name 2", 10, List.of(
                new SubItem("SubItem 1", BigDecimal.valueOf(1001)),
                new SubItem("SubItem 2", BigDecimal.valueOf(2001))
        ));

        webTestClient.get().uri("/api/v1/items/{id}", SECOND_ITEM_ID)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ItemModel.class)
                .isEqualTo(expectedData);
    }

    //Остальные тесты позже допишу
}
