package com.talentmarket.KmongJpa.Item.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItem is a Querydsl query type for Item
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItem extends EntityPathBase<Item> {

    private static final long serialVersionUID = 1569053474L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItem item = new QItem("item");

    public final com.talentmarket.KmongJpa.global.entity.QBaseEntity _super = new com.talentmarket.KmongJpa.global.entity.QBaseEntity(this);

    public final StringPath category = createString("category");

    public final ListPath<com.talentmarket.KmongJpa.comment.domain.Comment, com.talentmarket.KmongJpa.comment.domain.QComment> comment = this.<com.talentmarket.KmongJpa.comment.domain.Comment, com.talentmarket.KmongJpa.comment.domain.QComment>createList("comment", com.talentmarket.KmongJpa.comment.domain.Comment.class, com.talentmarket.KmongJpa.comment.domain.QComment.class, PathInits.DIRECT2);

    public final StringPath contents = createString("contents");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final StringPath detail = createString("detail");

    public final NumberPath<Long> Id = createNumber("Id", Long.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Integer> stockQuantity = createNumber("stockQuantity", Integer.class);

    public final StringPath thumbnail = createString("thumbnail");

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.talentmarket.KmongJpa.user.domain.QUsers users;

    public final StringPath writer = createString("writer");

    public QItem(String variable) {
        this(Item.class, forVariable(variable), INITS);
    }

    public QItem(Path<? extends Item> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItem(PathMetadata metadata, PathInits inits) {
        this(Item.class, metadata, inits);
    }

    public QItem(Class<? extends Item> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.users = inits.isInitialized("users") ? new com.talentmarket.KmongJpa.user.domain.QUsers(forProperty("users")) : null;
    }

}

