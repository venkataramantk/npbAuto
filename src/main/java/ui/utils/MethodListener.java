package ui.utils;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

/**
 * Created by skonda on 3/1/2017.
 */
public class MethodListener implements IInvokedMethodListener {
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        System.out.println("************************** Starting "+method.getTestMethod().getMethodName()+" in "+testResult.getTestClass().getName()+" class **************************");


    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        System.out.println("************************ Ending "+method.getTestMethod().getMethodName()+" in "+testResult.getTestClass().getName()+" class *******************************");

    }
}
