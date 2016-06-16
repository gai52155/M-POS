package com.mpos.controller;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Katawut
 */
public class CreatePDF {

    public class FooterTable extends PdfPageEventHelper {

        protected PdfPTable footer;

        public FooterTable(PdfPTable footer) {
            this.footer = footer;
        }

        public void onEndPage(PdfWriter writer, Document document) {
            footer.writeSelectedRows(0, -1, 36, 64, writer.getDirectContent());
        }
    }

    public static Font headerfont = new Font(Font.FontFamily.TIMES_ROMAN, 27);

    public static void mainPDF(String country, ArrayList<String> list) throws IOException, DocumentException {
        String DEST = "D:/M-POS/M-POS/web/PDF/" + country + ".pdf"; // SAVE TO ?
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        ArrayList<String> data = list;
        new CreatePDF().createPdf(DEST, country, list);
    }

    public void createPdf(String dest, String country, ArrayList<String> data) throws IOException, DocumentException {

        Document document = new Document(PageSize.A4, 0f, 0f, 0f, 0f);

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));

        document.open();
        Paragraph header = new Paragraph("LOCATION REPORT" + "\n" + "Country : " + country, headerfont); // SET HEADER
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);

        if (data.size() == 1) {
            Paragraph error = new Paragraph("Database doesn't having " + country + " place", headerfont);
            error.setAlignment(Element.ALIGN_CENTER);
            document.add(error);
        } else {
            Paragraph n = new Paragraph("\n");
            document.add(n);

            PdfPTable table = new PdfPTable(3);

            PdfPCell cell0 = new PdfPCell(new Paragraph("Name"));
            PdfPCell cell1 = new PdfPCell(new Paragraph("Lat"));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Lng"));

            cell0.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell0.setBackgroundColor(BaseColor.GREEN);

            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setBackgroundColor(BaseColor.GREEN);

            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setBackgroundColor(BaseColor.GREEN);

            table.addCell(cell0);
            table.addCell(cell1);
            table.addCell(cell2);
            for (int i = 0; i < data.size(); i++) {
                PdfPCell temp = new PdfPCell(new Paragraph(data.get(i)));
                temp.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(temp);
            }

            document.add(table);
            PdfPTable tablee = new PdfPTable(1);
            tablee.setTotalWidth(523);
            int page = document.getPageNumber() + 1;
            PdfPCell cell = new PdfPCell(new Phrase("PAGE : " + page + "-"));
            cell.setBorderColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablee.addCell(cell);
            CreatePDF.FooterTable event = new CreatePDF.FooterTable(tablee);
            writer.setPageEvent(event);
        }
        document.close();
    }
}
