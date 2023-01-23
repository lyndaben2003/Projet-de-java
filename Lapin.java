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
			System.out.println("Le lapin ne se promène plus: il a faim!");
		} else {
			super.sePromener();
		}
	}
	public boolean manger(int i){//energie = i * 1 (i: nombre de pommes)
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
}
