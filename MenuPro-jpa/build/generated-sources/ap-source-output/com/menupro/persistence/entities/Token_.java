package com.menupro.persistence.entities;

import com.menupro.persistence.entities.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-06-23T00:15:40")
@StaticMetamodel(Token.class)
public class Token_ { 

    public static volatile SingularAttribute<Token, Long> id;
    public static volatile SingularAttribute<Token, User> user;
    public static volatile SingularAttribute<Token, String> token;

}