import java.awt.geom.Line2D;
import java.awt.Graphics2D;
/*
 * Custom class for the tictactoe grid
 * This class works as the grid but beyond that the outline drawing
 * happens here, and the cells are drawn here as well each time the frame is repainted
 * it happens according to the two dimensional array supplied by the tttcomponent
 */
public class TictactoeGrid
{
 //dimensions of the grid 
 private int dimensions; 
 //the two dimensional array of marked grids
 private XO[][] markedGrids; 
 // the length of one edge of each individual grid
 private int size;
 // custom variable to push the grid into a visible place
 private final int PUSH = 45;
 //default constructor 
 public TictactoeGrid(int dimensions,XO[][] markedGrids)
 { 
  //initializing atttribtes
  this.markedGrids = markedGrids; 
  this.dimensions = dimensions;
  this.size = 400/(dimensions);
 }
 public void draw(Graphics2D g2)
 {
  //first the outlines(the lines) of the tictactoe grid are drawn
  //each column outline is drawn here
  for (int c = 0;c<dimensions+1;c++)
  {
   Line2D coloutline = new Line2D.Double(PUSH+c*size,PUSH-size/2,PUSH+c*size,(dimensions)*size+PUSH+size/2);
   g2.draw(coloutline);
  }
  //each row outline is drawn here
  for(int r = 0;r<dimensions+1;r++)
  {
   Line2D rowoutline = new Line2D.Double(PUSH-size/2,PUSH+r*size,PUSH+(dimensions)*size+size/2,PUSH+r*size);
   g2.draw(rowoutline);
  }
  //then each cell, marked or not is drawn here
  // if it is not marked then nothing is drawn
  for(int i = 0;i<dimensions;i++)
  {
   for(int x = 0; x<dimensions;x++) 
   {
    markedGrids[x][i].draw(g2);
   }
  }
 }
 
}