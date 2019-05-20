package dynamicProgramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

public class CoinChangeProblem {
	static Map<Integer, Integer> coinsNumStorage = new HashMap<>();
	static Map<Integer, CoinResult> coinsStorage = new HashMap<>();
	static Map<Long, Integer> coinsNumStorageBU = new HashMap<>();
	public static void main(String[] args) {
		int[] coins = {1, 2147483647};
		int sum = 2;
		List<Integer> coinsL = Lists.newArrayList(419,408,186,83);
		//CoinResult result = minCoinsNeededTopDownApproach(sum, coinsL);
		//System.out.println(result.numCoins +" coins: "+ result.coins);
		
		//int result2 = minNumCoinsNeededTopDownApproach(sum, coinsL, Integer.MAX_VALUE, 0);
		//System.out.println(result2);
		int resultBU = minNumCoinsNeededBottomUpApproach(0, sum, coins, new int[sum], 0, Integer.MAX_VALUE);
		System.out.println(resultBU);
		
	}
	private static int minNumCoinsNeededBottomUpApproach(int sum, int fSum, int[] coins, int[] coinCache, int level, int maxLevel) {
		level++;
		if(level >= maxLevel) return -1;
		if(sum == fSum) {
			return 0;
		}
		if(coinCache[sum] != 0){
			return coinCache[sum];
		}
		int oldVal = -1;
		for(int i = coins.length-1;i > 0; i--) {
			int coin = coins[i];
			int cc = sum + coin;
			if(coin > sum || cc > fSum) continue;
			if(oldVal != -1) maxLevel = oldVal;
			//if(coins.contains(sum)) return 1;
			int newVal =  minNumCoinsNeededBottomUpApproach(cc, fSum, coins, coinCache, level++, maxLevel);
			if(newVal != -1) newVal++;
			//kick start the old val
			if(oldVal == -1) oldVal = newVal;
			//when finding if new val is better(lesser) than oldVal avoid comparing with -1 because its invalid 
			if(oldVal != -1 && newVal != -1 && newVal < oldVal) oldVal = newVal;	
		}
		coinCache[sum] = oldVal;
		return oldVal;
	}
	
	private static CoinResult minCoinsNeededTopDownApproach(int sum, List<Integer> coins) {
		if(coinsStorage.containsKey(sum)){
			return coinsStorage.get(sum);
		} 
		if(coins.contains(sum)) {
			return new CoinResult(1, new ArrayList<Integer>(){{ add(sum); }});
		}
		if(sum == 0) {
			return new CoinResult(0, new ArrayList<Integer>());
		}

		CoinResult oldVal = new CoinResult(-1, new ArrayList<Integer>());
		for(int coin: coins) {
			int cc = sum-coin;
			if(cc < 0) continue;
			//CoinResult resCoinResult = new CoinResult(-1, new ArrayList<Integer>());
			CoinResult resCoinResult = minCoinsNeededTopDownApproach(cc, coins);
			if(resCoinResult.numCoins != -1) {
				resCoinResult.numCoins++;
				resCoinResult.coins.add(coin);
			}
			if(oldVal.numCoins == -1) {
				//spent almost 2 hours to find assigning reference is bad and need to create new instance
				oldVal.coins = new ArrayList<Integer>(resCoinResult.coins);
				oldVal.numCoins = resCoinResult.numCoins;
			}
			if(oldVal.numCoins != -1 && resCoinResult.numCoins != -1 && resCoinResult.numCoins < oldVal.numCoins) {
				oldVal.coins = new ArrayList<Integer>(resCoinResult.coins);
				oldVal.numCoins = resCoinResult.numCoins;
			}
		}
		coinsStorage.put(sum, oldVal);
		return new CoinResult(oldVal.numCoins, new ArrayList<Integer>(oldVal.coins));
	}
	
	private static int minNumCoinsNeededTopDownApproach(int sum, List<Integer> coins, Integer min, int level) {
		level++;
		if(level >= min) return -1;
		if(coinsNumStorage.containsKey(sum)){
			return coinsNumStorage.get(sum);
		} 
		if(coins.contains(sum)) {
			return 1;
		}
		if(sum == 0) {
			return 0;
		}
		int oldVal = -1;
		for(int coin: coins) {
			int cc = sum-coin;
			if(cc < 0) continue;
			if(oldVal != -1) min = oldVal;
			//passing sum-cc is send sum as param, seriously?
			int newVal =  minNumCoinsNeededTopDownApproach(cc, coins, min, level);
			//if new value is -1 that means its invalid coins combo so dont count
			if(newVal != -1) newVal++;
			//kick start the old val
			if(oldVal == -1) oldVal = newVal;
			//when finding if new val is better(lesser) than oldVal avoid comparing with -1 because its invalid 
			if(oldVal != -1 && newVal != -1 && newVal < oldVal) oldVal = newVal;	
		}
		coinsNumStorage.put(sum, oldVal);
		return oldVal;
	}
 
	static class CoinResult{
		int numCoins;
		List<Integer> coins = new ArrayList<>();
		public CoinResult(int s, List<Integer> coins2) {
			this.numCoins = s;
			this.coins = coins2;
		}
	}
}
