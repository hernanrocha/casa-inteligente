package casa.ventana;

import jade.content.Predicate;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class BehaviourProcesarConsultas extends CyclicBehaviour {

	private static final long serialVersionUID = 1L;

	@Override
	public void action() {
		
		Ventana ventana = (Ventana) myAgent;
		
		// Esperar Consultas
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.QUERY_IF);
		ACLMessage msg = myAgent.receive(mt);
		
		Predicate ce = null;
		if (msg != null) {
			ventana.log("QUERY Recibido");
			try {
				// Procesar Consulta
				ce = (Predicate) myAgent.getContentManager().extractContent(msg);				
				IsWindowClosePredicate c = (IsWindowClosePredicate) ce;
				ventana.log("Agente Consultador: " + c.getWindowid().getName());
				
				// Procesar Respuesta
				ACLMessage resp = msg.createReply();
				ventana.log("Verificar estado de la ventana");
				Ventana v = (Ventana) myAgent;
				if (v.isClosed()){
					ventana.log("Estado Actual: Ventana Cerrada");
					resp.setPerformative(ACLMessage.CONFIRM);
				}else{
					ventana.log("Estado Actual: Ventana No Cerrada (Abierta)");
					resp.setPerformative(ACLMessage.DISCONFIRM);
				}

				// Agregar el contenido
				try {
					myAgent.getContentManager().fillContent(resp, c);
				} catch (CodecException e) {
					e.printStackTrace();
				} catch (OntologyException e) {
					e.printStackTrace();
				}
				
				// Enviar respuesta
				myAgent.send(resp);
				
				ventana.log("QUERY Respondido");
			} catch (UngroundedException e) {
				e.printStackTrace();
			} catch (CodecException e) {
				e.printStackTrace();
			} catch (OntologyException e) {
				e.printStackTrace();
			} catch (ClassCastException e){
				e.printStackTrace();
			}
		}else{
			block();
		}
	}

}
