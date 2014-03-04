package casa.termostato;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jess.Jesp;
import jess.JessException;
import jess.Rete;

public class BehaviourSensor extends TickerBehaviour {

	private static final long serialVersionUID = 1L;
	
	private Rete jess;
	
	public BehaviourSensor(Agent a) {
		super(a, 5000);
		
		jess = new Rete();
		try {
			String jessFile = "rules.clp";
			jess.addUserfunction(new JessBajarTemperatura(myAgent));
			jess.addUserfunction(new JessSubirTemperatura(myAgent));
			File file = new File (jessFile);
			FileReader fr = new FileReader(file);
			Jesp j = new Jesp(fr, jess);
			
			// Ejecuta el parser (sin mostrar datos por consola)
			j.parse(false);
		}catch (JessException je) {
			je.printStackTrace();
		}catch (FileNotFoundException je) {
			je.printStackTrace();
		}
	}

	@Override
	protected void onTick() {
		try {
			Termostato termostato = (Termostato) myAgent;
			
			termostato.log("Ticker. Simular temperatura");
			
			// Simula la temperatura sensada
			int temp = ((int)(Math.random()*100) % 10) + 15;
			jess.eval("(bind ?temp " + temp + ")");
			termostato.log("Temperatura Simulada: " + temp);
			
			// Llama a la función que chequea los cambios de temperatura
			jess.eval("(temp-checking)");
			
			// Ejecuta el motor de inferencia
			jess.run();
		}catch (JessException je) {
			je.printStackTrace();
		}
		
	}	

}
