package com.logistic.domain;

import com.logistic.domain.base.impl.AbstractEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by kafein on 10/26/2014.
 */
@Data
@Entity
@Table(name = "TEST")
public class Test extends AbstractEntity<Long>{

    @Column(name = "NAME")
    private String name;

    @Column(name = "SURNAME")
    private String surname;

}
