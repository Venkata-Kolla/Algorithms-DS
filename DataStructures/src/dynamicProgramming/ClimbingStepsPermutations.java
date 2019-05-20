package dynamicProgramming;

import java.util.HashMap;
import java.util.Map;

public class ClimbingStepsPermutations {
	static Map<Integer, Integer> memo;
	public static void main(String[] args) {
        memo = new HashMap<>();
        int n = 8;
        int result = fibonacciBottomUp(n);
        System.out.println(result);
	}
    
    /**
	 * more space efficient
	 * Time --> O(N) 
	 * Space --> O(1)
	 */
    private static int fibonacciBottomUp(int n) {
		//we can climb one step in one way(1)
    		if(n == 1) return 1;
		//we can climb two steps in two ways(1,1 & 2)
		if(n == 2) return 2;
        int i = 0, prev1 = 2, prev2 = 1, result = 0;
		while(i < n-2) {
			result = prev1 + prev2;
			prev2 = prev1;
			prev1 = result;
			i++;
		}
		return result;
	}

}
