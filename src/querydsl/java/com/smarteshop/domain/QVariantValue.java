package com.smarteshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVariantValue is a Querydsl query type for VariantValue
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QVariantValue extends EntityPathBase<VariantValue> {

    private static final long serialVersionUID = 166143943L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVariantValue variantValue = new QVariantValue("variantValue");

    public final com.smarteshop.common.entity.QBusinessObjectEntity _super = new com.smarteshop.common.entity.QBusinessObjectEntity(this);

    public final StringPath code = createString("code");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> createdDate = _super.createdDate;

    public final NumberPath<Integer> displayOrder = createNumber("displayOrder", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.ZonedDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath value = createString("value");

    public final QVariant variant;

    public QVariantValue(String variable) {
        this(VariantValue.class, forVariable(variable), INITS);
    }

    public QVariantValue(Path<? extends VariantValue> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVariantValue(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVariantValue(PathMetadata metadata, PathInits inits) {
        this(VariantValue.class, metadata, inits);
    }

    public QVariantValue(Class<? extends VariantValue> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.variant = inits.isInitialized("variant") ? new QVariant(forProperty("variant")) : null;
    }

}

