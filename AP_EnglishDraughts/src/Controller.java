import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JOptionPane;

/**
 * @author - Bokyung Lee (2431088L)
 * 
 * Controller - controller class implements runnable interface
 * */
public class Controller implements Runnable {
	private boolean continueGame;
	private boolean waits;
	private boolean isOver;
	
	//Network
	private DataInputStream dis;
	private DataOutputStream dos;
	
	private BoardPanel boardPanel;
	private Player player;
	
	//Data
	private LinkedList<Square> selectedSquares;
	private LinkedList<Square> playableSquares;
	
	public Controller(Player player, DataInputStream input, DataOutputStream output){
		this.player = player;
		this.dis = input;
		this.dos= output;
		
		selectedSquares = new LinkedList<Square>();
		playableSquares = new LinkedList<Square>();
	}
	
	public void setBoardPanel(BoardPanel panel){
		this.boardPanel = panel;
	}
	
	@Override
	public void run() {
		continueGame = true;
		waits = true;
		isOver=false;
		
		try {
			
			//Player One
			if(player.getPlayerID()==EnglishDraughts.PLAYER_ONE.getValue()){
				//wait for the notification to start
				dis.readInt();
				player.setMyTurn(true);
			}
					
			while(continueGame && !isOver){
				if(player.getPlayerID()==EnglishDraughts.PLAYER_ONE.getValue()){
					waitForPlayerAction();
					if(!isOver)
						receiveInfoFromServer();
				}else if(player.getPlayerID()==EnglishDraughts.PLAYER_TWO.getValue()){
					receiveInfoFromServer();
					if(!isOver)
						waitForPlayerAction();
				}
			}
			
			if(isOver){
				JOptionPane.showMessageDialog(null, "Game is over",
						"Information", JOptionPane.INFORMATION_MESSAGE, null);
				System.exit(0);
			}
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Connection lost",
					"Error", JOptionPane.ERROR_MESSAGE, null);
			System.exit(0);
		} catch (InterruptedException e) {
			JOptionPane.showMessageDialog(null, "Connection interrupted",
					"Error", JOptionPane.ERROR_MESSAGE, null);
		}			
	}
	
	private void receiveInfoFromServer() throws IOException {
		player.setMyTurn(false);
		int from = dis.readInt();
		if(from==EnglishDraughts.YOU_LOSE.getValue()){
			from = dis.readInt();
			int to = dis.readInt();
			updateReceivedInfo(from, to);
			isOver=true;
		}else if(from==EnglishDraughts.YOU_WIN.getValue()){
			isOver=true;
			continueGame=false;
		}else{
			int to = dis.readInt();
			updateReceivedInfo(from, to);
		}
	}	

	private void sendMove(Square from, Square to) throws IOException {
		dos.writeInt(from.getSquareID());
		dos.writeInt(to.getSquareID());
	}

	private void waitForPlayerAction() throws InterruptedException {
		player.setMyTurn(true);
		while(waits){
			Thread.sleep(100);
		}
		waits = true;		
	}
	
	public void move(Square from, Square to){
		to.setPlayerID(from.getPlayerID());
		from.setPlayerID(EnglishDraughts.EMPTY_SQUARE.getValue());
		checkCrossJump(from, to);
		checkKing(from, to);
		squareDeselected();
		
		waits = false;
		try {
			sendMove(from, to);
		} catch (IOException e) {
			System.out.println("Sending failed");
		}		
	}
	
	//When a square is selected
	public void squareSelected(Square s) {
		
		if(selectedSquares.isEmpty()){
			addToSelected(s);
		}		
		//if one is already selected, check if it is possible move
		else if(selectedSquares.size()>=1){
			if(playableSquares.contains(s)){
				move(selectedSquares.getFirst(),s);
			}else{
				squareDeselected();
				addToSelected(s);
			}
		}
	}
	
	private void addToSelected(Square s){
		s.setSelected(true);
		selectedSquares.add(s);
		getPlayableSquares(s);
	}

	public void squareDeselected() {
		
		for(Square square:selectedSquares)
			square.setSelected(false);
		
		selectedSquares.clear();
		
		for(Square square:playableSquares){
			square.setPossibleToMove(false);
		}
		
		playableSquares.clear();
		boardPanel.repaintPanels();
	}
	
	
	private void getPlayableSquares(Square s){
		playableSquares.clear();		
		playableSquares = boardPanel.getPlayableSquares(s);
		
		for(Square square:playableSquares){
			System.out.println(square.getSquareID());
		}		
		boardPanel.repaintPanels();
	}
	
	public boolean isHisTurn(){
		return player.isMyTurn();
	}
	
	private void checkCrossJump(Square from, Square to){		
		if(Math.abs(from.getSRow()-to.getSRow())==2){		
			int middleRow = (from.getSRow() + to.getSRow())/2;
			int middleCol = (from.getSCol() + to.getSCol())/2;
			
			Square middleSquare = boardPanel.getSquare((middleRow*8)+middleCol+1);
			middleSquare.setPlayerID(EnglishDraughts.EMPTY_SQUARE.getValue());
			middleSquare.removeKing();
		}
	}
	
	private void checkKing(Square from, Square movedSquare){		
		if(from.isKing()){
			movedSquare.setKing();
			from.removeKing();
		}else if(movedSquare.getSRow()==7 && movedSquare.getPlayerID()==EnglishDraughts.PLAYER_ONE.getValue()){
			movedSquare.setKing();
		}else if(movedSquare.getSRow()==0 && movedSquare.getPlayerID()==EnglishDraughts.PLAYER_TWO.getValue()){
			movedSquare.setKing();
		}
	}
	
	private void updateReceivedInfo(int from, int to){
		Square fromSquare = boardPanel.getSquare(from);
		Square toSquare = boardPanel.getSquare(to);
		toSquare.setPlayerID(fromSquare.getPlayerID());
		fromSquare.setPlayerID(EnglishDraughts.EMPTY_SQUARE.getValue());
		checkCrossJump(fromSquare, toSquare);
		checkKing(fromSquare, toSquare);
		boardPanel.repaintPanels();
	}
}
