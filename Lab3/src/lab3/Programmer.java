package lab3;

import java.time.LocalDate;
import java.util.HashMap;

public class Programmer extends Person {
    private int yrsOfExp;
    private String favLang; //limbajul preferat de programare

    public Programmer(String name, LocalDate birth, String nationality, int exp, String favLang)
    {
        super(name, birth, nationality);
        this.yrsOfExp = exp;
        this.favLang = favLang;
    }

    public int getYrsOfExp() {
        return yrsOfExp;
    }
    @Override
    public String toString() {
        return this.getID() + " - " + this.getName() +
                "\nSpecialization: Programmer\nYears of experience: " +
                this.yrsOfExp + "\nFavorite programming language: " + this.favLang + "\nImportance: " + this.getImportance() + "\n";
    }
}
