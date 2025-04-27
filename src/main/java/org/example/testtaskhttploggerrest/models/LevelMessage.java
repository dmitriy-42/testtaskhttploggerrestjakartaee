package org.example.testtaskhttploggerrest.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LevelMessage {

    ERROR(2),
    WARNING(1),
    INFO(0),
    DEBUG(-1),
    TRACE(-2);

    private final int levelCritical;
}
