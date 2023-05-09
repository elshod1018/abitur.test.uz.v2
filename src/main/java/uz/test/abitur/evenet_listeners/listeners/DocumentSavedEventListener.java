package uz.test.abitur.evenet_listeners.listeners;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.multipart.MultipartFile;
import uz.test.abitur.domains.Document;
import uz.test.abitur.evenet_listeners.events.DocumentSavedEvent;
import uz.test.abitur.evenet_listeners.events.UserCreatedEvent;
import uz.test.abitur.services.DocumentService;
import uz.test.abitur.services.TwilioService;
import uz.test.abitur.services.firebase.FirebaseService;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class DocumentSavedEventListener {

    private final FirebaseService firebaseService;
    private final DocumentService documentService;

    @Async
    @TransactionalEventListener(value = DocumentSavedEvent.class)
    public void userCreatedEventListener(DocumentSavedEvent event) {
        MultipartFile file = event.getFile();
        Document document = event.getDocument();
        if (!Objects.isNull(file) && !Objects.isNull(document)) {
            String uploaded = firebaseService.upload(file, document.getGeneratedName());
            document.setFilePath(uploaded);
            documentService.update(document);
        }
    }
}
