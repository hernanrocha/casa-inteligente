package casa.termostato;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jess.Context;
import jess.Funcall;
import jess.JessException;
import jess.Userfunction;
import jess.Value;
import jess.ValueVector;

public class JessBajarTemperatura implements Userfunction {

	private Agent myAgent;

	public JessBajarTemperatura(Agent myAgent) {
		this.myAgent = myAgent;
	}

	@Override
	public Value call(ValueVector arg0, Context arg1) throws JessException {
		if ( ((Termostato) myAgent).isDisponible() ){
			System.out.println("[   JESS   ] Agregar comportamiento: buscar ventanas para ABRIR");
			Behaviour b = new BehaviourFindWindow();
			b.getDataStore().put("closed", "n");
			myAgent.addBehaviour(b);
		}else{
			System.out.println("[   JESS   ] Termostato no disponible.");
		}

		return Funcall.TRUE;
	}

	@Override
	public String getName() {
		return "bajarTemp";
	}

}
