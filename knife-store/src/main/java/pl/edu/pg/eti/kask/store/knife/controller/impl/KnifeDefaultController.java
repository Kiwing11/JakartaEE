package pl.edu.pg.eti.kask.store.knife.controller.impl;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBAccessException;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.store.factory.DtoFunctionFactory;
import pl.edu.pg.eti.kask.store.knife.controller.api.KnifeController;
import pl.edu.pg.eti.kask.store.knife.dto.GetKnifeResponse;
import pl.edu.pg.eti.kask.store.knife.dto.GetKnivesResponse;
import pl.edu.pg.eti.kask.store.knife.dto.PatchKnifeRequest;
import pl.edu.pg.eti.kask.store.knife.dto.PutKnifeRequest;
import pl.edu.pg.eti.kask.store.knife.service.CategoryService;
import pl.edu.pg.eti.kask.store.knife.service.KnifeService;
import pl.edu.pg.eti.kask.store.user.entity.UserRoles;

import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
@RolesAllowed(UserRoles.USER)
public class KnifeDefaultController implements KnifeController {
    private KnifeService service;

    private CategoryService categoryService;

    private final DtoFunctionFactory factory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public KnifeDefaultController(DtoFunctionFactory factory, @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setService(KnifeService service) {
        this.service = service;
    }

    @EJB
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public GetKnivesResponse getKnives() {
        try {
            return factory.knivesToResponse().apply(service.findAllForCallerPrincipal());
        } catch (EJBAccessException e) {
            log.log(Level.WARNING, e.getMessage(), e);
            throw new ForbiddenException(e.getMessage());
        }
    }


    @Override
    public GetKnifeResponse getKnife(UUID id) {
        try{
            if(service.find(id).isPresent()){
                return service.findForCallerPrincipal(id).map(factory.knifeToResponse()).orElseThrow(() -> new NotFoundException());
            }
            throw new NotFoundException();

        } catch (EJBAccessException e){
            log.log(Level.WARNING, e.getMessage(), e);
            throw new ForbiddenException(e.getMessage());
        }

//        return service.find(id)
//                .map(factory.knifeToResponse())
//                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void deleteKnife(UUID id) {
        try {
            service.find(id)
                    .ifPresentOrElse(service::delete, () -> {
                        throw new NotFoundException();
                    });
        } catch (EJBAccessException e) {
            log.log(Level.WARNING, e.getMessage(), e);
            throw new ForbiddenException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public void putKnife(UUID id, PutKnifeRequest request){
        try{
            service.create(factory.requestToKnife().apply(id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(KnifeController.class, "getKnife")
                    .build(id)
                    .toString()
            );
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e);
        }
    }

    @Override
    @SneakyThrows
    public void putKnife(UUID id, UUID userId, PutKnifeRequest request){
        try{
            service.createForCallerPrincipal(factory.requestToKnife().apply(id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(KnifeController.class, "getKnife")
                    .build(id)
                    .toString()
            );
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e);
        }
    }

    @Override
    @SneakyThrows
    public void putKnifeByCategory(UUID categoryId, UUID knifeId, PutKnifeRequest request){
        try{
            categoryService.find(categoryId).ifPresentOrElse(
                    category -> {
                        request.setCategory(categoryId);
                        service.createForCallerPrincipal(factory.requestToKnife().apply(knifeId, request));
                        response.setHeader("Location", uriInfo.getBaseUriBuilder()
                                .path(KnifeController.class, "getKnife")
                                .build(knifeId)
                                .toString()
                        );
                        throw new WebApplicationException(Response.Status.CREATED);
                    },
                    () -> {
                        throw new NotFoundException();
                    }
            );
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e);
        }
    }

    @Override
    public void patchKnife(UUID id, PatchKnifeRequest request){
        try {
            service.find(id)
                    .ifPresentOrElse(entity -> {
                        service.update(factory.updateKnife().apply(entity, request));
                    }, () -> {
                        throw new NotFoundException();
                    });
        } catch (EJBAccessException e) {
            log.log(Level.WARNING, e.getMessage(), e);
            throw new ForbiddenException(e.getMessage());
        }
    }

    @Override
    public GetKnivesResponse getKnivesByCategory(UUID id) {
        return service.findAllByCategory(id)
                .map(factory.knivesToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetKnivesResponse getKnivesByUser(UUID id) {
        return service.findAllByUser(id)
                .map(factory.knivesToResponse())
                .orElseThrow(NotFoundException::new);
    }
}
