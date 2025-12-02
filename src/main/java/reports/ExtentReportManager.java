package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {
    private static final ExtentReports extentReports = new ExtentReports();

    public synchronized static ExtentReports getExtentReports() {
        ExtentSparkReporter reporter = new ExtentSparkReporter("exports/ExtentReport/ExtentReport.html");
        reporter.config().setReportName("Extent Report for Automation Test");
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("Framework Name", "Selenium Java | Mahendra");
        extentReports.setSystemInfo("Author", "Mahendra");
        extentReports.setSystemInfo("Version", "1.0");

        return extentReports;
    }
}
