public class TestSimulation{
	public static void main(String[] agrs){
		//int nbLig, int nbCol, int maxArb, int cap_arb, int pouv_arb, int cap_panier, int max_home, int num_manger
		Simulation s = new Simulation(6, 6, 2, 3, 13, 20, 10, 15);
		System.out.println("NBLIGNESMAX= " + Terrain.NBLIGNESMAX);
		System.out.println("NBCOLONNESMAX= " + Terrain.NBCOLONNESMAX);
		Agriculteur a = new Agriculteur("Agriculteur", (int)(Math.random()*((s.terrain).nbLignes-1)), (int)(Math.random()*((s.terrain).nbColonnes-1)));
		Lapin c = new Lapin("Lapin", (int)(Math.random()*((s.terrain).nbLignes-1)), (int)(Math.random()*((s.terrain).nbColonnes-1)), 5);
		//l'ordre d'ajouter les agents: Agriculteur-->Lapin(sinon, on aura problème)
		s.addAgent(a);
		s.addAgent(c);
		//planter les arbres(nombre qu'on a défini dans l'initiamisation)
		for(int i = 0; i < s.getMaxArb(); i++){
			int tmp = (int)(Math.random()*5+1);//se promener aléatoirement [1,5] fois, puis planter
			for(int j = 0; j < tmp; j++){
				s.promener();
			}
			s.planter(a);
		}

		s.runSimulation(14);

	}
}