import java.awt.geom.Line2D;
import java.awt.Graphics2D;
import java.awt.Color;
//Custom class to represent individual cells in a grid
// is only considered with drawing the markings and the placement of the cells
// otherwise it doesn't draw the walls or the outlines of the grid, those are drawn by the grid
public class XO
{
 private int row,column,size,cellholder;
 private int x,y;
 private boolean celllock;
 private int PUSH = 45;
 //default constructor for each individual cell
 public XO(int row,int column,int size,int cellholder)
 {
  //it has its row 
  this.row = row;
  // a x coordinate
  x = row*size+PUSH;
  // a y coordinate
  y = column*size+PUSH;
  // it has its column  
  this.column = column;
  // it has its size
  this.size = size;
  // this variable serves to show if it is marked or not
  this.cellholder = cellholder;
  // the cell by default is open to being marked
  celllock = false;
 }
 //Custom method to draw the insides of the cell(the marks, 
 //otherwise nothing is drawn as the outlines are already drawn by the grid
 public void draw(Graphics2D g2)
 {
  int x1 = this.x+size/4;
  int y1 = this.y+size/4;
  //if there must be an X drawn in the cell  
  if(cellholder == 1)
  {
   //Two lines are drawn which intersect at the center of the cell
   Line2D yx1 = new Line2D.Double(x1,y1,x1+size/2,y1+size/2);
   Line2D yx2 = new Line2D.Double(x1+size/2,y1,x1,y1+size/2);
   g2.draw(yx1);
   g2.draw(yx2);
   celllock = true;
  } 
   //if there must be an O drawn in the cell  
  if(cellholder == 2)
  {
   x1 = size/8+this.x;
   y1 = size/8+this.y; 
   //an oval is drawn in the middle of the cell
   g2.drawOval(x1,y1,size*3/4,size*3/4);
   celllock = true;
  }
 }
 //Custom method to mark the cell with necessary mark(X or O)
 public void setXO(int xo)
 {
  cellholder = xo;
 }
 //Custom method to change if the cell is locked or not(if the cell is available for marking or not)
 public void setLock(boolean lock)
 {
  celllock = lock;
 }
 //Custom method to return the x coordinate of the cell
 public int getX()
 {
  return size*row;
 }
 //Custom method to return row of the cell 
 public int getRow()
 {
  return row; 
 }
//Custom method to return the column of the cell
 public int getColumn()
 {
  return column; 
 }
 //Custom method to return the y coordinate of the cell
 public int getY()
 {
  return size*column;
 }
 //Custom method to compare cells and return true if they have the same mark
 public boolean compareCells(XO next)
 {
  if(this.cellholder == next.getMark())
   return true;
  else 
   return false;
 }
 //Custom method to return the mark of the current cell
 private int getMark()
 {
  return cellholder; 
 }
 //Custom method to return the boolean variable if the cell is locked
 public boolean ifLocked()
 {
  return celllock;
 }
 //Custom method to show the cell's position, was used for bug fixing
 public String toString()
 {
  return "("+row+","+column+")";
 }
}