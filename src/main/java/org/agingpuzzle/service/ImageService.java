package org.agingpuzzle.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.agingpuzzle.model.Image;
import org.agingpuzzle.model.WithImage;
import org.agingpuzzle.repo.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${image.dir}")
    private String imageDir;

    @Autowired
    private ImageRepository imageRepository;

    public void saveImage(WithImage entity, MultipartFile file, String source) throws IOException {
        Image image = Optional.ofNullable(entity.getImage()).orElse(new Image());
        image.setSource(source);

        if (!file.isEmpty()) {
            String outputPath = String.format("%d_%s", System.currentTimeMillis(), file.getOriginalFilename());
            Files.copy(file.getInputStream(), Paths.get(imageDir, outputPath));
            image.setPath(outputPath);
            imageRepository.save(image);
            log.info("Saved image with {} with id={}", image.getPath(), image.getId());
        }
        if (image.getPath() != null) {
            entity.setImage(image);
        }
    }
}
