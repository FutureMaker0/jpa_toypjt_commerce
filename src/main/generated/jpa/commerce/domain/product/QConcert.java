package jpa.commerce.domain.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QConcert is a Querydsl query type for Concert
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConcert extends EntityPathBase<Concert> {

    private static final long serialVersionUID = -129909315L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QConcert concert = new QConcert("concert");

    public final QProduct _super;

    public final StringPath actor = createString("actor");

    //inherited
    public final ListPath<jpa.commerce.domain.Category, jpa.commerce.domain.QCategory> categories;

    public final StringPath director = createString("director");

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final ListPath<UploadFile, QUploadFile> imageFileList;

    //inherited
    public final StringPath name;

    //inherited
    public final NumberPath<Integer> price;

    //inherited
    public final NumberPath<Integer> stockQuantity;

    // inherited
    public final QUploadFile uploadFile;

    public QConcert(String variable) {
        this(Concert.class, forVariable(variable), INITS);
    }

    public QConcert(Path<? extends Concert> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QConcert(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QConcert(PathMetadata metadata, PathInits inits) {
        this(Concert.class, metadata, inits);
    }

    public QConcert(Class<? extends Concert> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QProduct(type, metadata, inits);
        this.categories = _super.categories;
        this.id = _super.id;
        this.imageFileList = _super.imageFileList;
        this.name = _super.name;
        this.price = _super.price;
        this.stockQuantity = _super.stockQuantity;
        this.uploadFile = _super.uploadFile;
    }

}

