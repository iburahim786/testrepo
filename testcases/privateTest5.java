package testcases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class privateTest5 {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
         BufferedReader br = new BufferedReader(new FileReader(new File("C:\\Users\\miburahi\\Desktop\\filetext.txt")));
         String rl =br.readLine();
		 String [] spl = rl.split(" ");
		 for(String s : spl){
			 System.out.print(s+" ");
		 }
		 Map<String, Integer> map = new HashMap<String, Integer>();
		 for(int i=0;i<=spl.length-1;i++){
			 if(map.containsKey(spl[i])){
				int cnt = map.get(spl[i]);
				map.put(spl[i], cnt+1);
			 }
			 else{
				map.put(spl[i], 1);
			 }
		 }
		 System.out.println("File has the below words with No of accurance...");
//		 System.out.println(map);
//		 for(Map.Entry<String, Integer> temp: map.entrySet()){
//			 System.out.println(temp.getKey()+" : "+temp.getValue());
//		 }
}

}
