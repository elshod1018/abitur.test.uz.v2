package uz.test.abitur.evenet_listeners.listeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import uz.test.abitur.domains.Document;
import uz.test.abitur.domains.TestHistory;
import uz.test.abitur.domains.TestSession;
import uz.test.abitur.dtos.pdf.ParagraphDTO;
import uz.test.abitur.dtos.pdf.TestHistoryPDFGenerateDTO;
import uz.test.abitur.evenet_listeners.events.*;
import uz.test.abitur.services.DocumentService;
import uz.test.abitur.services.SolveQuestionService;
import uz.test.abitur.services.TestHistoryService;
import uz.test.abitur.services.pdf.GeneratePDF;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestEventListener {
    private final SolveQuestionService solveQuestionService;
    private final TestHistoryService testHistoryService;
    private final GeneratePDF generatePDF;
    private final DocumentService documentService;

    @Async
    @EventListener(value = TestSessionCreatedEvent.class)
    public void testSessionCreatedEventListener(TestSessionCreatedEvent event) {
        TestSession testSession = event.getTestSession();
        if (!Objects.isNull(testSession)) {
            solveQuestionService.create(testSession);
            testHistoryService.create(testSession);
        }
    }

    @Async
    @EventListener(value = TestSessionFinishedEvent.class)
    public void testSessionFinishedEventListener(TestSessionFinishedEvent event) {
        TestSession testSession = event.getTestSession();
        if (!Objects.isNull(testSession)) {
            testHistoryService.finish(testSession);
        }
    }

    @Async
    @EventListener(value = TestHistoryFinishedEvent.class)
    public CompletableFuture<FileGeneratedEvent> TestHistoryFinishedEventListener(TestHistoryFinishedEvent event) throws IOException {
        List<TestHistoryPDFGenerateDTO> forPDF = event.getForPDF();
        ParagraphDTO paragraphDTO = event.getParagraphDTO();
        Integer testSessionId = event.getTestSessionId();
        if (!Objects.isNull(forPDF) && !Objects.isNull(paragraphDTO) && !Objects.isNull(testSessionId)) {
            File file = generatePDF.generatePDF(paragraphDTO, forPDF);
            return CompletableFuture.completedFuture(new FileGeneratedEvent(file, testSessionId));
        }
        return null;
    }
}
