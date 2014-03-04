package casa;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;
import java.util.List;

public class Casa {

	private static String hostname = "127.0.0.1"; 
	private static AgentContainer mainContainer;
	private static List<AgentController> agentList; // agents's ref

	public static void main(String[] args){

		// 1) Crear plataforma (Main Container + DF + AMS)
		emptyPlatform();

		// 2) Crear RMA
		createRMA(mainContainer);

		// 3) Crear Agentes
		agentList = createAgents(mainContainer);

		// 4) Iniciar Agentes
		startAgents(agentList);

	}

	// 1) Crear plataforma (Main Container + DF + AMS)
	private static void emptyPlatform(){

		Runtime rt = Runtime.instance();

		System.out.println("Launching a main-container");
		Profile pMain = new ProfileImpl(hostname, 8888, null);
		mainContainer = rt.createMainContainer(pMain); //DF + AMS incluido
		
		System.out.println("Plaform ok");
		
	}

	// 2) Crear RMA
	private static void createRMA(ContainerController mc) {

		System.out.println("Launching the rma agent on the main container ...");
		AgentController rma;

		try {
			rma = mc.createNewAgent("rma", "jade.tools.rma.rma", new Object[0]);
			rma.start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
			System.out.println("Launching of rma agent failed");
		}
		
	}

	// 3) Crear Agentes
	private static List<AgentController> createAgents(ContainerController c) {

		List<AgentController> agentList = new ArrayList<AgentController>();

		System.out.println("Launching agents...");
		AgentController v1 = null, t1 = null, t2 = null;
		try {
			v1 = c.createNewAgent("ventana", "casa.ventana.Ventana", null);
			t1 = c.createNewAgent("termo1", "casa.termostato.Termostato", null);
			t2 = c.createNewAgent("termo2", "casa.termostato.Termostato", null);
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
		
		// Agregar a la lista de agentes
		agentList.add(v1);
		agentList.add(t1);
		agentList.add(t2);
		
		System.out.println("Agents launched...");
		
		return agentList;
		
	}

	// 4) Iniciar Agentes
	private static void startAgents(List<AgentController> agentList){

		System.out.println("Starting agents...");

		for(final AgentController ac: agentList){
			try {
				ac.start();
			} catch (StaleProxyException e) {
				e.printStackTrace();
			}

		}
		
		System.out.println("Agents started...");
		
	}

}
