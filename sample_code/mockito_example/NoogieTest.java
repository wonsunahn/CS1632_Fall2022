import org.junit.Test;
import static org.junit.Assert.*;

import org.mockito.*;

public class NoogieTest {

    // The following two tests should always pass.
    // They don't really check anything.
    
    @Test
    public void testShouldPass() {
	assertEquals(1, 1);
    }

    @Test
    public void testShouldAlsoPass() {
	int x = 1;
	int y = 1;
	int z = x + y;
	assertTrue(z == 2);
    }

    // This test should ALWAYS fail - it creates an object
    // and then checks if its ref is null. 
    
    @Test
    public void testShouldFail() {
	Object o = new Object();
	assertNull(o);
    }

    // Actual Noogie Tests - from here to the end of the file,
    // these actually test aspects of the Noogie class.

    // Test to see that if we create a Noogie object with 0 cats,
    // getting the number of cats will return 0.
    
    @Test
    public void testNoogieNumCats0() {
	Noogie n = new Noogie(0);
	assertEquals(0, n.getNumCats());
    }

    // Test to see that if we create a Noogie object with 10 cats,
    // getting the number of cats will return 10.
    
    @Test
    public void testNoogieNumCats10() {
	Noogie n = new Noogie(10);
	assertEquals(10, n.getNumCats());
    }

    // Test that for a Noogie with a positive # of cats, if we call
    // the negativeCats() method, it will return the correct
    // number of (negative) cats.
    
    @Test
    public void testNegative() {
	Noogie n = new Noogie(5);
	assertEquals(-5, n.negativeCats());
    }

    // Test that for a Noogie with a negative # of cats, if we call
    // the negativeCats() method, it will return the correct
    // number of (positive) cats.
    
    @Test
    public void testDoubleNegative() {
	Noogie n = new Noogie(-5);
	assertEquals(5, n.negativeCats());
    }

    // Test adding a positive number of cats.
    
    @Test
    public void testAdd1() {
	Noogie n = new Noogie(0);
	n.addSomeCats(1);
	assertEquals(1, n.getNumCats());
    }

    // Test adding a negative number of cats throws an exception.
    
    @Test
    public void testNegativeAdd() {
	Noogie n = new Noogie(0);
	try {
	    n.addSomeCats(-2);
	    // Note that if fail() is called, result will be "null" and that is
	    // what will be displayed in the TestRunner
	    fail();
	} catch (ArithmeticException aex) {
	    // expected behavior
	}
	// Number of cats should remain 0 (initial value)
	assertEquals(0, n.getNumCats());

    }

    // Test adding cats multiple times.
    
    @Test
    public void testMultipleAdds() {
	Noogie n = new Noogie(0);
	for (int j = 0; j < 10; j++) {
	    n.addSomeCats(5);
	}
	assertEquals(50, n.getNumCats());
    }

    // TESTS USING MOCKITO

    // Assume Badger.java is not our code, so we are not
    // interested in testing it per se.  However, the
    // Noogie class which we are working on depends on
    // it.  So we will double it and "fake" it to avoid
    // depending on it in our tests, as well as saving
    // time.
    
    // Using these doubles will prevent the time-consuming
    // Badger methods from being called.

    // Note that since I used "import static" above, then
    // I do not need to type e.g. "Mockito.mock", but just
    // "mock"

    // Simple double
    // Under ordinary circumstances, no exception should be
    // thrown by the Badger, so we should return 0.
    
    @Test
    public void testBadgerPlay() {
	Badger b = Mockito.mock(Badger.class);
	Noogie n = new Noogie(0);
	int val = n.playWithBadger(b);
	assertEquals(0, val);
    }

    // Force our doubled Badger object to throw an exception whenever
    // .play() is called.  In this case playWithBadger should return 1.

    @Test
    public void testBadgerPlayException() {
	Badger b = Mockito.mock(Badger.class);
	Mockito.when(b.play()).thenThrow(new ArithmeticException());
	Noogie n = new Noogie(0);
	int val = n.playWithBadger(b);
	assertEquals(1, val);
    }

    // Make a true mock to ensure that .play() is called
    // only once in the .playWithBadger() method.
    // Note that I stub before I verify.

    @Test
    public void testBadgerPlayCalled() {
	Noogie n = new Noogie(0);
	Badger b = Mockito.mock(Badger.class);
	Mockito.when(b.play()).thenReturn("");
	n.playWithBadger(b);
	Mockito.verify(b, Mockito.times(1)).play();
	    
    }
    
    // Stub out getNumFlerbos() to give us no flerbos.
    
    @Test
    public void testBadgerSimOneBadgerNoCats() {
    	Noogie n = new Noogie(0);
    	Badger b = Mockito.mock(Badger.class);
    	Mockito.when(b.getNumFlerbos()).thenReturn(0);
    	Badger[] bs = new Badger[1];
    	bs[0] = b;
    	int val = n.simulateBadgers(bs);
    	assertEquals(0, val);	
    }

    // Stub out getNumFlerbos() to give us lots of flerbos.
    // Give the class under test (Noogie) lots of cats.
    
    @Test
    public void testBadgerSimOneBadgerManyCatsManyFlerbos() {
    	Noogie n = new Noogie(100);
    	Badger b = Mockito.mock(Badger.class);
    	Mockito.when(b.getNumFlerbos()).thenReturn(100);
    	Badger[] bs = new Badger[1];
    	bs[0] = b;
    	int val = n.simulateBadgers(bs);
    	assertEquals(200, val);	
    }

    // Combine tests to check many cats, and many badgers, all
    // with many flerbos.
    
    @Test
    public void testBadgerSimManyBadgersManyCatsManyFlerbos() {
    	Noogie n = new Noogie(100);
    	Badger[] bs = new Badger[10];
	for (int j = 0; j < 10; j++) {
	    Badger mb = Mockito.mock(Badger.class);
	    Mockito.when(mb.getNumFlerbos()).thenReturn(100);
	    bs[j] = mb;
	}
    	int val = n.simulateBadgers(bs);
    	assertEquals(1100, val);	
    }
    

    
}
