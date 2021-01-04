package test_cases;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
public class TestRunner {
   public static void main(String[] args) {
	  System.err.println("Please wait for the test result!"); 
      Result result = JUnitCore.runClasses(AllTest.class);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      System.err.println("Test result is successful: "+result.wasSuccessful());
   }
}  	