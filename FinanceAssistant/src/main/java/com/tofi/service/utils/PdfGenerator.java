package com.tofi.service.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

/**
 * Created by ulian_000 on 27.12.2016.
 */
public class PdfGenerator {

    public static Document generateCreditPdf() {
        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("HelloWorld.pdf"));

            document.open();
            BaseFont times =
                    BaseFont.createFont("resources/times.ttf", "cp1251", BaseFont.EMBEDDED);
            Paragraph par = new Paragraph(mapCreditDTOForReport(creditDTO), new Font(times, 14));
            document.add(par);

            document.close();
            writer.close();
        }
        catch(Exception ex) {

        }
    }

}
