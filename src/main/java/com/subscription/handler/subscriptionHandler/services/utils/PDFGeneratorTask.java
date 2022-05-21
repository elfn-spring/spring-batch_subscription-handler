package com.subscription.handler.subscriptionHandler.services.utils;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.subscription.handler.subscriptionHandler.dtos.SubscriptionDTO;
import com.subscription.handler.subscriptionHandler.dtos.mappers.SubscriptionMapper;
import com.subscription.handler.subscriptionHandler.entities.Client;
import com.subscription.handler.subscriptionHandler.entities.Subscription;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @PROJECT subscriptionHandler
 * @Author Elimane on 17/05/2022
 */
public class PDFGeneratorTask extends AbstractTask {

  @Autowired
   private SubscriptionMapper subscriptionMapper;
  public static String path = "";

  @Override
  public void send() {

  }

  @Override
  public void generatePDF(Subscription subscription) throws IOException {

    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");


    //Attach document to the response

    Document document = new Document(PageSize.A4);
    path = System.getProperty("user.dir")+"/pdf/SubscriptionRenewal"+(subscription.getName())+".pdf";

    PdfWriter.getInstance(document, new FileOutputStream(path));

    //Open the document
    document.open();
    Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
    fontTitle.setSize(18);


    //Add a paragraph
    Paragraph paragraph = new Paragraph("About your "+subscription.getName()+" subscription", fontTitle);
    paragraph.setAlignment(Paragraph.ALIGN_CENTER);

    //Add another paragraph
    Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
    fontParagraph.setSize(12);

    Paragraph paragraph2 = new Paragraph("Your subscription has been renewed", fontParagraph);
    Paragraph paragraph3 = new Paragraph("Your subscription: ", fontParagraph);
    Paragraph paragraph4 = new Paragraph("Name: "+subscription.getName(), fontParagraph);
    Paragraph paragraph5 = new Paragraph("Type: "+subscription.getSubscriptionType(), fontParagraph);
    Paragraph paragraph6 = new Paragraph("New expiration date: "+format.format(subscription.getExpirationDate()), fontParagraph);
    paragraph2.setAlignment(Paragraph.ALIGN_LEFT);

    document.add(paragraph);
    document.add(paragraph2);
    document.add(paragraph3);
    document.add(paragraph4);
    document.add(paragraph5);
    document.add(paragraph6);
    document.close();


  }


}
