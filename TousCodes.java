//Ben Bekkou Lynda 21121340
//Wang Zhenming 21202770
::::::::::::::
Agent.java
::::::::::::::
public abstract class Agent implements Mouvable{
	public String type;
	public int lig;
	public int col;

	
	public void distance(int x, int y){
		String s = "";
		int dis_x = this.lig - x;
		int dis_y = this.col - y;
		s += "La distance de " + this.toString() + " à [" + x + ", " + y +"]  est: ["
			+ dis_x + ", " + dis_y + "].";
		System.out.println(s);
	}
	public void sePromener(){//se promener aléatoirement à la case à coté
		int tmp = (int)(Math.random()*4+1);
		try{
			switch(tmp){
				// on ne peut pas se promener à la case (0,0): c'est home! donc pas possible
				case 1:{//up
					if(this.lig > 1){
						this.seDeplacer(lig-1, col);
					}
					break;
				}
				case 2: {//right
					if(this.col < Terrain.NBCOLONNESMAX-1){
						this.seDeplacer(lig, col+1);
					}
					break;
				}
				case 3: {//down
					if(this.lig < Terrain.NBLIGNESMAX-1){
						this.seDeplacer(lig+1, col);
					}
					break;
				}
				case 4: {//left
					if(this.col > 1){
						this.seDeplacer(lig, col-1);
					}
					break;
				}
			}
		} catch(NullPointerException e){
			// System.out.println("------------------ Problème, Continuer --------------------");
		}
	}
	public boolean getPosAgt(int x, int y){
		return pos_agt[x][y];
	}
	public abstract boolean seDeplacer(int xnew, int ynew);
	public String toString(){
		return (" en position: ("+this.lig+", "+this.col+")");
	}
	public int[] getPosition(){
		int[] tab = {this.lig, this.col};
		return tab;
	}
}::::::::::::::
Agriculteur.java
::::::::::::::
public class Agriculteur extends Agent{
	public Agriculteur(String type, int lig, int col){
		this.type = type;
		this.lig = lig;
		this.col = col;
	}
	public boolean seDeplacer(int xnew, int ynew) {
		if(!pos_agt[xnew][ynew]){//on peut deplacer(si c'est vide)
			pos_agt[lig][col] = false;
			pos_agt[xnew][ynew] = true;
			this.lig = xnew;
			this.col = ynew;
			return true;
		} else {
			System.out.println("Le deplacement de " + this.toString() + ") incorrect: la case ("+xnew+","+ynew+") est occupé par un autre agent.");
			return false;
		}
	}

