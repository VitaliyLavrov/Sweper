package sweeper;

import java.util.IllegalFormatCodePointException;

class Bomb {
    private Matrix bombMap;
    private int totalBombs;

    public Bomb(int totalBombs) {
        this.totalBombs = totalBombs;
        fixBombsCount();
    }

    private void fixBombsCount() {
        int maxBombs = Ranges.getSize().x* Ranges.getSize().y /2;
        if (totalBombs> maxBombs){
            totalBombs = maxBombs;
        }
    }

    void start() {
        bombMap = new Matrix(Box.ZERO);
        for (int i = 0; i < totalBombs; i++) {
            placeBomb();
        }
    }

    Box get (Coord coord) {
        return bombMap.get(coord);
    }

    private void placeBomb() {
        Coord coord = Ranges.getRandomCoord();
        if (bombMap.get(coord) != Box.BOMB){
            bombMap.set(coord, Box.BOMB);
            incNumAroundBomb(coord);
        } else placeBomb();

    }

    private void incNumAroundBomb(Coord coord){
        for (Coord around : Ranges.getCoordsAround(coord)) {
            if (Box.BOMB !=bombMap.get(around))
            bombMap.set(around, bombMap.get(around).nextNumBox());
        }
    }

    public int getTotalBobs() {
        return totalBombs;
    }
}
