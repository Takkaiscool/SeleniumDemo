package Test;

import Pages.HomePage;
import Pages.Passenger;
import Pages.ResultsPage;
import Pages.ReviewPage;
import Utils.BaseTest;
import Utils.TestListeners;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Date;
@Listeners(TestListeners.class)

public class TestCases extends BaseTest {

    @Test
    public void bookTicket(){

        Passenger details[] = getPassengerData();


        HomePage homePage=new HomePage(driver);
        homePage.selectTripType("Round Trip");
        homePage.enterDestination("Bangalore","Delhi");
        homePage.enterDepartDate2WeeksFromToday();
        homePage.enterReturnDateNextDate();
        homePage.selectNumberOfAdults(2);
        homePage.selectNumberOfChildrens(1);
        ResultsPage resultsPage = homePage.clickOnSearchFlight();
        resultsPage.waitTillResultsAppear();
        resultsPage.selectNonStopFlightsOption();
        resultsPage.selectLastReturnTime();
        resultsPage.selectBasedOnPriceWithoutSorting(homePage.fromDest+"_"+homePage.toDest);
        resultsPage.sortBasedOnDepartureTime(homePage.toDest+"_"+homePage.fromDest,"Descending");
        //resultsPage.sortBasedOnPrice(homePage.fromDest+'_'+homePage.toDest,"Ascending");
        ReviewPage reviewPage=resultsPage.clickOnBookFlight();
        reviewPage.waitTillPageLoads();
        reviewPage.enterIternaryDetails(homePage.fromDest,homePage.toDest,resultsPage.departTime,resultsPage.arrivalTime,resultsPage.retunDepartTime,resultsPage.returnArrivalTime);
        reviewPage.enterEmailId("test"+new Date().getTime() +"@test.com");
        reviewPage.enterTravellerDetails(details);


    }

    public Passenger[] getPassengerData(){
        Passenger details[] = new Passenger[3];

        details[0] = new Passenger();

        details[0].title="Mr";
        details[0].type="Adult";
        details[0].firstName="Karan";
        details[0].lastName="Kumar";
        details[0].mobileNumber="9876543210";

        details[1] = new Passenger();
        details[1].title="Mrs";
        details[1].type="Adult";
        details[1].firstName="Sandhya";
        details[1].lastName="Karan";

        details[2] = new Passenger();
        details[2].title="Miss";
        details[2].type="Child";
        details[2].firstName="Aradhana";
        details[2].lastName="Karan";
        details[2].dob="13-Mar-2010";

        return details;
    }


}
