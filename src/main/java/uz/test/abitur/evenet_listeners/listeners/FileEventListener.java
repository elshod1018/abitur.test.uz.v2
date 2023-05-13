package uz.test.abitur.evenet_listeners.listeners;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import uz.test.abitur.domains.Document;
import uz.test.abitur.domains.TestHistory;
import uz.test.abitur.evenet_listeners.events.DocumentSavedEvent;
import uz.test.abitur.evenet_listeners.events.FileGeneratedEvent;
import uz.test.abitur.evenet_listeners.events.FileUploadedEvent;
import uz.test.abitur.services.DocumentService;
import uz.test.abitur.services.TestHistoryService;
import uz.test.abitur.services.firebase.FirebaseService;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FileEventListener {

    private final FirebaseService firebaseService;
    private final DocumentService documentService;
    private final TestHistoryService testHistoryService;

    @Async
    @EventListener(value = FileUploadedEvent.class)
    public void fileUploadedEventListener(FileUploadedEvent event) {
        MultipartFile file = event.getFile();
        Document document = event.getDocument();
        if (!Objects.isNull(file) && !Objects.isNull(document)) {
            String uploaded = firebaseService.upload(file, document.getGeneratedName());
            document.setFilePath(uploaded);
            documentService.update(document);
        }
    }

    @Async
    @EventListener(value = DocumentSavedEvent.class)
    public void documentSavedEventListener(DocumentSavedEvent event) throws IOException {
        File file = event.getFile();
        Document document = event.getDocument();
        if (!Objects.isNull(file) && !Objects.isNull(document)) {
            String uploaded = firebaseService.upload(file, document.getGeneratedName());
            document.setFilePath(uploaded);
            documentService.update(document);
        }
    }
    @Async
    @EventListener(value = FileGeneratedEvent.class)
    public void fileCreatedEventListener(FileGeneratedEvent event) {
        File file = event.getFile();
        Document document = documentService.saveFileDocument(file);

        TestHistory testHistory = testHistoryService.findByTestSessionId(event.getTestSessionId());
        if (!Objects.isNull(testHistory)) {
            testHistory.setDocumentId(document.getGeneratedName());
            testHistoryService.update(testHistory);
        }
    }
}
