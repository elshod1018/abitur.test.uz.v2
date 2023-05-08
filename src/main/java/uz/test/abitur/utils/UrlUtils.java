package uz.test.abitur.utils;

import org.springframework.stereotype.Component;


@Component
public interface UrlUtils {
    String BASE_URL = "/api/v2";

    /*Auth*/
    String BASE_AUTH_URL=BASE_URL+"/auth";

    /* News */
    String BASE_NEWS_URL=BASE_URL+"/news";
}
