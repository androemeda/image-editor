import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.Scanner; 

public class imageEditor{

    // Function to perform inversion on an input image
    static BufferedImage inversion(BufferedImage inputImage){

        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        BufferedImage outputImage = new BufferedImage(width , height , BufferedImage.TYPE_INT_RGB);

        for(int i=0 ; i<height ; i++){

            for(int j=0 ; j<width ; j++){

                // Get the RGB values of the pixel
                Color pixel = new Color(inputImage.getRGB(j,i));

                int red = pixel.getRed();
                int green = pixel.getGreen();
                int blue = pixel.getBlue();

                // Invert the color channels
                red = 255-red;
                green = 255-green;
                blue = 255-blue;
                
                // Create a new pixel with inverted colors
                Color newPixel = new Color(red , green , blue);

                // Set the pixel in the output image
                outputImage.setRGB(j , i , newPixel.getRGB());

            }
        }
        return outputImage;
    }

    // Function to perform edge detection on an input image
    static BufferedImage edgeDetection(BufferedImage inputImage){

        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        // Create an output image with slightly larger dimensions
        BufferedImage outputImage = new BufferedImage(width+5 , height+5 ,BufferedImage.TYPE_INT_RGB);

        // Copy the input image to the output image
        for(int i=0 ; i<height ; i++){
            for(int j=0 ; j<width ; j++){

                Color pixel = new Color(inputImage.getRGB(j,i));

                outputImage.setRGB(j , i , pixel.getRGB());
            }
        }

        // Perform edge detection by subtracting nearby pixel values
            for(int i=5 ; i<height+5 ; i++){
                for(int j=5 ; j<width+5 ; j++){

                    Color pixel = new Color(inputImage.getRGB(j-5 , i-5));

                    Color newPixel = new Color(outputImage.getRGB(j, i));

                    int finalRed = newPixel.getRed()-pixel.getRed();
                    int finalGreen = newPixel.getGreen()-pixel.getGreen();
                    int finalBlue = newPixel.getBlue()-pixel.getBlue();

                // Ensure color values are non-negative
                    if(finalRed<0) {finalRed=0;}
                    if(finalGreen<0) {finalGreen=0;}
                    if(finalBlue<0) {finalBlue=0;}

                    Color finalPixel = new Color(finalRed , finalGreen , finalBlue);

                // Set the pixel in the output image
                    outputImage.setRGB(j , i , finalPixel.getRGB() );
                }
            }
        return outputImage;
    }

    // Function to crop an input image to a circle with a given radius
    static BufferedImage cropToCircle(BufferedImage inputImage , int radius){

        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        int centreRow = height/2;
        int centreColumn = width/2;

        BufferedImage outputImage = new BufferedImage(width , height , BufferedImage.TYPE_INT_RGB);

        Color blackColor = new Color(0 ,0 ,0);
        for(int i=0 ; i<height ; i++){
            for(int j=0 ; j<width ; j++){

                // Check if the pixel is outside the circle
                if((j-centreColumn)*(j-centreColumn)+(i-centreRow)*(i-centreRow) > radius*radius){
                    // Set the pixel to black
                    outputImage.setRGB(j, i , blackColor.getRGB());
                }else{
                    // Copy the pixel from the input image
                    outputImage.setRGB(j , i , inputImage.getRGB(j,i));
                }
            }
        }
        return outputImage;
    }
    
