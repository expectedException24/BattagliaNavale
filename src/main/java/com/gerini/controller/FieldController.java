package com.gerini.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gerini.game.BattleField;
import com.gerini.game.Coordinates;
import com.gerini.game.Ship;

@RestController
public class FieldController {
    private BattleField  game=new BattleField();
    
    
    @GetMapping("/getships")
    public HashMap<String,List<Ship>> getShips(){
        HashMap<String,List<Ship>> map=new HashMap<>();
        game.createPcField();
        game.createRandomPlayerField();
        map.put("pcShips", game.getShipsOfPc());
        map.put("playerShips", game.getShips());
        return map;
    }
    @GetMapping("/attackships/{x}/{y}")
    public boolean attackShips(@PathVariable("x") int x, @PathVariable("y") int y)throws IOException{
        Coordinates c=new Coordinates(x,y);
        try {
            return game.managePlayerAttack(c);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }
}
