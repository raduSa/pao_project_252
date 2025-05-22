package Exceptii;

import Utile.Constants;

public class ExceptieProprietatiDB extends RuntimeException {
    public ExceptieProprietatiDB() {
        super(Constants.EXCEPT_DBPROPERTIES);
    }
}
