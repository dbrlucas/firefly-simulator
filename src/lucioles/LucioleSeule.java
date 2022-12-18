package lucioles;

// Étape 1 : Simulation d'une seule luciole

public class LucioleSeule {

	// Seuil au delà duquel une luciole émet un flash.
	public static final double SEUIL = 100.0;
	
	public static char symboliseLuciole(double niveauEnergie) {
		if(niveauEnergie > SEUIL) {
			return '*';
		}
		return '.';
	}
	
	public static void afficheLuciole(double niveauEnergie,boolean verbeux) {
		System.out.print(symboliseLuciole(niveauEnergie));
		if(verbeux) {
			System.out.print(" "+niveauEnergie);
		}
		System.out.println();
	}
	
	public static double incrementeLuciole(double niveauEnergie,double deltaEnergie) {
		if(niveauEnergie > SEUIL) {
			niveauEnergie = 0.0;
			return niveauEnergie;
		}
		niveauEnergie += deltaEnergie;
		return niveauEnergie;
	}
	
	public static void simuleLucioleNbPas(double lucioleEnergie,double lucioleDeltaEnergie,int nbPas) {
		for(int i = 0;i<nbPas;i++) {
			lucioleEnergie = incrementeLuciole(lucioleEnergie,lucioleDeltaEnergie);
			afficheLuciole(lucioleEnergie,true);
		}
	}
	
	public static void simuleLucioleNbFlashs(double lucioleEnergie,double lucioleDeltaEnergie) {
		int nbFlashs = 0;
		while(nbFlashs < 3) {
			lucioleEnergie = incrementeLuciole(lucioleEnergie,lucioleDeltaEnergie);
			afficheLuciole(lucioleEnergie,true);
			if(lucioleEnergie > SEUIL) {
				nbFlashs++;
			}
		}
	}
	
	// Tests
	public static void main(String[] args) {
		//double lucioleEnergie = RandomGen.rGen.nextInt(100);
		
		/*
		afficheLuciole(lucioleEnergie,true);
		lucioleEnergie += 30.0;
		afficheLuciole(lucioleEnergie,true);
		lucioleEnergie += 70.0;
		afficheLuciole(lucioleEnergie,true);
		lucioleEnergie -= 90.0;
		afficheLuciole(lucioleEnergie,true);
		*/
		
		//double lucioleDeltaEnergie = RandomGen.rGen.nextDouble();
		
		/*
		for(int i = 0;i<10;i++) {
			lucioleEnergie = incrementeLuciole(lucioleEnergie,lucioleDeltaEnergie);
			afficheLuciole(lucioleEnergie,true);
		}
		*/
		
		//simuleLucioleNbPas(lucioleEnergie,lucioleDeltaEnergie,100);

		//simuleLucioleNbFlashs(lucioleEnergie,lucioleDeltaEnergie);
	}

}
