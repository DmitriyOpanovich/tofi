package by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot;

import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.model.Credit;
import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.model.dto.CreditDTO;
import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.model.dto.EnumDTO;
import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.model.dto.PercentageTermDTO;
import by.bsuir.tofi.finance_assistant.bots.credit_assistant_bot.services.CreditAssistantService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 1 on 22.12.2016.
 */
public class Main {

    public static void main(String[] args) throws Exception{
        PercentageTermDTO percentageTermDTO = new PercentageTermDTO();
        percentageTermDTO.setMinAmmount(5000);
        percentageTermDTO.setMaxAmmount(10000);
        EnumDTO enumDTO = new EnumDTO();
        enumDTO.setName("BYN");
        percentageTermDTO.setCurrency(enumDTO);
        percentageTermDTO.setMinTermMonth(12);
        percentageTermDTO.setMaxTermMonth(24);
        percentageTermDTO.setPercentage(5.5);
        System.out.println(percentageTermDTO.describe());
        List<PercentageTermDTO> percentageTermDTOList = new ArrayList<PercentageTermDTO>();
        percentageTermDTOList.add(percentageTermDTO);

        CreditDTO creditDTO = new CreditDTO();
        creditDTO.setName("Имя");
        creditDTO.setNeedsGurantor(true);
        creditDTO.setBankName("БАНК");
        EnumDTO clientType = new EnumDTO();
        clientType.setName("Fiz");
        creditDTO.setClientType(clientType);
        EnumDTO goal = new EnumDTO();
        goal.setName("car");
        creditDTO.setGoal(goal);
        creditDTO.setTerms(percentageTermDTOList);

        creditDTO.setUpdateDate(new Date());
        creditDTO.setUrl("http://google.com");
        EnumDTO enumDTO1 = new EnumDTO();
        enumDTO1.setName("blah");
        creditDTO.setRepaymentMethod(enumDTO1);
        List<EnumDTO> paym = new ArrayList<>();
        EnumDTO p = new EnumDTO();
        p.setName("Равномерно");
        paym.add(p);
        creditDTO.setPaymentPosibilities(paym);


        Credit credit = new Credit(creditDTO);
        System.out.println(CreditAssistantService.getInstance().showBestCredit(credit, "ru"));
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("HelloWorld.pdf"));

        document.open();
        BaseFont times =
                BaseFont.createFont("c:/windows/fonts/times.ttf","cp1251",BaseFont.EMBEDDED);
        Paragraph par =new Paragraph(CreditAssistantService.getInstance().showBestCredit(credit, "ru"),new Font(times,14));
        document.add(par);

        document.close();
        writer.close();
    }
}
