package casa.termostato;

import jade.core.behaviours.OneShotBehaviour;

public class BehaviourFinalizarInteraccion extends OneShotBehaviour {

	private static final long serialVersionUID = 1L;

	@Override
	public void action() {
		Termostato termostato = (Termostato) myAgent;

		termostato.log("Interaccion realizada correctamente");
	}

}
