package jpa.commerce.domain.product;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Data
@Entity
@Getter
@Setter
//@Embeddable
public class UploadFile {

    @Id
    @GeneratedValue
    //@Column(name = "uploadFile_id")
    private Long id;

    @Column(name = "uploadFileName")
    private String uploadFileName;
    private String storeFileName;

    protected UploadFile() {

    }

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
