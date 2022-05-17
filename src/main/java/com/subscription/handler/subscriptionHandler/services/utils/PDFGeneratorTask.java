package com.subscription.handler.subscriptionHandler.services.utils;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.subscription.handler.subscriptionHandler.dtos.SubscriptionDTO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @PROJECT subscriptionHandler
 * @Author Elimane on 17/05/2022
 */
public class PDFGeneratorTask extends AbstractTask {



  @Override
  public void send() {

  }

  @Override
  public Document generatePDF(HttpServletResponse response, SubscriptionDTO subscriptionDTO) throws IOException {
    //Attach document to the response
    Document document = new Document(PageSize.A4);
    PdfWriter.getInstance(document, response.getOutputStream());

    //Open the document
    document.open();
    Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
    fontTitle.setSize(18);

    //Add a paragraph
    Paragraph paragraph = new Paragraph("This is a title", fontTitle);
    paragraph.setAlignment(Paragraph.ALIGN_CENTER);

    //Add another paragraph
    Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
    fontParagraph.setSize(12);

    Paragraph paragraph2 = new Paragraph("This is a paragraph", fontParagraph);
    paragraph2.setAlignment(Paragraph.ALIGN_LEFT);

    document.add(paragraph);
    document.add(paragraph2);
    document.close();

    return document;
  }
}
