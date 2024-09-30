
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    
    @Test
    public void testWrittenBy() {
        List<Tweet> writtenByAlyssa = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
        assertEquals("expected singleton list", 1, writtenByAlyssa.size());
        assertTrue("expected list to contain tweet1", writtenByAlyssa.contains(tweet1));
        
        List<Tweet> writtenByBbitdiddle = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "bbitdiddle");
        assertEquals("expected singleton list", 1, writtenByBbitdiddle.size());
        assertTrue("expected list to contain tweet2", writtenByBbitdiddle.contains(tweet2));
        
        List<Tweet> writtenByInvalid = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "nonexistent");
        assertTrue("expected empty list", writtenByInvalid.isEmpty());
    }
    
    @Test
    public void testInTimespan() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        
        Instant testStart2 = Instant.parse("2016-02-17T11:00:01Z");
        List<Tweet> inTimespanAfter = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart2, testEnd));
        assertTrue("expected empty list", inTimespanAfter.isEmpty());
    }
    
    @Test
    public void testContaining() {
        List<Tweet> containingTalk = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));
        
        assertFalse("expected non-empty list", containingTalk.isEmpty());
        assertTrue("expected list to contain tweet1 and tweet2", containingTalk.containsAll(Arrays.asList(tweet1, tweet2)));
        
        List<Tweet> containingNonExistent = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("nonexistent"));
        assertTrue("expected empty list", containingNonExistent.isEmpty());
    }

}