package kaleb.game;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse implements MouseListener {
	private Handler handler;
	

	
	public Mouse(Handler handler){
		this.handler = handler;
	}

	public void mouseClicked(MouseEvent m) {
		 handler.clickLocation = m.getPoint();
		System.out.println(handler.clickLocation);
	}

	public void mouseEntered(MouseEvent arg0) {

	}

	public void mouseExited(MouseEvent arg0) {

	}

	public void mousePressed(MouseEvent arg0) {

	}

	public void mouseReleased(MouseEvent arg0) {

	}
	


}
