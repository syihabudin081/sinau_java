package com.example.Binarfud.controller;

import com.example.Binarfud.model.Role;
import com.example.Binarfud.model.Users;
import com.example.Binarfud.payload.JwtResponseDTO;
import com.example.Binarfud.payload.LoginRequestDTO;
import com.example.Binarfud.payload.SignUpRequestDTO;
import com.example.Binarfud.payload.UserDTO;
import com.example.Binarfud.repository.RoleRepository;
import com.example.Binarfud.security.jwt.JwtUtils;
import com.example.Binarfud.security.service.UserDetailsImpl;
import com.example.Binarfud.service.MailService;
import com.example.Binarfud.service.OTPService;
import com.example.Binarfud.service.UsersService;
import com.example.Binarfud.utils.OTPUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsersService usersService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    MailService mailService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    OTPUtil otpUtil;

    @Autowired
    OTPService otpService;

    @PostMapping("/signin")
    public ResponseEntity<Map<String, Object>> authenticate(@RequestBody LoginRequestDTO loginRequestDto) {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        try {
            log.info("loginRequestDto: {}", loginRequestDto);
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),
                            loginRequestDto.getPassword()));
            log.info("authentication: {}", authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            JwtResponseDTO jwtResponse = new JwtResponseDTO(jwt, userDetails.getUsername(), roles);
            data.put("jwt", jwtResponse);
            response.put("status", "success");
            response.put("data", data);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (AuthenticationException e) {
            log.error("Authentication failed: {}", e.getMessage());
            response.put("status", "error");
            response.put("message", "Invalid username or password");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            log.error("An unexpected error occurred: {}", e.getMessage());
            response.put("status", "error");
            response.put("message", "An unexpected error occurred. Please try again later.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sendmail")
    public ResponseEntity<Map<String, Object>> sendMail() {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        log.info("Sending email...");
        try {
            mailService.sendEmail("syihabupnyk@gmail.com", "Test Subject", "Test Content");
            response.put("status", "success");
            response.put("message", "Email sent successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("An unexpected error occurred: {}", e.getMessage());
            response.put("status", "error");
            response.put("message", "An unexpected error occurred. Please try again later.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequestDTO signUpRequest) {
        if (usersService.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }

        if (usersService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        log.info("signUpRequest: {}", signUpRequest);

        // Menyimpan peran (role) yang baru
        Set<Role> savedRoles = new HashSet<>();
        for (Role role : signUpRequest.getRoles()) {
            Role existingRole = roleRepository.findByName(role.getName());
            if (existingRole == null) {
                existingRole = roleRepository.save(role);
            }
            savedRoles.add(existingRole);
        }

        // Membuat pengguna dengan peran yang sudah disimpan
        UserDTO user = new UserDTO(signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword(), savedRoles);

        usersService.saveUser(user);

        // Send OTP after user registration
        ResponseEntity<?> otpResponse = sendOTP(signUpRequest.getEmail());
        if (otpResponse.getStatusCode() != HttpStatus.OK) {
            // Handle error if OTP sending fails
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send OTP after user registration");
        }

        return ResponseEntity.ok("User registered successfully!");
    }

    @GetMapping("/oauth2/success")
    public ResponseEntity<Map<String, Object>> googleLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            // Autentikasi belum berhasil, mungkin belum selesai
            // Handle error, bisa jadi redirect kembali ke halaman login
            return new ResponseEntity("Authentication failed", HttpStatus.UNAUTHORIZED);
        }

        // Autentikasi berhasil, lanjutkan dengan logika Anda
        log.info("OAUTH2 Authentication: {}", authentication);
        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
        Collection<GrantedAuthority> authorities = new ArrayList<>(oidcUser.getAuthorities());
        authorities.add(new SimpleGrantedAuthority("ROLE_USER")); // TODO: Fix it. Get it from DB

        UserDetailsImpl modifiedUserDetails = UserDetailsImpl.build(oidcUser);
        OidcUser modifiedOidcUser = new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo());

        // Create a new Authentication object with the modified Principal
        Authentication modifiedAuthentication = new UsernamePasswordAuthenticationToken(
                modifiedOidcUser,
                oidcUser.getIdToken(),
                authorities
        );

        // Generate token using the modified authentication
        String jwt = jwtUtils.generateToken(modifiedAuthentication);

        // Extract user details from the modified authentication
        List<String> roles = modifiedAuthentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        // Prepare response
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("status", "success");
        Map<String, Object> data = new HashMap<>();
        JwtResponseDTO jwtResponse = new JwtResponseDTO(jwt, modifiedUserDetails.getUsername(), roles);
        data.put("jwt", jwtResponse);
        responseData.put("data", data);

        return new ResponseEntity<>(responseData, HttpStatus.CREATED);
    }

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOTP(@RequestParam String email) {
        String otp = otpUtil.generateOTP();
        otpService.saveOTP(email, otp);
        mailService.sendOTP(email, otp);
        return ResponseEntity.ok("OTP sent successfully");
    }


    @PostMapping("/validate-otp")
    public ResponseEntity<Map<String, Object>> validateOTP(@RequestParam String email, @RequestParam String otp) {
        boolean isValid = otpService.validateOTP(email, otp);
        Map<String, Object> response = new HashMap<>();
        if (isValid) {
            usersService.verifyUser(email);
            response.put("status", "success");
            response.put("message", "OTP is valid");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", "Invalid OTP");
            return ResponseEntity.badRequest().body(response);
        }
    }


    @GetMapping("/loginoauth")
    public String login() {
        System.out.println("Login");
        return "login";
    }
}
