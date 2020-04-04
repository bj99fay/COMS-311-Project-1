
public class Interval {
	private int low, high;
	
	/**
	 * @param low
	 * 		the lower bound of the interval
	 * @param high 
	 * 		the upper bound of the interval
	 */
	public Interval(int low, int high) {
		this.low = low;
		this.high = high;
	}
	
	/**
	 * @return the lower bound of the interval
	 */
	public int getLow() {
		return low;
	}
	
	/**
	 * @return the upper bound of the interval
	 */
	public int getHigh() {
		return high;
	}
} 
