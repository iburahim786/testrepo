package testcases;

import java.util.ArrayList;

public class privateTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
     
		ArrayList<PrivateClass> ls = new ArrayList<PrivateClass>();
		
		PrivateClass p1 = new PrivateClass("Iburahim", 23, "Ramnad");
		PrivateClass p2 = new PrivateClass("Sha", 24, "Madurai");
		
		ls.add(p1);
		ls.add(p2);
		
		for(PrivateClass b : ls){
			System.out.println(b.Name+" "+b.Age+" "+b.Native);
		}
		
	}

}
