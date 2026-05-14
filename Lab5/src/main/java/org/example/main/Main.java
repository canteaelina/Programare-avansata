package org.example.main;
/*
import org.example.exceptions.CommandException;
import org.example.model.Article;
import org.example.model.Book;
import org.example.model.Item;
import org.example.repository.Catalog;

public class Main {
    static void main() {
        Catalog catalog = new Catalog();

        //cream resursele
        Item book = new Book("knuth67", "The Art of Computer Programming",
                "d:/books/programming/tacp.ps", "1967", "Donald E. Knuth", "desc1");

        Item jvmSpec = new Article("jvm25", "The Java Virtual Machine Specification",
                "https://docs.oracle.com/javase/specs/jvms/se25/html/index.html", "2025", "Tim Lindholm & others", "desc2");

        Item article = new Article("java25", "The Java Language Specification",
                "https://docs.oracle.com/javase/specs/jls/se25/jls25.pdf", "2025", "James Gosling & others", "desc3");

        //le adaugam in catalog
        catalog.add(book);
        catalog.add(jvmSpec);
        catalog.add(article);

        // testez al treilea link
        try {
            Item foundArticle = catalog.findById("java25");
            if (foundArticle != null) {
                System.out.println("Incercam sa deschidem: " + foundArticle.getTitle());
                catalog.openResource(foundArticle);
            }
        } catch (CommandException e) {
            System.err.println("Eroare la deschiderea articolului: " + e.getMessage());
        }

        // eroare intentionata
        try {
            Item foundBook = catalog.findById("knuth67");
            if (foundBook != null) {
                System.out.println("Incercam sa deschidem: " + foundBook.getTitle());
                catalog.openResource(foundBook);
            }
        } catch (CommandException e) {
            System.err.println("Eroare prinsă cu succes: " + e.getMessage());
        }

        //al doilea link
        try {
            Item foundItem = catalog.findById("jvm25");
            if (foundItem != null) {
                System.out.println("Incercam sa deschidem: " + foundItem.getTitle());
                // Ar trebui să deschidă link-ul Oracle în browser-ul tău implicit
                catalog.openResource(foundItem);
            } else {
                System.out.println("Resursa nu a fost gasita.");
            }
        } catch (CommandException e) {
            System.err.println("Eroare la deschiderea resursei: " + e.getMessage());
        }
    }
}
*/


import org.example.commands.*;
import org.example.exceptions.CommandException;
import org.example.model.Article;
import org.example.model.Book;
import org.example.model.Item;
import org.example.repository.Catalog;

public class Main {
    public static void main(String[] args) {
        Catalog catalog = new Catalog();

        Item book = new Book("knuth67", "The Art of Computer Programming",
                "d:/books/programming/tacp.ps", "1967", "Donald E. Knuth", "desc1");
        Item jvmSpec = new Article("jvm25", "The Java Virtual Machine Specification",
                "https://docs.oracle.com/javase/specs/jvms/se25/html/index.html", "2025", "Tim Lindholm & others", "desc2");

        catalog.add(book);
        catalog.add(jvmSpec);

        try {
            //Testam ListCommand
            Command listCmd = new ListCommand(catalog);
            listCmd.execute();

            //Testam SaveCommand
            Command saveCmd = new SaveCommand(catalog, "catalog.ser");
            saveCmd.execute();

            //Testam LoadCommand
            LoadCommand loadCmd = new LoadCommand("catalog.ser");
            loadCmd.execute();
            Catalog loadedCatalog = loadCmd.getCatalog(); // Poti lucra mai departe cu acest catalog

            //Testam ReportCommand (HTML)
            Command reportCmd = new ReportCommand(loadedCatalog);
            reportCmd.execute();

            //Testam ViewCommand
            Item itemToView = loadedCatalog.findById("jvm25");
            if (itemToView != null) {
                System.out.println("Deschidem resursa: " + itemToView.getTitle());
                Command viewCmd = new ViewCommand(itemToView);
                viewCmd.execute();
            }

        } catch (CommandException e) {
            System.err.println("A aparut o eroare la executia comenzii: " + e.getMessage());
            e.printStackTrace();
        }
    }
}