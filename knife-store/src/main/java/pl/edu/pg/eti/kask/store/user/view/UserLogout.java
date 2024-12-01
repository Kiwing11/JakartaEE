package pl.edu.pg.eti.kask.store.user.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
@ViewScoped
@Named
public class UserLogout {
    private final HttpServletRequest request;
    @Inject
    public UserLogout(HttpServletRequest request) {
        this.request = request;
    }
    @SneakyThrows
    public String logoutAction() {
        request.logout();//Session invalidate can possibly not work with JASPIC.
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}
