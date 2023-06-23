/**
 * @author - Bokyung Lee (2431088L)
 * 
 * Square - for client
 * */
public class Square {
	
	private int SquareID;
	private int sRow;
	private int sCol;	
	private boolean isKing;
	private boolean filled;
	private boolean selected;
	private boolean isPossibleToMove;
	private int playerID;	
	
	//Constructor
	public Square(int SquareID, int SquareRow, int SquareCol, boolean isFilled){
		this.SquareID=SquareID;
		this.sRow=SquareRow;
		this.sCol=SquareCol;
		this.setFilled(isFilled);
		
		if(this.filled){
			this.playerID = EnglishDraughts.EMPTY_SQUARE.getValue();
		}
		
		this.isKing = false;
		this.selected = false;
		this.isPossibleToMove = false;
	}

	public boolean getIsFilled() {
		return filled;
	}

	private void setFilled(boolean filled) {
		this.filled = filled;
	}
	
	public void setPlayerID(int ID){
		this.playerID=ID;
	}
	
	public int getPlayerID(){
		return this.playerID;
	}
	
	public int getSquareID(){
		return this.SquareID;
	}
	
	public int getSRow(){
		return this.sRow;
	}
	
	public int getSCol(){
		return this.sCol;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isPossibleToMove() {
		return isPossibleToMove;
	}

	public void setPossibleToMove(boolean isPossibleToMove) {
		this.isPossibleToMove = isPossibleToMove;
	}
	
	public boolean isOpponentSquare(){
		if(playerID!=SessionNumber.myID.getValue() && playerID!=EnglishDraughts.EMPTY_SQUARE.getValue())
			return true;
		else
			return false;
	}

	public boolean isKing() {
		return isKing;
	}

	public void setKing() {
		this.isKing = true;
	}
	
	public void removeKing(){
		this.isKing = false;
	}
}
