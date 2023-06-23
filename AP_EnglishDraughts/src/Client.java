import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.io.*;
import java.net.*;
import java.util.Random;

/**
 * @author - Bokyung Lee (2431088L)
 * 
 * Client gui class
 * */
public class Client extends JFrame {

	private static final long serialVersionUID = 1L;

	private String address="127.0.0.1";
	private int port=8765;

	// Model
	private Player player;

	// View
	private BoardPanel boardPanel;

	// Network
	private Socket listener;
	private DataInputStream dis;
	private DataOutputStream dos;

	// Constructor
	public Client() {			
		String name = (String) JOptionPane.showInputDialog(null, "Enter your name to Connect", "Connect to Server",
				JOptionPane.OK_CANCEL_OPTION);

		if (name != null && name.length() > 0) {
			player = new Player(name);
			connect();
		} else {
			JOptionPane.showMessageDialog(null, "Name should not be null", "Error", JOptionPane.ERROR_MESSAGE,
					null);
			System.exit(0);
		}
	}

	private void connect() {

		try {
			listener = new Socket(address, port);

			// Should error occurs, handle it in a seperate thread (under try)
			dis = new DataInputStream(listener.getInputStream());
			dos = new DataOutputStream(listener.getOutputStream());

			// First player get 1 and second player get 2
			player.setPlayerID(dis.readInt());

			Controller task = new Controller(player, dis, dos);
			setup(task);

			new Thread(task).start();
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Server Error", "Error",
					JOptionPane.ERROR_MESSAGE, null);
			System.exit(0);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Connection Error.", "Error",
					JOptionPane.ERROR_MESSAGE, null);
			System.exit(0);
		}
	}

	private void setup(Controller c) {
		MouseListener mouseListener = new MouseListener();
		mouseListener.setController(c);

		boardPanel = new BoardPanel(mouseListener);
		c.setBoardPanel(boardPanel);
		add(boardPanel);
	}
	
	public static void main(String[] args) {
		Client client = new Client();
		client.setTitle("English-Draughts");
		client.pack();
		client.setVisible(true);
		client.setLocation(250, 150);
		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
