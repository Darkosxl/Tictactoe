import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Font;

//Custom class to use as the TictactoeFrame
// will only be used to put on the panel, the buttons and the text field
// doesn't affect other things, only uses methods of other classes and
// sends the initial input 
public class TictactoeFrame extends JFrame
{
 //tttcomp is the component to be drawn 
 private TictactoeComponent tttcomp;
 private JFrame victoryscreen;
 private JButton reset,nextgame,exit,endearly,newgame;
 //text that the dimension will be written on
 private JTextField dimensiontxt;
 // size of frame  
 private final int JFRAMESIZE = 600;
 // this basically represents the marker
 // that draws upon the grid,
 // when it is 1 it draws an X
 // when it is 2 it draws an O
 // when it is anything else it doesn't draw anything
 private int xomarker = 1;
  private int games;
 private boolean vscomp;
 private int round;
 private String username1,username2;
 public TictactoeFrame(int games, boolean vscomp,String username1,String username2)
 {
  this.games = games;
  this.vscomp = vscomp;
  this.username1 = username1;
  this.username2 = username2;
  round = 1;
  //initializing frame properties
  this.setTitle("Tic Tac Toee");
  this.setSize(JFRAMESIZE+200,JFRAMESIZE);
  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  tttcomp = new TictactoeComponent(3,this.getHeight(),this.getWidth(),username1,username2,games);

  
  
  this.add(tttcomp, BorderLayout.CENTER);
  tttcomp.addMouseListener(new TttListener());
  
  //initializing buttons, panels and adding listeners
  newgame = new JButton("New Game");
  
  reset = new JButton("Reset Game");
  exit = new JButton("Exit Game");
  endearly = new JButton("End The Match Early");
  nextgame = new JButton("Next Game");
  reset.addActionListener(new ButtonListener());
  exit.addActionListener(new ButtonListener());
  endearly.addActionListener(new ButtonListener());
  nextgame.addActionListener(new ButtonListener());
  newgame.addActionListener(new ButtonListener());
  nextgame.setEnabled(false);
  
  //the centerpan is the panel with all the buttons
  // the rest of the stuff is drawn on top of the frame itself
  JPanel centerpan = new JPanel();
  centerpan.setLayout(new GridLayout(1,4));
  centerpan.add(reset);
  centerpan.add(nextgame);
  centerpan.add(endearly);
  centerpan.add(exit);
  this.add(centerpan, BorderLayout.SOUTH);
 }
 
