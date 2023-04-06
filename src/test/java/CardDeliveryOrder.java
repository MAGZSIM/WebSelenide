import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.SetValueOptions.withText;
import static java.util.Calendar.getInstance;

public class CardDeliveryOrder {

    public String generateDate (int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    public void fillingForm() {
        Configuration.holdBrowserOpen = true; //позволяет не закрывать браузер после открытия
        //Configuration.browserSize = "800x200"; // позволяет протестировать как приложение будет работать на экранах разных устройств
        //Configuration.timeout = 15; // таймаут для неявных ожиданий, время кот селенид каждый раз будет ждать при работе с каждым элементом на странице
        open("http://localhost:9999");
        $x("//span [@data-test-id = 'city'] //input").setValue("Москва");
        $x("//span [@data-test-id = 'date'] //input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        String planningDate = generateDate(4);
        $x("//span [@data-test-id='date'] //input").sendKeys(planningDate);
        $x("//span [@data-test-id='name'] //input").setValue("Пирожков Артур");
        $x("//span [@data-test-id='phone'] //input").setValue("+79261234567");
        $x("//label [@data-test-id='agreement']").click();
        $x("//span [@class = 'button__text']").click();
        $x("//div [@class = 'notification__content']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + planningDate));
    }
}
