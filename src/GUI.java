import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GUI extends JFrame{
	
	public boolean resetter = false;
	
	public boolean flagger = false;
	
	Date startDate = new Date();
	
	Date endDate;
	
	int spacing = 5;
	int neighs = 0;
	String vicMes = "Nothing yet!";
	Random rand = new Random();
	int[][] mines = new int[16][9];
	int[][] neighbours = new int[16][9];
	boolean[][] revealed = new boolean[16][9];
	boolean[][] flagged = new boolean[16][9];

	public int mx = -100;
	public int my = -100;
	
	public int smileyX = 605;
	public int smileyY = 5;
	
	public int smileyCentreX = smileyX + 35;
	public int smileyCentreY = smileyY + 35;
	
	public int flaggerX = 445;
	public int flaggerY = 5;
	
	public int flaggerCenterX = flaggerX + 35;
	public int flaggerCenterY = smileyY + 35;
	
	public int timeX = 1130;
	public int timeY = 5;
	
	public int vicMesX = 740;
	public int vicMesY = -50;
	
	public int sec = 0;
	
	public boolean happiness = true;
	
	public boolean victory = false;
	
	public boolean defeat = false;
	
	public GUI() {
		this.setTitle("Minesweeper");
		this.setSize(1286,829);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
		
		for(int i=0;i<16;i++) {
			for(int j=0;j<9;j++) {
				if(rand.nextInt(100)<20) {
					mines[i][j] = 1;
				}
				else {
					mines[i][j] = 0;
				}
				revealed[i][j]=false;
			}
		}
		
		for(int i=0;i<16;i++) {
			for(int j=0;j<9;j++) {
				neighs = 0;
				for(int m=0;m<16;m++) {
					for(int n=0;n<9;n++) {
						if(!(m==i && n==j)) {
						if(isN(i, j, m, n)==true) 
							neighs++;
						}
					}
				}
				neighbours[i][j] = neighs;
			}
		}
		
		Board board = new Board();
		this.setContentPane(board);
		
		Move move = new Move();
		this.addMouseMotionListener(move);
		
		Click click = new Click();
		this.addMouseListener(click);
	}
	
	public class Board extends JPanel{
		public void paintComponent(Graphics g) {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 0, 1280, 800);
			for(int i=0;i<16;i++) {
				for(int j=0;j<9;j++) {
					g.setColor(Color.gray);
					if(revealed[i][j]==true) {
						g.setColor(Color.white);
						if(mines[i][j]==1) {
							g.setColor(Color.red);
						}
					}
					if(mx>=spacing+i*80 && mx<spacing+i*80+80-2*spacing && my>=spacing+j*80+80+26 && my<spacing+j*80+26+80+80-2*spacing) {
						g.setColor(Color.lightGray);
					}
					g.fillRect(spacing+i*80,spacing+j*80+80,80-2*spacing,80-2*spacing);
					if(revealed[i][j]==true) {
						g.setColor(Color.black);
						if(mines[i][j]==0 && neighbours[i][j]!=0) {
						if(neighbours[i][j]==1) {
							g.setColor(Color.blue);
						}
						else if(neighbours[i][j]==2) {
							g.setColor(Color.green);
						}
						else if(neighbours[i][j]==3) {
							g.setColor(Color.red);
						}
						else if(neighbours[i][j]==4) {
							g.setColor(new Color(0,0,128));
						}
						else if(neighbours[i][j]==5) {
							g.setColor(new Color(178,34,34));
						}
						else if(neighbours[i][j]==6) {
							g.setColor(new Color(72,209,204));
						}
						else if(neighbours[i][j]==8) {
							g.setColor(Color.DARK_GRAY);
						}
						g.setFont(new Font("Tahoma",Font.BOLD,40));
						g.drawString(Integer.toString(neighbours[i][j]),i*80+27,j*80+80+55);
						}
						else if(mines[i][j]==1){
							g.fillRect(i*80+10+20,j*80+80+20,20,40);
							g.fillRect(i*80+20,j*80+80+10+20,40,20);
							g.fillRect(i*80+5+20,j*80+80+5+20,30,30);
							g.fillRect(i*80+38, j*80+80+15, 4, 50);
							g.fillRect(i*80+15, j*80+80+38, 50, 4);
						}
					}
					
					//flags painting
					if(flagged[i][j]==true) {
						g.setColor(Color.black);
						g.fillRect(i*80+31+5,j*80+80+15+5, 7, 40);
						g.fillRect(i*80+20+5,j*80+80+50+5, 30, 10);
						g.setColor(Color.red);
						g.fillRect(i*80+16+5,j*80+80+15+5, 20, 15);
						g.setColor(Color.black);
						g.drawRect(i*80+16+5,j*80+80+15+5, 20, 15);
						g.drawRect(i*80+17+5,j*80+80+16+5, 18, 13);
						g.drawRect(i*80+18+5,j*80+80+17+5, 16, 11);
					}
				}
			}
			//smiley painting
			
			g.setColor(Color.yellow);
			g.fillOval(smileyX, smileyY, 70, 70);
			g.setColor(Color.black);
			g.fillOval(smileyX+15, smileyY+15, 10, 10);
			g.fillOval(smileyX+45, smileyY+15, 10, 10);
			if(happiness==true) {
				g.fillRect(smileyX+20, smileyY+50, 30, 5);
				g.fillRect(smileyX+17, smileyY+45, 5, 5);
				g.fillRect(smileyX+48, smileyY+45, 5, 5);
			}
			else {
				g.fillRect(smileyX+20, smileyY+45, 30, 5);
				g.fillRect(smileyX+17, smileyY+50, 5, 5);
				g.fillRect(smileyX+48, smileyY+50, 5, 5);
			}
			
			//flagger painting 
			
			g.setColor(Color.black);
			g.fillRect(flaggerX+31,flaggerY+15, 7, 40);
			g.fillRect(flaggerX+20,flaggerY+50, 30, 10);
			g.setColor(Color.red);
			g.fillRect(flaggerX+16,flaggerY+15, 20, 15);
			g.setColor(Color.black);
			g.drawRect(flaggerX+16,flaggerY+15, 20, 15);
			g.drawRect(flaggerX+17,flaggerY+16, 18, 13);
			g.drawRect(flaggerX+18,flaggerY+17, 16, 11);
			if(flagger==true) {
				g.setColor(Color.red);
			}
			g.drawOval(flaggerX, flaggerY, 70, 70);
			g.drawOval(flaggerX+1, flaggerY+1, 68, 68);
			g.drawOval(flaggerX+2, flaggerY+2, 66, 66);
			
			//time counter painting
			
			g.setColor(Color.black);
			g.fillRect(timeX, timeY,140,70);
			if(defeat==false && victory==false) {
				sec = (int)((new Date().getTime()-startDate.getTime())/1000);
			}
			if(sec>999) {
				sec=999;
			}
			g.setColor(Color.white);
			if(victory==true) {
				g.setColor(Color.green);
			}
			else if(defeat==true) {
				g.setColor(Color.red);
			}
			g.setFont(new Font("Tahoma",Font.PLAIN,80));
			if(sec<10) {
				g.drawString("00"+Integer.toString(sec), timeX, timeY+65);
			}else if(sec<100) {
				g.drawString("0"+Integer.toString(sec), timeX, timeY+65);
			}else {
				g.drawString(Integer.toString(sec), timeX, timeY+65);
			}
			
			//victory message painting 
			
			if(victory==true) {
				g.setColor(Color.green);
				vicMes = "YOU WIN";
			}else if(defeat==true) {
				g.setColor(Color.red);
				vicMes = "YOU LOSE";
			}
			
			if(victory==true || defeat==true) {
				
				vicMesY = -50 + (int)(new Date().getTime()-endDate.getTime())/10;
				if(vicMesY>67) {
					vicMesY = 67;
				}
				g.setFont(new Font("Tahoma",Font.PLAIN,70));
				g.drawString(vicMes, vicMesX, vicMesY);
			}
			
			
		}
	}
	
	public class Move implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			mx=e.getX();
			my=e.getY();
			/*
			System.out.println("The Mouse was moved");
			System.out.println("X: " +mx+", Y: "+my);
			*/
		}
	}
	
	public class Click implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			mx=e.getX();
			my=e.getY();
			if(inBoxX()!=-1 && inBoxY()!=-1 && revealed[inBoxX()][inBoxY()]==false) {
				System.out.println("The Mouse is in the ["+inBoxX()+","+inBoxY()+"], Number of mine neighs: "+neighbours[inBoxX()][inBoxY()]);
			if(flagger==true) 
			{
				if(flagged[inBoxX()][inBoxY()]==false) 
				{
					flagged[inBoxX()][inBoxY()] = true;
				} 
				else 
				{
					flagged[inBoxX()][inBoxY()] = false;
				}
			}
		    	else 
		    	{
		    		if(flagged[inBoxX()][inBoxY()]==false) {
		    			revealed[inBoxX()][inBoxY()] = true;
		    		}
		    	}
			}
			else {
				System.out.println("The Pointer is not inside of any box!");
			}
			if(inSmiley()==true) {
				resetAll();
			}
			if(inFlagger()==true) {
				if(flagger==false) {
					flagger=true;
					System.out.println("In flagger = true!");
				} else {
					flagger=false;
				}
			}
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public void checkVictoryStatus() {
		if(defeat==false) {
		for(int i=0;i<16;i++) {
			for(int j=0;j<9;j++) {
				if(revealed[i][j]==true && mines[i][j]==1) {
					defeat = true;
					happiness = false;
					endDate = new Date();
				}
			}
		}
	}
		
		if(totalBoxesRevealed() >= 144 - totalMines() && victory==false) {
			victory = true;
			endDate = new Date();
		}
	}
	
	public int totalMines() {
		int total = 0;
		for(int i=0;i<16;i++) {
			for(int j=0;j<9;j++) {
				if(mines[i][j]==1) {
					total++;
				}
			}
		}
		return total;
	}
	
	public int totalBoxesRevealed() {
		int total = 0;
		for(int i=0;i<16;i++) {
			for(int j=0;j<9;j++) {
				if(revealed[i][j]==true) {
					total++;
				}
			}
		}
		return total;
	}
	
	public void resetAll() {
		flagger=false;
		vicMesY = -50;
		vicMes = "Nothing yet!";
		resetter = true;
		startDate = new Date();
		
		happiness = true;
		victory = false;
		defeat = false;
		
		for(int i=0;i<16;i++) {
			for(int j=0;j<9;j++) {
				if(rand.nextInt(100)<20) {
					mines[i][j] = 1;
				}
				else {
					mines[i][j] = 0;
				}
				revealed[i][j]=false;
				flagged[i][j] = false;
			}
		}
		
		for(int i=0;i<16;i++) {
			for(int j=0;j<9;j++) {
				neighs = 0;
				for(int m=0;m<16;m++) {
					for(int n=0;n<9;n++) {
						if(!(m==i && n==j)) {
						if(isN(i, j, m, n)==true) 
							neighs++;
						}
					}
				}
				neighbours[i][j] = neighs;
			}
		}
		
		resetter = false;
	}
	
	public boolean inSmiley() {
		int dif =(int) Math.sqrt(Math.abs(mx-smileyCentreX)*Math.abs(mx-smileyCentreX)+Math.abs(my-smileyCentreY)*Math.abs(my-smileyCentreY));
		if(dif<35) {
			return true;
		}
		return false;
	}
	
	public boolean inFlagger() {
		int dif =(int) Math.sqrt(Math.abs(mx-flaggerCenterX)*Math.abs(mx-flaggerCenterY)+Math.abs(my-flaggerCenterY)*Math.abs(my-flaggerCenterY));
		if(dif<35) {
			return true;
		}
		return false;
	}
	
	public int inBoxX() {
		for(int i=0;i<16;i++) {
			for(int j=0;j<9;j++) {
				if(mx>=spacing+i*80 && mx<spacing+i*80+80-2*spacing && my>=spacing+j*80+80+26 && my<spacing+j*80+26+80+80-2*spacing) {
					return i;
				}
			}
		}
		return -1;
	}
	public int inBoxY() {
		for(int i=0;i<16;i++) {
			for(int j=0;j<9;j++) {
				if(mx>=spacing+i*80 && mx<spacing+i*80+80-2*spacing && my>=spacing+j*80+80+26 && my<spacing+j*80+26+80+80-2*spacing) {
					return j;
				}
			}
		}
		return -1;
	}
	
	public boolean isN(int mX,int mY,int cX,int cY) {
		if(mX-cX<2 && mX-cX>-2 && mY-cY<2 && mY-cY>-2 && mines[cX][cY]==1) {
			return true;
		}
		return false;
	}
}