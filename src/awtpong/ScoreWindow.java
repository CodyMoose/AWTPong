package awtpong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
// import java.awt.Insets;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Window that holds the game. Holds paddles, ball, and scores. Closing it will
 * close program.
 * 
 * @author Cody Moose
 * @since 2016
 * @version 1.0
 */
public class ScoreWindow extends JFrame implements KeyListener
{
    /**
     * 
     */
    private static final long      serialVersionUID     = -6831488356359858965L;
    /**
     * Panel which holds scores
     */
    private JPanel                 scorePanel           = new JPanel();
    /**
     * Integer value that keeps Player 1's score for displaying
     */
    private int                    player1Score         = 0;
    /**
     * Integer value that keeps Player 2's score for displaying
     */
    private int                    player2Score         = 0;
    private Label                  player1Lbl           = new Label("Player 1");
    private Label                  player2Lbl           = new Label("Player 2");
    /**
     * Label to display Player 1's score
     */
    private Label                  player1ScoreLbl      = new Label("0");
    /**
     * Label to display Player 2's score
     */
    private Label                  player2ScoreLbl      = new Label("0");
    /**
     * Window for Player 1's paddle(the red one)
     */
    private Paddle                 player1Paddle        = new Paddle(true);
    /**
     * Window for Player 2's paddle(the green one)
     */
    private Paddle                 player2Paddle        = new Paddle(false);
    /**
     * Window for ball
     */
    private Ball                   ball                 = new Ball();
    /**
     * Dimension for screen size used for game borders
     */
    private static final Dimension SCREEN_SIZE          = Toolkit.getDefaultToolkit().getScreenSize();
    /**
     * Height element of screen size, used to keep from calling getHeight()
     * function of the screen size dimension every time
     */
    private static final double    SCREEN_HEIGHT        = SCREEN_SIZE.getHeight();
    /**
     * Width element of screen size, used to keep from calling getWidth()
     * function of the screen size dimension every time
     */
    private static final double    SCREEN_WIDTH         = SCREEN_SIZE.getWidth();
    /**
     * Maximum angle from the horizontal ball can move at
     */
    private static final double    MAX_ANGLE            = 60;
    /**
     * Multiplier used to control ball speed, increasing as time goes on for
     * increased difficulty
     */
    public static double           speedMultiplier      = 10;
    /**
     * Constant used to keep ball from going too fast
     */
    public static final double     MAX_SPEED_MULTIPLIER = 40;
    /**
     * Constant used to keep ball from going too slow
     */
    public static final double     MIN_SPEED_MULTIPLIER = 10;
    /**
     * Boolean used to determine if UP key is being held
     */
    private static boolean         upHeld               = false;
    /**
     * Boolean used to determine if DOWN key is being held
     */
    private static boolean         downHeld             = false;
    /**
     * Boolean used to determine if "A" key is being held
     */
    private static boolean         aHeld                = false;
    /**
     * Boolean used to determine if "Z" key is being held
     */
    private static boolean         zHeld                = false;
    /**
     * The time at which the game was started in miliseconds. Used for refresh
     * rate.
     */
    private static final long      START_TIME           = System.currentTimeMillis();
    /**
     * The optimal fps to keep the game running at.
     */
    private static final int       OPTIMAL_FPS          = 60;
    /**
     * Amount of miliseconds between each frame at optimal frame rate
     */
    private static final double    SKIP_TICKS           = 1000 / OPTIMAL_FPS;
    /**
     * The time at which the next game tick should occur
     */
    private static double          nextGameTick         = START_TIME + SKIP_TICKS;
    /**
     * How many ticks to sleep each cycle to keep with the optimal FPS
     */
    private static double          sleepTicks           = 0;
    /**
     * Y-coordinate for bottom of this frame for reflection purposes
     */
    private static int             thisBottom           = 100;
    /**
     * Label to indicate intellectual property rights held by Robert Cody Moose
     */
    public static Label            copyrightLbl         = new Label("©Cody Moose, 2016");

