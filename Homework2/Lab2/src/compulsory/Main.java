package compulsory;

class Main {
    public static void main(String args[])
    {
       
        Harta harta = new Harta(10, 10);

        City bucuresti = new City("Bucuresti", 255, 300, 2000000);
        City iasi = new City("Iasi", 100, 500, 300000);
        GasStation mol = new GasStation("Mol", 115, 505, 9);

        Road dn24 = new Road(RoadTypes.COUNTRY, 20, 50, iasi, mol);
        Road a2 = new Road(RoadTypes.HIGHWAY, 600, 120, iasi, bucuresti);

        System.out.println(a2.toString());
        System.out.println(iasi.toString());
        System.out.println(mol.toString());
        System.out.println(dn24.toString());
        System.out.println(bucuresti.toString());

        a2.setLength(1000);
        System.out.println("New length a2: " + a2.getLength());
        System.out.println(mol.getName() + " gas price: " + mol.gasPrice());
        System.out.println();

        harta.addLocation(bucuresti);
        harta.addLocation(iasi);
        harta.addLocation(mol);

        harta.addRoad(dn24);
        harta.addRoad(a2);
        System.out.println();

        System.out.println("Este harta valida? " + harta.isValid());

        boolean existaDrum = harta.isPath(iasi, bucuresti);
        System.out.println("Exista drum de la Iasi la Bucuresti? " + existaDrum);
        existaDrum = harta.isPath(bucuresti, mol);
        System.out.println("Exista drum de la Bucuresti la Mol? " + existaDrum);

    }

}
