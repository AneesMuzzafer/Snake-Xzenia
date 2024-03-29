
package trysnake;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;


public class TrySnake implements ActionListener, KeyListener{
    
    public static TrySnake snake;
    public renderSnake render ;
    public Rectangle food;
    public ArrayList<Rectangle> snakeBody ;
    
    Random rand = new Random();
    public final int UP = 1 , LEFT = 2 , DOWN = 3 , RIGHT = 4 ;
    public int xfood , yfood , pieces , currentMotion = 0 , previousMotion = 0 , xtemp , ytemp, highscore, direction;
    
    
    boolean started = false , gameOver = false ;
    public int tailx[] = new int[3] ;
    public int taily[] = new int[3] ;
    public int eyex1, eyex2 , eyey1 , eyey2 ;
    
    
    public final int WIDTH = 1000, HEIGHT = 800 ;
    
    public TrySnake()
    {
        JFrame jframe = new JFrame();
        Timer timer = new Timer(15, this);
        
        render = new renderSnake();
        
        jframe.add(render);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(WIDTH,HEIGHT);
        jframe.addKeyListener(this);
        jframe.setResizable(false);
        jframe.setTitle("Snake");
        jframe.setVisible(true);
        
        
        xfood = rand.nextInt(700);
        yfood = rand.nextInt(700);
        
        food = new Rectangle(xfood, yfood , 20,20);
        snakeBody = new ArrayList<Rectangle>();
      
        restart();
        
        timer.start();
    }

    
    public static void main(String[] args) {
        snake = new TrySnake();
    }

 
    @Override
    public void actionPerformed(ActionEvent ae) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
       xtemp = snakeBody.get(0).x;
       ytemp = snakeBody.get(0).y;
       
       
        if(started)
        {
               if(currentMotion == 1)
               {
                   snakeBody.get(0).y -= 11 ;
               }
               else if(currentMotion == 2)
               {
                   snakeBody.get(0).x -= 11 ;
               }
               else if(currentMotion == 3)
               {
                   snakeBody.get(0).y += 11 ;
               }
               else if(currentMotion == 4)
               {
                   snakeBody.get(0).x += 11 ;
               }
               if(currentMotion > 0)
               {
           for(int i = 1; i <  snakeBody.size()  ; i++)
           {
                          
               int temp = xtemp ;
               xtemp = snakeBody.get(i).x ;
               snakeBody.get(i).x = temp;
               
               temp = ytemp ;
               ytemp = snakeBody.get(i).y ;
               snakeBody.get(i).y = temp;

              }   
               }
           if(snakeBody.get(0).intersects(food))
           {
               addPiece();
               addPiece();
               addPiece();
               addPiece();
               xfood = 10 + rand.nextInt(700);
               yfood = 10 + rand.nextInt(700);
               food.x = xfood ;
               food.y = yfood ;
           }
           
           
           if(snakeBody.get(0).x > WIDTH - 210)
               snakeBody.get(0).x = 0 ;
           if(snakeBody.get(0).x < 0)
               snakeBody.get(0).x = WIDTH - 210 ;
           if(snakeBody.get(0).y > HEIGHT-50)
               snakeBody.get(0).y = 0 ;
           if(snakeBody.get(0).y < 0)
               snakeBody.get(0).y = HEIGHT-50 ;
           
           for(int i = 1 ; i < snakeBody.size() ; i++ )
           {
               if(snakeBody.get(0).intersects(snakeBody.get(i)))
               {
                   currentMotion = 0;
                   previousMotion = 0 ;
                   if(highscore < snakeBody.size() - 4)
                       highscore = snakeBody.size() - 4 ;
                   restart();
                   
                   gameOver = true ;
               }
                   
           }
           
           
        }
         
