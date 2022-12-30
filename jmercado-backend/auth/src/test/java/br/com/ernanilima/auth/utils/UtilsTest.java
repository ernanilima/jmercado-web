package br.com.ernanilima.auth.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UtilsTest {

    @Test
    @DisplayName("Deve retornar o valor depois do ultimo espaco na string")
    void getLastValue_Must_Return_The_Value_After_The_Last_Space_In_The_String() {
        var entityNotFoundException = new EntityNotFoundException("unable to find com.package.classname with id 123456");
        var jpaObjectRetrievalFailureException = new JpaObjectRetrievalFailureException(entityNotFoundException);

        assertEquals("123456", Utils.getLastValue(jpaObjectRetrievalFailureException));
    }
}