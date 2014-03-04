package casa.termostato;
import jade.content.ContentElement;
import jade.content.Predicate;
import jade.core.AID;


public class IsWindowClosePredicate implements Predicate, ContentElement {
	
	private static final long serialVersionUID = 1L;
	
	private AID windowid;

	public AID getWindowid() {
//		System.out.println("[Termostato] getWindowid");
		return windowid;
	}

	public void setWindowid(AID windowid) {
//		System.out.println("[Termostato] setWindowid");
		this.windowid = windowid;
	}
	
}