	public String toString(){
		return (this.type + super.toString());
	}
	
}::::::::::::::
Arbre.java
::::::::::::::
public class Arbre extends Plante implements Immuable{
	public Arbre(String type, int quantite){
		super(type, quantite);
	}

}::::::::::::::
Bourgeon.java
::::::::::::::
public class Bourgeon extends Arbre implements DevenirFleur{
	public Bourgeon(String type, int quantite){
		super(type, quantite);
	}
}::::::::::::::
DevenirFleur.java
::::::::::::::
public interface DevenirFleur{
}::::::::::::::
DevenirFruit.java
::::::::::::::
public interface DevenirFruit{
}::::::::::::::
Fleur.java
::::::::::::::
public class Fleur extends Bourgeon implements DevenirFruit{
	public Fleur(String type, int quantite){
		super(type, quantite);
	}
}::::::::::::::
Fruit.java
::::::::::::::
public class Fruit extends Fleur implements Mur{
	public Fruit(String type, int quantite){
		super(type, quantite);
	}
}::::::::::::::
Immuable.java
::::::::::::::
public interface Immuable{
	
}::::::::::::::
Lapin.java
::::::::::::::
public class Lapin extends Agent{
	//flag: == 0-->faim   > 0-->pas faim
	private int energie;
	public Lapin(String type, int lig, int col, int energie){
		this.type = type;
		this.lig = lig;
		this.col = col;
		this.energie = energie;
	}
	public boolean seDeplacer(int xnew, int ynew) {
		if(energie > 0){
			if(!pos_agt[xnew][ynew]){//on peut deplacer
				pos_agt[lig][col] = false;
				pos_agt[xnew][ynew] = true;
				this.lig = xnew;
				this.col = ynew;
				energie-=1;
				return true;
			} else {
				System.out.println("Le deplacement de " + this.toString() + ") incorrect: la case("+xnew+","+ynew+") est occupé par un autre agent.");
				return false;
			}
		} else {
			System.out.println("Le deplacement de " + this.toString() + ") incorrect: il a faim !");
			return true;
		}
	}
	public int getEnergie(){
		return this.energie;
	}
	public void sePromener(){//se promener aléatoirement à la case à coté
		if(energie <= 0){
			System.out.println("Le chien ne se promène plus: il a faim!");
		} else {
			super.sePromener();
		}
	}
	public boolean manger(int i){//energie = i * 2 (i: nombre de pommes)
		if(this.energie >0){
			System.out.println("Lapin ne mange pas: il n'a pas encore faim.");
			return false;
		}else {
			//se deplacer à home
			pos_agt[lig][col] = false;
			pos_agt[0][0] = true;
			this.lig = 0;
			this.col = 0;
			this.energie = (i * 1);//on a déjà définie s'il a faim ou pas dans la classe Simulation(ainsi, pas besoin d'écrire "+=")
			System.out.println("Miam miam miam...");
			return true;
		}
	}
	public String toString(){
		return (this.type + super.toString());
	}
}::::::::::::::
Mouvable.java
::::::::::::::
public interface Mouvable{
	//pos_agt: position des agents sur le terrain   True->pleine; False->vide
	public static boolean[][] pos_agt = new boolean[Terrain.NBLIGNESMAX][Terrain.NBCOLONNESMAX];
}::::::::::::::
Mur.java
::::::::::::::
public interface Mur{
}::::::::::::::
Plante.java
::::::::::::::
public class Plante extends Ressource{
	public Plante(String type, int quantite){
		super(type, quantite);
	}
}::::::::::::::
Simulation.java
::::::::::::::
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

public class Simulation {
	public Terrain terrain;
	public Agent[] lst_agents;//on peux juste avoir deux agents: lst_agents[0]-->Agriculteur   lst_agents[1]-->Lapin
	public ArrayList<Arbre> lst_arbres;
	public ArrayList<Ressource> lst_pommes;//les pommes sur le terrain
	private int panier;// panier==2: il y a 2 pommes dans le panier 
	//map_arb: <Arbre -> pouv_arb>
	public Map<Ressource, Integer> map_arb;
	//cap_panier: max de pommes que le panier peut prendre
	public int cap_panier;
	//max_home: max de nombre de pommes qu'on peut prendre dans l'home
	public int max_home;
	//num_manger: nombre de pommes que le lapin va manger à chaque fois
	public int num_manger;
	//maxArb: nombre max d'arbre sur le terrain
	private int maxArb;
	//idx_agt: nombre d'agent pour l'instant
	public int idx_agt;
	//idx_arb: nombre d'arbre pour l'instant
	public int idx_arb;
	//idx_res: nombre de pommes(de case) pour l'instant(sur le terrain)
	public int idx_res;
	//cap_arb: capacité d'un arbre de produire une quantité de bourgeon/temps
	public int cap_arb;
	//pouv_arb: le pouvoir(nombre total) d'un arbre de produire les pommes dans sa vie
	public int pouv_arb;
	//idx_pom: nombre de pommes dans le panier pour l'instant
	public int idx_pom;

