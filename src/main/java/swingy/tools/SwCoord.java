package swingy.tools;

import lombok.Getter;
import lombok.Setter;

public class SwCoord
{
    @Getter @Setter
    private int x;

    @Getter @Setter
    private int y;

    /*
     * Constructor
     */
    public SwCoord(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /*
     * Public Methods
     */
    public boolean equals(SwCoord position) {
        return (this.x == position.x && this.y == position.y);
    }
}
