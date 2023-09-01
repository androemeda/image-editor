import java.util.Arrays;

public class StringProblems2{

    //-------EXPLORE STRING BUILDER-------//

    //Q.given an array of strings , return string with highest frequency and also the frequency. ["abc", "def", "abc","xyz"]

    //Q.return co-ordinates of an element(first occurence) present in the matrix.
    static int[] coOrdinates(int[][] A , int target){
        int[] coordinates = new int[2];

        for(int i=0 ; i<A.length ; i++){

            for(int j=0 ; j<A[i].length ; j++){

                if(A[i][j]==target){
                    coordinates[0] = i;
                    coordinates[1] = j;
                    return coordinates;
                }
            }
        }
        
        coordinates[0]=-1;
        coordinates[1]=-1;

        return coordinates;
    }

    //Q.given only lowercase english alphabets sort a string.
    static String sortString(String s){

        String sortedString = "";
        char[] arr = new char[s.length()];
        for(int i=0 ; i<s.length() ; i++){
            arr[i] = s.charAt(i);
        }
        Arrays.sort(arr);
        for(int i=0 ; i<s.length() ; i++){
            sortedString+=arr[i];
        }
        return sortedString;        

    }

    static void printArray(int[] arr){
        for(int i=0 ; i<arr.length ; i++){
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }

    public static void main(String[] args){

        // String s = "kjdfwfblkmd";
        // System.out.println(sortString(s));

        printArray(coOrdinates(new int[][] {{1,2},{3,4}} , 2));

    }
}