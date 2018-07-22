package nhs;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DecimalFormat;

public class RegularAmountValidator implements ConstraintValidator<CheckRegularAmount,RegularAmount> {

    /**
     *  probably should have a maximum digits, but it's not specify in the description
     */
    private static final String regexForTwoDecimal = "^\\d+\\.\\d{0,2}$";
    private static final String regexForNumber = "^\\d+";

    private static final Integer TWOWEEK_NUM = 2;
    private static final Integer FOURWEEK_NUM = 4;
    private static final Integer QUARTER_NUM = 13;
    private static final Integer YEAR_NUM = 52;

    public void initialize(CheckRegularAmount checkRegularAmount) {
    }

    public boolean isValid(RegularAmount regularAmount, ConstraintValidatorContext constraintValidatorContext) {
        // check input value, should only be 2 decimals (pence) and only positive number is allowed
        final String amount = regularAmount.getAmount();
        if(checkValidValueWithRegex(amount)){
            switch(regularAmount.getFrequency()){
                case TWO_WEEK:
                    return weeklyDivisible(amount,TWOWEEK_NUM);
                case FOUR_WEEK:
                    return weeklyDivisible(amount,FOURWEEK_NUM);
                case QUARTER:
                    return weeklyDivisible(amount,QUARTER_NUM);
                case YEAR:
                    return weeklyDivisible(amount,YEAR_NUM);
                default: // no need to check WEEK and MONTH
                    return true;
            }
        }else{
            return false;
        }
    }

    private boolean weeklyDivisible(final String amount, final Integer weeks){
        /**
         precision error is a known problem in CS, e.g. 5206.76 / 52 = 100.13000000001
         apply division first and format to 2 decimals
         then multiply the value and see if it returns the original number
         */
        Double result = Double.valueOf(amount) / weeks;

        DecimalFormat format = new DecimalFormat("###.##");
        String roundedValue = format.format(result);

        Double getOriginalValue = Double.valueOf(roundedValue) * weeks;

        if(getOriginalValue.equals(Double.valueOf(amount))){
            return checkValidValueWithRegex(String.valueOf(roundedValue));
        }
        return false;

    }

    private boolean checkValidValueWithRegex(final String amount){
        // String should be in format like: 250, 250.00, 250.11, 1.1 etc.
        return amount.matches(regexForNumber) || amount.matches(regexForTwoDecimal);
    }
}
