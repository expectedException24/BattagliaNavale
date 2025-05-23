package com.gerini.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gerini.game.*;


@RestController
public class FieldController {
    private BattleField  game=new BattleField();
    public String eccezioni;
    
    @GetMapping("/getships")
    public HashMap<String,List<Ship>> getShips(){
        HashMap<String,List<Ship>> map=new HashMap<>();
        game.createPcField();
        game.createRandomPlayerField();
        //map.put("pcShips", game.getShipsOfPc());
        map.put("playerShips", game.getShips());
        return map;
    }
    @GetMapping("/attackships/{x}/{y}")
    public Boolean attackShips(@PathVariable("x") int x, @PathVariable("y") int y){
        Coordinates c=new Coordinates(x,y);
        try {
            return game.managePlayerAttack(c);
        } catch (Exception e) {
            eccezioni=e.getMessage();
            return false;
        }
    }
    @GetMapping("/attackpcships")
    public Hit attackShips(){
        try {
            return game.pcAttack();
        } catch (Exception e) {
            eccezioni=e.getMessage();
            return null;
        }
    }
    @GetMapping("/eccezione")
    public String getEccezione(){
        return eccezioni;
    }
    @GetMapping("/haswon")
    public int hasWon(){
        return game.winCheck();
    }    
    @GetMapping("/reset")
    public void reset(){
        this.game=new BattleField();
    }
}
