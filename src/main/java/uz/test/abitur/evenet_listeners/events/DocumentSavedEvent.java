package uz.test.abitur.evenet_listeners.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import uz.test.abitur.domains.Document;

@Getter
@RequiredArgsConstructor
public final class DocumentSavedEvent {
    private final MultipartFile file;
    private final Document document;
}
