package com.gerini.pojo;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data
@NonNull
public class Ship {
    private int lenght;
    private ArrayList<Coordinates> cordinates = new ArrayList<>();
    private boolean vertical;
    
}