    /**
     * Sets properties of ScoreWindow
     */
    public ScoreWindow()
    {
        setTitle("AWT Pong");
        setSize((int) SCREEN_WIDTH, (int) SCREEN_HEIGHT / 15);
        setDefaultCloseOperation(3);
        setLocation(0, 0);
        setUndecorated(true);
        setOpacity(0.75f);
        setBackground(new Color(0, 0, 0));
        scorePanel.setVisible(true);
        player1Paddle.setPositionInfo();
        player2Paddle.setPositionInfo();
        setResizable(false);
        player1Lbl.setForeground(Colors.SCORE_YELLOW);
        player2Lbl.setForeground(Colors.SCORE_YELLOW);
        player1ScoreLbl.setForeground(Color.WHITE);
        player2ScoreLbl.setForeground(Color.WHITE);
        copyrightLbl.setForeground(Color.WHITE);
        player1Lbl.setBackground(Color.BLACK);
        player2Lbl.setBackground(Color.BLACK);
        player1ScoreLbl.setBackground(Color.BLACK);
        player2ScoreLbl.setBackground(Color.BLACK);
        copyrightLbl.setBackground(Color.BLACK);
        Font font = new Font("Comic Sans MS", 1, 32);
        player1Lbl.setFont(font);
        player2Lbl.setFont(font);
        player1ScoreLbl.setFont(font);
        player2ScoreLbl.setFont(font);
        Font cpFont = new Font("Comic Sans MS", 1, 12);
        copyrightLbl.setFont(cpFont);
        add(player1Lbl);
        add(player1ScoreLbl);
        add(player2ScoreLbl);
        add(player2Lbl);
        add(copyrightLbl);
        player1Lbl.setVisible(true);
        player2Lbl.setVisible(true);
        player1ScoreLbl.setVisible(true);
        player2ScoreLbl.setVisible(true);
        copyrightLbl.setVisible(true);
        player1Lbl.setAlignment(Label.RIGHT);
        player1ScoreLbl.setAlignment(Label.CENTER);
        player2ScoreLbl.setAlignment(Label.CENTER);
        player2Lbl.setAlignment(Label.LEFT);
        copyrightLbl.setAlignment(Label.CENTER);
        player1Lbl.setBounds((int) (SCREEN_WIDTH / 2 - 250), 0, 200, getHeight());
        player1ScoreLbl.setBounds(player1Lbl.getX() + player1Lbl.getWidth(), 0, 50, getHeight());
        player2ScoreLbl.setBounds(player1ScoreLbl.getX() + player1ScoreLbl.getWidth(), 0, 50, getHeight());
        player2Lbl.setBounds(player2ScoreLbl.getX() + player2ScoreLbl.getWidth(), 0, 200, getHeight());
        copyrightLbl.setBounds((int) (SCREEN_SIZE.getWidth() - 150), 0, 150, getHeight());
        addKeyListener(this);
        thisBottom = getY() + getHeight();
        scorePanel.setBackground(new Color(0, 0, 0));
        add(scorePanel);
        setVisible(true);
    }

    public void paint(Graphics g)
    {
        ball.setPositionInfo();
        player1Paddle.setPositionInfo();
        player2Paddle.setPositionInfo();
        super.paint(g);
        if (checkCollisionP1())
        {
            doCollisionP1();
        }
        if (checkCollisionP2())
        {
            doCollisionP2();
        }
        if (aHeld && !zHeld) player1Paddle.paddleUp(thisBottom);
        if (zHeld && !aHeld) player1Paddle.paddleDown();
        if (upHeld && !downHeld) player2Paddle.paddleUp(thisBottom);
        if (downHeld && !upHeld) player2Paddle.paddleDown();
        if (ball.bottom >= SCREEN_HEIGHT - 40 || ball.top <= getY() + getHeight())
        {
            if (ball.bottom >= SCREEN_HEIGHT - 40)
            {
                ball.setTrueY(((SCREEN_HEIGHT - 40) - ball.getHeight()) - 1);
            }
            else if (ball.top <= thisBottom) ball.setTrueY(thisBottom + 1);
            ball.deltaY = -1 * ball.deltaY;
        }
        ball.move();
        if (ball.right >= SCREEN_WIDTH)
        {
            player1Score++;
            player1ScoreLbl.setText(Integer.toString(player1Score));
            ball.set(SCREEN_WIDTH / 2., SCREEN_HEIGHT / 2.);
            double angle = (Math.random() - .5) * 2. * MAX_ANGLE;
            speedMultiplier = MIN_SPEED_MULTIPLIER;
            ball.deltaX = -1 * speedMultiplier * Math.cos(Math.toRadians(angle));
            ball.deltaY = speedMultiplier * Math.sin(Math.toRadians(angle));
            doLabelColorSwitch();
        }
        if (ball.left <= 0)
        {
            player2Score++;
            player2ScoreLbl.setText(Integer.toString(player2Score));
            ball.set(SCREEN_WIDTH / 2., SCREEN_HEIGHT / 2.);
            double angle = (Math.random() - .5) * 2. * MAX_ANGLE;
            speedMultiplier = MIN_SPEED_MULTIPLIER;
            ball.deltaX = speedMultiplier * Math.cos(Math.toRadians(angle));
            ball.deltaY = speedMultiplier * Math.sin(Math.toRadians(angle));
            doLabelColorSwitch();
        }
        sleepUntilNextFrame();
        repaint();
    }

    public void keyTyped(KeyEvent e)
    {
    }

