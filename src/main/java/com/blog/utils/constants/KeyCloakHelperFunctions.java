package com.blog.utils.constants;

import java.util.function.Function;
import org.keycloak.representations.idm.CredentialRepresentation;

public final class KeyCloakHelperFunctions {
  KeyCloakHelperFunctions() {}

  public static final Function<String, CredentialRepresentation> getKeyCloakPasswordCred =
      (password) -> {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        return credentialRepresentation;
      };
}
