import javax.swing.*;

import java.io.*;
import java.net.*;
import java.awt.*;

/**
 * @author - Bokyung Lee (2431088L)
 * 
 * Session - implements runnable 
 * */
public class Session implements Runnable {
	
	private Game englishDraughts;
	private Player player1;
	private Player player2;
	
	private boolean continueGame = true;
	
	//Construct thread
	public Session(Socket p1, Socket p2){
		player1 = new Player(EnglishDraughts.PLAYER_ONE.getValue(), p1);
		player2 = new Player(EnglishDraughts.PLAYER_TWO.getValue(), p2);
		
		englishDraughts = new Game();
	}
	
	public void run() {		
		
		//Send Data back and forth		
		try{
				//notify Player 1 to start
				player1.sendData(1);				
				
				while(continueGame){
					//wait for player 1's Action
					int from = player1.receiveData();
					int to = player1.receiveData();
					checkStatus(from, to);
					updateGameModel(from, to);
							
					//Send Data back to 2nd Player
					if(englishDraughts.isOver())
						player2.sendData(EnglishDraughts.YOU_LOSE.getValue());		//Game Over notification
					int fromStatus = player2.sendData(from);
					int toStatus = player2.sendData(to);
					checkStatus(fromStatus,toStatus);
					
					//IF game is over, break
					if(englishDraughts.isOver()){
						player1.sendData(EnglishDraughts.YOU_WIN.getValue());
						continueGame=false;
						break;
					}
					
					System.out.println("after break");
					
					//wait for player 2's Action
					from = player2.receiveData();
					to = player2.receiveData();
					checkStatus(from, to);
					updateGameModel(from, to);					
					
					//Send Data back to 1st Player
					if(englishDraughts.isOver()){
						player1.sendData(EnglishDraughts.YOU_LOSE.getValue());		//Game Over notification
					}					
					fromStatus = player1.sendData(from);
					toStatus = player1.sendData(to);
					checkStatus(fromStatus,toStatus);
					
					//IF game is over, break
					if(englishDraughts.isOver()){
						player2.sendData(EnglishDraughts.YOU_WIN.getValue());
						continueGame=false;
						break;
					}
					
					System.out.println("second break");
				}
				
				
				
		}catch(Exception ex){
			System.out.println("Connection is being closed");
			
			if(player1.isOnline())
				player1.closeConnection();
			
			if(player2.isOnline())
				player2.closeConnection();
			
			return;
		}
	}
	
	private void checkStatus(int status, int status2) throws Exception{
		if(status==-1 || status2==-1){
			throw new Exception("Connection is lost");
		}
	}
	
	private void updateGameModel(int from, int to){
		Square fromSquare = englishDraughts.getSquare(from);
		Square toSquare = englishDraughts.getSquare(to);
		toSquare.setPlayerID(fromSquare.getPlayerID());
		fromSquare.setPlayerID(EnglishDraughts.EMPTY_SQUARE.getValue());
		
		checkCrossJump(fromSquare, toSquare);
	}
	
	private void checkCrossJump(Square from, Square to){		
		if(Math.abs(from.getSRow()-to.getSRow())==2){		
			int middleRow = (from.getSRow() + to.getSRow())/2;
			int middleCol = (from.getSCol() + to.getSCol())/2;
			
			Square middleSquare = englishDraughts.getSquare((middleRow*8)+middleCol+1);
			middleSquare.setPlayerID(EnglishDraughts.EMPTY_SQUARE.getValue());
		}
	}
}