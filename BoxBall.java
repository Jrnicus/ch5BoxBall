import java.awt.*;
import java.awt.geom.*;
import java.awt.Dimension;
import java.util.Random;

/**
 * Write a description of class BoxBall here.
 *
 * @author Steve Cate
 * @version 10/10/19
 */
public class BoxBall
{
    private Ellipse2D.Double circle;
    private Color color;
    private Random rand;
    private int diameter;
    private int xPosition;
    private int yPosition;
    private Canvas canvas;
    private int xSpeed;
    private int ySpeed;
    
    // all varibles for outter box walls
    private int leftWall;
    private int rightWall;
    private int bottomWall;
    private int topWall;
    
    // all varibles for inner box walls
    private int innerLeftWall;
    private int innerRightWall;
    private int innerBottomWall;
    private int innerTopWall;

    /**
     * Constructor for objects of class BouncingBall
     *
     * @param xPos  the horizontal coordinate of the ball
     * @param yPos  the vertical coordinate of the ball
     * @param ballDiameter  the diameter (in pixels) of the ball
     * @param ballColor  the color of the ball
     * @param groundPos  the position of the ground (where the wall will bounce)
     * @param drawingCanvas  the canvas to draw this ball on
     */
    public BoxBall(int xPos, int yPos, int ballDiameter, Canvas drawingCanvas)
    {
        rand = new Random();
        
        xPosition = xPos;
        yPosition = yPos;
        diameter = ballDiameter;
        
        canvas = drawingCanvas;
        color = new Color(rand.nextInt(226), rand.nextInt(226), rand.nextInt(226));
        

        
        xSpeed = 0;
        ySpeed = 0;
        
        drawBox();
        drawInnerBox();
        wallCheck();
        
        // to make sure the ball doesnt move directly horizontal or virticle
        while(xSpeed == 0)
        {
            xSpeed = (rand.nextInt(15) - 7);
        }
        while(ySpeed == 0)
        {
            ySpeed = (rand.nextInt(15) - 7);
        }
    }
    
    /**
     * Set up walls as varibles
     */
    public void setWallVariblegs()
    {
        
    }

    /**
     * Draw this ball at its current position onto the canvas.
     **/
    public void draw()
    {
        canvas.setForegroundColor(color);
        canvas.fillCircle(xPosition, yPosition, diameter);
    }

    /**
     * Erase this ball at its current position.
     **/
    public void erase()
    {
        canvas.eraseCircle(xPosition, yPosition, diameter);
    }    

    /**
     * Move this ball according to its position and speed and redraw.
     **/
    public void move()
    {
        // remove from canvas at the current position
        erase();
            
        // compute new position
        yPosition += ySpeed;
        xPosition += xSpeed;
        
        wallCheck();

        // draw again at new position
        draw();
    } 
    
    /**
     * Test to make sure the ball stays within the walls
     */
    public void wallCheck()
    {
        // check to see if it is hitting the outter walls
        // check if it has hit the bottomWall
        if(yPosition >= (bottomWall - diameter))
        {
            yPosition = (int)(bottomWall - diameter);
            ySpeed = -ySpeed;
        }
        // check if it has hit the topWall
        if(yPosition <= topWall)
        {
            yPosition = (int)(topWall);
            ySpeed = -ySpeed;
        }
        // check if it hit the leftWall
        if(xPosition <= leftWall)
        {
            xPosition = (int)(leftWall + diameter);
            xSpeed = -xSpeed;
        }
        // check if it hit the right wall
        if(xPosition >= (rightWall - diameter))
        {
            xPosition = (int)(rightWall - diameter);
            xSpeed = -xSpeed;
        }
        
        // check to see if it hitting the inner walls
        // check if it hit the innerBottomWall or innerTopWall
        if(((yPosition <= innerBottomWall) && (yPosition >= (innerTopWall + diameter))) && 
        ((xPosition >= (innerLeftWall + diameter)) && (xPosition <= innerRightWall)))
        {
            int bottom = Math.abs(innerBottomWall - yPosition);
            int top = Math.abs(innerTopWall - yPosition);
            int left = Math.abs(innerLeftWall - xPosition);
            int right =Math.abs(innerRightWall - xPosition);
            
            if((bottom < left && bottom < right) || (top < left && top <right)) 
            {
                if(ySpeed < 0)
                {
                    yPosition = (int)(innerBottomWall);
                    ySpeed = -ySpeed;
                }
                else
                {
                    yPosition = (int)(innerTopWall + diameter);
                    ySpeed = -ySpeed;
                }
            }
            else
            {
                if(xSpeed > 0)
                {
                    xPosition = (int)(innerLeftWall + diameter);
                    xSpeed = -xSpeed;
                }
                else
                {
                    xPosition = (int)(innerRightWall);
                    xSpeed = -xSpeed;
                }
            }  
        }
        // to make sure the box doesnt get chipped at all
        //drawBox();
        //drawInnerBox();
    }


    /**
     * return the horizontal position of this ball
     */
    public int getXPosition()
    {
        return xPosition;
    }

    /**
     * return the vertical position of this ball
     */
    public int getYPosition()
    {
        return yPosition;
    }
    
    /**
     * @return the height of the canvas as a int
     */
    public int canvasHeight()
    {
        Dimension canvasSize = canvas.getSize();
        Double height = canvasSize.getHeight();
        int canvasHeight = height.intValue();
        
        return canvasHeight;
    }
    
    /**
     * @return the width of the canvas as a int
     */
    public int canvasWidth()
    {
        Dimension canvasSize = canvas.getSize();
        Double width = canvasSize.getWidth();
        int canvasWidth = width.intValue();
        
        return canvasWidth;
    }   
    
    /**
     * This will draw a box 10 pixles away from the canvas edges
     */
    public void drawBox()
    {
        int canvasHeight = canvasHeight();
        int canvasWidth = canvasWidth();
        
        leftWall = 10;
        rightWall = (canvasWidth - 10);
        bottomWall = (canvasHeight - 10);
        topWall = 10;
        
        canvas.setForegroundColor(Color.BLACK);
        
        //left wall
        canvas.drawLine(leftWall, topWall, leftWall, bottomWall);
        //right wall
        canvas.drawLine(rightWall, topWall, rightWall, bottomWall);
        //bottom wall
        canvas.drawLine(leftWall, bottomWall, rightWall, bottomWall);
        //top wall
        canvas.drawLine(leftWall, topWall, rightWall, topWall);
    }
    
    /**
     * This will draw a box in the middle of the canvas
     */
    public void drawInnerBox()
    {
        int canvasHeight = canvasHeight();
        int canvasWidth = canvasWidth();
        
        innerLeftWall = ((canvasWidth / 2) - 25);
        innerRightWall = ((canvasWidth / 2) + 25);
        innerBottomWall = ((canvasHeight / 2) + 25);
        innerTopWall = ((canvasHeight / 2) -25);
        
        canvas.setForegroundColor(Color.BLACK);
        
        //left wall
        canvas.drawLine(innerLeftWall, innerTopWall, innerLeftWall, innerBottomWall);
        //right wall
        canvas.drawLine(innerRightWall, innerTopWall, innerRightWall, innerBottomWall);
        //bottom wall
        canvas.drawLine(innerLeftWall, innerBottomWall, innerRightWall, innerBottomWall);
        //top wall
        canvas.drawLine(innerLeftWall, innerTopWall, innerRightWall, innerTopWall);
    }

}
