import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.BasicStroke;
import java.awt.Font;
import java.util.Random;

//Custom class designed to act as the component of a tictactoe panel
//the whole tictactoe plus the effects if somebody wins is drawn on this panel
//this also regulates a lot of stuff such as checking whether somebody won
// or managing the grid itself
public class TictactoeComponent extends JPanel
{
 //the dimensions of the tictactoe board, x*x 
 private int dimensions; 
 // this is the two dimensional array
 // representation of a grid upon which one can mark, 
 private XO[][] markedgrids;
 // these are the row differences and column differences
 // between the current cell and the cell that is being checked
 private int rowdifference,coldifference;
 // this is the current cell, the cell that has been most recently marked
 private XO currentcell;
 // the size of the cells, size * size = area of cell
 private int size;
 // frh = height of frame, frw = width of frame
 private int frw,frh;
 // this boolean variable checks if the program is complete or not 
 private boolean done,done2;
 // this final constant is to push the whole thing into the visible space of the frame
 // only to move the x coordinates of everything that is drawn
 private final int PUSH = 45;
 private String username1,username2;
 private int score1,score2;
 private int games,gametemp;
 //default constructor 
 private int whosplayin;
 private boolean draw;
 public TictactoeComponent(int dimensions, int frameheight, int framewidth,String username1,String username2,int games)
 {
  //the game isn't complete because it has just begun so 
  done = false;
  done2 = false;
  draw = false;
  // initializing variables
  frw = framewidth;
  frh = frameheight;
  this.username1 = username1;
  this.username2 = username2;
  this.games = games;
  gametemp = games;
  //This variable determines the player that is playing at the time
  // if it is 2, player 1 is playing, if it is 1 player 2 is playing
  // that is because the marker changes after each click
  // so the one that is playing is actually the one who is the penultimate clicker, not the last one who clicked
  // and the last one who clicked determines the variable whosplayiin
  whosplayin = 2;
  // score1 = score of player1, score2 = score of player2
  score1 = 0;
  score2 = 0;
  // the two dimensionsal grid for the game, it consists of all cells, not just the marked ones
  markedgrids = new XO[dimensions][dimensions];
  //the set size for the whole panel is 400 units
  //this determines how it will be split up between individual cells
  this.size = 400/dimensions;
  // just to avoid null pointer exception these are initalized in random fashion,
  // actually this is the first cell that will be checked when comparing with current cell
  rowdifference = -1;
  coldifference = -1;
  //this nested for loop fills the grid
  // with unmarked cells
  for(int i = 0;i<dimensions;i++)
  {
   for(int x = 0; x<dimensions;x++) 
   { 
    markedgrids[x][i] = new XO(x,i,size,0);
   }
  }
  this.dimensions = dimensions;
 }
 //paintcomponent method to draw the necessary stuff 
 // two things here: the tictactoe grid, and the win features
 public void paintComponent(Graphics g)
 {
  Graphics2D g2 = (Graphics2D) g;
  //the tictactoe grid initialized and drawn
  System.out.println("Dimensions: "+dimensions);
  TictactoeGrid tttgrid = new TictactoeGrid(dimensions,markedgrids);
  tttgrid.draw(g2);
  Font current = g2.getFont();
  //the one that is playing will have their name drawn in bold
  // the other one's name will be drawn normally
  if(whosplayin == 2)
   g2.setFont(new Font("Courier", Font.BOLD,12)); 
  g2.drawString(username1+": "+score1,PUSH+size*(dimensions+1),PUSH+(dimensions/2)*size-100);
  g2.setFont(current);
  if(whosplayin == 1)
   g2.setFont(new Font("Courier", Font.BOLD,12));
  g2.drawString(username2+": "+score2,PUSH+size*(dimensions+1),PUSH+(dimensions/2)*size-50);
  g2.setFont(current);
  //this variable shows the current game number 
  // the first game is game number 1, and the last game is the nth game
  int gameshow = gametemp-games+1;
  // this shows the game number beneath the usernames
  g2.drawString("Current Game Number: "+gameshow, PUSH+size*(dimensions+1),PUSH+(dimensions/2)*size+100);
  //checking and drawing the winning features
  // three cases of winning, the first two are both wins, just different kinds of wins
  // the last one is when it is a draw
  if(done2 || done || draw)
  {
   // the first winning condition, done2, is when you put for example O in between two O's,
   // this requires a separate way of being drawn that is why it is separate 
   // the second winning condition, done is when you put the last one in front of two of the same kind,
   // the last one, draw, is simply the number of rounds, which is 9 at max as there are 9 tiles, runs out before
   // any of the winning conditions are achieved
    
   // x1 and y1 is where the drawing will start, these are initalized to avoid null pointer exception
   // x2 and y2 is where it will end
   int x1 = 0;
   int x2 = 0;
   int y1 = 0;
   int y2 = 0;
   // rd and cd are the row and column differences of the third cell
   int rd = 0;
   int cd = 0;
   if(done2)
   {
    // in this case the third cell is the reverse one to the rowdifference of the second cell
    // as we are putting the current cell between the second and third
    rd = rowdifference*-1;
    cd = coldifference*-1;
    // and thus the coordinates are initialized
    x1 = markedgrids[currentcell.getRow()+rowdifference][currentcell.getColumn()+
    coldifference].getX();
    y1 = markedgrids[currentcell.getRow()+rowdifference][currentcell.getColumn()+
    coldifference].getY();                                         
    x2 = markedgrids[currentcell.getRow()+rd][currentcell.getColumn()+cd].getX();
    y2 = markedgrids[currentcell.getRow()+rd][currentcell.getColumn()+cd].getY();
    //Just to make the line a little thick and satisfying when drawn across
    g2.setStroke(new BasicStroke(5));
    g2.drawLine(x1+size-20,y1+size-20,x2+size-20,y2+size-20); 
   }
   else if(done)
   {
    // in this case the third cell continues upon the second cell so we must go in the same direction
    // one more time to find the third cell
    rd = rowdifference*2;
    cd = coldifference*2;
    x1 = currentcell.getX();
    y1 = currentcell.getY();
    x2 = markedgrids[currentcell.getRow()+rd][currentcell.getColumn()+cd].getX();
    y2 = markedgrids[currentcell.getRow()+rd][currentcell.getColumn()+cd].getY();
    // the same thing happens, except now the line starts from the current cell instead of the second cell as 
    // the current cell is in the edge now
    g2.setStroke(new BasicStroke(5));
    g2.drawLine(x1+size-20,y1+size-20,x2+size-20,y2+size-20); 
   }
                                                           
   
   if(draw)
   {
    //If it is a draw nothing shall be drawn except a notifier string
    g2.drawString("Draw!!!",PUSH+size*(dimensions+1),PUSH+(dimensions/2)*size+150);
   }
   else
   {
    //If there is a winner the game is over
    System.out.println("Game is Over!!!");
    g2.drawString("Game is Done!",PUSH+size*(dimensions+1),PUSH+(dimensions/2)*size+150);
   }
  }
 }
 //custom method to reset the grid
 //will be used by the "reset" button
 public void resetGrid()
 {
  for(int i = 0;i<dimensions;i++)
  {
   for(int x = 0; x<dimensions;x++) 
   {
    //just recreates the whole grid 
    markedgrids[x][i] = new XO(x,i,size,0);
    //but all the locks are off(the lock to press again on the same cell)
    markedgrids[x][i].setLock(false);
   }  
  }
  //to make sure the winning features aren't drawn after being reset
  done = false;
  done2 = false;
 }
 //custom method to mark a certain cell based upon the
 // position of the mouse when it clicks
 public void setMark(int x, int y, int mark)
 {
  int row, column;
// it just estimates the row based upon the coordinate
// by reversing the calculation that was used for drawing the grid
  int estimrow = (x-PUSH)/size;
  int estimcol = (y-PUSH)/size;
// if out of bounds:
  if(estimrow > dimensions || estimcol > dimensions)
  {
   //set them to the initial cell 
   estimrow = 0;
   estimcol = 0;
  }
  //as this is the latest grid to be marked, it will be the current cell
  currentcell = markedgrids[estimrow][estimcol];
  // if it isn't locked, we must make sure it should be marked
  if(!markedgrids[estimrow][estimcol].ifLocked())
  {
   whosplayin = mark; 
   currentcell.setXO(mark);
  }
 }
 //Custom method to lock the current cell from the TictactoeFrame
 public void setLock(boolean lock)
 {
  currentcell.setLock(lock);
 }
 //Custom method to check if the current cell is locked from the TictactoeFrame
 public boolean ifLocked()
 {
  return currentcell.ifLocked(); 
 }
 //Custom method to check if the game is done
 public boolean checkIfDone()
 {
  
  int col = currentcell.getColumn();
  int row = currentcell.getRow();
  //It will go through the closest neighbours, even diagonal ones
  // these variables serve to just do that
  //these will be adjusted according to edge boundaries
  int startingrow = -1; 
  int startingcol = -1;
  int endingrow = 2;
  int endingcol = 2;
  
  // It checks if the cell is not in the outer boundaries
  //if it is in the top boundary
  if(col == 0)
  //It has to start from it's own column
   startingcol = 0;
  //if it is in the left boundary
  if(row == 0)
  // It must start from it's own row
   startingrow = 0;
  // If it is in the bottom boundary
  if(col == dimensions-1)
  //It must end at it's own column
   endingcol = 1; 
  // if it is in the right boundary
  if(row == dimensions-1)
  //It must end at it's own row
   endingrow = 1;
    
  done = false;
  done2 = false;
  int r = 0;
  int c = 0;
  /*
   * Now the system here works as follows:
   * the r and c variables, the current row and column being searched
   * are checked for done's, if it can't find any dones it will check through all tiles (max 8)
   * around the currentcell, if it does find a possible candidate for a done(e.g. two same symbols next
   * to each other) it will break the for loops and process with the second check( e.g. search the third of the same
   * kind) and if it can't do that, it will pick up where the ctemp and rtemp variables left off.
   */
  int rtemp = row+startingrow;
  int ctemp = col+startingcol;
  //while all columns and rows are searched
  while(c<col+endingcol && r<row+endingrow)
  {
  //ctemp is where c lefts off once it is looking for a column
   for(c = ctemp;c<col+endingcol;c++)
   {
   // rtemp serves the same purpose as ctemp except it is for rows
    for(r = rtemp;r<row+endingrow;r++)
    {
     
     // if it is not searching the current cell(if it is not searching itself)
     if(r != row || c != col)
     {
      //it compares the cells for same symbols
      done = currentcell.compareCells(markedgrids[r][c]);
     }
     //if the first check is done
     if(done)
     { 
      //the row difference is how much one must increase row of the current cell to find the cell with the same 
      // symbol
      rowdifference = r-row;
      //the column difference is how much one must increase 
      //row of the current cell to find the cell with the same symbol
      coldifference = c-col;
      done2 = true;
      //as we must start from the next cell if the second check isn't met
      //we must either increase the row(go from left to right)
      if(r < row+endingrow)
      {
       //it starts from the right row 
       rtemp = r+1;
       // and it starts from the same column next time the loop is initiated
       ctemp = c;
      }
      // or we must increase the column and start from left
      else if(c < col+endingcol)
      {
       //it starts from the right row
       ctemp = c+1;
       //and it starts from the same column next time the loop is initiated
       rtemp = r-2;
      }
      break;
     }
    }
    //to break the second loop
    if(done)
    {
     break;
    }
   }
   //rd and cd is for done1 stage 2 check, rd2 and cd2 is for the done2 stage 2 check
   int rd = rowdifference*2;
   int cd = coldifference*2;
   int rd2 = rowdifference*-1;
   int cd2 = coldifference*-1;
   //stage 2 check
   // if the first check is done and the the second checks' cell isn't out of bounds
   if(done && (row+rd >= 0 && row+rd < dimensions) && (col+cd >= 0 && col+cd < dimensions))
   {
    //if the symbol of the third cell and the current cell are the same, it must be done
    done = currentcell.compareCells(markedgrids[row+rd][col+cd]);
    if(done)
    {
     //if it is done then return done
     done2 = false;
     return done;
    }
   }
   else
    //if it isn't done then done1 must be false
    done = false;
   //this is the stage 2 check for done 2
   // same logic as done 1(the third cell shouldn'T be out of bounds and the first check must be met)
   if(done2 && (row+rd2 >= 0 && row+rd2 < dimensions) && (col+cd2 >= 0 && col+cd2 < dimensions)) 
   {
   //it compares the current cell with the third cell which is on the opposite side of the second cell
   done2 = currentcell.compareCells(markedgrids[row+rd2][col+cd2]);
   if(done2)
    //if it is done, returns done2
    return done2;
   }
   else
    done2 = false;
  }
  //this is to avoid having an incomplete method but under perfect conditions should never be reached
  // IF IT IS TRUE, however if it is false then this should be reached
  return done;
 }
 //custom method to lock the grid once the game is finished
 public void lockGrid()
 {
  //goes through the whole grid, locks each cell
  for(int c = 0;c<dimensions;c++)
  {
   for(int r = 0; r<dimensions;r++) 
   {
    markedgrids[r][c].setLock(true);
   }  
  }
 }
 //custom method to increase the score of a player through the TictactoeFrame
 public void increaseScore(int player)
 {
  if(player == 1)
   score2++;
  else
   score1++;
 }
 //Custom method to return the wintext that will be displayed
 public String getWinText()
 {
  // if it is a draw
  if(score1 == score2)
  {
   // if it is 4-4 then it is a special draw case(just for fun)
   if(score1 == 4)
    return "A Draw of huge importance, have a rematch because 4-4 Has to be settled!!";
   //otherwise it is just a normal draw
   else
    return "Draw!!! A battle with equal footing!";
  }
  //if player 1 wins
  else if(score1>score2)
   return username1+" Won!! Better luck next time "+username2+"!";  
  //if player 2 wins
  else if(score2>score1)
   return username2+" Won!! Better luck next time "+username1+"!"; 
  //if the game bugs out
  else
   return "No Data Present! Play more games!";
 }
 // returns the score text that will be displayed in the ending screen
 public String getScoreText()
 {
  return score1+" - "+score2; 
 }
 
