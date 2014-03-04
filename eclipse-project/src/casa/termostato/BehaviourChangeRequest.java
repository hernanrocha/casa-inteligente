package casa.termostato;

import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BehaviourChangeRequest extends OneShotBehaviour {

	private static final long serialVersionUID = 1L;

	@Override
	public void action() {
		Termostato termostato = (Termostato) myAgent;
		
		termostato.log("Enviar REQUEST (pedir cambio de estado a la ventana)");
		
		// Crear mensaje
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		
		// Generar ConvID
		String convID = (String) getDataStore().get("conv-id");
		msg.setConversationId(convID);
		
		// Reply With
		msg.setReplyWith(convID + "_request");
		
		// Agregar parametros
		DFAgentDescription ventana = (DFAgentDescription) getDataStore().get("window");
		
		termostato.log("Destinatario: " + ventana.getName().getName());
		msg.addReceiver(ventana.getName());	
		msg.setLanguage(((Termostato) myAgent).codec.getName());
		msg.setOntology(((Termostato) myAgent).ontology.getName());
		
		// Crear clase
		ChangeWindowStateAction changeState = new ChangeWindowStateAction();
		changeState.setWindowid(myAgent.getAID());
		changeState.setPriority(1);
		String string = "January 2, 2010";
		Date date = null;
		try {
			date = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(string);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		changeState.setPrioritytime(date);
		
		// Agregar el contenido
		try {			
			myAgent.getContentManager().fillContent(msg, new Action(myAgent.getAID(), changeState));
		} catch (CodecException e) {
			e.printStackTrace();
		} catch (OntologyException e) {
			e.printStackTrace();
		}
		
		// Configurar Espera de Respuesta
		MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchConversationId(msg.getConversationId()),
												 MessageTemplate.MatchInReplyTo(msg.getReplyWith())
												 );
		getDataStore().put("mt-request", mt);
		
		// Enviar el mensaje
		termostato.log("REQUEST Enviado");
		myAgent.send(msg);
		
	}

}
