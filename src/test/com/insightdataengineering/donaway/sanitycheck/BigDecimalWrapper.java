package com.insightdataengineering.donaway.sanitycheck;

import java.math.BigDecimal;

class BigDecimalWrapper {

    BigDecimal currentTotal = BigDecimal.ZERO;

    void add(BigDecimal bd) {
        currentTotal = currentTotal.add(bd);
    }

}
