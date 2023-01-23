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
	
}