package com.example.springmvcrest.utils;

public class FileUploadUtil {
/*
    private static final String UPLOAD_DIR = "upload/";

    public static String saveFile(
                                MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()){
            throw new ImageStoreNotFoundException();
        }
        if (isSupportedContentType(Objects.requireNonNull(multipartFile.getContentType()))){
            Path uploadPath = Paths.get(UPLOAD_DIR);
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());


            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = multipartFile.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

            } catch (IOException ioe) {
                throw new IOException("Could not save image file: " + fileName, ioe);
            }
            return fileName;

        }
        return null;
    }

    private static boolean  isSupportedContentType (String contentType){
        return contentType.equals("image/png")
                || contentType.equals("image/jpg")
                || contentType.equals("image/jpeg")
                || contentType.equals("image/*");
    }*/
}
