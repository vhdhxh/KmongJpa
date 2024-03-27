package com.talentmarket.KmongJpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.talentmarket.KmongJpa.Item.domain.Itemcount;


/**
 * QItemcount is a Querydsl query type for Itemcount
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItemcount extends EntityPathBase<Itemcount> {

    private static final long serialVersionUID = -994206543L;

    public static final QItemcount itemcount1 = new QItemcount("itemcount1");

    public final NumberPath<Long> Id = createNumber("Id", Long.class);

    public final NumberPath<Long> itemcount = createNumber("itemcount", Long.class);

    public QItemcount(String variable) {
        super(Itemcount.class, forVariable(variable));
    }

    public QItemcount(Path<? extends Itemcount> path) {
        super(path.getType(), path.getMetadata());
    }

    public QItemcount(PathMetadata metadata) {
        super(Itemcount.class, metadata);
    }

}

