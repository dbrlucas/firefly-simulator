package lucioles;

import outils.*;

// Étapes 2 et 3 : Définition de prairies, et simulation sans interaction

public class Prairie {

	// Seuil au delà duquel une luciole émet un flash.
	public static final double SEUIL = 100.0;

	// Indices nommés pour accéder aux données d'une luciole.
	public static final int ENERGIE = 0;
	public static final int DELTA = 1;
	
	public static double[] creerLuciole() {
		double[] luciole = new double[2];
		double lucioleEnergie = RandomGen.rGen.nextInt(100);
		double lucioleDeltaEnergie = RandomGen.rGen.nextDouble();
		luciole[ENERGIE] = lucioleEnergie;
		luciole[DELTA] = lucioleDeltaEnergie;
		return luciole;
	}

	public static double[] incrementeLuciole(double[] luciole) {
		if(luciole[ENERGIE] > SEUIL) {
			luciole[ENERGIE] = 0.0;
			return luciole;
		}
		luciole[ENERGIE] = luciole[ENERGIE] + luciole[DELTA];
		return luciole;
	}
	
	public static double[][] creerPopulation(int nbLucioles) {
		double[][] population = new double[nbLucioles][2];
		for(int i = 0;i<population.length;i++) {
			population[i] = creerLuciole();
		}
		return population;
	}
	
	public static int[][] prairieVide(int nbLignes,int nbColonnes) {
		int[][] prairie = new int[nbLignes][nbColonnes];
		 for(int i = 0;i<prairie.length;i++) {
		        for(int j = 0;j<prairie[i].length;j++) {
		            prairie[i][j] = -1;
		        }
		 }
		 return prairie;
	}
	
	// Renvoie true si la case de la prairie est occupée par une luciole et false sinon.
	public static boolean caseOccupee(int valeurCase) {
		if(valeurCase != -1) {
			return true;
		}
		return false;
	}
	
	// Renvoie true si la luciole émet un flash et false sinon.
	public static boolean lucioleEmetFlash(double[][] population,int numeroLuciole) {
		if(population[numeroLuciole][ENERGIE] > SEUIL) {
			return true;
		}
		return false;
	}
	
	// Affiche les bordures de la prairie
	public static void creerBordures(int nbBordures) {
		for(int i = 0;i<nbBordures;i++) {
			System.out.print("#");
		}
	}
	
	public static void affichePrairie(int[][] prairie,double[][] population) {
		int valeurCase =  0;
		creerBordures(prairie[0].length/2);
		System.out.println();
		
		 for(int i = 0;i<prairie.length;i++) {
			 creerBordures(1);
			 for(int j = 0;j<prairie[i].length;j++) {
				 valeurCase = prairie[i][j];
		        	
		        // Si la case est inoccupée
		        if(! caseOccupee(valeurCase)) {
		        	System.out.print(" ");
		        }
		        // Si la case est occupée par une luciole qui n'émet pas de flash
		        if(caseOccupee(valeurCase) && ! lucioleEmetFlash(population,valeurCase)) {
		        	System.out.print(".");
		        }
		        // Si la case est occupée par une luciole qui émet un flash
		        if(caseOccupee(valeurCase) &&  lucioleEmetFlash(population,valeurCase)) {
		        	System.out.print("*");
		        }
			 }
			 creerBordures(1);
			 System.out.println();
		 }
		 creerBordures(prairie[0].length/2);
		 System.out.println();
	}
	
	public static int[][] prairieLucioles(int nbLignes,int nbColonnes,double[][] population) {
		int[][] prairie = prairieVide(nbLignes,nbColonnes);
		int ligne = 0;
		int colonne = 0;
		for(int i = 0;i<population.length;i++) {
			 ligne = RandomGen.rGen.nextInt(nbLignes);
			 colonne = RandomGen.rGen.nextInt(nbColonnes);
			 if(prairie[ligne][colonne] == -1) {
				 prairie[ligne][colonne] = i;
			 } else {
				 i--;
			 }
		}
		return prairie;
	}
	
	public static void simulationPrairie(int[][] prairie,double[][] population,int nbPas) {
		for(int j = 0;j<nbPas;j++) {
			for(int i = 0;i<population.length;i++) {
				population[i] = incrementeLuciole(population[i]);
			}
			affichePrairie(prairie,population);
		}
	}
	
	public static void simulationPrairieGIF(int[][] prairie,double[][] population,int nbPas) {
		String nomFichier = "";
		String[] images = new String[nbPas];
		for(int j = 0;j<nbPas;j++) {
			for(int i = 0;i<population.length;i++) {
				population[i] = incrementeLuciole(population[i]);
			}
			nomFichier = "img/"+(j+1)+".bmp";
			images[j] = nomFichier;
			BitMap.bmpEcritureFichier(nomFichier,prairie,population,SEUIL);
		}
		GifCreator.construitGIF("simu/prairieGIF.gif", images);
	}
	
	// Fonctions d'affichage qui permettent de vérifier le bon fonctionnement du programme
	public static void afficheLuciole(double[] luciole) {
		for(int i = 0;i<luciole.length;i++) {
			System.out.print(luciole[i]+"   ");
		}
		System.out.println();
	}
	
	public static void affichePopulation(double[][] population) {
	    for(int i = 0;i<population.length;i++) {
	        for(int j = 0;j<population[i].length;j++) {
	            System.out.print(population[i][j]+"   ");
	        }
	        System.out.println();
	    }
	}
	
	// Tests
	public static void main(String[] args) {
		//double[] luciole  = creerLuciole();
		//double[][] population = creerPopulation(100);
		//double[][] population2 = creerPopulation(500);
		//int[][] prairie3 = prairieVide(5,30);
		//int[][] prairie4 = {{1,2,-1,3,4,-1,5,6,15,16,-1,19},{7,-1,9,10,11,12,-1,14,17,-1,18,0}};
		//int [][] prairie1 = prairieLucioles(5,50,population);
		//int [][] prairie2 = prairieLucioles(200,200,population);
		//afficheLuciole(luciole);
		//incrementeLuciole(luciole);
		//afficheLuciole(luciole);
		//affichePopulation(population);
		//affichePrairie(prairie3,population);
		//affichePrairie(prairie4,population);
		//affichePrairie(prairie1,population);
		//simulationPrairie(prairie1,population,20);
		//simulationPrairieGIF(prairie2,population2,30);
	}

}