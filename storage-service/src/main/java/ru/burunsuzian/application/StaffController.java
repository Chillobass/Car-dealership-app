package ru.burunsuzian.application;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.burunsuzian.application.responses.ManagerResponse;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/staff")
public class StaffController {
    public final Keycloak keycloak;

    @GetMapping("/manager")
    public ResponseEntity<List<ManagerResponse>> getManagers(){
        return new ResponseEntity<>(keycloak.realm("car-realm")
                .roles()
                .get("MANAGER")
                .getUserMembers()
                .stream()
                .map(user -> new ManagerResponse(
                        UUID.fromString(user.getId()),
                        user.getUsername(),
                        user.getFirstName(),
                        user.getLastName())).toList(), HttpStatus.OK);
    }
}
