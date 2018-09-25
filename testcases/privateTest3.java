package testcases;

import java.util.ArrayList;

public class privateTest3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
         String s = "i am tester in amazon and My Role Is Test Lead";
         String[] spl = s.split(" ");
         for(int i=0;i<=spl.length-1;i++){
        	if(i%2==0){
        		System.out.print(spl[i]+" ");
        	}
        	else{
        		String reverse = new StringBuffer(spl[i]).reverse().toString();
        		System.out.print(reverse+" ");
        	}
         }
         System.out.println("");
         System.out.println("**********************************************");
         for(int i=0;i<=spl.length-1;i++){
        	if(i%2==0){
        		String reverse = new StringBuffer(spl[i]).reverse().toString();
        		System.out.print(reverse+" ");
        	}
        	else{
        		System.out.print(spl[i]+" ");
        	}
         }
		
//		for(PrivateClass b : ls){
//			System.out.println(b.Name+" "+b.Age+" "+b.Native);
//		}
		
	}

}
