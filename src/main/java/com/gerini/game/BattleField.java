package com.gerini.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Getter;

public class BattleField {
    @Getter
    private List<Ship> ships = new ArrayList<>();
    @Getter
    private List<Ship> shipsOfPc = new ArrayList<>();
    @Getter
    private List<Hit> pcHitList = new ArrayList<>();
    @Getter
    private List<Hit> userHitList = new ArrayList<>();

    private int l4 = 1;// corazzate
    private int l3 = 3;// sottomarini
    private int l2 = 3;// corvette
    private int l1 = 2;// lancie

    public void addPlayerShip(Coordinates cordinate, int size, boolean vertical) throws IOException {

        try {
            switch (size) {
                case 4:
                    if (l4 > 0) {
                        l4--;
                        addAShip(cordinate, size, vertical, ships);
                    } else {
                        throw new IOException("nave già posizionata il numero massivo di volte");
                    }
                    break;
                case 3:
                    if (l3 > 0) {
                        l3--;
                        addAShip(cordinate, size, vertical, ships);
                    } else {
                        throw new IOException("nave già posizionata il numero massivo di volte");
                    }
                    break;
                case 2:
                    if (l2 > 0) {
                        l2--;
                        addAShip(cordinate, size, vertical, ships);
                    } else {
                        throw new IOException("nave già posizionata il numero massivo di volte");
                    }
                    break;
                case 1:
                    if (l1 > 0) {
                        l1--;
                        addAShip(cordinate, size, vertical, ships);
                    } else {
                        throw new IOException("nave già posizionata il numero massivo di volte");
                    }
                    break;
                default:
                    throw new IOException("dimensione non ammessa");
            }
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    public boolean managePlayerAttack(Coordinates coordToAttack) throws IOException {
        if (hasAlreadyTryed(coordToAttack, userHitList)) {
            throw new IOException("Coordinate già tentate");
        }
        return haveAHit(shipsOfPc, coordToAttack);
    }

    public boolean isPlayerFieldFull() {
        if (ships.size() == 9)
            return true;
        return false;
    }

    public Hit pcAttack() throws IOException {
        Coordinates c;
        do {
            Random rand = new Random();
            c = new Coordinates(rand.nextInt(10), rand.nextInt(10));
        } while (hasAlreadyTryed(c, pcHitList));
        Hit hit = new Hit(c, null);

        try {
            if (haveAHit(ships, c)) {
                hit.setHasHit(true);
                pcHitList.add(hit);
                return hit;
            } else {
                hit.setHasHit(false);
                pcHitList.add(hit);
                return hit;
            }
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }

    }

    public void createPcField() {
        if (shipsOfPc.isEmpty())
            createField(shipsOfPc);
    }

    public void createRandomPlayerField() {
        createField(ships);
    }

    public int winCheck() {
        if (isEmptyField(ships)) {
            return 1; // ritorna 1 se il campo del player è vuoto (ha perso)
        }
        if (isEmptyField(shipsOfPc)) {
            return 2; // ritorna 2 se il campo del bot è vuoto (ha perso) gg
        }
        return 3; // nessuno ha perso
    }

    public List<Coordinates> getPlayerShipsCoordinates() {
        return getCords(ships);
    }

    public List<Coordinates> getPcShipsCoordinates() {
        return getCords(shipsOfPc);
    }

    public int hasAPlayerShipSunk() {
        return witchShipSunk(ships);
    }

    public int hasAEnemyShipSunk() {
        return witchShipSunk(shipsOfPc);
    }

    private int witchShipSunk(List<Ship> shipsToBo) {
        for (Ship ship : shipsToBo) {
            if (ship.getCordinates().size() == 0) {
                shipsToBo.remove(ship);
                return ship.getLenght();
            }
        }
        return -1;
    }

    private Boolean hasAlreadyTryed(Coordinates c, List<Hit> HitList) {
        for (Hit hit : HitList) {
            if (hit.getCoordinateAttack().equals(c)) {
                return true;
            }
        }
        return false;
    }

    private List<Coordinates> getCords(List<Ship> shipsToBo) {
        List<Coordinates> cords = new ArrayList<>();
        for (Ship ship : shipsToBo) {
            cords.addAll(ship.getCordinates());
        }
        return cords;
    }

    private boolean isEmptyField(List<Ship> shipsToCheck) {
        for (Ship ship : shipsToCheck) {
            if (ship.getCordinates().size() > 0) {
                return false;
            }
        }
        return true;
    }

    private List<Ship> createField(List<Ship> field) {
        field.clear();
        for (int i = 0; i < l4; i++) {
            field.add(getRandomShip(4, field));
        }
        for (int i = 0; i < l3; i++) {
            field.add(getRandomShip(3, field));
        }
        for (int i = 0; i < l2; i++) {
            field.add(getRandomShip(2, field));
        }
        for (int i = 0; i < l1; i++) {
            field.add(getRandomShip(1, field));
        }
        return field;
    }

    private Ship getRandomShip(int size, List<Ship> shipsToCheck) {
        Random rand = new Random();
        boolean success = false;
        Ship s = null;
        do {
            try {
                Coordinates c = new Coordinates(rand.nextInt(10), rand.nextInt(10));
                s = createShip(c, size, rand.nextBoolean());
                success = checkValidShipPosition(s, shipsToCheck);
            } catch (Exception e) {
                success = false;
            }
        } while (!success);
        return s;
    }

    private boolean haveAHit(List<Ship> shipsToCheck, Coordinates coordToAttack) throws IOException {
        if (coordToAttack.getX() < 0 || coordToAttack.getX() >= 10 || coordToAttack.getY() < 0
                || coordToAttack.getY() >= 10) {
            throw new IOException("coordinate non permesse");
        }
        for (Ship ship : shipsToCheck) {
            if (ship.getCordinates().remove(coordToAttack))
                return true;
        }
        return false;
    }

    private void addAShip(Coordinates cordinate, int size, boolean vertical, List<Ship> listOfShips) throws Exception {
        Ship nShip = createShip(cordinate, size, vertical);
        if (!checkValidShipPosition(nShip, ships)) {
            throw new IOException("la nave è sopra a un'altra");
        }
        listOfShips.add(nShip);
    }

    private boolean checkValidShipPosition(Ship nShip, List<Ship> shipsToCheck)
            throws IOException {
        if (nShip.getCordinates().get(0).getX() < 0 || nShip.getCordinates().get(0).getX() >= 10
                || nShip.getCordinates().get(0).getY() < 0 || nShip.getCordinates().get(0).getY() >= 10) {
            throw new IOException("il campo non è così grande ");
        }
        if (nShip.isVertical()) {
            if (nShip.getCordinates().get(0).getY() - (nShip.getLenght() - 1) < 0) {
                throw new IOException("il campo non è così grande ");
            }
        } else {
            if (nShip.getCordinates().get(0).getX() + (nShip.getLenght() - 1) >= 10) {
                throw new IOException("il campo non è così grande ");
            }
        }
        ArrayList<Coordinates> c = nShip.getCordinates();
        for (Ship ship : shipsToCheck) {
            for (int i = 0; i < ship.getCordinates().size(); i++) {
                for (Coordinates cordinate2 : c) {
                    if (ship.getCordinates().get(i).equals(cordinate2)) {
                        return false;
                    }
                }

            }
        }
        return true;
    }

    private Ship createShip(Coordinates cordinate, int size, boolean vertical) {
        ArrayList<Coordinates> c = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (vertical) {
                Coordinates cord = new Coordinates(cordinate.getX(), cordinate.getY() - i);
                c.add(cord);
            } else {
                Coordinates cord = new Coordinates(cordinate.getX() + i, cordinate.getY());
                c.add(cord);
            }
        }
        Ship s = new Ship(size, c, vertical);
        return s;
    }

}
