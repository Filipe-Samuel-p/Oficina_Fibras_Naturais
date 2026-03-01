package oficina.fibrasnaturais.services;


import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImageUploadService {

    private final Storage storage;
    private final String bucketName;

    public ImageUploadService(Storage storage, @Value("${gcp.bucket.name}") String bucketName) {
        this.storage = storage;
        this.bucketName = bucketName;
    }

    public String uploadImage(MultipartFile file) {
        try {
            String originalFileName = file.getOriginalFilename();
            String extension = (originalFileName != null && originalFileName.contains("."))
                    ? originalFileName.substring(originalFileName.lastIndexOf("."))
                    : ".jpg";

            String fileName = UUID.randomUUID().toString() + extension;

            BlobId blobId = BlobId.of(bucketName, fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType(file.getContentType())
                    .build();

            storage.create(blobInfo, file.getBytes());

            return String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao fazer upload da imagem para o Google Cloud Storage", e);
        }
    }

    public void deleteImage(String imageUrl) {
        try {
            if (imageUrl == null || imageUrl.isBlank()) return;

            String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);

            BlobId blobId = BlobId.of(bucketName, fileName);
            boolean deleted = storage.delete(blobId);

            if (deleted) {
                System.out.println("Imagem antiga apagada com sucesso do bucket: " + fileName);
            }
        } catch (Exception e) {
            System.err.println("Aviso: Falha ao deletar imagem antiga do GCP: " + e.getMessage());
        }
    }
}
