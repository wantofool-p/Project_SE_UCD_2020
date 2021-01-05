package test_cases;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
@RunWith(Suite.class)
@SuiteClasses({
	DeckTest.class,
	BoardTest.class,
	PlayerMovementTest.class,
	PlayerTest.class,
	MyGameManagerTest.class,
})
public class AllTest{
	;
}
