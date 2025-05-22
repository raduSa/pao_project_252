package Exceptii;

import Utile.Constants;

public class ExceptieVarsta extends RuntimeException {
    public ExceptieVarsta() {
        super(Constants.EXCEPT_VARSTA);
    }
}
