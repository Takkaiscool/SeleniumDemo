package Reports;

import com.relevantcodes.extentreports.ExtentReports;

public class ReportsManager {
    private static ExtentReports reports;

    public synchronized static  ExtentReports getReporter() {
        if (reports == null) {
            String workingDir = System.getProperty("user.dir");
            reports = new ExtentReports(workingDir + "/Reports/ReportResults.html", true);
        }
        return reports;
    }
}
