import org.junit.Test;
import static org.junit.Assert.*;

// This class doesn't really test anything - it's just here
// to show you how to test multiple files with the TestRunner.

public class CoogieTest {

    
    // This test is meant to fail.
    
    @Test
    public void testMeowAndBarkAreEqualWillFail() {
	assertEquals("Meow", "Bark");
    }

    @Test
    public void testShouldPass() {
	int z = 2;
	assertTrue(z == 2);
    }
    
}
