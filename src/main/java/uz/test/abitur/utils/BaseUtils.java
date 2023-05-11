package uz.test.abitur.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Service
@RequiredArgsConstructor
public class BaseUtils {
    private final Random random;
    public static final ConcurrentHashMap<String, Integer> QUESTION_COUNT_MAP = new ConcurrentHashMap<>() {{
        put("mainSubject", 30);
        put("mandatorySubject", 10);
    }};

    public String generateCode() {
        return String.valueOf(random.nextInt(100_000, 999_999));
    }

    public static String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public static String generateFileName() {
        return UUID.randomUUID().toString();
    }
}
