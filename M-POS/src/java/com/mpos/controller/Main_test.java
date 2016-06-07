package com.mpos.controller;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Main_test {

    public static final String DEST = "D:/Intern/Report.pdf"; // SAVE TO ?
    public static Font headerfont = new Font(Font.FontFamily.TIMES_ROMAN, 27);

    public static void main(String[] args) throws IOException,
            DocumentException {
        String country = "Thailand";

        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new Main_test().createPdf(DEST, country);
    }

    public void createPdf(String dest, String country) throws IOException, DocumentException {

        ArrayList<String> data = ControllerSelect.selector(country);

        Document document = new Document(PageSize.A4, 0f, 0f, 0f, 0f);
        PdfWriter.getInstance(document, new FileOutputStream(dest));

        document.open();

        Paragraph header = new Paragraph(country, headerfont); // SET HEADER
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);
        document.add(Chunk.NEWLINE);

        if (data.size() == 1) {
            Paragraph error = new Paragraph("Database not having data", headerfont);
        } else {

            PdfPTable table = new PdfPTable(3);
            PdfPCell cell0 = new PdfPCell(new Paragraph("Name"));
            PdfPCell cell1 = new PdfPCell(new Paragraph("Lat"));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Lng"));

            cell0.setHorizontalAlignment(Element.ALIGN_CENTER); cell0.setBackgroundColor(BaseColor.GREEN);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER); cell1.setBackgroundColor(BaseColor.GREEN);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER); cell2.setBackgroundColor(BaseColor.GREEN);

            table.addCell(cell0);
            table.addCell(cell1);
            table.addCell(cell2);
            for (int i = 0; i < data.size(); i++) {
                PdfPCell temp = new PdfPCell(new Paragraph(data.get(i)));
                System.out.println(i + " " + data.get(i));
                temp.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(temp);
            }
            
            document.add(table);
        }
        document.close();
    }

}
