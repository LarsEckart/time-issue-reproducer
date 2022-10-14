package kata;

import com.spun.util.DateDifference;
import com.spun.util.DateUtils;
import com.spun.util.parser.TemplateDate;
import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.approvaltests.reporters.Junit5Reporter;
import org.approvaltests.utils.WithTimeZone;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.DateFormat;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AppTest {

    @RepeatedTest(10000)
    public void testFebruaryAndDaylightSavingsTime()
    {
        try (WithTimeZone tz = new WithTimeZone("PST"))
        {
            StringBuilder buffer = new StringBuilder();
            DateFormat f = TemplateDate.FORMATS.DATE_SHORT;
            for (int i = 1; i <= 28; i++)
            {
                Timestamp a = DateUtils.parse("2010/02/0" + i);
                Timestamp b = DateUtils.parse("2010/03/0" + i);
                DateDifference dif = new DateDifference(a, b);
                String standardTimeText = dif.getStandardTimeText(2, "days", "seconds", null, null);
                buffer.append(String.format("%s, %s => %s (%s, %s) \n", f.format(a), f.format(b), standardTimeText, a.getTime(), b.getTime()));
            }
            Approvals.verify(buffer.toString(), new Options(new Junit5Reporter()));
        }
    }
}
