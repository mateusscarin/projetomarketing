package com.fatec.quintosemestre.projetomarketing.service.impl;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.fatec.quintosemestre.projetomarketing.model.dto.LoginDTO;
import com.fatec.quintosemestre.projetomarketing.service.LoginService;
import com.fatec.quintosemestre.projetomarketing.service.util.ApiResponse;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private JwtEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<Object> login(LoginDTO dto) {

        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(dto.getCpf(),
                dto.getSenha());
        Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);

        Instant now = Instant.now();
        long expiry = 36000L;

        String scope = authenticationResponse.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(authenticationResponse.getName())
                .claim("scope", scope)
                .claim("principal", dto.getCpf())
                .build();

        String token = encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("usuarioLogado", authenticationResponse.getName());
        response.put("permissoes", authenticationResponse.getAuthorities().stream().map(GrantedAuthority::getAuthority));

        return ResponseEntity.ok(new ApiResponse<>(response));
    }

}
