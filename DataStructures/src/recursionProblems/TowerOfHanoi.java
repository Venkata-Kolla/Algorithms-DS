package recursionProblems;

import java.util.LinkedList;
import java.util.Stack;

import com.google.common.base.Strings;

public class TowerOfHanoi {
	static int s1Code = -1;
	static int s2Code = -1;
	static int s3Code = -1;
	public static void main(String[] args) {
		Stack<Integer> s1 = new Stack<Integer>();
		Stack<Integer> s2 = new Stack<Integer>();
		Stack<Integer> s3 = new Stack<Integer>();
		//number of rings
		int i = 5;
		//put the rings in first stack
		for(int j = i; j > 0; j--) {
			s1.push(j);
		}
		//because the roles will be switched in step 3 below, 
		//we have to store ids to preserve original names stacks to use them in printing
		s1Code = System.identityHashCode(s1);
		s2Code = System.identityHashCode(s2);
		s3Code = System.identityHashCode(s3);
		//this means, we are trying to move rings from s1 to s2 using standby stack s3
		changeRings(i, s1, s2, s3);
	}

	/*
	 * This algorithm has three key steps with a base condition
	 * 1. First move all the rings except last one to standby stack
	 * 2. Now move the last ring to destination stack
	 * 3. Move all the rings on Stand by stack to destination
	 * (Note for step 1&3: Now, they again follows the same process recursively, the only change is 
	 * the stacks roles will be switched. for step 1:Standby becomes destination, and destination becomes standby
	 * for step 3:Standby becomes source, and source becomes standby)
	 * 
	 * Base condition: When there are only two rings need to be moved, 
	 * we move ring1 from source --> standby 
	 * & ring2 from source --> destination 
	 * & finally, move ring1 from standby --> destination
	 */
	private static void changeRings(int i, Stack<Integer> source, Stack<Integer> destination, Stack<Integer> standby) {
		//base condition
		if(i == 2) {
			System.out.println("moving "+source.peek()+" from "+getStackName(System.identityHashCode(source))+" to "+getStackName(System.identityHashCode(standby)));
			standby.push(source.pop());
			System.out.println("moving "+source.peek()+" from "+getStackName(System.identityHashCode(source))+" to "+getStackName(System.identityHashCode(destination)));
			destination.push(source.pop());
			System.out.println("moving "+standby.peek()+" from "+getStackName(System.identityHashCode(standby))+" to "+getStackName(System.identityHashCode(destination)));
			destination.push(standby.pop());
			return;
		}
		//step 1
		changeRings(i-1, source, standby, destination);
		//step 2
		System.out.println("moving "+source.peek()+" from "+getStackName(System.identityHashCode(source))+" to "+getStackName(System.identityHashCode(destination)));
		destination.push(source.pop());
		//step 3
		changeRings(i-1, standby, destination, source);
	}

	//helper method to provide name of stack based on hashcode
	private static String getStackName(int stackCode) {
		if(stackCode == s1Code)
        		return "S1";
		else if(stackCode == s2Code)
    			return "S2";
		else if(stackCode == s3Code)
			return "S3";
		else
			return "";
	}

}
