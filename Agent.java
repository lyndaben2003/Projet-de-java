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
		return pos_agt[x][y];     //if true donc la case est occupé par un autre agent
	}
	public abstract boolean seDeplacer(int xnew, int ynew);
	public String toString(){
		return (" en position: ("+this.lig+", "+this.col+")");
	}
	public int[] getPosition(){
		int[] tab = {this.lig, this.col};
		return tab;
	}
}
