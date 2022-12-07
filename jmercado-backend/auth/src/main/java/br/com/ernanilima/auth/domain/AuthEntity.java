package br.com.ernanilima.auth.domain;

import java.io.Serializable;

public interface AuthEntity<K extends Serializable> {

    K getId();

}
