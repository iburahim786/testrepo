package testcases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class privateTest4 {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
         Scanner br = new Scanner(new File("C:\\Users\\miburahi\\Desktop\\filetext.txt"));
         Map <String, Integer> words = new HashMap<String, Integer>();
         while(br.hasNext()){
        	 String word = br.next();
        	 Integer cnt = words.get(word);
        	 if(cnt != null)
        		 cnt++;
        	 else
        		 cnt=1;
        		 words.put(word, cnt);
         }
         for(Map.Entry<String, Integer> temp : words.entrySet()){
        	 System.out.println(temp.getKey()+" : "+temp.getValue());
         }
//         int j= 0;String k="";
//         Map<String, Integer> map = new HashMap<String,Integer>();
//         for(Map.Entry<String, Integer> temp : words.entrySet()){
//            if(temp.getValue()>=j){
//            	 if(temp.getValue()==j){
//            		j=temp.getValue();
//                  	k=temp.getKey();
//                 	map.put(k, j);
//                 	map.put(temp.getKey(), temp.getValue());
//                 }
//            	j=temp.getValue();
//             	k=temp.getKey();
//            }
//	     }
//         for(Map.Entry<String, Integer> temp1: map.entrySet()){
//	         if(temp1.getValue()>=j){
//	          System.out.println("Higeshest Occurance in the file is ");
//	          System.out.println(temp1.getKey()+" : "+temp1.getValue());
//	         }
//            else{
//              System.out.println("Higeshest Occurance in the file is ");
//   	          System.out.println(k+" : "+j);
//            }
//         }
}
}