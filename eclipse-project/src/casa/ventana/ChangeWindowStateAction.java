package casa.ventana;

import java.util.Date;
import jade.content.AgentAction;
import jade.core.AID;

public class ChangeWindowStateAction implements AgentAction {
	
	private static final long serialVersionUID = 1L;
	
	private AID windowid;
	private int priority;
	private Date prioritytime;
	
	public AID getWindowid() {
		return windowid;
	}
	public void setWindowid(AID windowid) {
		this.windowid = windowid;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public Date getPrioritytime() {
		return prioritytime;
	}
	public void setPrioritytime(Date prioritytime) {
		this.prioritytime = prioritytime;
	}
	
}
