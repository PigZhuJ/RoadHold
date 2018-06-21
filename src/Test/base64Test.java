package Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

public class base64Test {

    public static void main(String[] args) {
    String[] arr={"Ngm8vgAAAAA=","MwlLvgAAAAA=","MAmyvgAAAAA=","LAlJvgAAAAA=","KQnEvgAAAAA=","JQmPvgAAAAA=","Ign5vgAAAAA=","HwnXvgAAAAA=","Gwk/vgAAAAA=","GAmFvgAAAAA=","FQmLvgAAAAA=","EQmQvgAAAAA=","DgknvgAAAAA=","CgmVvgAAAAA=","BwmYvgAAAAA=","BAk/vgAAAAA=","AAmNvgAAAAA=","/Qj0vgAAAAA=","+giMvgAAAAA=","9gilvgAAAAA=","8whtvgAAAAA=","7whPvgAAAAA=","7AiBvgAAAAA=","6QiUvgAAAAA=","5QiSvgAAAAA=","4gjPvgAAAAA=","3wiovgAAAAA=","2wgDvwAAAAA=","2AjQvgAAAAA=","1AjWvgAAAAA=","0QiSvgAAAAA=","zgiSvgAAAAA=","ygiHvgAAAAA=","xwgnvgAAAAA=","xAjHvgAAAAA=","wAh/vgAAAAA=","vQiQvgAAAAA=","uQizvgAAAAA=","tgibvgAAAAA=","swjQvgAAAAA=","rwhzvgAAAAA=","rAi2vgAAAAA=","qQhjvgAAAAA=","pQg5vgAAAAA=","oghPvgAAAAA=","nwhavgAAAAA=","mwhFvgAAAAA=","mAhDvgAAAAA=","lAhPvgAAAAA=","kQg9vgAAAAA=","jgi6vgAAAAA=","igiLvgAAAAA=","hwgvvgAAAAA=","ZgjfvgAAAAA=","YghlvgAAAAA=","XwigvgAAAAA=","WwhPvgAAAAA=","VwiuvgAAAAA=","VAiDvgAAAAA=","UQjAvgAAAAA=","TQiIvgAAAAA=","SQh9vgAAAAA=","RQh3vgAAAAA=","QgjRvgAAAAA=","PwiHvgAAAAA=","OwilvgAAAAA=","OAjMvgAAAAA=","NQjvvgAAAAA=","MQiSvgAAAAA=","Lgh3vgAAAAA=","KggDvwAAAAA=","JwjMvgAAAAA=","IwiqvgAAAAA=","RwRxvgAAAAA=","QwS9vgAAAAA=","QAS3vgAAAAA=","PQRgvgAAAAA=","OATovgAAAAA=","NQTNvgAAAAA=","JgR5vgAAAAA="};
//       String s=arr[0];
        for (int i = 1; i < arr.length; i++) {
            System.out.println(Arrays.toString(Base64.getDecoder().decode(arr[i])));
        }

    }
}