	public Simulation(int nbLig, int nbCol, int maxArb, int cap_arb, int pouv_arb, int cap_panier, int max_home, int num_manger){
		this.terrain = new Terrain(nbLig, nbCol);
		this.lst_agents = new Agent[2];
		this.lst_arbres = new ArrayList<Arbre>();
		this.lst_pommes = new ArrayList<Ressource>();
		this.map_arb = new HashMap<>();
		this.cap_panier = cap_panier;
		this.cap_arb = cap_arb;
		this.pouv_arb = pouv_arb;
		this.max_home = max_home;
		this.num_manger = num_manger;
		this.maxArb = maxArb;
		this.idx_agt = 0;
		this.idx_arb = 0;
		this.idx_res = 0;
		this.idx_pom = 0;
	}

	public void runSimulation(int nbCaracteres){//pour faire la simulation
		this.affiche(nbCaracteres);
		/*règle: quand: 1 --> la somme des "cap_arb" == 0: les arbres ne produitent plus de pommes
						2 --> la case (0,0) == null: il n'y a plus de pommes dans le home ((0,0)est la position de home)
						3 --> panier <= 0: le panier est vide
						4 --> pommes_fututre == 0: il n'y a plus de bourgeons ni fleurs qui vont devenir les pommes
				si (1 && 2 && 3 && 4), on s'arrete
				sinon: 
					-> agrandir() + promener()
					-> si lst_pommes n'est pas vide -> recolter() 
					-> transporter() + promener()
					-> manger()
					-> agrandir() + ...
		*/
		System.out.println("#################### Nous commençons notre simulation ######################\n");
		//cette boucle pour savoir s'il y a des bourgeons ou fleurs qui vont devenir pommes (pommes_future)
		int pommes_future = 0;
		for(int i = 0; i < terrain.nbLignes; i++){
			for(int j = 0; j < terrain.nbColonnes; j++){
				if(terrain.getCase(i, j) instanceof DevenirFleur){
					pommes_future += 1;
				}	
			}
		}
		//this.getPouvArbTotal(): somme de "pouv_arb" de tous les arbres sur le terrain
		while (!((this.getPouvArbTotal() <= 0) && (terrain.getCase(0, 0) == null) && (panier <= 0) && (pommes_future == 0))){
			this.promener();
			this.agrandir(nbCaracteres);
			while(this.idx_res == 0){
				this.promener();
				this.agrandir(nbCaracteres);
			}
			System.out.println("Arraylist de lst_pommes: ");
			for(Ressource r: this.lst_pommes){
				System.out.println(r.toString());
			}
			System.out.println("\nAgriculteur va recolter les pommes.");
			while(this.panier < cap_panier && idx_res > 0){
				if(!this.recolter()){//éviter le cas quand il y reste seulement les pommes dans home
					break;
				}
			}
			System.out.println();//changer la ligne (pour mieux exprimer les informations sur le terminal)
			if(this.transporter()){
				System.out.println("___________ Agriculter a transproté les pommes à home.\n");
			}
			System.out.println("Arraylist de lst_pommes: ");
			for(Ressource r: this.lst_pommes){
				System.out.println(r.toString());
			}
			while(panier != 0){//on attend le lapin manger tous les pommes dans homme pour vide le panier, puis on recolte les pommes
				System.out.println("Il y reste "+ panier +" pommes dans le panier.");
				this.affiche(nbCaracteres);
				int fois = (int)(Math.random()*3+3);
				/*Les agents promenent [3, 5] fois pour: 
				1) Lapin a la chance de manger les pommes dans home(agriculteur n'est pas dans home)
				2) Lapin a faim pour manger*/
				for(int i = 0; i < fois; i++){
					this.promener();
				}
				try{
					if(this.manger()==true){
						System.out.println("___________ Lapin a mangé les pommes.\n");
					}
				} catch(NullPointerException e){

				}
				System.out.println("Arraylist de lst_pommes: ");
				for(Ressource r: this.lst_pommes){
					System.out.println(r.toString());
				}
				this.affiche(nbCaracteres);
				if(this.transporter()){
					System.out.println("___________ Agriculter a transproté les pommes à home.\n");
				}
			}
			System.out.println("L'agriculteur a vidé le panier.");
			this.affiche(nbCaracteres);
			int fois = (int)(Math.random()*3+3);
			//lapin peut manger 
			for(int i = 0; i < fois; i++){
				this.promener();
			}
			try{
				if(this.manger()==true){
					System.out.println("___________ Lapin a mangé les pommes.\n");
				}
			} catch(NullPointerException e){

			}
			System.out.println("Arraylist de lst_pommes: ");
			for(Ressource r: this.lst_pommes){
				System.out.println(r.toString());
			}
			this.affiche(nbCaracteres);
			pommes_future = 0;
			for(int i = 0; i < terrain.nbLignes; i++){
				for(int j = 0; j < terrain.nbColonnes; j++){
					if(terrain.getCase(i, j) instanceof DevenirFleur){
						pommes_future += 1;
					}	
				}
			}
		}
		System.out.println("################## C'est fini #######################");
	}
	