 //custom method to set the number of games that are currently remaining
 public void setGames(int games)
 {
  this.games = games; 
 }
 //custom method for the computer to mark
 //the smart moves happen here
 public void setComputerMark(int mark)
 {
  int row = currentcell.getRow();
  int col = currentcell.getColumn();
  //two scenarios:
  //1) priority ***: If it detects available space around the latest cell that can lead to a win in one move, it will block that path
  //2) priority *: If it detects available space around the latest cell that can possibly lead to a win in 2 moves, it will block that path
  
  //The bot works very similar to the checkIfDone method
  //it looks for possible places that are right next to the latest put cell but also has
  //enough empty space(2 blocks) to score a win in two moves,
  int startingrow = -1;
  int startingcol = -1;
  int endingrow = 2;
  int endingcol = 2;
  
  
  //the search boundaries initialized
  if(col == 0)
   startingcol = 0;
  if(row == 0)
   startingrow = 0;
  if(col == dimensions-1)
   endingcol = 1; 
  if(row == dimensions-1)
   endingrow = 1;
  //the availableplaces are for the second priority case
  //it will put to the cells in this array if the enemyplaces array is empty
  XO[] availableplaces = new XO[8];
  //this is the array with highest priority places, as if the player can end
  //the game in one move if these cells aren2t covered
  XO[] enemyplaces = new XO[10];
  //these are the helper variables for both arrays
  //as the arrays always have varying number of places(due to the changing nature of the game)
  int ahelpervar = 0;
  int ehelpervar = 0;
  //searches through a place,
  //as it is not looking for done(never breaks the loop)
  //it will be done in one nested for loop, so no while loop is necessary
  for(int c = col+startingcol;c<col+endingcol;c++)
  {
   for(int r = row+startingrow;r<row+endingrow;r++)
   {
    if(r != row || c != col)
    {
     if(currentcell.compareCells(markedgrids[r][c]))
     {      
      int cd = 2*(c-col); 
      int rd = 2*(r-row);
      int cd2 = -1*(c-col); 
      int rd2 = -1*(r-row);
      //the two done cases (done1 and done2) are looked for, and if they are possible
      //then the priority array is filled with those places
      //so the bot can place in those places to stop the player from winning
      if(cd+col < dimensions && cd+col >= 0 && rd+row < dimensions && rd+row >= 0 && !markedgrids[rd+row][cd+col].ifLocked())
      {
       enemyplaces[ehelpervar] = markedgrids[rd+row][cd+col];
       ehelpervar++;
      }
      if(cd2+col < dimensions && cd2+col >= 0 && rd2+row < dimensions && rd2+row >= 0 && !markedgrids[rd2+row][cd2+col].ifLocked())
      {
       enemyplaces[ehelpervar] = markedgrids[rd2+row][cd2+col];
       ehelpervar++;
      }
     }
     else if(!markedgrids[r][c].ifLocked())
     {
      //this is the second priority placement, if there is possible space around the cell,
      //it will place in those spaces
      availableplaces[ahelpervar] = markedgrids[r][c]; 
      ahelpervar++;
     }
    }
   }
  }
  Random rng = new Random();
  //here the cell that will be marked is chosen
  // it is picked randomly from the arrays,
  //if there are cells in the enemy array(priority array)
  if(ehelpervar > 0)
  {
   System.out.println("Saeed: façaný aldým çocuk");
   currentcell = enemyplaces[rng.nextInt(ehelpervar)]; 
   //these are kept stable as actually 
   //whosplayin almost never changes during player vs computer
   //as the computer takes no time in forming a response
   whosplayin = mark;
   //same with the marker, so the computer has it's own marker in order to avoid confusion
   currentcell.setXO(mark);
  }
  //if not the other array is looked for places
  else if(ahelpervar > 0)
  { 
   currentcell = availableplaces[rng.nextInt(ahelpervar)]; 
   whosplayin = mark;
   currentcell.setXO(mark);
  }
  //the worst case scenario, the bot just randomly places, this happens only when:
  //the player hasn't put in anything or the whole grid is almost full
  else
  {
   XO[] randompick = new XO[dimensions*dimensions];
   int rnghelpervar = 0;
   //here the bot searches for cells that are not locked
   for(int c = 0; c< dimensions;c++)
   {
    for(int r = 0; r<dimensions;r++)
    {
     if(!markedgrids[r][c].ifLocked())
     {
       //adds them to the array
      randompick[rnghelpervar] = markedgrids[r][c]; 
      rnghelpervar++;
     }   
    }
   }
   //and then marks one of the not locked cells randomly
   currentcell = randompick[rng.nextInt(rnghelpervar)];
   whosplayin = mark;
   currentcell.setXO(mark);
  } 
 }
 //custom method to set whosplayin from TictactoeFrame
 public void setPlayer(int player)
 {
  whosplayin = player; 
 }
 //Custom method to set if a draw has happened from TictactoeFrame
 public void setDraw(boolean draw)
 {
  this.draw = draw; 
 }
}