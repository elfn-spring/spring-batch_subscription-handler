package com.subscription.handler.subscriptionHandler.services.utils;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.subscription.handler.subscriptionHandler.dtos.SubscriptionDTO;
import com.subscription.handler.subscriptionHandler.dtos.mappers.SubscriptionMapper;
import com.subscription.handler.subscriptionHandler.entities.Subscription;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @PROJECT subscriptionHandler
 * @Author Elimane on 17/05/2022
 */
public class PDFGeneratorTask extends AbstractTask {

  @Autowired
   private SubscriptionMapper subscriptionMapper;

  public static String path = System.getProperty("user.dir")+"/pdf/SubscriptionRenewal.pdf";

  @Override
  public void send() {

  }

  @Override
  public Document generatePDF(Subscription subscription) throws IOException {

    SubscriptionDTO subscriptionDTO = subscriptionMapper.toSubscriptionDTO(subscription);

    //Attach document to the response

    Document document = new Document(PageSize.A4);
    PdfWriter.getInstance(document, new FileOutputStream(path));

    //Open the document
    document.open();
    Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
    fontTitle.setSize(18);


    //Add a paragraph
    Paragraph paragraph = new Paragraph("About your "+subscriptionDTO.getName()+" subscription", fontTitle);
    paragraph.setAlignment(Paragraph.ALIGN_CENTER);

    //Add another paragraph
    Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
    fontParagraph.setSize(12);

    Paragraph paragraph2 = new Paragraph("Your subscription has been renewed", fontParagraph);
    Paragraph paragraph3 = new Paragraph("Your subscription: ", fontParagraph);
    Paragraph paragraph4 = new Paragraph("Name: "+subscriptionDTO.getName(), fontParagraph);
    Paragraph paragraph5 = new Paragraph("Type: "+subscriptionDTO.getSubscriptionType(), fontParagraph);
    Paragraph paragraph6 = new Paragraph("New expiration date: "+subscriptionDTO.getExpirationDate(), fontParagraph);
    paragraph2.setAlignment(Paragraph.ALIGN_LEFT);

    document.add(paragraph);
    document.add(paragraph2);
    document.add(paragraph3);
    document.add(paragraph4);
    document.add(paragraph5);
    document.add(paragraph6);
    document.close();


    return document;

  }


  //For the processor
//  private String getSubscriptionStatus(SubscriptionDTO subscriptionDTO) {
//    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//
//    String date1 = format.format(subscriptionDTO.getExpirationDate());
//    String date2 = format.format(new Date());
//
//    java.sql.Date sqlDate1 = java.sql.Date.valueOf(date1);
//    java.sql.Date sqlDate2 = java.sql.Date.valueOf(date2);
//    LocalDate ld1 = sqlDate1.toLocalDate();
//    LocalDate ld2 = sqlDate2.toLocalDate();
//
//    long daysBetween = ChronoUnit.DAYS.between(ld1, ld2);
//
//
//    boolean isExpired = ((date1.compareTo(date2) == 0)) ? true : false;
//    boolean isAlmostExpired = ((daysBetween == 5)) ? true : false;
//    boolean isvalid = ((daysBetween > 5)) ? true : false;
//
//    return (isAlmostExpired) ? "ALMOST" : (isExpired) ? "EXPIRED" : (isvalid) ? "VALID" : "";
//
//  }
}
