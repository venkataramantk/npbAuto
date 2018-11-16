package tests.web.initializer;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryTests implements IRetryAnalyzer {
    private int retryCount = 0;
    private int maxRetryCount = 1;

    @Override
    public boolean retry(ITestResult result) {
        if (!result.isSuccess()) {
            if (retryCount < maxRetryCount) {
                System.out.println("Retrying test " + result.getTestName() + "" + retryCount + 1 + " " + " time");
                retryCount++;
                result.setStatus(result.FAILURE);
                return true;
            } else {
                result.setStatus(result.FAILURE);
            }
        } else {
            result.setStatus(result.SUCCESS);
        }
        return false;
    }
}
