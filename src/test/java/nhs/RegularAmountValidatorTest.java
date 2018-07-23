package nhs;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static nhs.Frequency.*;

public class RegularAmountValidatorTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testIncorrectInput(){
        testRegularAmount(WEEK,"500.00000",false);
        testRegularAmount(TWO_WEEK,"500,122",false);
        testRegularAmount(MONTH,"asdioasdoa",false);
        testRegularAmount(MONTH,"12312.abc",false);
        testRegularAmount(WEEK,"-555",false);
        testRegularAmount(TWO_WEEK,"0",false);
        testRegularAmount(WEEK,"0.00",false);
        testRegularAmount(WEEK,"0.89",false);
        testRegularAmount(WEEK,"0100000",false);
        testRegularAmount(TWO_WEEK,"20.20",true);
    }

    @Test
    public void testOneWeek(){
        testRegularAmount(WEEK,"500.1111",false);
        testRegularAmount(WEEK,"500",true);
        testRegularAmount(WEEK,"500.11",true);
        testRegularAmount(WEEK,"1",true);
    }

    @Test
    public void testTwoWeeks(){
        testRegularAmount(TWO_WEEK,"500.00",true);
        testRegularAmount(TWO_WEEK,"777.11",false);
        testRegularAmount(TWO_WEEK,"100.5555",false);
        testRegularAmount(TWO_WEEK,"100000",true);
    }

    @Test
    public void testFourWeeks(){
        testRegularAmount(FOUR_WEEK,"888.81",false);
        testRegularAmount(FOUR_WEEK,"111.222",false);
        testRegularAmount(FOUR_WEEK,"1776.4",true);
        testRegularAmount(FOUR_WEEK,"60",true);
    }


    @Test
    public void testQuarterly(){
        testRegularAmount(QUARTER,"1444.43",true);
        testRegularAmount(QUARTER,"888.81",false);
        testRegularAmount(QUARTER,"1690",true);
        testRegularAmount(QUARTER,"5778.5",true);
    }

    @Test
    public void testMonthly(){
        testRegularAmount(MONTH,"1444.43",true);
        testRegularAmount(MONTH,"888.81",true);
        testRegularAmount(MONTH,"1690.0001",false);
        testRegularAmount(MONTH,"9",true);
    }

    @Test
    public void testYearly(){
        testRegularAmount(YEAR,"5206.76",true);
        testRegularAmount(YEAR,"777.77",false);
        testRegularAmount(YEAR,"1690.01",false);
        testRegularAmount(YEAR,"6938.88",true);
    }


    private void testRegularAmount(final Frequency frequency, final String amount, final boolean correctOrNot){
        RegularAmount regularAmount = new RegularAmount(frequency,amount);
        Set<ConstraintViolation<RegularAmount>> constraintViolations = validator.validate( regularAmount );
        assertEquals( correctOrNot, constraintViolations.size()== 0 );
    }
}
