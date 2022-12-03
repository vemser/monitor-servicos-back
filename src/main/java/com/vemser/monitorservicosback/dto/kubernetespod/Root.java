package com.vemser.monitorservicosback.dto.kubernetespod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Root {
    public String kind;
    public String apiVersion;
    public Metadata metadata;
    public ArrayList<Item> items;
}
