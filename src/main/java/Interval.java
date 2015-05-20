
public class Interval {
	
	public void validateIntervalConsistence(int[] a, int[] b) throws IllegalArgumentException {
		if(a[0] > a[1])
			throw new IllegalArgumentException("Intervalo A inconsistente.");
		
		if(b[0] > b[1])
			throw new IllegalArgumentException("Intervalo B inconsistente.");
	}
	
	public void validateNullInterval(int[] a, int[] b) {
		
	}
	
	public int[] getInterval(int[] a, int [] b) throws IllegalArgumentException {
		
		this.validateIntervalConsistence(a, b);
		
		int[] result = new int[2];
		
		if(a[1] < b[0])
			return null;
		
		if(a[0] > b[1])
			return null;
		
		if(a[0] > b[0])
			result[0] = a[0];
		else
			result[0] = b[0];
		
		if(a[1] < b[1])
			result[1] = a[1];
		else
			result[1] = b[1];
		
		return result;
	}
}