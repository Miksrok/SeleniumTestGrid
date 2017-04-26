package miksrok.selenium;

import miksrok.selenium.models.Product;
import miksrok.selenium.models.User;
import miksrok.selenium.utils.DataConverter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;

/**
 * Created by Залізний Мозок on 24.04.2017.
 */
public class GeneralActions  {

    private WebDriver driver;
    private WebDriverWait wait;
    private Product product;
    private User user;
    private String randomProductUrl;

    private final String MAIN_PAGE= "http://prestashop-automation.qatestlab.com.ua/ru/";
    private By allProductLink = By.cssSelector(".all-product-link");
    private By randomProduct = By.className("product-title");

    private By productName = By.xpath("//h1[@itemprop='name']");
    private By productPrice = By.xpath("//span[@itemprop='price']");
    private By productInformation = By.xpath("//a[@href='#product-details']");
    private By productQty = By.cssSelector(".product-quantities span");

    private By addToChartBtn = By.className("add-to-cart");
    private By creatNewDelivery = By.cssSelector(".cart-content a");

    //check main information
    private By controlProductName = By.cssSelector(".product-line-info a") ;
    private By controlProductQty = By.cssSelector("#cart-subtotal-products .label");
    private By controlProductPrice = By.cssSelector("#cart-subtotal-products .value");

    private By newOrderBtn = By.cssSelector(".cart-detailed-actions div a");
    private By customerForm = By.id("customer-form");
    private By name = By.name("firstname");
    private By surname = By.name("lastname");
    private By email = By.name("email");
    private By continueBtn = By.name("continue");
    private By checkoutAddressesStep = By.id("checkout-addresses-step");
    private By address = By.name("address1");
    private By postcode = By.name("postcode");
    private By city = By.name("city");
    private By confirmAddresses = By.name("confirm-addresses");
    private By confirmDeliveryOptions = By.name("confirmDeliveryOption");
    private By checkoutPaymentStep = By.id("checkout-payment-step");
    private By paymentOptions = By.id("payment-option-2");
    private By checkBox = By.id("conditions_to_approve[terms-and-conditions]");
    private By paymentConfirmation = By.cssSelector("#payment-confirmation div button");

    private By cartTitle = By.cssSelector(".card-title");



    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    public void openAllProductsPage(){
        driver.navigate().to(MAIN_PAGE);
        wait.until(ExpectedConditions.visibilityOfElementLocated(this.allProductLink));
        WebElement allProductLink = driver.findElement(this.allProductLink);
        allProductLink.click();
    }
    public void openRandomProduct() throws InterruptedException {
        Thread.sleep(20000);
        List<WebElement> elements = driver.findElements(this.randomProduct);
        elements.get(new Random().nextInt(elements.size())).click();

        //get product information

        wait.until(ExpectedConditions.visibilityOfElementLocated(this.productInformation));
        WebElement name = driver.findElement(this.productName);
        WebElement price = driver.findElement(this.productPrice);

        randomProductUrl = driver.getCurrentUrl();
        System.err.println(randomProductUrl);
        System.out.println(name.getText().toLowerCase());
        System.out.println(DataConverter.parsePriceValue(price.getText()));

        WebElement productInformation = driver.findElement(this.productInformation);
        productInformation.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(this.productQty));
        WebElement qty = driver.findElement(this.productQty);

        System.out.println(DataConverter.parseStockValue(qty.getText()));

        product = new Product(name.getText().toLowerCase(),
                DataConverter.parseStockValue(qty.getText()),
                DataConverter.parsePriceValue(price.getText()));


    }
    public void addProducrToChart(){

        wait.until(ExpectedConditions.visibilityOfElementLocated(this.addToChartBtn));
        WebElement addToChertBtn = driver.findElement(this.addToChartBtn);
        addToChertBtn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(this.creatNewDelivery));
        WebElement creatNewDelivery = driver.findElement(this.creatNewDelivery);
        creatNewDelivery.click();

    }

    public boolean isNameEquals(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(this.newOrderBtn));
        WebElement controlProductName = driver.findElement(this.controlProductName);
        return controlProductName.getText().toLowerCase().equals(product.getName());
    }

    public boolean isPriceEquals(){
        WebElement controlProductPrice = driver.findElement(this.controlProductPrice);
        float pr = DataConverter.parsePriceValue(controlProductPrice.getText());
        return DataConverter.convertPriceValue(pr).equals(product.getPrice());
    }

    public boolean isQtyEquals(){
        WebElement controlProductQty = driver.findElement(this.controlProductQty);
        return DataConverter.parseStockValue(controlProductQty.getText()) == 1;
    }

    public void createNewOrder(){
        user = User.generate();

        wait.until(ExpectedConditions.visibilityOfElementLocated(this.newOrderBtn));
        WebElement newOrderButton = driver.findElement(this.newOrderBtn);
        newOrderButton.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(this.customerForm));
        WebElement name = driver.findElement(this.name);
        WebElement surname = driver.findElement(this.surname);
        WebElement email = driver.findElement(this.email);
        name.sendKeys(user.getName());
        surname.sendKeys(user.getSurname());
        email.sendKeys(user.getEmail());
        WebElement continueButton = driver.findElement(this.continueBtn);
        continueButton.click();
    }

    public void addAddress(){

        wait.until(ExpectedConditions.visibilityOfElementLocated(this.checkoutAddressesStep));
        WebElement address = driver.findElement(this.address);
        WebElement postcode = driver.findElement(this.postcode);
        WebElement city = driver.findElement(this.city);
        address.sendKeys(user.getAddress());
        postcode.sendKeys(user.getPostcode()+"");
        city.sendKeys(user.getCity());
        WebElement submitAddress = driver.findElement(this.confirmAddresses);
        submitAddress.click();

    }
    public void addDeliveryOptions(){

        wait.until(ExpectedConditions.visibilityOfElementLocated(this.confirmDeliveryOptions));
        WebElement confirmDeliveryOptions = driver.findElement(this.confirmDeliveryOptions);
        confirmDeliveryOptions.click();

    }

    public void paymentConfirmation(){

        wait.until(ExpectedConditions.visibilityOfElementLocated(this.checkoutPaymentStep));
        WebElement paymentOptions = driver.findElement(this.paymentOptions);
        paymentOptions.click();
        WebElement checkBox = driver.findElement(this.checkBox);
        checkBox.click();
        WebElement paymentConfirmation = driver.findElement(this.paymentConfirmation);
        paymentConfirmation.click();

    }
    public boolean isTitleCorrect(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(this.cartTitle));
        WebElement cartTitle = driver.findElement(this.cartTitle);
        String title = cartTitle.getText().substring(1);
        System.err.println(title.equals("ВАШ ЗАКАЗ ПОДТВЕРЖДЁН"));
        System.err.println(title);
        return title.equals("ВАШ ЗАКАЗ ПОДТВЕРЖДЁН");
    }

    public boolean returnToProduct(){

        driver.navigate().to(randomProductUrl);
        wait.until(ExpectedConditions.visibilityOfElementLocated(this.productInformation));
        WebElement productInformation = driver.findElement(this.productInformation);
        productInformation.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(this.productQty));
        WebElement qty = driver.findElement(this.productQty);
        int tmp = DataConverter.parseStockValue(qty.getText());
        System.out.println(tmp);
        System.out.println(tmp == product.getQty()-1);
        return tmp == product.getQty()-1;
    }

}
