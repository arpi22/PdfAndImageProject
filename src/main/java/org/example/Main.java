package org.example;


import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

            Stamp stamp = new Stamp();
            stamp.insertSignature(inputSignature());
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter input pdf path: ");
            String input = scan.nextLine();
            System.out.println("Enter output pdf path: ");
            String output = scan.nextLine();
           try {
               imageInPdf(input, output);
           } catch(IOException | DocumentException e){
            System.out.println(e.getMessage());
        }



    }

    public static String inputSignature() {
        Scanner signatureScn = new Scanner(System.in);
        String signature;
        do {
            System.out.println("Please enter your signature:");
            signature = signatureScn.nextLine();
        } while (signature.isEmpty() || signature.length() > 3);
        return signature;

    }
    public static void imageInPdf(String pdfInputPath, String pdfOutputPath) throws IOException, DocumentException {


        PdfReader pdfReader = new PdfReader(pdfInputPath);
        PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(pdfOutputPath));

        PdfGState gs = new PdfGState();
        gs.setFillOpacity(1.0f);

        PdfContentByte pdfContentByte = pdfStamper.getOverContent(1);

        Image image = null;
        try {
            image = Image.getInstance("src/main/resources/stamp.jpg");
        } catch (BadElementException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        float desiredWidth = 70;
        float desiredHeight = 70;
        image.scaleAbsolute(desiredWidth, desiredHeight);
        image.setAbsolutePosition(60, 100);
        try {
            pdfContentByte.addImage(image);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }

        try {
            pdfStamper.close();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Image added to the PDF successfully!");
    }
}