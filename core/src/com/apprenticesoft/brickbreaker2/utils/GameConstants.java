package com.apprenticesoft.brickbreaker2.utils;

public class GameConstants {

    //Constantes du World
    public static float WORLD_TO_BOX = 0.02f;
    public static float BOX_TO_WORLD = 1/WORLD_TO_BOX;
    public static float BOX_STEP = 1/60f;
    public static int BOX_VELOCITY_ITERATIONS = 6;
    public static int BOX_POSITION_ITERATIONS = 2;
    //Gestion des niveaux
    public static int niveauxParGroupe = 25;
    public static int NombreDeGroupes = 7;
    public static int niveauSelectione = 1;
    public static int groupeSelectione = 1;
    public static boolean choixNiveau = false;
    public static boolean niveauFini = true;
    public static boolean pause = false;
    public static int INTERSTITIAL_TRIGGER = 2;
    //Propriétés spéciales
    public static boolean microgravite = false;
    public static boolean epileptique = false;
    //Option vitesse jeu
    public static float vitesseJeuMin = 0.6f;
    public static float vitesseJeuNormale = 0.8f;
    public static float vitesseJeuMax = 1.0f;
    //Gestion des objets
    public static int objet = 9;
    public static int bricksInitiales = 0;
    public static int bricksDetruites = 0;
    public static int bricksDetruitesAuLaser = 0;
    public static int dernierObjet = 0;
    public static int avantDernierObjet = 0;
    //Objet modification barre
    public static float barreMin = 0f;
    public static float barreNormale = 0.4f;
    public static float barreMax = 0.8f;
    public static float ecart = barreNormale;
    //Objet modification vitesse balle
    public static float vitesseBalleMin = 0.5f;
    public static float vitesseBalleNormale = 1.0f;
    public static float vitesseBalleMax = 1.5f;
    public static float vitesseBalle = vitesseBalleNormale;
    public static long vitesseBalleTime;
    //Gestion du bouclier
    public static boolean bouclierActif = false;
    //Gestion du laser
    public static boolean laserActif = false;
    public static long laserTime;
    public static int tirs = 0;
    //Nombre de vies usées
    public static int viesPerdues;

    //Liens vers les App Store
    public static final String GOOGLE_PLAY_GAME_URL = "https://play.google.com/store/apps/details?id=com.minimal.brick.breaker.free.android";
    public static final String GOOGLE_PLAY_STORE_URL = "https://play.google.com/store/apps/developer?id=Apprentice+Soft";
    public static final String AMAZON_GAME_URL = "http://www.amazon.com/gp/mas/dl/android?p=com.minimal.brick.breaker.free.android";
    public static final String AMAZON_STORE_URL = "http://www.amazon.com/gp/mas/dl/android?p=com.premier.jeu.android&showAll=1";

}
