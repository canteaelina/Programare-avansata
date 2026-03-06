package compulsory;
/**
 * Reprezinta un drum intre doua locatii de pe harta.
 * Clasa contine detalii despre tipul drumului, lungime, limita de viteza si capetele acestuia.
 */
public class Road {

    private RoadTypes type;
    private int length;
    private int speed;
    private Location loc1, loc2;

    /**
     * Construieste o noua instanta a clasei Road.
     *
     * @param type   Tipul drumului (din enum-ul {@link RoadTypes}).
     * @param length Lungimea drumului.
     * @param speed  Limita de viteza maxima admisa pe acest drum.
     * @param L1     Prima locatie conectata de drum.
     * @param L2     A doua locatie conectata de drum.
     */
    public Road(RoadTypes type, int length, int speed, Location L1, Location L2) {
        this.type = type;
        this.length = length;
        this.speed = speed;
        this.loc1 = L1;
        this.loc2 = L2;
    }

     /**
     * Obtine tipul drumului.
     *
     * @return Tipul drumului.
     */
    public RoadTypes getType() {
        return type;
    }

    /**
     * Obtine lungimea drumului.
     *
     * @return Lungimea drumului.
     */
    public int getLength() {
        return length;
    }

    /**
     * Obtine limita de viteza a drumului.
     *
     * @return Limita de viteza.
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Obtine prima locatie (capatul 1) a drumului.
     *
     * @return Obiectul {@link Location} reprezentand primul capat.
     */
    public Location getLoc1() {
        return loc1;
    }

    /**
     * Obtine a doua locatie (capatul 2) a drumului.
     *
     * @return Obiectul {@link Location} reprezentand al doilea capat.
     */
    public Location getLoc2() {
        return loc2;
    }

    /**
     * Seteaza tipul drumului.
     *
     * @param type Noul tip al drumului.
     */
    public void setType(RoadTypes type) {
        this.type = type;
    }

    /**
     * Seteaza lungimea drumului.
     *
     * @param length Noua lungime.
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Seteaza limita de viteza a drumului.
     *
     * @param speed Noua limita de viteza.
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }


    /**
     * Returneaza o reprezentare textuala a drumului, incluzand toate proprietatile sale.
     *
     * @return Un string care contine detaliile drumului.
     */
    @Override
    public String toString()
    {
        //Afisarea datelor
        StringBuilder data = new StringBuilder("");
        data.append("Type " + this.type + '\n');
        data.append("Length: " + this.length + '\n');
        data.append("Speed limit: " + this.speed + '\n');
        data.append("Location 1: " + this.loc1.getName() + '\n');
        data.append("Location 2: " + this.loc2.getName() + '\n');

        return data.toString();
    }

    /**
     * Verifica daca acest drum este egal cu un alt obiect.
     * Doua drumuri sunt considerate egale daca au acelasi tip, aceeasi lungime,
     * aceeasi viteza si conecteaza aceleasi locatii (in aceeasi ordine).
     *
     * @param obj Obiectul cu care se face comparatia.
     * @return {@code true} daca obiectele sunt identice, {@code false} in caz contrar.
     */
    @Override
    public boolean equals(Object obj)
    {
        //verific daca parametrul obj e valid (ambele obiecte apartin aceeasi clase)
        if(this == obj) return true;

        //verific daca parametrul obj e valid (ambele obiecte trebuie sa apartina aceleiasi clase si obj sa nu fie null)
        if(this.getClass() != obj.getClass() || obj == null)
            return false;

        Road other = (Road) obj;

        if(this.type == other.type && this.length == other.length && this.speed == other.speed && this.loc1.equals(this.loc1) && this.loc2.equals(this.loc2))
            return true;

        return false;
    }
}


