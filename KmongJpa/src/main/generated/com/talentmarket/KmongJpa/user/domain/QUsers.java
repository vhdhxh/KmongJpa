package com.talentmarket.KmongJpa.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUsers is a Querydsl query type for Users
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsers extends EntityPathBase<Users> {

    private static final long serialVersionUID = -1194104255L;

    public static final QUsers users = new QUsers("users");

    public final StringPath address = createString("address");

    public final StringPath email = createString("email");

    public final StringPath gender = createString("gender");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image = createString("image");

    public final ListPath<com.talentmarket.KmongJpa.Item.domain.Item, com.talentmarket.KmongJpa.Item.domain.QItem> items = this.<com.talentmarket.KmongJpa.Item.domain.Item, com.talentmarket.KmongJpa.Item.domain.QItem>createList("items", com.talentmarket.KmongJpa.Item.domain.Item.class, com.talentmarket.KmongJpa.Item.domain.QItem.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final StringPath provider = createString("provider");

    public final NumberPath<Long> providerId = createNumber("providerId", Long.class);

    public QUsers(String variable) {
        super(Users.class, forVariable(variable));
    }

    public QUsers(Path<? extends Users> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsers(PathMetadata metadata) {
        super(Users.class, metadata);
    }

}

