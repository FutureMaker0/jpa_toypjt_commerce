package jpa.commerce.file;

import jpa.commerce.domain.product.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    // 파일이 저장되어 있는 fullPath를 리턴하는 메서드
    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);

        // 최종적으로 디렉토리/파일네임 풀패스 만들어지고 멀티파트파일로 전송되어 저장이 될 것이다.
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new UploadFile(originalFilename, storeFileName);
    }

    private String createStoreFileName(String originalFilename) {
        // 확장자 추출, 별도 메서드로 추출
        String ext = extractExt(originalFilename);
        // 사용자가 제공한 오리지날 파일명을 갖고 어떻게 가공할지 생각해야 한다. uuid 활용하되 확장자만 떼오도록 하자.
        String uuid = UUID.randomUUID().toString();
        // uuid에 확장자까지 붙은, 서버 내부적으로 관리할 최종 파일네임
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
