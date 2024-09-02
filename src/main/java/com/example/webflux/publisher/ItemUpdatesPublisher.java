package com.example.webflux.publisher;

import com.example.webflux.model.ItemModel;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

@Component
public class ItemUpdatesPublisher {

    private final Sinks.Many<ItemModel> itemModelUpdateManySink; //Множество реактивных сигналов

    public ItemUpdatesPublisher(){
        this.itemModelUpdateManySink = Sinks.many().unicast().onBackpressureBuffer(); //Множество с несколькими подписчиками
    }

    public void publish(ItemModel itemModel){ //Отправляем сигнал
        itemModelUpdateManySink.tryEmitNext(itemModel);
    }

    public Sinks.Many<ItemModel> getUpdateSink(){ //Предоставляет доступ к множеству сигналов
        return itemModelUpdateManySink;
    }
}
