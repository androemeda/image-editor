import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.*;

public class imageEditor{


    static BufferedImage mirror(BufferedImage inputImage){
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        BufferedImage outputImage = new BufferedImage(width , height , BufferedImage.TYPE_INT_RGB);
        
        for(int i=0 ; i<height ; i++){
            for(int j=0 ; j<width/2 ; j++){
                Color pixel = new Color(inputImage.getRGB(j,i));
                outputImage.setRGB(j,i,inputImage.getRGB(inputImage.getWidth()-1-j , i));
                outputImage.setRGB(inputImage.getWidth()-1-j , i , pixel.getRGB());
                
            }
        }
        return outputImage;
    }


    static BufferedImage rotateAntiClockwise(BufferedImage inputImage){
        BufferedImage outputImage = rotateClockwise(inputImage);
        outputImage = rotateClockwise(outputImage);
        outputImage = rotateClockwise(outputImage);
        return outputImage;
    }


    static BufferedImage rotateClockwise(BufferedImage inputImage){
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        BufferedImage outputImage = new BufferedImage(height , width , BufferedImage.TYPE_INT_RGB);
        
        //transpose.
        for(int i=0 ; i<width ; i++){
            for(int j=0; j<height ; j++){
                Color pixel = new Color(inputImage.getRGB(i,j));
                outputImage.setRGB(j,i,pixel.getRGB());
            }
        }

        outputImage = mirror(outputImage);

        return outputImage;
    }


    static BufferedImage changeBrightness(BufferedImage inputImage , int increase){
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        BufferedImage outputImage = new BufferedImage(width , height , BufferedImage.TYPE_3BYTE_BGR);
        for(int i=0 ; i<height ; i++){
            for(int j=0 ; j<width ; j++){
                Color pixel = new Color(inputImage.getRGB(j,i));
                int red = pixel.getRed();
                int blue = pixel.getBlue();
                int green = pixel.getGreen();
                red = red+(increase*red/100);
                blue = blue+(increase*blue/100);
                green = green+(increase*green/100);

                if(red>255){red=255;}
                if(blue>255){blue=255;}
                if(green>255){green=255;}
                if(red<0){red=0;}
                if(blue<0){blue=0;}
                if(green<0){green=0;}

                Color newPixel = new Color(red , green , blue);

                outputImage.setRGB(j,i,newPixel.getRGB());


            }
        }

        return outputImage;
    }


    static BufferedImage convertToGreyScale(BufferedImage inputImage){
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        BufferedImage outputImage = new BufferedImage(width , height , BufferedImage.TYPE_BYTE_GRAY);
        for(int i=0 ; i<height ; i++){
            for(int j=0 ; j<width ; j++){
                outputImage.setRGB(j,i, inputImage.getRGB(j,i));

            }
        }
        return outputImage;
    }


    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        boolean iterate = true;

        while(iterate){

   
           System.out.println("chose the operation you would like to perform on the image:\n\n1.convert to grey-scale\n2.change brightness\n3.rotate clockwise\n4.rotate anti-clockwise\n5.mirror\n6.exit.\n");
           System.out.print("enter choice: ");
           int choice = sc.nextInt();

           if(choice==6){
            break;
           }

           sc.nextLine();
           
           System.out.print("\nshare the address of the image: ");
           String address = sc.nextLine();
           File inputFile = new File(address);
           
           try{
               BufferedImage inputImage = ImageIO.read(inputFile);

   
               switch(choice){
   
                   case 1: BufferedImage grayScale = convertToGreyScale(inputImage);
                           File grayScaleImage = new File("grayScaleImage.jpeg");
                           ImageIO.write(grayScale , "jpeg" , grayScaleImage);
                           System.out.println("\ndone!\n");
                           break;
   
                   case 2: System.out.print("change brightness by what percentage: ");
                           int factor = sc.nextInt();
                           BufferedImage changedBrightness = changeBrightness(inputImage , factor);
                           File changedBrightnessImage = new File("changedBrightness.jpeg");
                           ImageIO.write(changedBrightness , "jpeg" , changedBrightnessImage);
                           System.out.println("\ndone!\n");
                           break;
   
                   case 3: BufferedImage rotatedClockwise = rotateClockwise(inputImage);
                           File rotatedClockwiseImage = new File("rotatedClockwise.jpeg");
                           ImageIO.write(rotatedClockwise , "jpeg" , rotatedClockwiseImage);
                           System.out.println("\ndone!\n");
                           break;
   
                   case 4: BufferedImage rotatedAntiClockwise = rotateAntiClockwise(inputImage);
                           File rotatedAntiClockwiseImage = new File("rotatedAntiClockwise.jpeg");
                           ImageIO.write(rotatedAntiClockwise , "jpeg" , rotatedAntiClockwiseImage);
                           System.out.println("\ndone!\n");
                           break;
   
                   case 5: BufferedImage mirrored = mirror(inputImage);
                           File mirroredImage = new File("mirrored.jpeg");
                           ImageIO.write(mirrored , "jpeg" , mirroredImage);
                           System.out.println("\ndone!\n");
                           break;
   
                   default: System.out.println("\nplease enter a valid option.\n");
               }
   
   
   
           }catch(IOException e){
               e.printStackTrace();
           }
        }

        System.out.println("\n\n----PROGRAM ENDED----\n\n");
    }
}