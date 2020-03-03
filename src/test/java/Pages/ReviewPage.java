package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class ReviewPage {
    private WebDriver driver;

    @FindBy(id = "LoginContinueBtn_1")
    private WebElement continueButtonOfEmail;
    @FindBy(id = "username")
    private WebElement emailID;

    @FindBy(id = "itineraryBtn")
    private WebElement continueBooking;

    @FindBy(id = "travellerBtn")
    private WebElement continueButton;

    @FindBy(id = "mobileNumber")
    private WebElement mobileNumber;

    public ReviewPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public void waitTillPageLoads(){
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOf(continueBooking));
    }
    public void enterTravellerDetails(Passenger []passengersDetails){
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOf(continueButton));
        int j=1;
        for(int i=1;i<=passengersDetails.length;i++){
            if(passengersDetails[i-1].type.equalsIgnoreCase("adult")){
                Select sel = new Select(driver.findElement(By.id("AdultTitle"+i)));
                sel.selectByVisibleText(passengersDetails[i-1].title);
                driver.findElement(By.id("AdultFname"+i)).sendKeys(passengersDetails[i-1].firstName);
                driver.findElement(By.id("AdultLname"+i)).sendKeys(passengersDetails[i-1].lastName);
            }else{
                Select sel = new Select(driver.findElement(By.id("ChildTitle"+j)));
                sel.selectByVisibleText(passengersDetails[i-1].title);
                driver.findElement(By.id("ChildFname"+j)).sendKeys(passengersDetails[i-1].firstName);
                driver.findElement(By.id("ChildLname"+j)).sendKeys(passengersDetails[i-1].lastName);
                String dob[] = passengersDetails[i-1].dob.split("-");
                sel = new Select(driver.findElement(By.id("ChildDobDay"+j)));
                sel.selectByVisibleText(dob[0]);
                sel = new Select(driver.findElement(By.id("ChildDobMonth"+j)));
                sel.selectByVisibleText(dob[1]);
                sel = new Select(driver.findElement(By.id("ChildDobYear"+j)));
                sel.selectByVisibleText(dob[2]);
            }
        }
        mobileNumber.sendKeys(passengersDetails[0].mobileNumber);
        continueButton.click();
    }

    public void enterEmailId(String email){
        WebDriverWait wait = new WebDriverWait(driver,240);
        wait.until(ExpectedConditions.visibilityOf(continueButtonOfEmail));
        emailID.sendKeys(email);
        continueButtonOfEmail.click();
        wait.until(ExpectedConditions.invisibilityOf(continueButtonOfEmail));
    }
    public void  enterIternaryDetails(String from,String to,String fromDepartTime, String fromArrivalTime,String toDepartTime, String toArrivalTime){
        Assert.assertEquals(driver.findElement(By.xpath("(//ul[@data-sector='"+from+"_"+to+"']//span[@class='placeTime']//span)[1]")).getText().toString().trim(),from);
        Assert.assertEquals(driver.findElement(By.xpath("(//ul[@data-sector='"+from+"_"+to+"']//span[@class='placeTime']//strong)[1]")).getText().toString().trim(),fromDepartTime);

        Assert.assertEquals(driver.findElement(By.xpath("(//ul[@data-sector='"+from+"_"+to+"']//span[@class='placeTime']//span)[2]")).getText().toString().trim(),to);
        Assert.assertEquals(driver.findElement(By.xpath("(//ul[@data-sector='"+from+"_"+to+"']//span[@class='placeTime']//strong)[2]")).getText().toString().trim(),fromArrivalTime);

        Assert.assertEquals(driver.findElement(By.xpath("(//ul[@data-sector='"+to+"_"+from+"']//span[@class='placeTime']//span)[1]")).getText().toString().trim(),to);
        Assert.assertEquals(driver.findElement(By.xpath("(//ul[@data-sector='"+to+"_"+from+"']//span[@class='placeTime']//strong)[1]")).getText().toString().trim(),toDepartTime);

        Assert.assertEquals(driver.findElement(By.xpath("(//ul[@data-sector='"+to+"_"+from+"']//span[@class='placeTime']//span)[2]")).getText().toString().trim(),from);
        Assert.assertEquals(driver.findElement(By.xpath("(//ul[@data-sector='"+to+"_"+from+"']//span[@class='placeTime']//strong)[2]")).getText().toString().trim(),toArrivalTime);
        continueBooking.click();
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.invisibilityOf(continueBooking));

    }
}

