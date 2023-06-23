# Advanced-Programming
English Draught Game in Java


This socket programming for English-Draughts game is for a two-player in 8 * 8 board. Game follows regular rules of English-Draughts except calculating time, recording and winning process therefore the match continued in turn and when one player wins game ends. In each turn of the game, opponent cannot make any actions unless will get warning message while actions are available by mouse click.
Entire game process screenshots are attached in appendix.


Normal rules for 2 players are as follows:
1.	The piece can be moved to empty square that is diagonally adjacent to and below it.
2.	The piece can be jumped and remove opponent’s piece when both pieces are at the diagonally adjacent location.
3.	The piece which had reached to the opponent’s side may changed to the square piece that occupies backward function and called “King”.


 Winning process as follows:
1.	Player1 and Player2 may both make “King” pieces.
2.	Player who moves pieces to result no remaining pieces of the opponent may win the game.



Code Running

The program requires access to the “/src” file. 
1.	Run the server (Server.java). Then the server connects via the port and host address.
2.	Run the client (Client.java). Then player1 may ready. Session 1 may on the progress.
3.	Run the client (Client.java) in another command-line page. Then player2 may ready and the game will be started. Session 2 may on the progress.

   
Known issues:
1.	Server could not participate in game.
2.	When game ends both players screen may close with alert message: “Game is over”
3.	To restart the game, it is necessary to rerun “Client.java” twice for two players.
4.	However server may not closed until the user manually closes.



Application protocol & Mechanisms

The server is listening on port 8765 of “127.0.0.1” host address. Both the server and client may initiate commands.


1. Client connection to server – Socket listener connects via host address and port number
When the connection to server fails due to the unknown host exception, error message dialog came out and system will exit the program automatically.
While the problem occurred in connection broadly, IOException may catches and prints out error message dialog then exit the program automatically.


2. Client name – client name should be inserted by player
A client name should not be null and the length should be longer than 0.
```
String name = (String) JOptionPane.showInputDialog(null, "Enter your name to Connect", "Connect to Server", JOptionPane.OK_CANCEL_OPTION);
```

The client constructor may print message dialog and exit the client program.
```
JOptionPane.showMessageDialog(null, "Name should not be null", "Error", 	JOptionPane.ERROR_MESSAGE, null);
System.exit(0);
```


3. Run the session
When both clients plays the game, session runs to send data back and forth. If the exception occurred, “Connection is being closed” message will printed out and disconnect the player who is still online.
```
System.out.println("Connection is being closed");
			
if(player1.isOnline())
	player1.closeConnection();
			
if(player2.isOnline())
	player2.closeConnection();
```

 
4. SendData – send player’s game data 
Player’s data may passed and return 1 if the result is successful and -1 if the result is failed.
Additionally, when the result is failed, prints out “sending: player not found”


5. ReceiveData – receive player’s game data 
Receive the data and return while if the program catches IOException, prints out “No response” and return -1.



Appendix
Process of game.
![image](https://github.com/Estelle-Lee/Advanced-Programming/assets/60001831/3eccf69f-1c24-420e-bcca-95addfd93666)
![image](https://github.com/Estelle-Lee/Advanced-Programming/assets/60001831/3a3ab840-c737-4ecf-a91e-5ffce10cfb23)
![image](https://github.com/Estelle-Lee/Advanced-Programming/assets/60001831/5069697d-1010-481c-a241-094ad096dd0a)
![image](https://github.com/Estelle-Lee/Advanced-Programming/assets/60001831/e79754d7-5551-471a-acba-d54e18fa6193)
![image](https://github.com/Estelle-Lee/Advanced-Programming/assets/60001831/8199a6ea-3c38-4bc8-a656-f5eb35e3261f)
![image](https://github.com/Estelle-Lee/Advanced-Programming/assets/60001831/5f2497ec-a132-4713-8139-5308836dd571)
![image](https://github.com/Estelle-Lee/Advanced-Programming/assets/60001831/9739c0a6-6ecf-42df-889d-97244c3fefc0)
![image](https://github.com/Estelle-Lee/Advanced-Programming/assets/60001831/48f230e5-5717-4070-95ca-ea8058b16b2c)
![image](https://github.com/Estelle-Lee/Advanced-Programming/assets/60001831/43bcc31c-d3a9-43b1-8a4f-9e37910d96ca)
![image](https://github.com/Estelle-Lee/Advanced-Programming/assets/60001831/3a984519-46a6-44e6-bea2-b8203c8db390)
![image](https://github.com/Estelle-Lee/Advanced-Programming/assets/60001831/577c0dd1-f73f-45bd-869c-09067004fbb2)
![image](https://github.com/Estelle-Lee/Advanced-Programming/assets/60001831/d4068354-a995-40d3-a636-4a571ca52cae)
![image](https://github.com/Estelle-Lee/Advanced-Programming/assets/60001831/81fae0bc-a3db-467b-ab5d-568ea5d0370c)
