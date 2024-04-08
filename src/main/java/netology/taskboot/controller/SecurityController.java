package netology.taskboot.controller;

import netology.taskboot.service.SystemProfile;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/")
public class SecurityController {

    private final SystemProfile profile;

    SecurityController(SystemProfile profile) {
        this.profile = profile;
    }

    /*
    * один из методов возвращает значения только для пользователей
    * с ролью "READ" (используйте @Secured);
    *
    */
    @GetMapping("hello-read")
    @Secured("READ")
    public String hiReadMethod() {
        return "Welcome READ";
    }

    /*
    * один из методов возвращает значения только для пользователей
    * с ролью "WRITE" (используйте @RolesAllowed);
    */
    @GetMapping("hello-write")
    @RolesAllowed("WRITE")
    public String hiWriteMethod() {
        return "Welcome WRITE";
    }

    /*
    * один из методов возвращает значения только для пользователей
    * с ролью "DELETE" (используйте @RolesAllowed);
    */
    @GetMapping("hello-delete")
    @RolesAllowed("DELETE")
    public String hiDeleteMethod() {
        return "Welcome DElETE";
    }

    /*
    * один из методов возвращает значения,
    * если у пользователя есть хотя бы одна из ролей из "WRITE",
    * "DELETE" (используйте pre/post аннотации);
    */
    @GetMapping("write-or-delete")
    @PreAuthorize("hasRole('WRITE') or hasRole('DELETE')")
    public String hiAllMethod4() {
        return "Welcome admin";
    }

    /*
    * один из методов, который принимает в качестве query-параметра имя пользователя (username),
    * должен возвращает значения,
    * только если у пользователя username совпадает с именем пользователя в вашем объекте Authentication,
    * который Spring security сохраняет в SecurityContextHolder после успешной аутентификации.
    */
    @PreAuthorize("#name == authentication.principal.username")
    @GetMapping("user")
    public String getUserName(@RequestParam String name) {
        return "userName " + name;
    }
}
