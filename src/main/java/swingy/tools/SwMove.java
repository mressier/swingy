package swingy.tools;

public enum SwMove {

    NORTH ("north (↑)"),
    SOUTH ("south (↓)"),
    EAST ("east (→)"),
    WEST ("west (←)");

    static public SwMove[]  allMoves = {SwMove.NORTH, SwMove.SOUTH, SwMove.WEST, SwMove.EAST};

    private String move;
    SwMove(String move) {
        this.move = move;
    }

    @Override
    public String toString() {
        return this.move;
    }


    static public boolean   isMove(String str) {
        return (toMove(str) != null);
    }

    static public SwMove    toMove(String str)
    {
        for (SwMove move : SwMove.allMoves)
        {
            if (str.equals(move.toString()))
                return (move);
        }
        return (null);
    }

    public boolean isNorth() { return this.move.equals(SwMove.NORTH.toString()); }
    public boolean isSouth() { return this.move.equals(SwMove.SOUTH.toString()); }
    public boolean isEast() { return this.move.equals(SwMove.EAST.toString()); }
    public boolean isWest() { return this.move.equals(SwMove.WEST.toString()); }

    static public SwCoord applyMove(SwMove move, SwCoord position)
    {
        if (move.isNorth()) {
            position.setY(position.getY() - 1);
        }
        else if (move.isSouth()) {
            position.setY(position.getY() + 1);
        }
        else if (move.isEast()) {
            position.setX(position.getX() + 1);
        }
        else if (move.isWest()) {
            position.setX(position.getX() - 1);
        }
        return (position);
    }
}
