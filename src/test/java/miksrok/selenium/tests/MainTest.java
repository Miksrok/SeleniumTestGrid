package miksrok.selenium.tests;

import miksrok.selenium.BaseTest;
import miksrok.selenium.GeneralActions;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Залізний Мозок on 24.04.2017.
 */
public class MainTest extends BaseTest {

   @Test
    public void openAllProductsPageTest(){
        generalActions.openAllProductsPage();
    }

    @Test(dependsOnMethods = "openAllProductsPageTest")
    public void openRandomProductPageTest(){
        try {
            generalActions.openRandomProduct();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = "openRandomProductPageTest")
    public void addProductToChartTest(){
        generalActions.addProducrToChart();
        Assert.assertTrue(generalActions.isNameEquals(), "incorrect product name");
        Assert.assertTrue(generalActions.isPriceEquals(), "incorrect product price");
        Assert.assertTrue(generalActions.isQtyEquals(), "incorrect product quantity");
    }

    @Test(dependsOnMethods = "addProductToChartTest")
    public void createNewOrderTest(){
        generalActions.createNewOrder();
        generalActions.addAddress();
        generalActions.addDeliveryOptions();
        generalActions.paymentConfirmation();
    }

    @Test(dependsOnMethods = "createNewOrderTest")
    public void confirmationTest(){
        Assert.assertTrue(generalActions.isTitleCorrect());
        Assert.assertTrue(generalActions.isFinalNameEquals(), "incorrect final product name");
        Assert.assertTrue(generalActions.isFinalPriceEquals(), "incorrect final product price");
        Assert.assertTrue(generalActions.isFinalQtyEquals(), "incorrect final product quantity");
    }

    @Test(dependsOnMethods = "confirmationTest")
    public void returnToProductTest(){
        Assert.assertTrue(generalActions.returnToProduct());
    }
}
