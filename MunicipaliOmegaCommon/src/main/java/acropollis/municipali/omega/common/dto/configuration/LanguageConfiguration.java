package acropollis.municipali.omega.common.dto.configuration;

import acropollis.municipali.omega.common.dto.language.Language;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class LanguageConfiguration {
    private Language defaultLanguage;
    private List<Language> supportedLanguages;

    public static LanguageConfiguration fromStrings(
            String defaultLanguage,
            List<String> supportedLanguages
    ) {
        return new LanguageConfiguration(
                Language.fromName(defaultLanguage),
                supportedLanguages
                        .stream()
                        .map(Language::fromName)
                        .collect(Collectors.toList())
        );
    }
}
