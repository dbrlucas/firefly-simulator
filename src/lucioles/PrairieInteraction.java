package lucioles;

import outils.*;

// Étape 4: Simulation d'une prairie avec interaction entre les lucioles

public class PrairieInteraction {

	// Seuil au delà duquel une luciole émet un flash.
	public static final double SEUIL = 100.0;

	// Indices nommés pour accéder aux données d'une luciole
	public static final int ENERGIE = 0;
	public static final int DELTA = 1;

	// Définition de l'apport d'énergie par flash, et du rayon de voisinage
	public static final double APPORT = 15.0;
	public static final int RAYON = 2;

	// Renvoie le nombre de lucioles présentes dans la prairie
	public static int nbrLucioles(int[][] prairie) {
		int cpt = 0;
		for(int i = 0;i<prairie.length;i++) {
			for(int j = 0;j<prairie[i].length;j++) {
				if(Prairie.caseOccupee(prairie[i][j])) {
					cpt++;
				}
			}
		}
		return cpt;
	}
	
	// Renvoie le nombre de lucioles présentes dans le voisinage d'une autre
	public static int nbrVoisines(int[][] prairie,int ligne,int colonne) {
		int cpt = 0;
		for(int i = ligne-RAYON;i<ligne+RAYON+1;i++) {
			if((i >= 0) & (i <prairie.length)) {
				for(int j = colonne-RAYON;j<colonne+RAYON+1;j++) {
					if(( j >= 0) & (j <prairie[i].length)) {
						if(prairie[i][j] != prairie[ligne][colonne]) {
							if(Prairie.caseOccupee(prairie[i][j])) {
								cpt++;
							}
						}
					}
				}
			}
		}
		return cpt;
	}
	
	// Renvoie le tableau de voisinage d'une seule luciole
	public static int[] voisinageLuciole(int[][] prairie,int ligne,int colonne) {
		int[] t = new int[nbrVoisines(prairie,ligne,colonne)];
		int k =0;
		for(int i = ligne-RAYON;i<ligne+RAYON+1;i++) {
			if((i >= 0) & (i <prairie.length)) {
				for(int j = colonne-RAYON;j<colonne+RAYON+1;j++) {
					if(( j >= 0) & (j <prairie[i].length)) {
						if(prairie[i][j] != prairie[ligne][colonne]) {
							if(Prairie.caseOccupee(prairie[i][j])) {
								t[k] = prairie[i][j];
								k++;
							}
						}
					}
				}
			}
		}
		return t;
	}
	
	public static int[][] voisinage(int[][] prairie) {
		int[][] t = new int[nbrLucioles(prairie)][];
		for(int i = 0;i<prairie.length;i++) {
			for(int j = 0;j<prairie[i].length;j++) {
				if(Prairie.caseOccupee(prairie[i][j])) {
					t[prairie[i][j]] = voisinageLuciole(prairie,i,j);
				}
			}
		}
		return t;
	}
	
	public static double[] incrementeLuciole(double[][] population,int[][] voisinage,double[] luciole,int numeroLuciole) {
		for(int i = 0;i<voisinage[numeroLuciole].length;i++) {
			if(Prairie.lucioleEmetFlash(population,voisinage[numeroLuciole][i])) {
				luciole[ENERGIE] += APPORT;
			}
		}
		return luciole;
	}
	
	// Renvoie une copie d'une population de lucioles
	public static double[][] creerCopiePopulation(double[][] population) {
		double[][] populationCopie = new double[population.length][];
		for(int i = 0;i<population.length;i++) {
			populationCopie[i] = population[i];
		}
		return populationCopie;
	}
	
	// Incrémente toute une population de lucioles
	public static double[][] incrementePopulation(double[][] population,double[][] populationCopie,int[][] voisinage) {
		for(int i = 0;i<population.length;i++) {
			populationCopie[i] = Prairie.incrementeLuciole(populationCopie[i]);
			populationCopie[i] = incrementeLuciole(population,voisinage,population[i],i);
		}
		return populationCopie;
	}
	
	public static void simulationPrairie(int[][] prairie,double[][] population,int nbPas) {
		int[][] voisinage = voisinage(prairie);
		double[][] populationCopie = creerCopiePopulation(population);
		
		for(int j = 0;j<nbPas/2;j++) {
			populationCopie = incrementePopulation(population,populationCopie,voisinage);
			Prairie.affichePrairie(prairie,populationCopie);
			
			populationCopie = creerCopiePopulation(population);
			populationCopie = incrementePopulation(population,populationCopie,voisinage);
			Prairie.affichePrairie(prairie,populationCopie);
			population = creerCopiePopulation(populationCopie);
		}
	}
	
	public static void simulationPrairieGIF(int[][] prairie,double[][] population,int nbPas) {
		int[][] voisinage = voisinage(prairie);
		double[][] populationCopie = creerCopiePopulation(population);
		String nomFichier = "";
		String[] images = new String[nbPas];
		
		for(int j = 0;j<nbPas;j+=2) {
			populationCopie = incrementePopulation(population,populationCopie,voisinage);
			nomFichier = "img/"+(j+1)+"_Interactions.bmp";
			images[j] = nomFichier;
			BitMap.bmpEcritureFichier(nomFichier,prairie,population,SEUIL);
			
			populationCopie = creerCopiePopulation(population);
			populationCopie = incrementePopulation(population,populationCopie,voisinage);
			nomFichier = "img/"+(j+2)+"_Interactions.bmp";
			images[j+1] = nomFichier;
			BitMap.bmpEcritureFichier(nomFichier,prairie,population,SEUIL);
			population = creerCopiePopulation(populationCopie);
		}
		GifCreator.construitGIF("simu/test.gif", images);
	}
	
	// Tests
	public static void main(String[] args) {
		/*
		int[][] prairie = {{-1,-1,-1,-1,-1,-1,0},
								{-1,-1,1,-1,2,-1,-1},
								{-1,-1,3,-1,-1,-1,-1},
								{-1,-1,-1,4,-1,-1,-1},
								{-1,-1,-1,5,-1,6,-1},
								{-1,7,8,-1,-1,-1,-1},
								{-1,-1,-1,-1,9,-1,-1}};
		*/
								
		// Test de simulationPrairie :
		/*
		double[][] population = Prairie.creerPopulation(100);
		int [][] prairie1 = Prairie.prairieLucioles(5,50,population);
		simulationPrairie(prairie1,population,20);
		*/
		
		// Test de simulationPrairieGIF :
		// Voir des exemples de simulations dans le dossier "simu"
		/*
		double[][] population1 = Prairie.creerPopulation(2000);
		int [][] prairie2 = Prairie.prairieLucioles(200,200,population1);
		simulationPrairieGIF(prairie2,population1,30);
		*/
	}

}
