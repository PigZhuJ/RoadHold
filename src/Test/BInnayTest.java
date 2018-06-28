package Test;

public class BInnayTest {
    public static void main(String[] args) {
        int[] i={0,1,1,2,1};
        BInnayTest b=new BInnayTest();
        System.out.println(b.ArraytoString(i));
    }
    private String ArraytoString(int[] DataArr) {
        String sx="";
        for (int i = 0; i <DataArr.length-1; i++) {
            sx=sx+DataArr[i]+",";
        }
        sx=sx+DataArr[DataArr.length-1];
        return sx;
    }
}
