package com.hyperaccess.gestiondiplome.services.impl;

import com.hyperaccess.gestiondiplome.config.JwtService;
import com.hyperaccess.gestiondiplome.dto.AuthenticationRequest;
import com.hyperaccess.gestiondiplome.dto.AuthenticationResponse;
import com.hyperaccess.gestiondiplome.dto.LightUtilisateurDto;
import com.hyperaccess.gestiondiplome.dto.UtilisateurDto;
import com.hyperaccess.gestiondiplome.entities.Role;
import com.hyperaccess.gestiondiplome.entities.Utilisateur;
import com.hyperaccess.gestiondiplome.exception.EntityNotFoundException;
import com.hyperaccess.gestiondiplome.exception.InvalidEntityException;
import com.hyperaccess.gestiondiplome.repository.RoleRepository;
import com.hyperaccess.gestiondiplome.repository.UtilisateurRepository;
import com.hyperaccess.gestiondiplome.services.UtilisateurService;
import com.hyperaccess.gestiondiplome.validator.ObjectsValidator;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import jakarta.servlet.ServletContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;



@Service
@RequiredArgsConstructor
@Slf4j
public class UtilisateurServiceImpl implements UtilisateurService {
    private static final String ROLE_USER = "ROLE_USER";
    public static final String PATH = "/Photos/";
    private final RoleRepository roleRepository;

    private final ObservationRegistry registry;
    private final UtilisateurRepository utilisateurRepository;
    private final ObjectsValidator<UtilisateurDto> validator;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final ServletContext context;



    @Override
    public Integer save(UtilisateurDto utilisateurDto) {
        validator.validate(utilisateurDto);

        Utilisateur utilisateur =  UtilisateurDto.toEntity(utilisateurDto);
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        utilisateur.setActive(true);
        Utilisateur utilisateurEnreg = utilisateurRepository.save(utilisateur);

        return Observation.createNotStarted("saveUtilisateur",registry)
                                .observe(utilisateurEnreg::getId);
    }



    @Override
    public UtilisateurDto findById(Integer id) {
        if (isNull(id)){
            log.error("l'ID est null");
            return null;
        }

        return  Observation.createNotStarted("findByIdUtilisateur",registry)
                .observe(() -> utilisateurRepository.findById(id)
                        .map(UtilisateurDto::toDto)
                        .orElseThrow(() -> new EntityNotFoundException("Utilisateur n'est pas valide " + id)));
    }

    @Override
    public List<UtilisateurDto> findAll() {
        return Observation.createNotStarted("findAllUtilisateur",registry)
                .observe(() -> utilisateurRepository.findAll(Sort.by("nom").ascending()).stream()
                .map(UtilisateurDto::toDto)
                .toList());
    }

    @Override
    public void delete(Integer id) {
       if (!utilisateurRepository.existsById(id)){
           log.error("l'ID est null");
           throw new InvalidEntityException("Utilisateur n'est pas valide "+ id);
       }

       utilisateurRepository.deleteById(id);

    }

    @Override
    public UtilisateurDto findUtilisateurByNom(String nom) {
        if (!StringUtils.hasLength(nom)){
            log.error("Le nom est null");
            return null;
        }

        return Observation.createNotStarted("findUtilisateurByNom",registry)
                .observe(() -> utilisateurRepository.findUtilisateursByNom(nom)
                        .map(UtilisateurDto::toDto)
                        .orElseThrow(() -> new EntityNotFoundException("Utilisateur n'est pas valide " + nom)));
    }

