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
		luciole[ENERGIE] += luciole[DELTA];
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
	
	// Tests
	public static void main(String[] args) {
		/*
		double[][] population1 = creerPopulation(10);
		for(int i = 0;i<population1.length;i++) {
			System.out.print(population1[i]);
			System.out.println("   "+population1[i][ENERGIE]);
		}
		*/
		
		/*
		double[][] population2 = creerPopulation(30);
		int[][] prairie1 = prairieVide(5,30);
		affichePrairie(prairie1,population2);
		int[][] prairie2 = prairieVide(3,100);
		affichePrairie(prairie2,population2);
		*/

		/*
		double[][] population3 = creerPopulation(100);
		int [][] prairie3 = prairieLucioles(5,50,population3);
		affichePrairie(prairie3,population3);
		*/
		
		/*
		double[][] population4 = creerPopulation(100);
		int [][] prairie4 = prairieLucioles(5,50,population4);
		simulationPrairie(prairie4,population4,20);
		int [][] prairie5 = prairieLucioles(3,100,population4);
		simulationPrairie(prairie5,population4,10);
		*/
		
		/*
		double[][] population5 = creerPopulation(500);
		int [][] prairie6 = prairieLucioles(200,200,population5);
		simulationPrairieGIF(prairie6,population5,30);
		*/
	}

}