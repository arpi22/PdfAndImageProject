package org.example;


import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        Stamp stamp = new Stamp();

        try {
            stamp.insertSignature(inputSignature());

            try {
                imageInPdf(inputStampedFile(), outputStampedFile());
            } catch(IOException | DocumentException e){
                System.out.println(e.getMessage());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public static String inputStampedFile(){
        Scanner inputScn = new Scanner(System.in);
        String input;
        boolean finished;
        do {
            System.out.println("Enter input pdf path: ");
            input = inputScn.nextLine();
            if(input.isEmpty())
                finished = false;
            else if(!new File(input).exists())
                finished = false;
            else finished = input.endsWith(".pdf");
        } while (!finished);
        return input;

    }
    public static String outputStampedFile(){
        Scanner outputScn = new Scanner(System.in);
        String output;
        do {
            System.out.println("Enter output pdf path: ");
            output = outputScn.nextLine();
        } while (output.isEmpty());
        return output;

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
        if(!pdfOutputPath.endsWith(".pdf")){
            pdfOutputPath += ".pdf";
        }
        PdfReader pdfReader = new PdfReader(pdfInputPath);
        PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(pdfOutputPath));

        PdfGState gs = new PdfGState();
        gs.setFillOpacity(1.0f);

        PdfContentByte pdfContentByte = pdfStamper.getOverContent(1);

        Image image;
        try {
            image = Image.getInstance("src/main/resources/stamp.png");
        } catch (BadElementException | IOException e) {
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
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Image added to the PDF successfully!");
    }

}