    @Override
    @Transactional
    public AuthenticationResponse create(MultipartFile file, UtilisateurDto utilisateurDto) {
        validator.validate(utilisateurDto);

        Utilisateur utilisateur =  UtilisateurDto.toEntity(utilisateurDto);
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));

        utilisateur.setRole(
                findByRole(utilisateurDto.roleId())
        );

        var fileDir = new File(context.getRealPath(PATH));
        if (!fileDir.exists())
            fileDir.mkdir();

        String filename = file.getOriginalFilename();
        String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
        File serverFile = new File (context.getRealPath(PATH+File.separator+newFileName));

        try {
            FileUtils.writeByteArrayToFile(serverFile,file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
    }

        utilisateur.setPhoto(newFileName);

        var utilisateurEnreg = utilisateurRepository.save(utilisateur);

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", utilisateurEnreg.getId());
        claims.put("fullName", utilisateurEnreg.getNom() + " " + utilisateurEnreg.getPrenoms());
        String token = jwtService.generateToken(claims,utilisateurEnreg);

        return AuthenticationResponse
                .builder()
                .token(token)
                .build();
    }

    @Override
    @Transactional
    public AuthenticationResponse register(UtilisateurDto utilisateurDto) {
        validator.validate(utilisateurDto);

        Utilisateur utilisateur =  UtilisateurDto.toEntity(utilisateurDto);
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));

        utilisateur.setRole(
                findByRole(utilisateurDto.roleId())
        );

        var utilisateurEnreg = utilisateurRepository.save(utilisateur);

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", utilisateurEnreg.getId());
        claims.put("fullName", utilisateurEnreg.getNom() + " " + utilisateurEnreg.getPrenoms());
        String token = jwtService.generateToken(claims,utilisateurEnreg);

        return AuthenticationResponse
                .builder()
                .token(token)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.email(),authenticationRequest.password())
        );

        var utilisateur = utilisateurRepository.findUtilisateursByEmail(authenticationRequest.email());
        var userDetails = utilisateur.orElse(null);

        if (isNull(userDetails)){
            log.error("Utilisateur n'est pas valide ");
            throw new EntityNotFoundException("Utilisateur n'est pas valide ");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userDetails.getId());
        claims.put("fullName", userDetails.getNom() + " " + userDetails.getPrenoms());
        claims.put("photo", userDetails.getPhoto());
        String token = jwtService.generateToken(claims,userDetails);

        return AuthenticationResponse.builder()
                        .token(token)
                        .build();
    }

    @Override
    @Transactional
    public Integer active(Integer id) {

        Utilisateur utilisateurEnreg = getUtilisateur(id,true);

        return Observation.createNotStarted("activeUtilisateur",registry)
                .observe(utilisateurEnreg::getId);

    }



    @Override
    @Transactional
    public Integer desactive(Integer id) {
        Utilisateur utilisateurEnreg = getUtilisateur(id,false);

        return Observation.createNotStarted("desactiveUtilisateur",registry)
                .observe(utilisateurEnreg::getId);
    }

    @Override
    @Transactional
    public Integer update(Integer id,LightUtilisateurDto lightUtilisateurDto) {

        if (!utilisateurRepository.existsById(id)) {
            throw new EntityNotFoundException("L'utilisateur avec ID :"+ id +" n'existe pas");
        }


            Utilisateur utilisateur = LightUtilisateurDto.toEntity(lightUtilisateurDto);
            Utilisateur utilisateurExistant = UtilisateurDto.toEntity(findById(id));

            utilisateurExistant.setNom(utilisateur.getNom());
            utilisateurExistant.setPrenoms(utilisateur.getPrenoms());
            if (!(utilisateur.getEmail() == null)) {
                utilisateurExistant.setEmail(utilisateur.getEmail());
            }

            //utilisateurExistant.setActive(true);

            if (!(utilisateur.getMotDePasse()==null)){
                utilisateurExistant.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
            }

           var role = utilisateur.getRole();
            if (isNull(role.getId())){
               throw new EntityNotFoundException("Le Role ne doit pas etre null");
            }
            utilisateurExistant.setRole(
                    findByRole(role.getId())
            );



            var utilisateurExistantEnreg = utilisateurRepository.save(utilisateurExistant);

            return Observation.createNotStarted("updateUtilisateur", registry)
                    .observe(utilisateurExistantEnreg::getId);


    }

    @Override
    public byte[] getPhoto(Integer id) throws IOException {
        var utilisateurExistantDto = findById(id);
        Utilisateur utilisateur =  UtilisateurDto.toEntity(utilisateurExistantDto);
        return Files.readAllBytes(Paths.get(context.getRealPath(PATH)+ utilisateur.getPhoto()));
    }

    private Utilisateur getUtilisateur(Integer id,Boolean active) {
        var utilisateur = requireNonNull(utilisateurRepository.findById(id).orElse(null),"L'utilisateur n'existe pas" + id);
        utilisateur.setActive(active);

        return utilisateurRepository.save(utilisateur);
    }


    private Role findByRole(Integer roleId){
        Role myRole = roleRepository.findById(roleId).orElse(null);
        if (isNull(myRole)) {
           return roleRepository.save(
                Role.builder().role(ROLE_USER).build()
            );
        }
        return myRole;
    }
}
