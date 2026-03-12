package lab3;

import java.time.LocalDate;
import java.util.HashMap;

public class Designer extends Person {
    private String favDesignTool; //Design tool ul preferat
    private String style;

    public Designer(String name, LocalDate birth, String nationality, String prefDesignTool, String style)
    {
        super(name, birth, nationality);
        this.favDesignTool = prefDesignTool;
        this.style = style;
    }

    public String getFavDesignTool() {
        return favDesignTool;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setFavDesignTool(String favDesignTool) {
        this.favDesignTool = favDesignTool;
    }

    @Override
    public String toString() {
        return this.getID() + " - " + this.getName() +
                "\nSpecialization: Designer\nFavorite Design tool: " +
                this.favDesignTool + "\nFavorite programming language: " + this.style + "\nImportance: " + this.getImportance() + "\n";
    }
}
