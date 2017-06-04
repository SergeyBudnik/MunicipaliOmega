package acropollis.municipali.omega.common.dto.language;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Language {
    ENGLISH("ENGLISH"),
    RUSSIAN("RUSSIAN"),
    MACEDONIAN("MACEDONIAN"),
    ARABIC("ARABIC"),
    HEBREW("HEBREW");

    private String name;

    public static Language fromName(String name) {
        for (Language language : values()) {
            if (language.name.equals(name)) {
                return language;
            }
        }

        throw new RuntimeException();
    }
}
