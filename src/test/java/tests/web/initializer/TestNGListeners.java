package tests.web.initializer;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.List;

public class TestNGListeners implements ITestListener {
    public static int failed = 0, passed = 0, skipped = 0, total = 0;
    public static List<String> failedTestNames, passedTestNames, skippedTestNames;

    @Override
    public void onTestStart(ITestResult result) {
        total = total + 1;
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        //passedTestNames.add(result.getMethod().getMethodName());
        passed = passed + 1;
    }

    @Override
    public void onTestFailure(ITestResult result) {
        //failedTestNames.add(result.getMethod().getMethodName());
        failed = failed + 1;
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        //skippedTestNames.add(result.getMethod().getMethodName());
        skipped = skipped + 1;
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onStart(ITestContext context) {
    }

    public void onFinish(ITestContext context) {
    }
}