	public boolean addAgent(Agent a){
		if(idx_agt < (lst_agents.length)){
			if(Agent.pos_agt[a.lig][a.col]==false){
				lst_agents[idx_agt++] = a;
				Agent.pos_agt[a.lig][a.col] = true;
				System.out.println("L'ajoute de l'agent("+a.toString()+") réussi!");
				return true;
			} else {
				System.out.println("L'ajoute de l'agent(" + a.toString() + ") incorrect: la case est déjà occupé par un autre agent.");
				return false;
			}
		} else {
			System.out.println("L'ajoute de l'agent(" + a.toString() + ") incorrect: les agents sont pleins.");
			return false;
		}
	}
	public void promener(){
		Agent[] lst_tmp = this.lst_agents;
		for(int i = 0; i < lst_agents.length-1; i++){
			lst_tmp[i] = lst_agents[i];
		}
		for(Agent a: lst_tmp){
			try{
				a.sePromener();
			} catch(NullPointerException e){
				// Il y aura peut-etre une exception de "NullPointer", je sais pas comment de le traiter pour l'instant
				// System.out.println("------------------ Problème, Continuer --------------------");
			}
		}

	}
	public boolean recolter(){//on recolte une case de pommes à chaque fois
		if(idx_res <= 0){
			System.out.println("Recoltation incorrect: il n'y a pas encore de pommes sur le terrain.");
			return false;
		}else{
			// on va recolter 
			Ressource r = this.lst_pommes.get(idx_res-1);
			boolean flag = true;//flag: pour savoir si ces pommes sont les dernière de lst_pommes ou pas (true->dernier  false->pas dernier)
			if(r.getX() == 0 && r.getY() == 0){//ce sont les pommes recoltés dans home, on ne les récolte pas
				if(idx_res > 1){//il y a d'autres pommes sur le terrain
					r = this.lst_pommes.get(idx_res-2);
					flag = false;
				} else {// il y a que les pommes dans home
					return false;//on fait rien
				}
			}
			try{
				lst_agents[0].seDeplacer(r.getX(), r.getY());//agriculteur se deplacer dans la case de ces pommes
			} catch (ArrayIndexOutOfBoundsException e){
					System.out.println("Recoltation incorrect: il n'y a pas d'autres pommes sur le terrain.");
					return false;
			}
			if((panier+r.getQuantite()) <= cap_panier){//panier ne sera pas plein
				terrain.videCase(r.getX(), r.getY());
				panier += r.getQuantite();
				//ces pommes ne sont plus sur le terrain, donc on les supprimer dans "lst_pommes"
				if(flag){//ces pommes sont les derniers dans lst_pommes
					lst_pommes.remove(idx_res-1);//ces pommes ne sont plus sur le terrain, donc on les supprimer dans "lst_pommes"
				} else {
					lst_pommes.remove(idx_res-2);
				}
				idx_res--;
			} else {//panier sera plein
				int reste = cap_panier - panier;//"reste": combien de pommes qu'on peut encore recolter dans le panier
				r.setQuantite(r.getQuantite() - reste);
				panier += reste;
			}
			System.out.println("Recoltation réussi!");
			return true;
		}
	}
	public boolean transporter(){
		//on va se deplacer dans la case (0,0) et vider le panier dans cette case
		if(panier <= 0){
			System.out.println("Transport incorrect: le panier est vide.");
			return false;
		} else {
			if(lst_agents[0].seDeplacer(0, 0)){//éviter le cas que le lapin est dans notre home
				if (terrain.getCase(0, 0) == null){// si c'est la première fois de placer les pommes dans notre home
					int tmp_add = Math.min(panier, max_home);
					Fruit p = new Fruit("Pommes_recoltés", tmp_add);
					terrain.setCase(0, 0, p);
					panier -= tmp_add;
					lst_pommes.add(idx_res++, p);
				} else {
					if((terrain.getCase(0, 0)).getQuantite() == max_home){
						System.out.println("Transport incorrect: home est déjà plein de pommes");
						return false;
					}
					//tmp_add: nombre de pommes qu'on va prendre dans cette case
					int tmp_add = Math.min(panier, max_home-(terrain.getCase(0, 0)).getQuantite());
					/*min(): soit tout les pommes de panier peuvent prendre dans la case
					soit jusqu'à "max_home" */
					int tmp = (terrain.getCase(0, 0)).getQuantite();// tmp: mémoriser la quantité de cette case
					(terrain.getCase(0, 0)).setQuantite(tmp+tmp_add);
					panier -= tmp_add;
				}
				System.out.println("Transport réussi!");
				return true;
			} else {
				System.out.println("Transport incorrect: il y a les autres agents dans le home.");
				return false;
			}
		}

	}
	public boolean manger(){
		if(terrain.getCase(0, 0) == null){
			System.out.println("Lapin ne peut pas manger: il n'y a de pommes dans le home.");
			return false;
		} else {
			//si agriculteur est dans la home, lapin ne peut pas manger
			if(lst_agents[1].getPosAgt(0, 0)){
				System.out.println("Lapin ne peut pas manger: Agriculter est encore dans le home.");
				return false;
			}
			int tmp = (terrain.getCase(0, 0)).getQuantite();//tmp: mémoriser le nombre de pommes dans ce case
			int idx = lst_pommes.indexOf(terrain.getCase(0,0));//idx: index de pommes_recoltés dans lst_pommes
			//tmp_mange: nombre de pommes que Lapin va manger
			int tmp_mange = Math.min(num_manger, tmp);
			if(!((Lapin)lst_agents[1]).manger(tmp_mange)){//s'il n'a pas faim, on ne mange pas
				return false;
			}
			if(tmp - tmp_mange == 0){// Lapin a mangé toutes les pommes
				terrain.videCase(0, 0);//on vide la case (0,0)
				lst_pommes.remove(idx);//on le supprimer dans lst_pommes 
				idx_res--;//index des ressources (au total)
			} else {
				(terrain.getCase(0, 0)).setQuantite(tmp - tmp_mange);
			}
			System.out.println("Lapin a réussi à manger!");
			return true;
		}
	}
	public boolean planter(Agriculteur agri){
		if(idx_arb >= maxArb){
			System.out.println("Plantation d'arbre incorrect: il y a déjà assez d'arbres sur terrain.");
			return false;
		}else {
			//il va se promener jusqu'à trouver une cqse libre pour planter un arbre
			//tmp<=10: éviter le cas de reculter sans arret
			int tmp = 0;
			//tant qu'on est en dehors du terrain, ou que la case n'est pas vide, ou c'est autour de la case (0, 0) (our sweety home)
			while(agri.lig >= terrain.nbLignes || agri.col >= terrain.nbColonnes || (terrain.getCase(agri.lig, agri.col)) != null 
				|| agri.lig <= 1 || agri.col <= 1){
				if(tmp > 10){//s'il s'est promené plusieurs fois en dehors du terrain, on va le forcer à se trouver dans le terrain
					agri.seDeplacer(0, 0);//par défault
					tmp = 0;
				}
				agri.sePromener();
				tmp++;
			} 
			Arbre a = new Arbre("Arbre", 1);
			terrain.setCase(agri.lig, agri.col, a);
			System.out.println("La plantation d'arbre réussi!");
			idx_arb++;
			map_arb.put(a, this.pouv_arb);//initialisation de pouv_arb de cet arbre et le mémoriser dans map_arb
			return true;
		}
	}


