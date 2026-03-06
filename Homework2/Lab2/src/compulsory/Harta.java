package compulsory;

/**
 * Reprezinta o instanta a problemei, definind o harta (graf) formata din locatii si drumuri.
 * Clasa gestioneaza adaugarea elementelor, validarea hartii si cautarea de trasee.
 */
public class Harta {
    private Location loc[];
    private Road roads[];
    private int capacityL, cntL;
    private int capacityR, cntR;

    /**
     * Construieste o noua instanta a hartii cu capacitatile specificate.
     *
     * @param capL Numarul maxim de locatii care pot fi adaugate.
     * @param capR Numarul maxim de drumuri care pot fi adaugate.
     */
    public Harta(int capL, int capR) {
        capacityL = capL;
        capacityR = capR;
        loc = new Location[capacityL];
        roads = new Road[capacityR];
        cntL = 0;
        cntR = 0;
    }

    /**
     * Metoda ajutatoare pentru a gasi indexul unei locatii in array-ul hartii.
     *
     * @param locatie Locatia cautata.
     * @return Indexul locatiei daca exista, sau -1 in caz contrar.
     */
    private int getIndex(Location locatie)
    {
        for(int i = 0; i < cntL; i++)
            if(loc[i].equals(locatie))
                return i;

        return -1; //nu exista
    }

    /**
     * Obtine array-ul de locatii de pe harta.
     *
     * @return Array-ul de obiecte {@link Location}.
     */
    public Location[] getLoc() {
        return loc;
    }

    /**
     * Obtine array-ul de drumuri de pe harta.
     *
     * @return Array-ul de obiecte {@link Road}.
     */
    public Road[] getRoads() {
        return roads;
    }

    /**
     * Obtine capacitatea maxima de locatii.
     *
     * @return Capacitatea maxima pentru locatii.
     */
    public int getCapacityL() {
        return capacityL;
    }
    /**
     * Obtine numarul curent de locatii adaugate pe harta.
     *
     * @return Numarul de locatii existente.
     */
    public int getCntL() {
        return cntL;
    }

    /**
     * Obtine capacitatea maxima de drumuri.
     *
     * @return Capacitatea maxima pentru drumuri.
     */
    public int getCapacityR() {
        return capacityR;
    }

    /**
     * Obtine numarul curent de drumuri adaugate pe harta.
     *
     * @return Numarul de drumuri existente.
     */
    public int getCntR() {
        return cntR;
    }

    /**
     * Adauga o locatie noua pe harta, verificand capacitatea si unicitatea.
     *
     * @param newLoc Locatia de adaugat.
     */
    public void addLocation(Location newLoc)
    {
        if(cntL > capacityL)
        {
            System.out.println("Eroare! Spatiul disponibil a fost depasit.");
            return;
        }

        for (int i = 0; i < cntL; i++)
            if (loc[i].equals(newLoc)) //folosesc metoda equals redefinita
            {
                System.out.println("Eroare! Locatia " + newLoc.getName() + " exista deja pe harta.");
                return;
            }
        loc[cntL++] = newLoc; // Adaugam locatia pe prima pozitie libera si crestem contorul
        System.out.println("Locatia " + newLoc.getName() + " a fost adaugata cu succes.");
    }

    /**
     * Adauga un drum nou pe harta, verificand capacitatea si unicitatea.
     *
     * @param newRoad Drumul de adaugat.
     */
    public void addRoad(Road newRoad)
    {
        if(cntR > capacityR)
        {
            System.out.println("Eroare! Spatiul disponibil a fost depasit.");
            return;
        }

        for (int i = 0; i < cntR; i++)
            if (roads[i].equals(newRoad)) //folosesc metoda equals redefinita
            {
                System.out.println("Eroare! Drumul " + newRoad.getLoc1().getName() + " - " + newRoad.getLoc2().getName() + " exista deja pe harta.");
                return;
            }
        roads[cntR++] = newRoad; // Adaugam locatia pe prima pozitie libera si crestem contorul
        System.out.println("Drumul " + newRoad.getLoc1().getName() + " - " + newRoad.getLoc2().getName() + " a fost adaugat cu succes.");
    }

