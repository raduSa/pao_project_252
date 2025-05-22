package Validari;

import Entitati.Client;

public class ValidariClienti {
    public static Boolean validareVarsta(Integer varsta) {
        return varsta > 14 && varsta < 120;
    }
}
