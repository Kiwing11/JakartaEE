package pl.edu.pg.eti.kask.store.controller.servlet;

import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pl.edu.pg.eti.kask.store.knife.controller.api.CategoryController;
import pl.edu.pg.eti.kask.store.knife.controller.api.KnifeController;
import pl.edu.pg.eti.kask.store.knife.dto.PatchCategoryRequest;
import pl.edu.pg.eti.kask.store.knife.dto.PatchKnifeRequest;
import pl.edu.pg.eti.kask.store.knife.dto.PutCategoryRequest;
import pl.edu.pg.eti.kask.store.knife.dto.PutKnifeRequest;
import pl.edu.pg.eti.kask.store.service.PhotoService;
import pl.edu.pg.eti.kask.store.user.controller.api.UserController;
import pl.edu.pg.eti.kask.store.user.dto.PatchUserRequest;
import pl.edu.pg.eti.kask.store.user.dto.PutUserRequest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@WebServlet(urlPatterns = {
        ApiServlet.Paths.API + "/*"
})
@MultipartConfig(maxFileSize = 200 * 1024)
public class ApiServlet extends HttpServlet {
    private UserController userController;
    private KnifeController knifeController;
    private CategoryController categoryController;
    private PhotoService photoService;


    public static final class Paths {
        public static final String API = "/api";

    }
    public static final class Patterns {
        private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");
        public static final Pattern USER = Pattern.compile("/users/(%s)".formatted(UUID.pattern()));
        public static final Pattern USERS = Pattern.compile("/users/?");
        public static final Pattern USER_PHOTO = Pattern.compile("/users/(%s)/photo".formatted(UUID.pattern()));
        public static final Pattern KNIFE = Pattern.compile("/knives/(%s)".formatted(UUID.pattern()));
        public static final Pattern KNIVES = Pattern.compile("/knives/?");
        public static final Pattern CATEGORY = Pattern.compile("/categories/(%s)".formatted(UUID.pattern()));
        public static final Pattern CATEGORIES = Pattern.compile("/categories/?");
        public static final Pattern CATEGORY_KNIVES = Pattern.compile("/categories/(%s)/knives".formatted(UUID.pattern()));
        public static final Pattern USER_KNIVES = Pattern.compile("/users/(%s)/knives".formatted(UUID.pattern()));
    }

    private final Jsonb jsonb = JsonbBuilder.create();

    @Inject
    public ApiServlet(UserController userController, PhotoService photoService, KnifeController knifeController, CategoryController categoryController) {
        this.userController = userController;
        this.photoService = photoService;
        this.knifeController = knifeController;
        this.categoryController = categoryController;
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getMethod().equals("PATCH")){
            doPatch(request, response);
        }else {
            super.service(request, response);
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
                try (InputStream photoStream = photoService.getPhoto(fileName)) {
                    photoStream.transferTo(response.getOutputStream());
                } catch (IOException e) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Photo not found");
                }
                return;
            } else if (path.matches(Patterns.KNIVES.pattern())){
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(knifeController.getKnives()));
                return;
            } else if (path.matches(Patterns.KNIFE.pattern())) {
                response.setContentType("application/json");
                UUID id = extractUuid(Patterns.KNIFE, path);
                response.getWriter().write(jsonb.toJson(knifeController.getKnife(id)));
                return;
            } else if (path.matches(Patterns.CATEGORIES.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(categoryController.getCategories()));
                return;
            } else if (path.matches(Patterns.CATEGORY.pattern())) {
                response.setContentType("application/json");
                UUID id = extractUuid(Patterns.CATEGORY, path);
                response.getWriter().write(jsonb.toJson(categoryController.getCategory(id)));
                return;
            } else if (path.matches(Patterns.CATEGORY_KNIVES.pattern())) {
                response.setContentType("application/json");
                UUID id = extractUuid(Patterns.CATEGORY_KNIVES, path);
                response.getWriter().write(jsonb.toJson(knifeController.getKnivesByCategory(id)));
                return;
            } else if (path.matches(Patterns.USER_KNIVES.pattern())) {
                response.setContentType("application/json");
                UUID id = extractUuid(Patterns.USER_KNIVES, path);
                response.getWriter().write(jsonb.toJson(knifeController.getKnivesByUser(id)));
                System.out.println(knifeController.getKnivesByUser(id));
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
            } else if (path.matches(Patterns.USER_PHOTO.pattern())) {
                UUID id = extractUuid(Patterns.USER_PHOTO, path);
                try (InputStream is = request.getPart("photo").getInputStream()) {
                    String newPhotoPath = photoService.savePhoto(id, is);
                    userController.putUserPhoto(id, newPhotoPath);
                } catch (IOException e) {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
                }
                return;
            } else if(path.matches(Patterns.KNIFE.pattern())){
                UUID id = extractUuid(Patterns.KNIFE, path);
                knifeController.putKnife(id, jsonb.fromJson(request.getReader(), PutKnifeRequest.class));
                response.addHeader("Location", createUrl(request, Paths.API, "knives", id.toString()));
                return;
            } else if(path.matches(Patterns.CATEGORY.pattern())){
                UUID id = extractUuid(Patterns.CATEGORY, path);
                categoryController.putCategory(id, jsonb.fromJson(request.getReader(), PutCategoryRequest.class));
                response.addHeader("Location", createUrl(request, Paths.API, "categories", id.toString()));
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
                try {
                    photoService.deletePhoto(photoToDelete);
                    userController.deleteUserPhoto(id);
                } catch (IOException e) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Photo not found in database file system.");
                }
                return;
            }else if(path.matches(Patterns.KNIFE.pattern())){
                UUID id = extractUuid(Patterns.KNIFE, path);
                knifeController.deleteKnife(id);
                return;
            }else if(path.matches(Patterns.CATEGORY.pattern())){
                UUID id = extractUuid(Patterns.CATEGORY, path);
                categoryController.deleteCategory(id);
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
                try (InputStream is = request.getPart("photo").getInputStream()) {
                    String newPhotoPath = photoService.savePhoto(id, is);
                    String oldPhotoPath = userController.getUserPhoto(id);
                    photoService.deletePhoto(oldPhotoPath);
                    userController.patchUserPhoto(id, newPhotoPath);
                } catch (IOException e) {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
                }
                return;
            }else if(path.matches(Patterns.KNIFE.pattern())){
                UUID id = extractUuid(Patterns.KNIFE, path);
                knifeController.patchKnife(id, jsonb.fromJson(request.getReader(), PatchKnifeRequest.class));
                return;
            }else if(path.matches(Patterns.CATEGORY.pattern())){
                UUID id = extractUuid(Patterns.CATEGORY, path);
                categoryController.patchCategory(id, jsonb.fromJson(request.getReader(), PatchCategoryRequest.class));
                return;
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
