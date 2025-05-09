package com.gerini.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gerini.game.BattleField;
import com.gerini.game.Coordinates;

@RestController
public class FieldController {
    private BattleField  game=new BattleField();
    
    
    @GetMapping("/getships")
    public HashMap<String,List<Coordinates>> getShips(){
        HashMap<String,List<Coordinates>> map=new HashMap<>();
        game.createPcField();
        game.createRandomPlayerField();
        map.put("pcShips", game.getPcShipsCoordinates());
        map.put("playerShips", game.getPlayerShipsCoordinates());
        return map;
    }
}