	public void agrandir(int nbCaracteres){
		System.out.println("============== Les ressources se transforment ==============");
		//parcourir tous les cases sur le terrains
		ArrayList<Bourgeon> lst_tmp = new ArrayList<Bourgeon>();//pour mémoriser les bourgeons qui sont produits par l'arbre, éviter de devenir le fleur
		for(int i = 0; i < terrain.nbLignes; i++){
			for(int j = 0; j < terrain.nbColonnes; j++){
				if(terrain.getCase(i, j) instanceof Mur){//c'est une pomme, on l'ajoute dans lst_pommes
					if(!lst_pommes.contains(terrain.getCase(i, j))){// si la pomme n'est pas dans lst_pommes
						lst_pommes.add(idx_res++, terrain.getCase(i, j));
					}
				}else if (terrain.getCase(i, j) instanceof DevenirFruit){//c'est une fleur
					int quant = (terrain.getCase(i, j)).getQuantite();// quantite de la ressource
					int x = (terrain.getCase(i, j)).getX();
					int y = (terrain.getCase(i, j)).getY();
					terrain.videCase(x, y);
					terrain.setCase(x, y, new Fruit("Pomme", quant));
					lst_pommes.add(idx_res++, terrain.getCase(i, j));//on l'ajoute dans lst_pommes

				}else if(terrain.getCase(i, j) instanceof DevenirFleur){//c'est un bourgeon
					//éviter de grandir lequel est viens de produit par l'arbre
					if(!lst_tmp.contains(terrain.getCase(i, j))){//si c'est pas lequel nouveau
						int quant = (terrain.getCase(i, j)).getQuantite();
						int x = (terrain.getCase(i, j)).getX();
						int y = (terrain.getCase(i, j)).getY();
						terrain.videCase(x, y);
						terrain.setCase(x, y, new Fleur("Fleur", quant));
					} // si c'est lequal nouveau, on ne le fait grandir
				}else if (terrain.getCase(i, j) instanceof Immuable){//c'est une arbre
					int x = (terrain.getCase(i, j)).getX();
					int y = (terrain.getCase(i, j)).getY();
					//chercher la case vide autour d'arbre, et set bougeon dans cette case
					//s'il n'y a pas de case vide, on fait rien
					//l'ordre: up-->right-->down-->left
					//pour produire les pommes, on va vérifier "pouv_arb" (arbre peut encore produire ou pas)
					int tmp = this.getPouvArb(terrain.getCase(i, j));//tmp = le reste de pouvoir de l'arbre (de produire les pommes)
					if(tmp <= 0){//l'arbre ne peut plus produire de pommes
						System.out.println("Production d'arbre incorrect: il ne peut plus produire de pommes.");
					} else {
						if(tmp > cap_arb){
							tmp = cap_arb;// on change le valeur de "tmp"
						}
						Bourgeon b = new Bourgeon("Bourgeon", tmp);
						if((x-1 >= 0)&&(terrain.getCase(x-1, y) == null)){//up
							terrain.setCase(x-1, y, b);
							lst_tmp.add(b);
							map_arb.put(terrain.getCase(i,j), (map_arb.get(terrain.getCase(i, j)) - tmp));//pouv_arb -= cap_arb
						} else if((y+1 < terrain.nbColonnes)&&(terrain.getCase(x, y+1) == null)){//right
							terrain.setCase(x, y+1, b);
							lst_tmp.add(b);
							map_arb.put(terrain.getCase(i,j), (map_arb.get(terrain.getCase(i, j)) - tmp));//pouv_arb -= cap_arb
						} else if((x+1 < terrain.nbLignes)&&(terrain.getCase(x+1, y)==null)){//down
							terrain.setCase(x+1, y, b);
							lst_tmp.add(b);
							map_arb.put(terrain.getCase(i,j), (map_arb.get(terrain.getCase(i, j)) - tmp));//pouv_arb -= cap_arb
						} else if((y-1 >= 0)&&(terrain.getCase(x, y-1)==null)){//left
							terrain.setCase(x, y-1, b);
							lst_tmp.add(b);
							map_arb.put(terrain.getCase(i,j), (map_arb.get(terrain.getCase(i, j)) - tmp));//pouv_arb -= cap_arb
						} //sinon(pas de case vide autour de l'arbre), on fait rien
					}
				} else{//c'est une case vide
					continue;
				}
			}
		}
		this.affiche(nbCaracteres);
	}

