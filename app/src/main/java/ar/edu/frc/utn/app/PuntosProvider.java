package ar.edu.frc.utn.app;

/**
 * Created by Usuario- on 03/11/2016.
 */

public class PuntosProvider {
    public static String[][] GetPositions(){
        String[][] dic = new String[][] {
                {"1", "Jacinto Perez"},
                {"2", "Alberto Robles"},
                {"3", "Mariana Cassini"},
                {"4", "Abel Canario"},
                {"5", "Maria de los Angeles Antonieta Medrano"}
        };
        return dic;
    }

    public static String[] GetPlanets(){
        String[] dic = new String[] {
                "Sol",
                "Mercurio",
                "Venus",
                "Tierra",
                "Marte",
                "Jupiter",
                "Saturno",
                "Urano",
                "Neptuno",
                "Pluton"
        };
        return dic;
    }
}
