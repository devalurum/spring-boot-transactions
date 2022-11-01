package org.devalurum.transactionsapp.utils;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.text.NumberFormat;

@UtilityClass
public class Utils {
    public static final NumberFormat fmt = NumberFormat.getCurrencyInstance();

    public static String numberFormat(double amount) {
        return fmt.format(amount);
    }

    public static String numberFormatForBigDecimal(@NonNull BigDecimal amount) {
        return fmt.format(amount.doubleValue());
    }
}
