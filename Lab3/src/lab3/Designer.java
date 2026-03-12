package lab3;

import java.time.LocalDate;
import java.util.HashMap;

public class Designer extends Person {
    private String prefDesignTool; //Design tool ul preferat
    private String style;

    public Designer(String name, LocalDate birth, String nationality, String prefDesignTool, String style)
    {
        super(name, birth, nationality);
        this.prefDesignTool = prefDesignTool;
        this.style = style;
    }

    public String getPrefDesignTool() {
        return prefDesignTool;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setPrefDesignTool(String prefDesignTool) {
        this.prefDesignTool = prefDesignTool;
    }

    @Override
    public String toString() {
        return this.getID() + " - " + this.getName() +
                "\nSpecialization: Designer\nYears of experience: " +
                this.yrsOfExp + "\nFavorite programming language: " + this.favLang;
    }
}
