package uz.test.abitur.evenet_listeners.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.File;

@Getter
@RequiredArgsConstructor
public class FileGeneratedEvent {
    private final File file;
    private final Integer testSessionId;
}
