package com.example.webflux;

import com.example.webflux.entity.Item;
import com.example.webflux.entity.SubItem;
import com.example.webflux.repository.ItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@Testcontainers
@AutoConfigureWebTestClient
public class AbstractTest {
    protected static String FIRST_ITEM_ID = UUID.randomUUID().toString();

    protected static String SECOND_ITEM_ID = UUID.randomUUID().toString();

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.14");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    protected WebTestClient webTestClient;

    @Autowired
    protected ItemRepository itemRepository;

    @BeforeEach
    public void setup(){
        itemRepository.saveAll(
                List.of(
                        new Item(FIRST_ITEM_ID, "Name 1", 10, Collections.emptyList()),
                        new Item(SECOND_ITEM_ID, "Name 2", 20, List.of(
                            new SubItem("SubItem 1", BigDecimal.valueOf(1001)),
                                new SubItem("SubItem 2", BigDecimal.valueOf(2001))
                        )
                )
        )
        ).collectList().block();
    }
    @AfterEach
    public void afterEach(){
        itemRepository.deleteAll().block();
    }

}
