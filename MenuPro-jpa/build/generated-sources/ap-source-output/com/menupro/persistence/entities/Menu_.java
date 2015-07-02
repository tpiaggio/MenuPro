package com.menupro.persistence.entities;

import com.menupro.persistence.entities.Plate;
import com.menupro.persistence.entities.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-06-25T22:53:35")
@StaticMetamodel(Menu.class)
public class Menu_ { 

    public static volatile SingularAttribute<Menu, User> owner;
    public static volatile ListAttribute<Menu, User> sharedUsers;
    public static volatile ListAttribute<Menu, Plate> plates;
    public static volatile SingularAttribute<Menu, String> name;
    public static volatile SingularAttribute<Menu, Long> id;

}