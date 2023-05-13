package uz.test.abitur.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.web.multipart.MultipartFile;
import uz.test.abitur.domains.Document;
import uz.test.abitur.evenet_listeners.events.DocumentSavedEvent;
import uz.test.abitur.evenet_listeners.events.FileUploadedEvent;
import uz.test.abitur.repositories.DocumentRepository;
import uz.test.abitur.services.firebase.FirebaseService;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static uz.test.abitur.utils.BaseUtils.generateFileName;
import static uz.test.abitur.utils.BaseUtils.getExtension;


@Service
@RequiredArgsConstructor
public class DocumentService {
    private final FirebaseService firebaseService;
    private final DocumentRepository documentRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public Document saveMultipartDocument(MultipartFile file) throws IOException {
        long size = file.getSize();
        if (size > 5 * 1024 * 1024) {
            throw new IOException("File size must be less than 5MB");
        }
        String originalFilename = file.getOriginalFilename();
        String extension = getExtension(Objects.requireNonNull(originalFilename));
        String generatedName = generateFileName() + extension;
        Document document = Document.childBuilder()
                .originalName(originalFilename)
                .generatedName(generatedName)
                .extension(extension)
                .contentType(file.getContentType())
                .size(size)
                .build();
        documentRepository.save(document);
        applicationEventPublisher.publishEvent(new FileUploadedEvent(file, document));
        return document;
    }

    public Document saveFileDocument(File file) {
        String originalFilename = file.getName();
        String extension = getExtension(Objects.requireNonNull(originalFilename));
        String generatedName = generateFileName() + extension;
        Document document = Document.childBuilder()
                .originalName(originalFilename)
                .generatedName(generatedName)
                .extension(extension)
                .contentType(MimeType.valueOf("application/pdf").getType())
                .size(file.length())
                .build();
        documentRepository.save(document);
        applicationEventPublisher.publishEvent(new DocumentSavedEvent(file, document));
        return document;
    }

    public File downloadFile(String generatedName) throws IOException {
        return firebaseService.download(generatedName);
    }
    public Document update(Document document) {
        return documentRepository.save(document);
    }

    public Document getById(Integer id) {
        return documentRepository.findDocumetById(id)
                .orElseThrow(() -> new RuntimeException("Document Not found by id: '%s'".formatted(id)));
    }

    public Document findByGeneratedName(String name) {
        return documentRepository.findByGeneratedName(name)
                .orElseThrow(() -> new RuntimeException("Document Not found by name: '%s'".formatted(name)));
    }
}
