package com.logistic.domain.base.impl;

import com.logistic.domain.base.IEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by kafein on 10/26/2014.
 */
@MappedSuperclass
@EqualsAndHashCode(exclude = {"createdUser", "createdTimestamp", "updatedUser", "updatedTimestamp"})
@ToString
@Setter
@Getter
@Slf4j
public abstract class AbstractEntity<P extends Serializable> implements IEntity<P>, Cloneable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "cshSeqGen")
    @SequenceGenerator(name = "cshSeqGen", sequenceName = "CSH_SEQ_GEN", allocationSize = 1)
    @Column(name = "ID", unique = true, nullable = false)
    private P id;

    @Column(name = "CREATED_USER")
    private String createdUser;

    @Basic(optional = false)
    @Column(name = "CREATED_TIMESTAMP", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTimestamp;

    @Column(name = "UPDATED_USER")
    private String updatedUser;

    @Basic(optional = true)
    @Column(name = "UPDATED_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTimestamp;

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
