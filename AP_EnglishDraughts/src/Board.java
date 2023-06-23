import java.util.LinkedList;

/**
 * @author - Bokyung Lee (2431088L)
 * 
 * Board - board model class
 * */
public class Board {
	
	private Square[][] squares;
	
	//Constructor
	public Board(){
		squares = new Square[8][8];
		
		initializeSquares();
		assignPlayerIDs();
	}
	
	/**
	 * Set 64 squares with ID, Row, Col, isFilled
	 */	
	private void initializeSquares() {
		boolean rowInitialFilled, isFilled;
		int count = 0;
		
		//Rows
		for(int r=0;r<EnglishDraughts.NUM_ROWS.getValue();r++){
			rowInitialFilled = (r%2 == 1) ? true : false;
			
			//Columns
			for(int c=0;c<EnglishDraughts.NUM_COLS.getValue();c++){
				isFilled = (rowInitialFilled && c%2 == 0) ? true : (!rowInitialFilled && c%2 == 1) ? true : false;
				count++;
				
				squares[r][c] = new Square(count, r, c, isFilled);
			}
		}		
	}

	public Square[][] getSquares(){
		return this.squares;
	}
	
	public int getTotlaSquares(){
		return squares.length;
	}
	
	public void printSquareDetails(){
		for(int r=0;r<EnglishDraughts.NUM_ROWS.getValue();r++){
			for(int c=0;c<EnglishDraughts.NUM_COLS.getValue();c++){
				System.out.println(squares[r][c].getSquareID() + " --"+ squares[r][c].isPossibleToMove());
			}
		}
	}
	
	private void assignPlayerIDs() {
		
		//Rows 0-2 for player ONE
		for(int r=0;r<3;r++){					
			//Columns
			for(int c=0;c<EnglishDraughts.NUM_COLS.getValue();c++){
				if(squares[r][c].getIsFilled()){
					squares[r][c].setPlayerID(EnglishDraughts.PLAYER_ONE.getValue());
				}
			}
		}
		
		//Rows 5-7 for player TWO
		for(int r=5;r<8;r++){					
			//Columns
			for(int c=0;c<EnglishDraughts.NUM_COLS.getValue();c++){
				if(squares[r][c].getIsFilled()){
					squares[r][c].setPlayerID(EnglishDraughts.PLAYER_TWO.getValue());
				}
			}
		}
	}
	
	public LinkedList<Square> findPlayableSquares(Square selectedSquare){
		
		LinkedList<Square> playableSquares = new LinkedList<Square>();
		
		int selectedRow = selectedSquare.getSRow();
		int selectedCol = selectedSquare.getSCol();
		
		int movableRow = (selectedSquare.getPlayerID()==1) ? selectedRow+1 : selectedRow-1;
		
		//check two front squares
		twoFrontSquares(playableSquares, movableRow, selectedCol);
		crossJumpFront(playableSquares, (selectedSquare.getPlayerID()==1) ? movableRow+1 : movableRow-1, selectedCol, movableRow);
		if(selectedSquare.isKing()){
			movableRow = (selectedSquare.getPlayerID()==1) ? selectedRow-1 : selectedRow+1;
			twoFrontSquares(playableSquares, movableRow , selectedCol);
			crossJumpFront(playableSquares, (selectedSquare.getPlayerID()==1) ? movableRow-1 : movableRow+1, selectedCol, movableRow);
		}
		return playableSquares;		
	}
	
	//check two front squares
	private void twoFrontSquares(LinkedList<Square> pack, int movableRow, int selectedCol){
		
		if(movableRow>=0 && movableRow<8){
			//right Corner
			if(selectedCol>=0 && selectedCol<7){
				Square rightCorner = squares[movableRow][selectedCol+1];
				if(rightCorner.getPlayerID()==0){
					rightCorner.setPossibleToMove(true);
					pack.add(rightCorner);
				}
			}
			
			//left upper corner
			if(selectedCol>0 && selectedCol <=8){
				Square leftCorner = squares[movableRow][selectedCol-1];
				if(leftCorner.getPlayerID()==0){
					leftCorner.setPossibleToMove(true);
					pack.add(leftCorner);
				}
			}
		}
	}
	
	//cross jump - two front
	private void crossJumpFront(LinkedList<Square> pack, int movableRow, int selectedCol, int middleRow){
		
		int middleCol;
		
		if(movableRow>=0 && movableRow<8){
			//right upper Corner
			if(selectedCol>=0 && selectedCol<6){
				Square rightCorner = squares[movableRow][selectedCol+2];				
				middleCol = (selectedCol+selectedCol+2)/2;				
				if(rightCorner.getPlayerID()==0 && isOpponentInbetween(middleRow, middleCol)){
					rightCorner.setPossibleToMove(true);
					pack.add(rightCorner);
				}
			}
			
			//left upper corner
			if(selectedCol>1 && selectedCol <=7){
				Square leftCorner = squares[movableRow][selectedCol-2];
				middleCol = (selectedCol+selectedCol-2)/2;
				if(leftCorner.getPlayerID()==0 && isOpponentInbetween(middleRow, middleCol)){
					leftCorner.setPossibleToMove(true);
					pack.add(leftCorner);
				}
			}
		}
	}
	
	private boolean isOpponentInbetween(int row, int col){
		return squares[row][col].isOpponentSquare();
	}
}