 //custom listener class for the mouse
 // the only necessary method was mouse clicked but I had to implement the other
 // ones as well as they are abstract
 class TttListener implements MouseListener
 {
  public void mouseClicked(MouseEvent e)
  {
   System.out.print("Rounds: ");
   System.out.println(round+1);
   boolean compmove = false;
   //gets the x and y coordinates of the mouse click 
   int x = e.getX();
   int y = e.getY();
   System.out.println("Pressed coordinates: ("+x+","+y+")");
   //feeds them to the marker method and what to be drawn
   tttcomp.setMark(x,y,xomarker); 
   // if it isn't locked and we have drawn an X
    if(xomarker == 1 && !tttcomp.ifLocked())
   {
    // next time the marker will draw an O  
    xomarker = 2;
    // and we must repaint now 
    TictactoeFrame.this.repaint();
    round++;
    compmove = true;
   }
    //if it isn't locked and we have drawn an O 
   else if(!tttcomp.ifLocked())
   {
    //set the marker to draw an X next time 
    xomarker = 1;
    TictactoeFrame.this.repaint();
    round++;
    compmove = true;
   }
   //checks the comp if it is done or if there is a draw(beyond round 9 the grid is full)
   if(tttcomp.checkIfDone() || round > 9)
   {
    //the grid is locked  
    tttcomp.lockGrid();
    //one can move onto the next game
    nextgame.setEnabled(true);
    //if it really is a draw, the component must know it
    if(round > 9)
    {
     tttcomp.setDraw(true); 
    }
   }
   TictactoeFrame.this.repaint();
   //if the computer is playing, and if the game hasn't ended yet
   if(compmove && vscomp && !tttcomp.checkIfDone())
   {
    //increase the round
    round++;
    //if the game isn't over yet by draw or by win
    if(round <= 9 && !tttcomp.checkIfDone())
    {
     //play the round of the computer 
     tttcomp.setComputerMark(xomarker);
     //and reverse the mark to the players mark
     if(xomarker == 1)
      xomarker = 2;
     else 
      xomarker = 1;
    }
    if(tttcomp.checkIfDone() || round > 9)
   {
    //the grid is locked  
    tttcomp.lockGrid();
    //one can move onto the next game
    nextgame.setEnabled(true);
    //if it really is a draw the tttcomponent must know
    if(round > 9)
    {
     tttcomp.setDraw(true); 
    }
   }
    TictactoeFrame.this.repaint();
   }
  }
  //these have to be implemented but I found no use for them
  public void mouseEntered(MouseEvent e)  
  {}
  public void mouseExited(MouseEvent e)
  {}
  public void mousePressed(MouseEvent e)
  {}
  public void mouseReleased(MouseEvent e)
  {}
 }
 //Custom method for all the buttons in the program
 class ButtonListener implements ActionListener
 {
  public void actionPerformed(ActionEvent ae)
  {
   //exit: closes the program 
   if(ae.getSource() == exit) 
   {
    System.exit(0);
   } 
   //reset: resets the grid to its unmarked state and even if the current game has ended it doesn't count it and 
   //starts the game over
   if(ae.getSource() == reset)
   {
    tttcomp.resetGrid();
    TictactoeFrame.this.repaint();
    round = 1;
    //if vs computer then the player must always start first, in player v player the players get start interchangeably
    if(vscomp)
    {
     xomarker = 1; 
     tttcomp.setPlayer(xomarker);
    }
    //even if it ends in a draw it should not count this game as done
    tttcomp.setDraw(false);
   }
   //if there are games remaining and nextgame is called
   if(ae.getSource() == nextgame && games > 0)
   {
    //if it is a draw and there is no win
    if(round > 9 && !tttcomp.checkIfDone())
    {
     //the scores are kept constant
    }
    //otherwise there is a winner
    else
    {
      //and scores are increased
     tttcomp.increaseScore(xomarker); 
    }
    //if vs comp
    if(vscomp)
    {
     //the next game shall start with the player 
     xomarker = 1;
     tttcomp.setPlayer(xomarker);
    }
    //the grid must be reset(as it is a new game)
    tttcomp.resetGrid();
    //  the round is reset
    round = 1;
    // the remaining games decrease
    games--;
    // the component must know this
    tttcomp.setGames(games);
    // as the game is done the next game shouldn'T start with draw sign
    tttcomp.setDraw(false);
    TictactoeFrame.this.repaint();
    
   }
   //if the matches are ended early or the amount of games run out and one still presses next game
   if(ae.getSource() == endearly || (ae.getSource() == nextgame && games <= 0))
   {
    //the win screen is shown, all are properties of the next game
    //the win screen has three parts:
    // the winner, the scores and the buttons for new game or exit
    String wintext = tttcomp.getWinText();
    String scoretext = tttcomp.getScoreText();
    //the properties of the frame initialized
    victoryscreen = new JFrame("Victory Screen");
    victoryscreen.setSize(300,300);
    victoryscreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    victoryscreen.setLayout(new GridLayout(3,1));
    //the three panels
    JPanel buttonpan = new JPanel();
    JLabel winlabel = new JLabel(wintext);
    JLabel scorelabel = new JLabel(scoretext);
    winlabel.setHorizontalAlignment(JLabel.CENTER);
    scorelabel.setHorizontalAlignment(JLabel.CENTER);
    //the score label has this font
    Font winfont = new Font("Courier", Font.BOLD,35);
    scorelabel.setFont(winfont);
    // the buttons necessary are initialized
    exit = new JButton("Exit");
    newgame = new JButton("New Game");
    newgame.addActionListener(this);
    exit.addActionListener(this);
    // everything is put together
    buttonpan.add(exit);
    buttonpan.add(newgame);
    victoryscreen.add(winlabel);
    victoryscreen.add(scorelabel);
    victoryscreen.add(buttonpan);
    //the frame is shown
    victoryscreen.setVisible(true);
    // the old frame is disposed
    TictactoeFrame.this.dispose();
   }
   //this is for the victory screen's button,
   // and the reason why victory screen is a global variable
   // as it must be accessible by this button to be disposed of
   if(ae.getSource() == newgame)
   {
    //it opens the prompter and disposes the victory screen frame
    TictactoePrompter tprompt = new TictactoePrompter();
    tprompt.setVisible(true);
    victoryscreen.dispose();
   }
  }
 }
}