package br.com.ernanilima.auth.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Utils {

    public static String getLastValue(JpaObjectRetrievalFailureException e) {
        // exemplo: unable to find com.package.classname with id 123456
        // return: 123456
        String exception = e.getMostSpecificCause().getMessage();
        return exception.replaceAll(".*\\s(?=\\S*$)", "");
    }
}
