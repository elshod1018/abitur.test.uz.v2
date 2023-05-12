package uz.test.abitur.evenet_listeners.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;
import uz.test.abitur.domains.TestSession;
import uz.test.abitur.dtos.pdf.ParagraphDTO;
import uz.test.abitur.dtos.pdf.TestHistoryPDFGenerateDTO;

import java.util.List;

@Getter
@RequiredArgsConstructor
public final class TestHistoryFinishedEvent {

    private final List<TestHistoryPDFGenerateDTO> forPDF;
    private final ParagraphDTO paragraphDTO;
    private final Integer testSessionId;

}
