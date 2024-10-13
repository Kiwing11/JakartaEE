package pl.edu.pg.eti.kask.store.controller.servlet;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import pl.edu.pg.eti.kask.store.controller.servlet.exception.BadRequestException;
import pl.edu.pg.eti.kask.store.user.controller.api.UserController;
import pl.edu.pg.eti.kask.store.user.dto.PatchUserRequest;
import pl.edu.pg.eti.kask.store.user.dto.PutUserRequest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = {
        ApiServlet.Paths.API + "/*"
})
@MultipartConfig(maxFileSize = 200 * 1024)
public class ApiServlet extends HttpServlet {
    private UserController userController;
    String photoDirectory;
    Path photoPath;


    public static final class Paths {
        public static final String API = "/api";

    }
    public static final class Patterns {
        private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");
        public static final Pattern USER = Pattern.compile("/users/(%s)".formatted(UUID.pattern()));
        public static final Pattern USERS = Pattern.compile("/users/?");
        public static final Pattern USER_PHOTO = Pattern.compile("/users/(%s)/photo".formatted(UUID.pattern()));
    }

    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getMethod().equals("PATCH")){
            doPatch(request, response);
        }else {
            super.service(request, response);
        }
    }

    @Override
    public void init() throws ServletException {
        photoDirectory = getServletContext().getInitParameter("photoDirectory");
        System.out.println("photoDirectory: " + photoDirectory);
        photoPath = java.nio.file.Paths.get(photoDirectory);
        System.out.println("photoPath: " + photoPath);
        super.init();
        userController = (UserController) getServletContext().getAttribute("userController");
        try {
            Files.createDirectories(photoPath);
        } catch (IOException e) {
            throw new ServletException("Could not create path directory", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)){
            if (path.matches(Patterns.USERS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(userController.getUsers()));
                return;
            } else if (path.matches(Patterns.USER.pattern())) {
                response.setContentType("application/json");
                UUID id = extractUuid(Patterns.USER, path);
                response.getWriter().write(jsonb.toJson(userController.getUser(id)));
                return;
            } else if (path.matches(Patterns.USER_PHOTO.pattern())){
                response.setContentType("image/jpeg");
                UUID id = extractUuid(Patterns.USER_PHOTO, path);
                String fileName = userController.getUserPhoto(id);
                System.out.println("fileName: " + fileName);
                Path pathToPhoto = java.nio.file.Paths.get(photoDirectory, userController.getUserPhoto(id));
                System.out.println("pathToPhoto: " + pathToPhoto);

                if(!userController.getUserPhoto(id).isEmpty() && Files.exists(pathToPhoto)){
                    Files.copy(pathToPhoto, response.getOutputStream());
                    response.getOutputStream().flush();
                }

//                response.setContentLength(photo.length);
//                response.getOutputStream().write(photo);
                return;
            }
        }
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)){
            if (path.matches(Patterns.USER.pattern())) {
                UUID id = extractUuid(Patterns.USER, path);
                userController.putUser(id, jsonb.fromJson(request.getReader(), PutUserRequest.class));
                response.addHeader("Location", createUrl(request, Paths.API, "users", id.toString()));
                return;
            } else if(path.matches(Patterns.USER_PHOTO.pattern())){
                UUID id = extractUuid(Patterns.USER_PHOTO, path);
                String newPhotoPath = createUniqueFileName(id);
                Path newPhotoToPath = java.nio.file.Paths.get(photoDirectory, newPhotoPath);
                System.out.println("newPhotoPath: " + newPhotoToPath);
                try {
                    InputStream is = request.getPart("photo").getInputStream();
                    userController.putUserPhoto(id, newPhotoPath);
                    Files.copy(is, newPhotoToPath);
                    System.out.println("Photo uploaded: " + newPhotoPath);
                }catch (IOException e) {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
                }
                return;
            }
        }
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if(Paths.API.equals(servletPath)){
            if(path.matches(Patterns.USER.pattern())){
                UUID id = extractUuid(Patterns.USER, path);
                userController.deleteUser(id);
                return;
            }else if(path.matches(Patterns.USER_PHOTO.pattern())){
                UUID id = extractUuid(Patterns.USER_PHOTO, path);
                String photoToDelete = userController.getUserPhoto(id);
                System.out.println("photoToDelete: " + photoToDelete);
                Path photoToDeletePath = java.nio.file.Paths.get(photoDirectory, photoToDelete);
                if(Files.exists(photoToDeletePath)){
                    userController.deleteUserPhoto(id);
                    Files.delete(photoToDeletePath);
                }else{
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Photo not found in database file system.");
                }
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if(Paths.API.equals(servletPath)){
            if(path.matches(Patterns.USER.pattern())){
                UUID id = extractUuid(Patterns.USER, path);
                userController.patchUser(id, jsonb.fromJson(request.getReader(), PatchUserRequest.class));
                return;
            }else if(path.matches(Patterns.USER_PHOTO.pattern())) {
                UUID id = extractUuid(Patterns.USER_PHOTO, path);
                String newPhotoPath = createUniqueFileName(id);
                Path newPhotoToPath = java.nio.file.Paths.get(photoDirectory, newPhotoPath);
                System.out.println("PhotoToUpdate: " + newPhotoToPath);
                if(!userController.getUserPhoto(id).isEmpty() || Files.exists(newPhotoToPath)){
                    try {
                        //Delete old photo from db
                        String oldPhotoPath = userController.getUserPhoto(id);
                        Path oldPhotoToDeletePath = java.nio.file.Paths.get(photoDirectory, oldPhotoPath);
                        if(Files.exists(oldPhotoToDeletePath)){
                            Files.delete(oldPhotoToDeletePath);
                        }

                        InputStream is = request.getPart("photo").getInputStream();
                        Files.copy(is, newPhotoToPath, StandardCopyOption.REPLACE_EXISTING);
                        userController.patchUserPhoto(id, newPhotoPath);
                        System.out.println("Photo updated: " + newPhotoPath);
                    }catch (IOException e) {
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
                    }
                    return;
                }
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private static UUID extractUuid(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return UUID.fromString(matcher.group(1));
        }
        throw new IllegalArgumentException("No UUID in path.");
    }

    private String parseRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }

    private String createUniqueFileName(UUID photoOwnerId){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = now.format(formatter);
        return photoOwnerId.toString() + "-" + timestamp + ".jpg";
    }

    public static String createUrl(HttpServletRequest request, String... paths) {
        StringBuilder builder = new StringBuilder();
        builder.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath());
        for (String path : paths) {
            builder.append("/")
                    .append(path, path.startsWith("/") ? 1 : 0, path.endsWith("/") ? path.length() - 1 : path.length());
        }
        return builder.toString();
    }

}
