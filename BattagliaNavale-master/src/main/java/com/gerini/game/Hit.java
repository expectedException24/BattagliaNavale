package com.gerini.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Data
public class Hit {

    private Coordinates coordinateAttack;
    private Boolean hasHit;
}