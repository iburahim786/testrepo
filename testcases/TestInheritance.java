package testcases;

public class TestInheritance {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		InheritanceParent ip1 = new InheritanceChild1();
		InheritanceChild1 ic1 = new InheritanceChild1();
//		InheritanceParent ip1 = (InheritanceParent)ic1;
//		InheritanceChild1 ic1 =  (InheritanceChild1)ip1;
		ic1.parent();
		ic1.execute0();
		ic1.execute1();
		ic1.execute2();	
		ic1.execute3();
		
	}

}
