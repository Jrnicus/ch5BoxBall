import java.awt.Color;
import java.util.Random;
import java.util.ArrayList;
/**
 * Class BallDemo - a short demonstration showing animation with the 
 * Canvas class. 
 *
 * @author Steve Cate
 * @version 210/08/2019
 *
 * @author Michael Kölling and David J. Barnes
 */

public class BallDemo   
{
    private Canvas myCanvas;
    private Random rand;
    private ArrayList<Color> colors;
    private ArrayList<BouncingBall> balls;

    /**
     * Create a BallDemo object. Creates a fresh canvas and makes it visible.
     */
    public BallDemo()
    {
        myCanvas = new Canvas("Ball Demo", 600, 500);
        rand = new Random();
        colors = new ArrayList<Color>();
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.ORANGE);
        colors.add(Color.PINK);
        balls = new ArrayList<BouncingBall>();
    }

    /**
     * Simulate bouncing balls the number of balls bouncing
     * is given by the user and the balls are then radnomly generated
     * @param numberOfBalls the number of balls you want to display
     */
    public void bounce(int numberOfBalls)
    {
        int ground = 400;   // position of the ground line

        myCanvas.setVisible(true);

        // draw the ground
        myCanvas.drawLine(50, ground, 550, ground);

        // crate and show the balls
        int i = 0;
        while (i < numberOfBalls)
        {   
            balls.add(new BouncingBall (rand.nextInt(300)+50, rand.nextInt(400), rand.nextInt(40)+5, 
                    colors.get(rand.nextInt(5)), ground, myCanvas));
                    
            balls.get(i).draw();
            i++;
        }

        // make them bounce
        boolean finished =  false;
        while(!finished) 
        {
            myCanvas.wait(50);           // small delay
            
            for(BouncingBall ball : balls)
            {
                ball.move();
            }
            // stop once ball has travelled a certain distance on x axis
            for(BouncingBall ball : balls)
            {    
                if(ball.getXPosition() >= 550) 
                {
                    finished = true;
                }
            }
        }
    }
}
