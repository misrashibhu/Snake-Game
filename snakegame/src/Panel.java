import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;
public class Panel extends JPanel implements ActionListener {
    static int width=1200;
    static int height=600;

    static int unit=20;

    Timer timer;
    static int delay=200;
    char dir='R';
    Random random;
    int fx,fy;
    int body=3;
    int score=0;
    boolean flag=false;
    static int size=(width*height)/(unit*unit);
    int xsnake[]=new int[size];
    int ysnake[]=new int[size];
    Panel()
    {
        this.setPreferredSize(new Dimension(width,height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        random=new Random();
        this.addKeyListener(new Key());
        game_start();
    }
    public void game_start()
    {
        spawnfood();
        flag=true;
        timer=new Timer(delay,this);
        timer.start();
    }
    public void  spawnfood()
    {
        fx=random.nextInt((int)(width/unit))*(unit);
        fy=random.nextInt((int)(width/height))*(unit);
    }
    public void checkHit()
    {
        for(int i=body;i>0;i--)
        {
            if((xsnake[0]==xsnake[i])&&(ysnake[0]==ysnake[i]))
            {
                flag=false;
            }
        }
        if(xsnake[0]<0)
        {
            flag=false;
        }
        else if(xsnake[0]>width)
        {
            flag=false;
        }
        else if(ysnake[0]<0)
        {
            flag=false;
        }
        else if (ysnake[0]>height)
        {
            flag=false;
        }
        if(flag==false)
        {
            timer.stop();
        }
    }

    public void paintComponent(Graphics graphic)
    {
        super.paintComponent(graphic);
        draw(graphic);
    }

    public void draw(Graphics graphic)
    {
        if(flag)
        {
            graphic.setColor(Color.RED);
            graphic.fillOval(fx,fy,unit,unit);

            for(int i=0;i<body;i++)
            {
                if(i==0)
                {
                    graphic.setColor(Color.GREEN);
                    graphic.fillRect(xsnake[0],ysnake[0],unit,unit);
                }
                else
                {
                    graphic.setColor(Color.ORANGE);
                    graphic.fillRect(xsnake[i],ysnake[i],unit,unit);
                }
            }
            graphic.setColor(Color.BLUE);
            graphic.setFont(new Font("Comic Sans",Font.BOLD,40));
            FontMetrics f=getFontMetrics(graphic.getFont());
            graphic.drawString("SCORE"+score,(width-f.stringWidth("SCORE"+score))/2,graphic.getFont().getSize());
        }
        else
        {
            gameOver(graphic);
        }
    }
    public void gameOver(Graphics graphic)
    {
        graphic.setColor(Color.BLUE);
        graphic.setFont(new Font("Comic Sans",Font.BOLD,40));
        FontMetrics f=getFontMetrics(graphic.getFont());
        graphic.drawString("SCORE"+score,(width-f.stringWidth("SCORE"+score))/2,graphic.getFont().getSize());

        graphic.setColor(Color.BLUE);
        graphic.setFont(new Font("Comic Sans",Font.BOLD,100));
        FontMetrics f2=getFontMetrics(graphic.getFont());
        graphic.drawString("GAME OVER !",(width-f2.stringWidth("GAME OVER !"))/2,height/2);

        graphic.setColor(Color.BLUE);
        graphic.setFont(new Font("Comic Sans",Font.BOLD,40));
        FontMetrics f3=getFontMetrics(graphic.getFont());
        graphic.drawString("Press R for Replay",(width-f3.stringWidth("Press R for Replay"))/2,height/2-180);
    }

    public void move()
    {
        for(int i=body;i>0;i--)
        {
            xsnake[i]=xsnake[i-1];
            ysnake[i]=ysnake[i-1];
        }
        switch(dir)
        {
            case 'U':
                ysnake[0]=ysnake[0]-unit;
                break;
            case 'D':
                ysnake[0]=ysnake[0]+unit;
                break;
            case 'L':
                xsnake[0]=xsnake[0]-unit;
                break;
            case 'R':
                xsnake[0]=xsnake[0]+unit;
                break;
        }
    }

    public void checkScore()
    {
        if((fx==xsnake[0])&&(fy==ysnake[0]))
        {
            body++;
            score++;
            delay=delay+100;
            spawnfood();
        }
    }
    public class Key extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            switch(e.getKeyCode())
            {
                case KeyEvent.VK_LEFT :
                    if(dir!='R')
                    {
                        dir='L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(dir!='L')
                    {
                        dir='R';
                    }
                    break;
                case KeyEvent.VK_UP :
                    if(dir!='D')
                    {
                        dir='U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(dir!='U')
                    {
                        dir='D';
                    }
                    break;
                case KeyEvent.VK_R:
                    if(!flag)
                    {
                        score=0;
                        body=3;
                        dir='R';
                        Arrays.fill(xsnake,0);
                        Arrays.fill(ysnake,0);
                        game_start();
                    }
                    break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg)
    {
        if(flag)
        {
            move();
            checkScore();
            checkHit();
        }
        repaint();
    }
}