    /**
     * Verifica daca instanta problemei este valida.
     * O harta valida are minim 2 locatii, minim 1 drum, toate drumurile conecteaza
     * locatii existente, iar lungimea fiecarui drum este mai mare sau egala cu distanta euclidiana.
     *
     * @return {@code true} daca harta este valida, {@code false} in caz contrar.
     */
    public boolean isValid()
    {
        //verific cazul de baza
        if(cntL < 2 || cntR == 0)
        {
            System.out.println("Eroare! Nu exista suficiente locatii sau drumuri.");
            return false;
        }

        // Verific fiecare drum din array
        for (int i = 0; i < cntR; i++) {
            Road currRoad = roads[i];
            Location loc1 = currRoad.getLoc1();
            Location loc2 = currRoad.getLoc2();

            //Verific daca ambele locatii exista in array
            boolean foundLoc1 = false;
            boolean foundLoc2 = false;

            for (int j = 0; j < cntL; j++) {
                if (loc[j].equals(loc1)) {
                    foundLoc1 = true;
                }
                if (loc[j].equals(loc2)) {
                    foundLoc2 = true;
                }
            }

            //Daca lipseste macar o locatie harta e invalida
            if (!foundLoc1 || !foundLoc2) {
                System.out.println("Eroare! Drumul conecteaza locatii inexistente pe harta.");
                return false;
            }

            int distX = loc1.getX() - loc2.getX();
            int distY = loc1.getY() - loc2.getY();

            //calculez radical din distX * distX + distY * distY
            double euclidDist = Math.hypot(distX, distY);

            if (currRoad.getLength() < euclidDist) {
                System.out.println("Eroare! Lungimea drumului este gresita.");
                return false;
            }
        }

        return true;
    }

    /**
     * Verifica daca exista un traseu posibil intre doua locatii date folosind o parcurgere DFS.
     *
     * @param start Locatia de plecare.
     * @param dest  Locatia de destinatie.
     * @return {@code true} daca se poate ajunge de la start la destinatie, {@code false} in caz contrar.
     */
    public boolean isPath(Location start, Location dest)
    {
        boolean viz[] = new boolean [cntL];
        return dfs(start, dest, viz);
    }

    /**
     * Metoda recursiva de parcurgere in adancime (Depth-First Search) pentru a cauta un traseu.
     *
     * @param curr Locatia curenta in timpul parcurgerii.
     * @param dest Locatia destinatie cautata.
     * @param viz  Array-ul boolean care memoreaza nodurile vizitate pentru a evita ciclurile.
     * @return {@code true} daca traseul a fost gasit in subgraf, {@code false} in caz contrar.
     */
    private boolean dfs(Location curr, Location dest, boolean viz[])
    {
        if(curr.equals(dest)) //verific daca am gasit
            return true;

        int currIndex = getIndex(curr); //indexul locatiei curente

        if(currIndex == -1 || viz[currIndex])
            return false;

        //marchez nodul curent ca vizitat
        viz[currIndex] = true;

        for(int i = 0; i < cntR; i++) //verific toate drumurile care sunt conectate de locatia curenta
        {
            Road drum = roads[i];
            Location nextLoc = null;

            //pt a gasi urmatoarea locatie verific ambele capetel deoarece e bidirectional
            if(drum.getLoc1().equals(curr))
            {
                nextLoc = drum.getLoc2();
            }
            else if(drum.getLoc2().equals(curr))
            {
                nextLoc = drum.getLoc1();
            }

            //daca am gasit o locatie urmatoare continui
            if(nextLoc != null)
            {
                if(dfs(nextLoc, dest, viz))
                    return true;
            }
        }
        //s au verificat toate drumurile si nu am gasit nimic
        return false;
    }
}