    // Function to adjust the contrast of an input image
    static BufferedImage contrast(BufferedImage inputImage , int percentage){

        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        BufferedImage outputImage = new BufferedImage(width , height , BufferedImage.TYPE_INT_RGB);

        for(int i=0 ; i<height ; i++){

            for(int j=0 ; j<width ; j++){

                Color pixel = new Color(inputImage.getRGB(j , i));

                int red = pixel.getRed();
                int green = pixel.getGreen();
                int blue = pixel.getBlue();

                // Adjust the color channels based on the percentage
                if(red>127) {red = red + (red*percentage/100);}
                else {red = red - (red*percentage/100);}
                if(green>127) {green = green + (green*percentage/100);}
                else {green = green - (green*percentage/100);}
                if(blue>127) {blue = blue + (blue*percentage/100);}
                else {blue = blue - (blue*percentage/100);}

                // Ensure color values are within the valid range
                if(red>255) {red=255;}
                if(red<0) {red=0;}
                if(green>255) {green=255;}
                if(green<0) {green=0;}
                if(blue>255) {blue=255;}
                if(blue<0) {blue=0;}

                Color newPixel = new Color(red , green , blue);

                // Set the pixel in the output image
                outputImage.setRGB(j ,i , newPixel.getRGB());
            }
        }
        return outputImage;
    }

    // Function to apply a blur effect to an input image
    static BufferedImage blurr(BufferedImage inputImage , int pixelCount){

        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        BufferedImage outputImage = new BufferedImage(width , height , BufferedImage.TYPE_INT_RGB);

        int rowStart=0;
        int rowEnd = pixelCount-1;

        while(rowEnd<height){

            int columnStart = 0;
            int columnEnd = pixelCount-1 ;

            while(columnEnd<width){

                int sumRed = 0;
                int sumGreen = 0;
                int sumBlue = 0;

                // Calculate the average color values in the pixel square
                for(int i=rowStart ; i<=rowEnd ; i++){
                    for(int j=columnStart ; j<=columnEnd ; j++){

                        Color pixel = new Color(inputImage.getRGB(j,i));

                        sumRed += pixel.getRed();
                        sumBlue += pixel.getBlue();
                        sumGreen += pixel.getGreen();

                    }
                }

                int avgRed = sumRed/(pixelCount*pixelCount);
                int avgBlue = sumBlue/(pixelCount*pixelCount);
                int avgGreen = sumGreen/(pixelCount*pixelCount);

                Color newPixel = new Color(avgRed , avgGreen , avgBlue);

                // Set the entire pixel square in the output image to the average color
                for(int i=rowStart ; i<=rowEnd ; i++){
                    for(int j=columnStart ; j<=columnEnd ; j++){
                        outputImage.setRGB(j , i , newPixel.getRGB() );
                    }
                }

                columnStart+=pixelCount;
                columnEnd+=pixelCount;
            }

            rowStart+=pixelCount;
            rowEnd+=pixelCount;
        }

        return outputImage;
    }

    // Function to mirror an input image vertically
    static BufferedImage mirror(BufferedImage inputImage){
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        BufferedImage outputImage = new BufferedImage(width , height , BufferedImage.TYPE_INT_RGB);
        
        for(int i=0 ; i<height ; i++){
            for(int j=0 ; j<width/2 ; j++){
                // Swap pixels horizontally to create a mirror effect
                Color pixel = new Color(inputImage.getRGB(j,i));
                outputImage.setRGB(j,i,inputImage.getRGB(inputImage.getWidth()-1-j , i));
                outputImage.setRGB(inputImage.getWidth()-1-j , i , pixel.getRGB());
                
            }
        }
        return outputImage;
    }


    // Function to rotate an input image anti-clockwise
    static BufferedImage rotateAntiClockwise(BufferedImage inputImage){
        BufferedImage outputImage = rotateClockwise(inputImage);
        outputImage = rotateClockwise(outputImage);
        outputImage = rotateClockwise(outputImage);
        return outputImage;
    }

    // Function to rotate an input image clockwise
    static BufferedImage rotateClockwise(BufferedImage inputImage){
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        BufferedImage outputImage = new BufferedImage(height , width , BufferedImage.TYPE_INT_RGB);
        
        //transpose
        for(int i=0 ; i<width ; i++){
            for(int j=0; j<height ; j++){
                Color pixel = new Color(inputImage.getRGB(i,j));
                outputImage.setRGB(j,i,pixel.getRGB());
            }
        }

        // Mirror the transposed image to rotate clockwise
        outputImage = mirror(outputImage);

        return outputImage;
    }

