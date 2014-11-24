package com.logistic.domain.base;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by kafein on 10/26/2014.
 */
public interface IEntity<P extends Serializable> extends Serializable{

    P getId();

    void setId(P id);

    String getCreatedUser();

    void setCreatedUser(String createdUser);

    Date getCreatedTimestamp();

    void setCreatedTimestamp(Date createdTimestamp);

    String getUpdatedUser();

    void setUpdatedUser(String updatedUser);

    Date getUpdatedTimestamp();

    void setUpdatedTimestamp(Date updatedTimestamp);
}
