package com.example.webflux.model;

import com.example.webflux.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemModel {

    private String name;

    private String id;

    private Integer count;

    private List<SubItemModel> subItems;

    public static ItemModel from(Item item) { //Потом изменим
        return new ItemModel(item.getName(), item.getId(), item.getCount(), new ArrayList<>());
    }
}
