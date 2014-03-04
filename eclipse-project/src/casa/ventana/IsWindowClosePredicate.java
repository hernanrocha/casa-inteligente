package casa.ventana;
import jade.content.ContentElement;
import jade.content.Predicate;
import jade.core.AID;


public class IsWindowClosePredicate implements Predicate, ContentElement {

	private static final long serialVersionUID = 1L;
	
	private AID windowid;

	public AID getWindowid() {
//		System.out.println("[ Ventana  ] getWindowid");
		return windowid;
	}

	public void setWindowid(AID windowid) {
//		System.out.println("[ Ventana  ] setWindowid");
		this.windowid = windowid;
	}
	
}
