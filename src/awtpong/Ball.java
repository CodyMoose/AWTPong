package awtpong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
// import java.awt.Graphics;
import java.awt.Toolkit;
// import java.awt.image.BufferedImage;
// import java.io.File;
// import java.io.IOException;
// import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Window that holds the Ball and all its info
 * 
 * @author Cody Moose
 * @since 2016
 * @version 1.0
 */
public class Ball extends JFrame
{
    /**
     * 
     */
    private static final long               serialVersionUID    = 5297259536316064354L;
    /**
     * Boolean that indicates whether or not the last paddle hit was Player 1's
     * paddle. Used for determining horizontal component of direction
     */
    protected boolean                       player1Hit          = true;
    /**
     * Panel that holds the Ball and all its aspects
     */
    protected JPanel                        ballPanel           = new JPanel();
    /**
     * Left X coordinate of Ball. Used for determining collisions
     */
    public double                           left                = getX();
    /**
     * Right X coordinate of Ball. Used for determining collisions
     */
    public double                           right               = getX() + getWidth();
    /**
     * Top Y coordinate of Ball. Used for determining collisions
     */
    public double                           top                 = getY();
    /**
     * Bottom Y coordinate of Ball. Used for determining collisions
     */
    public double                           bottom              = getY() + getHeight();
    /**
     * How far, and in what direction to move the ball horizontally
     */
    public double                           deltaX              = 0.1;
    /**
     * How far, and in what direction to move the ball vertically
     */
    public double                           deltaY              = 0;
    /**
     * Middle Y coordinate of the Ball. Used for determining paddle reflection
     * angles
     */
    public double                           middleY             = getY() + getHeight() / 2;
    /**
     * Angle from which the ball will reflect off of the paddle. Always reflects
     * from paddle back towards center of boundary
     */
    public double                           reflectionAngle     = 0;
    /**
     * True X coordinate of ball. Used for accuracy, because otherwise ball
     * would not move, because floats often round down when parsed to integers
     */
    public double                           trueX               = 800;
    /**
     * True Y coordinate of ball. Used for accuracy, because otherwise ball
     * would not move, because floats often round down when parsed to integers
     */
    public double                           trueY               = 450;
    /**
     * Array of colors used for green and magenta ball gradient
     */
    private static final Color[]            G_M_GRADIENT_COLORS = { new Color(0, 255, 0), new Color(255, 0, 255) };
    /**
     * Array of colors used for blue and yellow ball gradient
     */
    private static final Color[]            B_Y_GRADIENT_COLORS = { new Color(0, 0, 255), new Color(255, 255, 0) };
    /**
     * Array of floats used for showing locations of gradient colors
     */
    private static final float[]            GRADIENT_FLOATS     = { 0f, 1f };
    /**
     * Radial gradient of green in the middle, magenta on the outside
     */
    public static final RadialGradientPaint GREEN_MAGENTA       = new RadialGradientPaint(25, 25, 25, GRADIENT_FLOATS,
                    G_M_GRADIENT_COLORS);
    /**
     * Radial gradient of blue in the middle, yellow on the outside
     */
    public static final RadialGradientPaint BLUE_YELLOW         = new RadialGradientPaint(25, 25, 25, GRADIENT_FLOATS,
                    B_Y_GRADIENT_COLORS);
    /**
     * Boolean used to determine which gradient is currently being used to paint
     * the ball
     */
    private boolean                         paintColor          = true;

    /**
     * Sets properties of Ball Window
     */
    public Ball()
    {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        deltaX = (Math.random() - 0.5) > 1 ? ScoreWindow.speedMultiplier : -1 * ScoreWindow.speedMultiplier;
        setSize(50, 50);
        setDefaultCloseOperation(3);
        setUndecorated(true);
        setVisible(true);
        setLocation(d.width / 2, d.height / 2);
        add(this.ballPanel);
        this.ballPanel.setVisible(true);
        trueX = getX();
        trueY = getY();
        setPositionInfo();
        setBackground(new Color(0, 0, 0, 0));
        repaint();
    }

    public final void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        if (paintColor)
        {
            g2.setPaint(BLUE_YELLOW);
        }
        else
        {
            g2.setPaint(GREEN_MAGENTA);
        }
        paintColor = !paintColor;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillOval(0, 0, getWidth(), getHeight());
    }

    /**
     * Gives true X coordinate of the ball
     * 
     * @return trueX, the true X coordinate of the ball
     */
    public double getTrueX()
    {
        return trueX;
    }

    /**
     * Gives true Y coordinate of the ball
     * 
     * @return trueY, the true Y coordinate of the ball
     */
    public double getTrueY()
    {
        return trueY;
    }

    /**
     * Moves ball horizontally to new x-coordinate
     * 
     * @param X
     *            x-coordinate for ball to be moved to
     */
    public void setTrueX(double X)
    {
        trueX = X;
        setTrueLocation(trueX, trueY);
    }

    /**
     * Moves ball horizontally to new y-coordinate
     * 
     * @param Y
     *            y-coordinate for ball to be moved to
     */
    public void setTrueY(double Y)
    {
        trueY = Y;
        setTrueLocation(trueX, trueY);
    }

    /**
     * Gives true location of upper left corner of Ball
     * 
     * @return coordinates of upper left corner of Ball
     */
    public Dimension getTrueLocation()
    {
        Dimension location = new Dimension((int) trueX, (int) trueY);
        return location;
    }

    /**
     * Sets location of upper left corner of ball
     * 
     * @param X
     *            x-coordinate for upper left corner of ball
     * @param Y
     *            x-coordinate for upper left corner of ball
     */
    public void setTrueLocation(double X, double Y)
    {
        trueX = X;
        trueY = Y;
        setLocation((int) trueX, (int) trueY);
    }

    /**
     * Sets location of upper left corner of ball
     * 
     * @param d
     *            dimension of ball in (X, Y) format
     */
    public void setTrueLocation(Dimension d)
    {
        trueX = d.getWidth();
        trueY = d.getHeight();
        setLocation((int) trueX, (int) trueY);
    }

    /**
     * Moves ball to new position based on deltaX and deltaY
     */
    public void move()
    {
        trueX += deltaX;
        trueY += deltaY;
        setTrueLocation(trueX, trueY);
        setLocation((int) trueX, (int) trueY);
    }

    /**
     * Sets information about position of ball, such as top, bottom, and middle
     * y-coordinates, and left and right x-coordinates
     */
    public void setPositionInfo()
    {
        middleY = trueY + getHeight() / 2;
        top = trueY;
        bottom = trueY + getHeight();
        left = trueX;
        right = trueX + getWidth();
    }

    /**
     * Sets ball exactly at position (X, Y)
     * 
     * @param X
     *            x-coordinate for the center of the ball to be moved to
     * @param Y
     *            y-coordinate for the center of the ball to be moved to
     */
    public void set(double X, double Y)
    {
        X -= getWidth() / 2.;
        Y -= getHeight() / 2.;
        setTrueLocation(X, Y);
        setLocation((int) X, (int) Y);
    }
    // public Circle2D getBounds(){
    //
    // }
}
