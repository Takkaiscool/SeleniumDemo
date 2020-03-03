package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.text.DateFormatter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class HomePage {

    private WebDriver driver;

    public  String fromDest;

    public String toDest;

    @FindBy(id = "RoundTrip")
    private WebElement roundTrip;

    @FindBy(id = "FromTag")
    private WebElement from;

    @FindBy(id = "ToTag")
    private WebElement to;

    @FindBy(id="DepartDate")
    private WebElement departDate;

    @FindBy(id = "ReturnDate")
    private WebElement returnDate;

    @FindBy(id = "Adults")
    private WebElement noOfAdults;

    @FindBy(id = "Childrens")
    private WebElement noOfChildrens;

    @FindBy(id = "Infants")
    private WebElement noOfInfants;

    @FindBy(className = "ctDatePicker")
    private WebElement datePicker;

    @FindBy(id = "SearchBtn")
    private WebElement searchFlights;

    Calendar fromDate;
    Calendar toDate;
    public HomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }
    public void selectTripType(String tripType){
        if (tripType.equalsIgnoreCase("round trip")){
            roundTrip.click();
        }
    }

    public void enterDestination(String from,String to){
        this.from.sendKeys(from);
        WebDriverWait wait = new WebDriverWait(driver,15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'"+from+",')]")));
        driver.findElement(By.xpath("//a[contains(text(),'"+from+",')]")).click();
        fromDest = this.from.getAttribute("value").split("\\(")[1].replaceAll("\\)","");
        this.to.sendKeys(to);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'"+to+",')]")));
        driver.findElement(By.xpath("//a[contains(text(),'"+to+",')]")).click();
        toDest = this.to.getAttribute("value").split("\\(")[1].replaceAll("\\)","");
    }

    public void enterDepartDate2WeeksFromToday(){
        departDate.click();
        fromDate = Calendar.getInstance();
        fromDate.add(Calendar.DAY_OF_YEAR,14);
        SimpleDateFormat df = new SimpleDateFormat("d-MMMMMM-yyyy");
        selectDateFromDatePicker(df.format(fromDate.getTime()));
    }

    public void enterReturnDateNextDate(){
        fromDate.add(Calendar.DAY_OF_YEAR,1);
        SimpleDateFormat df = new SimpleDateFormat("d-MMMMMM-yyyy");
        selectDateFromDatePicker(df.format(fromDate.getTime()));
    }

    private void selectDateFromDatePicker(String date){
        String []dateData = date.split("-");
        String day = dateData[0];
        String month = dateData[1];
        String year = dateData[2];
        WebDriverWait wait = new WebDriverWait(driver,15);
        wait.until(ExpectedConditions.visibilityOf(datePicker));
        driver.findElement(By.xpath("//div[div[div[span[text()='"+month+"']]]]//a[text()='"+day+"']")).click();
    }

    public void selectNumberOfAdults(int noOfAdults){
        Select sel = new Select(this.noOfAdults);
        sel.selectByVisibleText(noOfAdults+"");
    }

    public void selectNumberOfChildrens(int noOfChildrens){
        Select sel = new Select(this.noOfChildrens);
        sel.selectByVisibleText(noOfChildrens+"");
    }

    public ResultsPage clickOnSearchFlight(){
        searchFlights.click();
        WebDriverWait wait = new WebDriverWait(driver,60);
        wait.until(ExpectedConditions.invisibilityOf(searchFlights));
        return new ResultsPage(driver);
    }
}
