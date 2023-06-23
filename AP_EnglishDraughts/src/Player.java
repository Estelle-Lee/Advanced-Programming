import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author - Bokyung Lee (2431088L)
 * 
 * Player - player model class
 * */
public class Player {
	
	private String name;
	private int playerID;
	private boolean myTurn;
	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;
	
	public Player(int ID, Socket s){
		this.playerID = ID;
		this.socket = s;
		
		try{
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			
		} catch (IOException e) {
		}
	}
	
	
	public Player(String name){
		this.name = name;
		
		setMyTurn(false);
	}

	public String getName(){
		return this.name;
	}
	
	public int getPlayerID() {
		return playerID;
	}


	public void setPlayerID(int playerID) {
		this.playerID = playerID;
		SessionNumber.myID.setValue(playerID);
	}


	public boolean isMyTurn() {
		return myTurn;
	}


	public void setMyTurn(boolean myTurn) {
		this.myTurn = myTurn;
	}
	
	public int sendData(int data){
		try {
			this.dos.writeInt(data);
			return 1; //Successfull
		} catch (IOException e) {
			System.out.println("sending: Player not found");
			return -1;	//failure
		}		
	}
	
	public int receiveData(){
		int data = 0;;
		try{
			data = this.dis.readInt();
			return data;
		}catch (IOException e) {
			System.out.println("No response");
			return -1;
		}
	}
	
	public void closeConnection(){
		try {
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isOnline(){
		return socket.isConnected();
	}
	
	
}
