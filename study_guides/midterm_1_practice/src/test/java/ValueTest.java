
import static org.junit.Assert.*;

import org.junit.*;
import org.mockito.*;

public class ValueTest {
	Value value;

	@Before
	public void setUp() {
		value = new Value();
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
		assertEquals(3, value.getVal());
	}
}
