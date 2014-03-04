package casa.termostato;

import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class BehaviourQuerySend extends OneShotBehaviour {

	private static final long serialVersionUID = 1L;

	@Override
	public void action() {
		Termostato termostato = (Termostato) myAgent;
				
		termostato.log("Enviar QUERY (averiguar si la ventana esta cerrada)");
		
		// Crear mensaje
		ACLMessage msg = new ACLMessage(ACLMessage.QUERY_IF);
		
		// Generar ConvID
		String convID = myAgent.getLocalName() + hashCode() + "_" + System.currentTimeMillis()%1000;
		getDataStore().put("conv-id", convID);
		msg.setConversationId(convID);
		
		// Reply With
		msg.setReplyWith(convID + "_query");
		
		// Agregar parametros
		DFAgentDescription ventana = (DFAgentDescription) getDataStore().get("window");
	
		termostato.log("Destinatario: " + ventana.getName().getName());
		msg.addReceiver(ventana.getName());		

		msg.setLanguage(((Termostato) myAgent).codec.getName());
		msg.setOntology(((Termostato) myAgent).ontology.getName());
		
		// Crear clase
		IsWindowClosePredicate ventanaCerrada = new IsWindowClosePredicate();
		ventanaCerrada.setWindowid(myAgent.getAID());
		try {			
			// Agregar el contenido
			myAgent.getContentManager().fillContent(msg, ventanaCerrada);
		} catch (CodecException e) {
			e.printStackTrace();
		} catch (OntologyException e) {
			e.printStackTrace();
		}
		
		// Configurar Espera de Respuesta
		MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId(msg.getConversationId()),
												 MessageTemplate.MatchInReplyTo(msg.getReplyWith())
												 );
		getDataStore().put("mt-query", mt);
		
		// Enviar el mensaje
		termostato.log("QUERY Enviado");
		myAgent.send(msg);
	}

}
