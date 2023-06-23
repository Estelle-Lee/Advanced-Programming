import java.awt.event.*;

import javax.swing.JOptionPane;
import javax.swing.border.Border;

/**
 * @author - Bokyung Lee (2431088L)
 * 
 * MouseListener - mouselistener class extends mouse adapter
 * */
public class MouseListener extends MouseAdapter{
	
	private SquarePanel squarePanel;
	private Controller controller;
	
	public void setController(Controller c){
		this.controller = c;
	}
	
	
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		
		try{			
			if(controller.isHisTurn()){
				ToggleSelectPiece(e);
			}else{
				JOptionPane.showMessageDialog(null, "Wait for opponent plays",
					"Error", JOptionPane.ERROR_MESSAGE, null);
			}
		}catch(Exception ex){
			System.out.println("Error");
		}	
		
		
	}
	
	private void ToggleSelectPiece(MouseEvent e){
		try{
			squarePanel = (SquarePanel) e.getSource();
			Square s = squarePanel.getSquare();
			
			//if square is already selected - deselect
			if(s.isSelected()){
				System.out.println("deselect - "+s.getSquareID());
				controller.squareDeselected();				
			}
			//else select
			else{
				System.out.println("select - "+s.getSquareID());
				controller.squareSelected(s);
			}
		}catch(Exception ex){
			System.out.println("error");
		}
	}
}
