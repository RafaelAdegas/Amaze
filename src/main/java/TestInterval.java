import org.junit.Assert;
import org.junit.Test;


public class TestInterval {
	
	Interval interval;
	
	@Test
	public void testInterval0() throws IllegalArgumentException {
		
		interval = new Interval();
		
		int[] a = {-3, 4};
		int[] b = {-2, 1};
		
		Assert.assertNotNull(interval.getInterval(a, b));
		Assert.assertArrayEquals(b, interval.getInterval(a, b));
	}
	
	@Test
	public void testInterval1() throws IllegalArgumentException {
		
		interval = new Interval();
		
		int[] a = {-3, 4};
		int[] b = {-4, 1};
		
		int[] res = {-3, 1};
		
		Assert.assertNotNull(interval.getInterval(a, b));
		Assert.assertArrayEquals(res, interval.getInterval(a, b));
	}
	
	@Test
	public void testInterval2() throws IllegalArgumentException {
		
		interval = new Interval();
		
		int[] a = {-3, 4};
		int[] b = {1, 6};
		
		int[] res = {1, 4};
		
		Assert.assertNotNull(interval.getInterval(a, b));
		Assert.assertArrayEquals(res, interval.getInterval(a, b));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInterval3() throws IllegalArgumentException {
		
		interval = new Interval();
		
		int[] a = {5, 4};
		int[] b = {1, 6};
		
		int[] res = {1, 4};
		
		Assert.assertNotNull(interval.getInterval(a, b));
		Assert.assertArrayEquals(res, interval.getInterval(a, b));
	}
	
	@Test
	public void testInvalidInterval() throws IllegalArgumentException {
		
		interval = new Interval();
		
		int[] a = {-3, 4};
		int[] b = {6, 8};
		
		Assert.assertNull(interval.getInterval(a, b));
	}
	
	@Test
	public void testInvalidInterval2() throws IllegalArgumentException {
		
		interval = new Interval();

		int[] a = {6, 8};
		int[] b = {-3, 4};
		
		Assert.assertNull(interval.getInterval(a, b));
	}
}
