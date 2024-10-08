package com.example.webflux.controller;


import com.example.webflux.entity.Item;
import com.example.webflux.model.ItemModel;
import com.example.webflux.publisher.ItemUpdatesPublisher;
import com.example.webflux.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    private final ItemUpdatesPublisher publisher;

    @GetMapping
    public Flux<ItemModel> getAllItems() {
        return itemService.findAll().map(ItemModel::from);
    }

    @GetMapping
    public Mono<ResponseEntity<ItemModel>> getById(@PathVariable String id) {
        return itemService.findById(id)
                .map(ItemModel::from)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-name")
    public Mono<ResponseEntity<ItemModel>> getItemByName(@RequestParam String name) {
        return itemService.findByName(name)
                .map(ItemModel::from)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<ItemModel>> createItem(@RequestBody ItemModel item) {
        return itemService.save(Item.from(item))
                .map(ItemModel::from)
                .doOnSuccess(publisher::publish) //Публикация созданной сущности
                .map(ResponseEntity::ok);
    }
    @PutMapping("/{id}")
    public Mono<ResponseEntity<ItemModel>> updateItem(@PathVariable String id, @RequestBody ItemModel item) {
        return itemService.update(id, Item.from(item))
                .map(ItemModel::from)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteItem(@PathVariable String id) {
        return itemService.deleteById(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<ItemModel>> getItemUpdates(){
        return publisher.getUpdateSink()
                .asFlux()
                .map(item -> ServerSentEvent.builder(item).build());
    }
}
