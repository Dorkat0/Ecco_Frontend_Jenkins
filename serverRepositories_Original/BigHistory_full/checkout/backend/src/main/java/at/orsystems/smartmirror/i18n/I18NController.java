package at.orsystems.smartmirror.i18n;

import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * This is the @{@link RestController} which is responsible for providing the i18n files.
 *
 * @author Michael Ratzenböck
 * @since 2021
 */
@RestController
public class I18NController {
    private final ResourceLoader resourceLoader;

    public I18NController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/i18n/{language}")
    public String getLocalization(@PathVariable final String language) {
        final var resource = resourceLoader.getResource("classpath:i18n/" + language);

        try {
            final var inputStream = resource.getInputStream();
            final var data = FileCopyUtils.copyToByteArray(inputStream);
            return new String(data, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("%s does not exist in i18n folder!\n", language));

        }
    }
}
