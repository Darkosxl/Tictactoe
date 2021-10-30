import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Custom class to work as the tictactoe menu
//here one picks the amount of games, writes the usernames and picks whether or not
//they want player vs player or computer vs player
public class TictactoePrompter extends JFrame
{
 //ppcp = playervsplayer/computer deciding panel
 //gameamount = amount of games one will play in a match
 //usernames = usernames of the player(s)
 private JPanel ppcp,gameamount,usernames;
 private JButton exit,startgame;
 private JRadioButton pp,cp;
 private boolean vscomp;
 private JTextArea player1,player2;
 private JComboBox<String> gamebox,timebox;
 public TictactoePrompter()
 {
  //three prompts to ask: 
  //1) player vs player or player vs comp(jradiobutton)
  //2) how many games (jcombobox)
  //3) usernames(jtextarea)(this is affected by the first prompt)
 //the frame is initialized
  this.setTitle("Tic tac toe Menu");
  this.setSize(300,300);
  
  //the ppcp buttons are initialized
  ButtonGroup group = new ButtonGroup();
  pp = new JRadioButton("Player vs Player", true);
  cp = new JRadioButton("Player vs Computer");
  vscomp = false;
  pp.addActionListener(new ButtonListener());
  cp.addActionListener(new ButtonListener());
  group.add(pp);
  group.add(cp);
  
  //here the gameamount combobox and label are initialized
  String[] games = {"1","2","3","4","5","6","7","8","9","10"
    ,"11","12","13","14","15","16","17","18","19","20"};
  gamebox = new JComboBox<String>(games);
  JLabel gamelabel = new JLabel("Please pick the amount of games you'd like to play");
  
  //textareas for the usernames                              
  player1 = new JTextArea();
  player2 = new JTextArea();

  //the panels are initialized
  ppcp = new JPanel();
  gameamount = new JPanel();
  usernames = new JPanel();
  //these are normal jpanels as they are only necessary for structure, not functionality
  JPanel buttonpan = new JPanel();
  JPanel fill = new JPanel();
  JPanel fill2 = new JPanel();
  JPanel userpan1 = new JPanel();
  JPanel userpan2 = new JPanel();
  //the layouts are declared
  userpan1.setLayout(new GridLayout(1,2));
  userpan2.setLayout(new GridLayout(1,2));
  usernames.setLayout(new GridLayout(2,1));
  
  //the two buttons are initialized
  exit = new JButton("Exit");
  startgame = new JButton("Start Game");
  exit.addActionListener(new ButtonListener());
  startgame.addActionListener(new ButtonListener());
 
  //here the functional panels have their components added into them
  ppcp.add(pp);
  ppcp.add(cp);
  gameamount.add(gamelabel);
  gameamount.add(gamebox);
  userpan1.add(new JLabel("Username of Player 1"));
  userpan1.add(player1);
  userpan2.add(new JLabel("Username of Player 2"));
  userpan2.add(player2);
  usernames.add(userpan1);
  usernames.add(userpan2);
  
  //the structural panel is filled with buttons
  fill2.add(exit);
  fill2.add(startgame);
  //the button panel is fileld with structural panels
  buttonpan.add(fill);
  buttonpan.add(fill2);
  //everything is added into the menu frame
  this.setLayout(new GridLayout(4,1));
  this.add(ppcp);
  this.add(gameamount);
  this.add(usernames);
  this.add(buttonpan);
 }
 //custom listener class for the two buttons in the game
 class ButtonListener implements ActionListener
 {
  public void actionPerformed(ActionEvent ae)
  {
   //closes the game
   if(ae.getSource() == exit)
   {
    System.exit(0); 
   }
   //starts the game with the defined properties
   if(ae.getSource() == startgame)
   {
    //gets the necessary info: amount of games, usernames, ppcp true or false etc
    int games = Integer.parseInt(gamebox.getSelectedItem().toString());
    String username1 = player1.getText();
    String username2 = player2.getText();
    //feeds them into a new tictactoeframe and 
    TictactoeFrame tframe = new TictactoeFrame(games,vscomp,username1,username2);
    //opens it up
    tframe.setVisible(true);
    //disposes of the menu
    TictactoePrompter.this.dispose(); 
   }
   //if player vs player is selected vscomp is false as player is not playing vs computer
   if(ae.getSource() == pp)
   {
    player2.setEditable(true);
    player2.setText("");
    vscomp = false;
   }
   // if player vs computer is selected the player 2/the AI has a predefined name
   // and vscomp is true
   if(ae.getSource() == cp)
   {
    player2.setEditable(false); 
    player2.setText("(AI) Saeed");
    vscomp = true;
   }
  } 
 }
}