    public void keyPressed(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_A:
                aHeld = true;
                break;
            case KeyEvent.VK_Z:
                zHeld = true;
                break;
            case KeyEvent.VK_UP:
                upHeld = true;
                break;
            case KeyEvent.VK_DOWN:
                downHeld = true;
                break;
            case KeyEvent.VK_NUMPAD5:
                upHeld = true;
                break;
            case KeyEvent.VK_NUMPAD2:
                downHeld = true;
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
            case KeyEvent.VK_R:
                player1Score = 0;
                player1ScoreLbl.setText(Integer.toString(player1Score));
                player2Score = 0;
                player2ScoreLbl.setText(Integer.toString(player2Score));
                ball.set(SCREEN_WIDTH / 2., SCREEN_HEIGHT / 2.);
                double angle = (Math.random() - .5) * 2. * MAX_ANGLE;
                speedMultiplier = MIN_SPEED_MULTIPLIER;
                Random r = new Random();
                ball.deltaX = (r.nextBoolean()) ? -1 : 1 * speedMultiplier * Math.cos(Math.toRadians(angle));
                ball.deltaY = speedMultiplier * Math.sin(Math.toRadians(angle));
                doLabelColorSwitch();
                break;
        }
    }

    public void keyReleased(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_A:
                aHeld = false;
                break;
            case KeyEvent.VK_Z:
                zHeld = false;
                break;
            case KeyEvent.VK_UP:
                upHeld = false;
                break;
            case KeyEvent.VK_DOWN:
                downHeld = false;
                break;
            case KeyEvent.VK_NUMPAD5:
                upHeld = false;
                break;
            case KeyEvent.VK_NUMPAD2:
                downHeld = false;
                break;
        }
    }

    public boolean checkIfPaddle()
    {
        return (((ball.left < player1Paddle.right) && (ball.top < player1Paddle.bottom)
                        && (ball.bottom > player1Paddle.top))
                        || ((ball.right > player2Paddle.left) && (ball.top < player2Paddle.bottom)
                                        && (ball.bottom > player2Paddle.top)));
    }

    private boolean checkCollisionP1()
    {
        return ball.getBounds().intersects(player1Paddle.getBounds());
    }

    private boolean checkCollisionP2()
    {
        return ball.getBounds().intersects(player2Paddle.getBounds());
    }

    /**
     * Performs collision between ball and player 1's paddle
     */
    private void doCollisionP1()
    {
        ball.reflectionAngle = MAX_ANGLE * (ball.middleY - player1Paddle.middleY) / (player1Paddle.getHeight() / 2);
        if (Math.abs(ball.reflectionAngle) > MAX_ANGLE)
        {
            double tempAngle = ball.reflectionAngle;
            ball.reflectionAngle = MAX_ANGLE;
            if (tempAngle < 0)
            {
                ball.reflectionAngle = -1 * ball.reflectionAngle;
            }
        }
        speedMultiplier += 3;
        ball.deltaX = speedMultiplier * Math.cos(Math.toRadians(ball.reflectionAngle));
        ball.deltaY = speedMultiplier * Math.sin(Math.toRadians(ball.reflectionAngle));
        if (speedMultiplier > MAX_SPEED_MULTIPLIER)
        {
            speedMultiplier = MAX_SPEED_MULTIPLIER;
        }
        ball.player1Hit = !ball.player1Hit;
        ball.repaint();
        ball.setTrueX(player1Paddle.right + 1);
    }

    /**
     * Performs collision between ball and player 2's paddle
     */
    private void doCollisionP2()
    {
        ball.reflectionAngle = MAX_ANGLE * (ball.middleY - player2Paddle.middleY) / (player2Paddle.getHeight() / 2);
        double tempAngle = ball.reflectionAngle;
        if (Math.abs(ball.reflectionAngle) > MAX_ANGLE)
        {
            ball.reflectionAngle = MAX_ANGLE;
        }
        if (tempAngle < 0 && ball.reflectionAngle > 0)
        {
            ball.reflectionAngle = -1 * ball.reflectionAngle;
        }
        speedMultiplier += 3;
        ball.deltaX = -1 * speedMultiplier * Math.cos(Math.toRadians(ball.reflectionAngle));
        ball.deltaY = speedMultiplier * Math.sin(Math.toRadians(ball.reflectionAngle));
        if (speedMultiplier > MAX_SPEED_MULTIPLIER)
        {
            speedMultiplier = MAX_SPEED_MULTIPLIER;
        }
        ball.player1Hit = !ball.player1Hit;
        ball.repaint();
        ball.setTrueX((player2Paddle.left - 1) - ball.getWidth());
    }

    /**
     * Makes program sleep so that it does not surpass FPS cap
     */
    private void sleepUntilNextFrame()
    {
        nextGameTick += SKIP_TICKS;
        sleepTicks = nextGameTick - System.currentTimeMillis();
        if (sleepTicks >= 0)
        {
            try
            {
                Thread.sleep((long) sleepTicks);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sets color of winning player's label to green, losing player to red, and if tied, sets both to yellow
     */
    private void doLabelColorSwitch()
    {
        if (player1Score > player2Score)
        {
            player1Lbl.setForeground(Colors.SCORE_GREEN);
            player2Lbl.setForeground(Colors.SCORE_RED);
        }
        else if (player1Score < player2Score)
        {
            player1Lbl.setForeground(Colors.SCORE_RED);
            player2Lbl.setForeground(Colors.SCORE_GREEN);
        }
        else if (player1Score == player2Score)
        {
            player1Lbl.setForeground(Colors.SCORE_YELLOW);
            player2Lbl.setForeground(Colors.SCORE_YELLOW);
        }
    }
}