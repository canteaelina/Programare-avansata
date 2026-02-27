package compulsory;

class Main {
    public static void main(String args[])
    {
        Location bucuresti = new Location("Bucuresti", "city", 255, 300);
        Location iasi = new Location("Iasi", "city", 100, 500);
        Location mol = new Location("Mol", "gas station", 115, 505);
        Road dn24 = new Road("Country", 20, 50, iasi, mol);
        Road a2 = new Road("Highway", 600, 120, iasi, bucuresti);

        System.out.println(a2.toString());
        System.out.println(iasi.toString());
        System.out.println(mol.toString());
        System.out.println(dn24.toString());
        System.out.println(bucuresti.toString());

        a2.setLength(1000);
        System.out.println("New length: " + a2.getLength());

        System.out.println(mol.getName());
        System.out.println(mol.getType());

    }

}
