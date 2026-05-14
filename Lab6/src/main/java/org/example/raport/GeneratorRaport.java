package org.example.raport;

import org.example.database.Database;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GeneratorRaport {
    public static void generateHTML(String filePath) {
        String template = readTemplate("template.html");
        StringBuilder rows = new StringBuilder();

        try (Connection con = Database.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM movie_report_view")) {

            while (rs.next()) {
                rows.append("<tr>")
                        .append("<td>").append(rs.getString("title")).append("</td>")
                        .append("<td>").append(rs.getDate("release_date")).append("</td>")
                        .append("<td>").append(rs.getInt("duration")).append("</td>")
                        .append("<td>").append(rs.getDouble("score")).append("</td>")
                        .append("<td>").append(rs.getString("genre_name")).append("</td>")
                        .append("</tr>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String finalHtml = template.replace("{{rows}}", rows.toString());

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(finalHtml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readTemplate(String fileName) {
        try (InputStream is = GeneratorRaport.class
                .getClassLoader()
                .getResourceAsStream(fileName)) {

            if (is == null) {
                throw new RuntimeException("Template-ul nu a fost gasit!");
            }

            return new String(is.readAllBytes());

        } catch (IOException e) {
            throw new RuntimeException("Eroare la citirea template-ului", e);
        }
    }
}
