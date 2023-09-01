class Student{
    String name;
    String email;
    String phNo;
    String parentPhNo;
    int rollNo;
    int homeTownPinCode;
    String mentorEmail;

    


    //-----HW: READ ABOUT .EQUALS() FUNCTIONS-------//




    //no need to specify return type for constructor function
    public Student(String n, String e , int r){
        name = n;
        email = e;
        rollNo = r;
    }
}

class StringIntPair{
    int i;
    String str;
}

class Coordinates{
    int row;
    int col;

    public void printCoordinates(){
        System.out.println(row+" "+col);
    }
}

public class Test{

    // public boolean isEqual(Coordinates c1 , coordinates c2){

    // }

    static StringIntPair mostFreqStrFun(String[] arr){

        StringIntPair ans = new StringIntPair();

        int[] count = new int[arr.length];

        ans.i = count[0];

        for(int i=0 ; i<arr.length ; i++){
            count[i]=0;
            for(int j=0 ; j<arr.length ; j++){
                if(arr[i].equals(arr[j])){
                    count[i]++;
                }
            }
            if(count[i]>ans.i){
                ans.i=count[i];
                ans.str=arr[i];
            }
        }
        return ans;
    }

    static Coordinates getCoordinates(int[][] A , int target){
        Coordinates ans = new Coordinates();

        ans.row=-1;
        ans.col=-1;

        for(int i=0 ; i<A.length ; i++){

            for(int j=0 ; j<A[i].length ; j++){

                if(A[i][j]==target){
                    ans.row = i;
                    ans.col = j;
                    break;
                }
            }
        }
        return ans;
    }

    public static void main(String[] args){

        Student s1 = new Student("kartik" , "kartik@gmail.com" , 101);
        Student s2 = new Student("abc" , "abc@gmail.com", 175);

        System.out.println(s1.name);
        System.out.println(s1.rollNo);
        System.out.println(s2);//prints address of this reference in the memory.
        System.out.println(s2.name);

        Student s3;
        s3=s2;//s3 starts pointing at the object created by s2. even if s2 points somewhere else now , s3 will keep pointing towards the same object.

        s2 = s1;//now both s1 and s2 point to the same reference in memory. this does not mean that the object s2 is changed.
        System.out.println(s2.name);

        System.out.println(s3.name);

        Coordinates c3 = new Coordinates(1,2);
        c3.printCoordinates();
    }

}