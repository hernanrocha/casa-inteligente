package casa.termostato;
import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.AgentActionSchema;
import jade.content.schema.ConceptSchema;
import jade.content.schema.ObjectSchema;
import jade.content.schema.PredicateSchema;
import jade.content.schema.PrimitiveSchema;


public class AmbientOntology extends Ontology {

	private static final long serialVersionUID = 9153321955359992330L;

	public static final String ONTOLOGY_NAME = "ambient-intelligence-ontology";
	
	public static final String IS_WINDOW_CLOSE = "is-window-close";
	public static final String CHANGE_WINDOW_STATE = "change-window-state";
	public static final String WINDOW_ID = "windowid";
	public static final String PRIORITY = "priority";
	public static final String PRIORITY_TIME = "prioritytime";
	
	private static Ontology instance = new AmbientOntology();

	public static Ontology getInstance() { return instance; }
	
	// Private constructor
	private AmbientOntology() {
		super(ONTOLOGY_NAME, BasicOntology.getInstance());
		try {
			// Predicado enviado en el QUERYIF para consultar su estado, 
			// la ventana utiliza el mismo predicado dentro del CONFIRM/DISCONFIRM
			add(new PredicateSchema(IS_WINDOW_CLOSE), IsWindowClosePredicate.class);
			// AgentAction enviado dentro del REQUEST, recordar que se pasa al msj ACL dentro de un Action (AID, AgentAction)
			// donde AID es el agente que envia el request. La ventana utiliza el mismo contenido cuando contesta AGREE
			add(new AgentActionSchema(CHANGE_WINDOW_STATE), ChangeWindowStateAction.class);
			
			// Descripción del predicado is-window-close
			PredicateSchema ps = (PredicateSchema) getSchema(IS_WINDOW_CLOSE);
			// ID de la ventana consultada
			ps.add(WINDOW_ID, (ConceptSchema)getSchema(BasicOntology.AID),ObjectSchema.MANDATORY);
			
			// Descripción del AgentAction change-window-state
			AgentActionSchema aas = (AgentActionSchema) getSchema(CHANGE_WINDOW_STATE);
			// ID de la ventana receptora
			aas.add(WINDOW_ID, (ConceptSchema)getSchema(BasicOntology.AID),ObjectSchema.MANDATORY);
			// Priodidad del pedido
			aas.add(PRIORITY, (PrimitiveSchema)getSchema(BasicOntology.INTEGER), ObjectSchema.OPTIONAL);
			// Fecha en la cual la prioridad caduca
			aas.add(PRIORITY_TIME, (PrimitiveSchema)getSchema(BasicOntology.DATE), ObjectSchema.OPTIONAL);
		}
		catch (OntologyException oe) { oe.printStackTrace();}
		
		
	}

}