    // Function to change the brightness of an input image
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

                // Adjust the color channels based on the increase percentage
                if(red>255){red=255;}
                if(blue>255){blue=255;}
                if(green>255){green=255;}

                // Ensure color values are within the valid range
                if(red<0){red=0;}
                if(blue<0){blue=0;}
                if(green<0){green=0;}

                Color newPixel = new Color(red , green , blue);

                // Set the pixel in the output image
                outputImage.setRGB(j,i,newPixel.getRGB());
            }
        }
        return outputImage;
    }

    // Function to convert an input image to grayscale
    static BufferedImage convertToGreyScale(BufferedImage inputImage){

        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        BufferedImage outputImage = new BufferedImage(width , height , BufferedImage.TYPE_BYTE_GRAY);

        for(int i=0 ; i<height ; i++){

            for(int j=0 ; j<width ; j++){

                // Set the grayscale pixel in the output image
                outputImage.setRGB(j,i, inputImage.getRGB(j,i));

            }
        }
        return outputImage;
    }

    // Function to create a pencil sketch effect from an input image
    static BufferedImage pencilSketch(BufferedImage inputImage){

        BufferedImage outputImage;
        // Apply edge detection
        outputImage = edgeDetection(inputImage);
        // Invert the colors to create a sketch effect
        outputImage = inversion(outputImage);

        return outputImage;
    }

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        boolean iterate = true;

        while(iterate){

   
           System.out.println("chose the operation you would like to perform on the image:\n\n1.convert to grey-scale\n2.change brightness\n3.rotate clockwise\n4.rotate anti-clockwise\n5.mirror\n6.blurr.\n7.contrast\n8.crop to circle\n9.edge detection\n10.inversion\n11.pencil sketch\n12.exit\n");
           System.out.print("enter choice: ");
           int choice = sc.nextInt();

           if(choice==12){
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

                    case 6: System.out.print("enter length of pixel square: ");
                            int sideLength = sc.nextInt();
                            BufferedImage blurred = blurr(inputImage , sideLength);
                            File blurredImage = new File("blurred.jpeg");
                            ImageIO.write(blurred , "jpeg" , blurredImage);
                            System.out.println("\ndone!\n");
                            break;

                    case 7: System.out.print("\ncontrast by what percentage: ");
                            int percentage = sc.nextInt();
                            BufferedImage contrasted = contrast(inputImage , percentage);
                            File contrastedImage = new File("contrasted.jpeg");
                            ImageIO.write(contrasted , "jpeg" , contrastedImage);
                            System.out.println("\ndone!\n");
                            break;

                    case 8: System.out.print("\nenter radius: ");
                            int radius = sc.nextInt();
                            BufferedImage croppedToCircle = cropToCircle(inputImage , radius);
                            File croppedToCircleImage = new File("croppedToCircle.jpeg");
                            ImageIO.write(croppedToCircle , "jpeg" , croppedToCircleImage);
                            System.out.println("\ndone!\n");
                            break;

                    case 9: BufferedImage edgeDetected = edgeDetection(inputImage);
                            File edgeDetectedImage = new File("edgeDetected.jpeg");
                            ImageIO.write(edgeDetected , "jpeg" , edgeDetectedImage);
                            System.out.println("\ndone!\n");
                            break;

                    case 10: BufferedImage inverted = inversion(inputImage);
                             File invertedImage = new File("inverted.jpeg");
                             ImageIO.write(inverted , "jpeg" , invertedImage);
                             System.out.println("\ndone!\n");
                             break;

                    case 11: BufferedImage pencilSketched = pencilSketch(inputImage);
                             File pencilSketchedImage = new File("pencilSketched.jpeg");
                             ImageIO.write(pencilSketched , "jpeg" , pencilSketchedImage);
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
