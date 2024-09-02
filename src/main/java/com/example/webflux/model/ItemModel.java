package com.example.webflux.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemModel {

    private String name;

    private String id;

    private Integer count;

    private List<SubItemModel> subItems;
}
