package Utils;

import Reports.ReportsManager;
import Reports.ReportsTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListeners extends BaseTest implements ITestListener {
    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        iTestContext.setAttribute("WebDriver", this.driver);
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        ReportsTestManager.endTest();
        ReportsManager.getReporter().flush();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {

        ReportsTestManager.startTest(getTestMethodName(iTestResult),"Started");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        ReportsTestManager.getTest().log(LogStatus.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        Object testClass = iTestResult.getInstance();
        WebDriver webDriver = ((BaseTest) testClass).driver;

        String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) webDriver).
                getScreenshotAs(OutputType.BASE64);

        ReportsTestManager.getTest().log(LogStatus.FAIL, "Test Failed",
                ReportsTestManager.getTest().addBase64ScreenShot(base64Screenshot));
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        ReportsTestManager.getTest().log(LogStatus.SKIP, "Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
    }

}
