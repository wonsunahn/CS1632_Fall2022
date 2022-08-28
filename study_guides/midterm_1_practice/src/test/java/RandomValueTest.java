
import static org.junit.Assert.*;

import org.junit.*;
import org.mockito.*;

public class RandomValueTest {
	RandomValue value;

	@Before
	public void setUp() {
		value = new RandomValue();
	}
	
	@Test
	public void testIncValNone() {
		assertEquals(0, value.getVal());
	}
	
	@Test
	public void testIncValOnce() {
		value.incVal();
		assertEquals(1, value.getVal());
	}

	@Test
	public void testIncValTwice() {
		value.incVal();
		value.incVal();
		assertEquals(2, value.getVal());
	}
}
