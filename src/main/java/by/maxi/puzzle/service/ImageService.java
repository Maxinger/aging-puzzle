package by.maxi.puzzle.service;

import by.maxi.puzzle.model.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${image.dir}")
    private String imageDir;

    public void saveImage(MultipartFile file, Image image) throws IOException {
        String outputPath = String.format("%d_%s", System.currentTimeMillis(), file.getOriginalFilename());
        Files.copy(file.getInputStream(), Paths.get(imageDir, outputPath));
        image.setPath(outputPath);
    }
}
