package com.menupro.persistence.entities;

import com.menupro.persistence.entities.Menu;
import com.menupro.persistence.entities.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-06-23T00:15:40")
@StaticMetamodel(Order.class)
public class Order_ { 

    public static volatile SingularAttribute<Order, Date> date;
    public static volatile SingularAttribute<Order, String> comments;
    public static volatile ListAttribute<Order, User> sideBuyers;
    public static volatile SingularAttribute<Order, Long> id;
    public static volatile ListAttribute<Order, Menu> menus;
    public static volatile SingularAttribute<Order, User> buyer;

}