        render.repaint();
    }

    public void repaint(Graphics g) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.GRAY);
        g.fillRect(WIDTH-200, 0, WIDTH, HEIGHT);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",4,22));
        g.drawString("Score = " + (snakeBody.size()-4), WIDTH-170,HEIGHT/2 - 200);
        g.drawString("HighScore = " + highscore, WIDTH-195,HEIGHT/2 - 160);
        g.setFont(new Font("Arial",4,15));
        g.drawString("Snake X-Zenia", WIDTH-170,50);
        g.drawString("Copright @ AMS", WIDTH-172,75);
        if(!started)
        {
            g.setFont(new Font("Arial",4,100));
            g.setColor(Color.WHITE);
            g.drawString("Snake X-Zenia", 100,HEIGHT/2 - 50);
            g.setFont(new Font("Arial",4,50));
            g.drawString("Press to Play", 300,HEIGHT/2 + 100);
            g.setFont(new Font("Arial",4,30));
            g.drawString("Copyright @ AMS", 325,HEIGHT/2 + 200);
            
            
        }
        if(started && gameOver == false)
        {
            g.setColor(Color.green.darker());
            g.fillRoundRect(food.x,food.y,food.width,food.height,10,10);
            g.setColor(Color.white);
            g.fillOval(food.x+5, food.y+5, food.width/2, food.height/2);
            paintSnake(g);
        }
        
        if(gameOver)
        {
            g.setFont(new Font("Arial",1,100));
            g.setColor(Color.WHITE);
            g.drawString("GameOver", 200,HEIGHT/2 - 50);
            g.setFont(new Font("Arial",4,50));
            g.drawString("Press to Play", 300,HEIGHT/2 + 100);
        }
    }
    
    public void addPiece()
    {
        if(snakeBody.size() == 0)
        {
            snakeBody.add(new Rectangle(WIDTH/2,HEIGHT/2,10,10));
        }
        else
        {
                snakeBody.add(new Rectangle(snakeBody.get(snakeBody.size()-1).x, snakeBody.get(snakeBody.size()-1).y, 10, 10));
        }
    }
    
    void paintSnake(Graphics g)
    {
        g.setColor(Color.red.darker());
        for(int i = 1 ; i < snakeBody.size() - 1; i++)
        {
            g.fillRect(snakeBody.get(i).x,snakeBody.get(i).y,snakeBody.get(i).width,snakeBody.get(i).height);
        }
       // g.setColor(Color.red.darker());
        g.fillOval(snakeBody.get(0).x-4,snakeBody.get(0).y-4,snakeBody.get(0).width + 8 ,snakeBody.get(0).height + 8);
        drawTriangle();
        //g.setColor(Color.green);
        g.fillPolygon(tailx, taily, 3);
        g.setColor(Color.white);
        drawEye() ;
        g.fillOval(eyex1,eyey1,6,6);
        g.fillOval(eyex2,eyey2,6,6);
      
    }

    
    
    void restart()
    {
        snakeBody.clear();
        snakeBody.add(new Rectangle(WIDTH/2,HEIGHT/2,10,10));
        snakeBody.add(new Rectangle(snakeBody.get(snakeBody.size()-1).x + 11, snakeBody.get(snakeBody.size()-1).y, 10, 10));
        snakeBody.add(new Rectangle(snakeBody.get(snakeBody.size()-1).x + 11, snakeBody.get(snakeBody.size()-1).y, 10, 10));
        snakeBody.add(new Rectangle(snakeBody.get(snakeBody.size()-1).x + 11, snakeBody.get(snakeBody.size()-1).y, 10, 10));   
    }
    
    
    @Override
    public void keyTyped(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ae) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if(ae.getKeyCode() == KeyEvent.VK_W || ae.getKeyCode() == KeyEvent.VK_UP)
        {
            if(started && previousMotion != 3 )
            {
             currentMotion = 1 ;
             previousMotion  = currentMotion ;
            }
             if(!started)
            {
                started = true;
            }
            if(gameOver)
            {
                gameOver = false ;
            }
           
        }
        else if(ae.getKeyCode() == KeyEvent.VK_A || ae.getKeyCode() == KeyEvent.VK_LEFT)
        {
             if(started && previousMotion != 4 )
            {
             currentMotion = 2 ;
             previousMotion  = currentMotion ;
            }
              if(!started)
            {
                started = true;
            }
            if(gameOver)
            {
                gameOver = false ;
            }
           
        }
        else if(ae.getKeyCode() == KeyEvent.VK_S || ae.getKeyCode() == KeyEvent.VK_DOWN)
        {
            if(started && previousMotion != 1 )
            {
                currentMotion = 3 ;
                previousMotion  = currentMotion ;
            }
             if(!started)
            {
                started = true;
            }
            if(gameOver)
            {
                gameOver = false ;
            }
          
        }
        else if(ae.getKeyCode() == KeyEvent.VK_D || ae.getKeyCode() == KeyEvent.VK_RIGHT)
        {

            if(currentMotion > 0)
            {
                if(started && previousMotion != 2 )
                {
                    currentMotion = 4 ;
                    previousMotion  = currentMotion ;
                }
            }
             if(!started)
            {
                started = true;
            }
            if(gameOver)
            {
                gameOver = false ;
            }
      
        }
        
        else if(ae.getKeyCode() == KeyEvent.VK_P)
        {
            if(!started)
            {
                started = true;
            }
            if(gameOver)
            {
                gameOver = false ;
            }
             
        }
    }

    @Override
    public void keyReleased(KeyEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        
       
    }
    
    public void drawTriangle()
    {
        
        getDirection();
        
        if(direction == LEFT)
        {
            tailx[0] = snakeBody.get(snakeBody.size() - 1).x ;
            taily[0] = snakeBody.get(snakeBody.size() - 1).y ;
            
            tailx[1] = snakeBody.get(snakeBody.size() - 1).x ;
            taily[1] = snakeBody.get(snakeBody.size() - 1).y + 10 ;
            
            tailx[2] = snakeBody.get(snakeBody.size() - 1).x + 40 ;
            taily[2] = snakeBody.get(snakeBody.size() - 1).y + 5 ;
        }
        
        if(direction == UP)
        {
            tailx[0] = snakeBody.get(snakeBody.size() - 1).x ;
            taily[0] = snakeBody.get(snakeBody.size() - 1).y ;
            
            tailx[1] = snakeBody.get(snakeBody.size() - 1).x + 10;
            taily[1] = snakeBody.get(snakeBody.size() - 1).y ;
            
            tailx[2] = snakeBody.get(snakeBody.size() - 1).x + 5  ;
            taily[2] = snakeBody.get(snakeBody.size() - 1).y + 40 ;
        }
        
        
        if(direction == RIGHT)
        {
            tailx[0] = snakeBody.get(snakeBody.size() - 1).x + 10 ;
            taily[0] = snakeBody.get(snakeBody.size() - 1).y ;
            
            tailx[1] = snakeBody.get(snakeBody.size() - 1).x + 10 ;
            taily[1] = snakeBody.get(snakeBody.size() - 1).y + 10 ;
            
            tailx[2] = snakeBody.get(snakeBody.size() - 1).x - 40  ;
            taily[2] = snakeBody.get(snakeBody.size() - 1).y + 5 ;
        }
        
        if(direction == DOWN)
        {
            tailx[0] = snakeBody.get(snakeBody.size() - 1).x ;
            taily[0] = snakeBody.get(snakeBody.size() - 1).y + 10;
            
            tailx[1] = snakeBody.get(snakeBody.size() - 1).x + 10;
            taily[1] = snakeBody.get(snakeBody.size() - 1).y + 10;
            
            tailx[2] = snakeBody.get(snakeBody.size() - 1).x + 5  ;
            taily[2] = snakeBody.get(snakeBody.size() - 1).y - 40 ;
        }
        
        
    }
    
    public void getDirection()
    {
        if(snakeBody.get(snakeBody.size() - 1).y == snakeBody.get(snakeBody.size() - 2).y && snakeBody.get(snakeBody.size() - 1).x > snakeBody.get(snakeBody.size() - 2).x)
        {
            direction = LEFT ;
        }
        else if(snakeBody.get(snakeBody.size() - 1).y == snakeBody.get(snakeBody.size() - 2).y && snakeBody.get(snakeBody.size() - 1).x < snakeBody.get(snakeBody.size() - 2).x)
        {
            direction = RIGHT ;
        }
        else if(snakeBody.get(snakeBody.size() - 1).x == snakeBody.get(snakeBody.size() - 2).x && snakeBody.get(snakeBody.size() - 1).y > snakeBody.get(snakeBody.size() - 2).y)
        {
            direction = UP ;
        }
        else if(snakeBody.get(snakeBody.size() - 1).x == snakeBody.get(snakeBody.size() - 2).x && snakeBody.get(snakeBody.size() - 1).y < snakeBody.get(snakeBody.size() - 2).y)
        {
            direction = DOWN ;
        }
    }
    
    public void drawEye()
    {
        if(currentMotion == 1)
        {
            eyex1 = snakeBody.get(0).x - 3;
            eyex2 = snakeBody.get(0).x + 7;
            
            eyey1 = snakeBody.get(0).y ;
            eyey2 = snakeBody.get(0).y ;
        }
        
        if(currentMotion == 2 || currentMotion == 0)
        {
            eyex1 = snakeBody.get(0).x ;
            eyex2 = snakeBody.get(0).x  ;
            
            eyey1 = snakeBody.get(0).y - 3 ;
            eyey2 = snakeBody.get(0).y + 7 ;
        }
        
        if(currentMotion == 3)
        {
            eyex1 = snakeBody.get(0).x - 3  ;
            eyex2 = snakeBody.get(0).x + 7;
            
            eyey1 = snakeBody.get(0).y + 6;
            eyey2 = snakeBody.get(0).y + 6 ;
        }
        
        if(currentMotion == 4)
        {
            eyex1 = snakeBody.get(0).x + 6 ;
            eyex2 = snakeBody.get(0).x + 6 ;
            
            eyey1 = snakeBody.get(0).y - 3;
            eyey2 = snakeBody.get(0).y + 7 ;
        }
    }
    
}
