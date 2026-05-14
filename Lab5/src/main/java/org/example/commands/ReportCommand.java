package org.example.commands;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.example.exceptions.CommandException;
import org.example.repository.Catalog;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class ReportCommand implements Command{
    private Catalog catalog;

    public ReportCommand(Catalog catalog) {
        this.catalog = catalog;
    }

    @Override
    public void execute() throws CommandException {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
            //cautam template ul
            cfg.setClassForTemplateLoading(this.getClass(), "/templates");
            cfg.setDefaultEncoding("UTF-8");

            Template template = cfg.getTemplate("report.ftl");

            Map<String, Object> templateData = new HashMap<>();
            templateData.put("items", catalog.getItems());

            File htmlFile = new File("catalog_report.html");
            Writer out = new FileWriter(htmlFile);
            template.process(templateData, out);
            out.flush();
            out.close();

            System.out.println("Raport HTML creat");
            Desktop.getDesktop().browse(htmlFile.toURI());

        } catch (Exception e) {
            throw new CommandException("Eroare la generarea raportului HTML", e);
        }
    }
}
