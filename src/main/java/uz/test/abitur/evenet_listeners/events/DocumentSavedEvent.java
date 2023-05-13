package uz.test.abitur.evenet_listeners.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import uz.test.abitur.domains.Document;

import java.io.File;

@Getter
@RequiredArgsConstructor
public final class DocumentSavedEvent {
    private final File file;
    private final Document document;
}