	public int getPouvArb(Ressource r){
		return map_arb.get(r);
	}
	public void affiche(int nbCaracteres){
		this.terrain.affiche(nbCaracteres);
	}
	public int getPanier(){
		return this.panier;
	}
	public int getMaxArb(){
		return this.maxArb;
	}
	public int getPouvArbTotal(){
		int pouv_arb_total = 0;
		for(Integer pouv_arb: map_arb.values()){
			pouv_arb_total += pouv_arb;
		}
		return pouv_arb_total;
	}


}::::::::::::::
TestSimulation.java
::::::::::::::
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
}::::::::::::::
TestTerrain.java
::::::::::::::
/**
 * @author Christophe Marsala (LU2IN002 2022oct)
 * 
 * Gestion d'un terrain
 *
 */

import java.util.ArrayList;

public class TestTerrain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Exemple de création de terrain
		Terrain t = new Terrain(5,6);
		
		// Terrain initial : il est vide
		t.affiche(2);
		// Informations sur le terrain
		System.out.println("Informations sur le terrain:\n"+t);		
		
		// On créé une ressource
		Ressource e1 = new Ressource("Miel",5);
		// et on la place sur le terrain
		if (t.setCase(2,3,e1))
			System.out.println("Ajout de " +e1+" valide !");
		else
			System.out.println("Ajout incorrect: problème de coordonnées !");
		
		// On créé une autre ressource
		Ressource e2 = new Ressource("Pollen",4);
		// On rajoute la ressource sur le terrain
		if (t.setCase(0,2,e2))
			System.out.println("Ajout de " +e2+" valide !");
		else
			System.out.println("Ajout incorrect: problème de coordonnées !");
		
		// On rajoute une autre ressource et on la met sur le terrain
		if (t.setCase(4,1,new Ressource("Pollen",3)))
			System.out.println("Ajout valide !");
		else
			System.out.println("Ajout incorrect: problème de coordonnées !");

		// Affichage du terrain avec les ressources ajoutées
		t.affiche(6);
		// Informations sur le terrain
		System.out.println("Informations sur le terrain:\n"+t);
		
		// Contenu d'une case:
		System.out.println("Dans la case (1,4): "+t.getCase(1,4));
		// Contenu d'une case:
		System.out.println("Dans la case (0,2): "+t.getCase(0,2));
		
		// Vidage d'une case:
		System.out.println("Vidage d'une case:");
		Ressource etaitDansLaCase = t.videCase(0,2);
		if (etaitDansLaCase == null)
			System.out.println("La case était déjà vide.");
		else 
			System.out.println("La case contenait : "+etaitDansLaCase);
		
		// Affichage du terrain avec les ressources ajoutées
		// on n'utilise que 3 caractères d'affichage
		t.affiche(3);
		
				System.out.println("Liste de toutes les ressources présentes actuellement:");
		ArrayList<Ressource> liste = t.lesRessources();
		for (Ressource r : liste) {
			System.out.println(r);
		}
		
	}

}
