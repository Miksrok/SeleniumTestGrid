package miksrok.selenium.tests;

import miksrok.selenium.BaseTest;
import miksrok.selenium.GeneralActions;
import org.testng.annotations.Test;

/**
 * Created by Залізний Мозок on 24.04.2017.
 */
public class MainTest extends BaseTest {

    @Test
    private void open() throws InterruptedException {
       generalActions.openAllProductsPage();
       generalActions.openRandomProduct();
       generalActions.addProducrToChart();
       generalActions.checkMainInformation();
       generalActions.creatNewOrder();
       generalActions.addAddress();
       generalActions.addDeliveryOptions();
       generalActions.paymentConfirmation();
       generalActions.checkTitle();
    }

}
