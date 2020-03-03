package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ResultsPage {
    public String departTime;
    public String arrivalTime;

    public String retunDepartTime;
    public String returnArrivalTime;

    @FindBy(xpath = "(//button[@class='booking fRight'])[2]")
    private WebElement bookFlight;
    @FindBy(xpath = "(//li[contains(@class,'nonStops')]//a)[2]")
    private WebElement nonStop;

    private WebDriver driver;

    @FindBy(xpath = "//section[@class='resultsContainer']//ul[contains(@class,'listView')] //li")
    private WebElement resultsContainer;

    public ResultsPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }

    public void waitTillResultsAppear(){
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOf(resultsContainer));
    }
    public void selectNonStopFlightsOption(){
        nonStop.click();
    }

    public void selectLastReturnTime(){
        List<WebElement> returnTimes=driver.findElements(By.xpath("//nav[@class='arrivalTime']//li"));
        returnTimes.get(returnTimes.size()-1).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sortBasedOnDepartureTime(String destination,String order){
        String ordering;
        if(order.equalsIgnoreCase("ascending"))
            ordering = "sortAsc";
        else
            ordering = "sortDes";
        while(!driver.findElement(By.xpath("//div[@data-fromto='"+destination+"']//li[@class='depart']/a")).getAttribute("class").contains(ordering)){
            driver.findElement(By.xpath("//div[@data-fromto='"+destination+"']//li[@class='depart']/a")).click();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        retunDepartTime= driver.findElement(By.xpath("(//div[@data-fromto='"+destination+"']//th[@class='depart'])")).getText();
        returnArrivalTime= driver.findElement(By.xpath("(//div[@data-fromto='"+destination+"']//th[@class='arrive'])")).getText();
    }

    public void sortBasedOnPrice(String destination, String order){
        String ordering;
        if(order.equalsIgnoreCase("ascending"))
            ordering = "sortAsc";
        else
            ordering = "sortDes";
        while(!driver.findElement(By.xpath("//div[@data-fromto='"+destination+"']//li[@class='price']/a")).getAttribute("class").contains(ordering)){
            driver.findElement(By.xpath("//div[@data-fromto='"+destination+"']//li[@class='price']/a")).click();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void selectBasedOnPriceWithoutSorting(String destination){
        List<WebElement> data = driver.findElements(By.xpath("//div[@data-fromto='"+destination+"']//th[@class='price ']"));
        int minPrice=999999;
        int index=0;
        for(int i=0;i<data.size();i++){
            int price = Integer.parseInt(data.get(i).getText().trim().replace(",","").replaceAll("Rs.",""));
            if(minPrice>price) {
                minPrice = price;
                index=i;
            }
        }
        departTime= driver.findElement(By.xpath("(//div[@data-fromto='"+destination+"']//th[@class='depart'])["+(index+1)+"]")).getText();
        arrivalTime= driver.findElement(By.xpath("(//div[@data-fromto='"+destination+"']//th[@class='arrive'])["+(index+1)+"]")).getText();
        data.get(index).click();
    }
    public ReviewPage clickOnBookFlight(){
        bookFlight.click();
        return new ReviewPage(driver);
    }
}
