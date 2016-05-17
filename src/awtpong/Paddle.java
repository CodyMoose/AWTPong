package awtpong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Window that holds the Paddle and all its info
 * 
 * @author Cody Moose
 * @since 2016
 * @version 1.0
 */
public class Paddle extends JFrame
{
    /**
     * 
     */
    private static final long      serialVersionUID = 9168092458364571287L;
    /**
     * Color for player 1(left) paddle. Currently red
     */
    public static final Color      P1_PADDLE_COLOR    = new Color(255, 0, 0);
    /**
     * Color for player 1(right) paddle. Currently green
     */
    public static final Color      P2_PADDLE_COLOR    = new Color(0, 255, 0);
    /**
     * Left X coordinate of Paddle. Used for determining collisions
     */
    public double                  left             = getX();
    /**
     * Right X coordinate of Paddle. Used for determining collisions
     */
    public double                  right            = getX() + getWidth();
    /**
     * Top Y coordinate of Paddle. Used for determining collisions
     */
    public double                  top              = getY();
    /**
     * Bottom Y coordinate of Paddle. Used for determining collisions
     */
    public double                  bottom           = getY() + getHeight();
    /**
     * Middle Y coordinate of the Paddle. Used for determining paddle reflection
     * angles
     */
    public double                  middleY          = getY() + getHeight() / 2;
    /**
     * Dimension for screen size used for game borders
     */
    private static final Dimension SCREEN_SIZE       = Toolkit.getDefaultToolkit().getScreenSize();
    /**
     * Height element of screen size, used to keep from calling getHeight()
     * function of the screen size dimension every time
     */
    private static final double    SCREEN_HEIGHT     = SCREEN_SIZE.getHeight();
    /**
     * Maximum distance a paddle may move each frame
     */
    private static final int       PADDLE_MOVE_DISTANCE = 30;

    /**
     * Sets properties of Paddle window
     * 
     * @param player1
     *            Indicates whether paddle is for Player 1 or Player 2, and sets
     *            position and color accordingly
     */
    public Paddle(boolean player1)
    {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        JPanel panel = new JPanel();
        setSize(50, 200);
        add(panel);
        setUndecorated(true);
        setVisible(true);
        if (player1)
        {
            setLocation(10, d.height / 2 - getHeight() / 2);
            panel.setBackground(Colors.GREY_BLUE);
        }
        else
        {
            setLocation(d.width - getWidth() - 10, d.height / 2 - getHeight() / 2);
            panel.setBackground(Colors.DANDELION);
        }
    }

    /**
     * Brings paddle up.
     */
    public void paddleUp(int Y)
    {
        if (top - PADDLE_MOVE_DISTANCE > Y) setLocation(getX(), getY() - PADDLE_MOVE_DISTANCE);
        else
            setLocation(getX(), Y);
    }

    /**
     * Brings paddle down.
     */
    public void paddleDown()
    {
        if (bottom + PADDLE_MOVE_DISTANCE < SCREEN_HEIGHT - 40) setLocation(getX(), getY() + PADDLE_MOVE_DISTANCE);
        else
            setLocation(getX(), (int) (SCREEN_HEIGHT - 40 - getHeight()));
    }

    /**
     * Sets information about position of paddle, such as top, bottom, and
     * middle y-coordinates, and left and right x-coordinates
     */
    public void setPositionInfo()
    {
        middleY = getY() + getHeight() / 2;
        top = getY();
        bottom = getY() + getHeight();
        left = getX();
        right = getX() + getWidth();
    }
}
