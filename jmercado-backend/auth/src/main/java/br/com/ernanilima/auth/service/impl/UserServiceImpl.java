package br.com.ernanilima.auth.service.impl;

import br.com.ernanilima.auth.domain.User;
import br.com.ernanilima.auth.dto.CompanyDTO;
import br.com.ernanilima.auth.dto.UserDTO;
import br.com.ernanilima.auth.dto.auth.LoginDTO;
import br.com.ernanilima.auth.repository.UserRepository;
import br.com.ernanilima.auth.security.JwtUtils;
import br.com.ernanilima.auth.security.UserSpringSecurity;
import br.com.ernanilima.auth.service.CompanyService;
import br.com.ernanilima.auth.service.CrudService;
import br.com.ernanilima.auth.service.UserService;
import br.com.ernanilima.auth.service.exception.DecoderException;
import br.com.ernanilima.auth.service.exception.ObjectNotFoundException;
import br.com.ernanilima.auth.utils.I18n;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
import java.util.Optional;

import static br.com.ernanilima.auth.utils.I18n.OBJECT_NOT_FOUND;
import static br.com.ernanilima.auth.utils.I18n.getClassName;
import static java.text.MessageFormat.format;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl extends CrudService<User, UserDTO> implements UserService {

    private final UserRepository userRepository;
    private final CompanyService companyService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public UserDTO findByEmail(String email) {
        User user = findByEmailAndCompanyEin(email, UserSpringSecurity.getAuthenticatedUser().getCompanyEin());
        return super.getConverter().toDTO(user);
    }

    @Override
    public User findByEmailAndCompanyEin(String email, String companyEin) {
        log.info("{}:findByEmailAndCompanyEin(obj), iniciando busca do usuario com o e-mail {} para a empresa {}", CLASS_NAME, email, companyEin);

        Optional<User> result = userRepository.findByEmailAndCompany_Ein(email, companyEin);

        User user = result.orElseThrow(() -> {
            log.error("{}:findByEmailAndCompanyEin(obj), nao localizado o usuario com o e-mail {} para a empresa {}", CLASS_NAME, email, companyEin);
            return new ObjectNotFoundException(
                    format(I18n.getMessage(OBJECT_NOT_FOUND), getClassName(User.class.getSimpleName()))
            );
        });

        log.info("{}:findByEmailAndCompanyEin(obj), localizado o usuario com o e-mail {} para a empresa {}", CLASS_NAME, email, companyEin);

        return user;
    }

    @Override
    protected UserDTO beforeInsert(UserDTO dto) {
        log.info("{}:beforeInsert(obj), iniciado manipulacao antes da insercao do usuario", CLASS_NAME);

        String password = passwordEncoder.encode(dto.getPassword());
        CompanyDTO companyDTO = companyService.findById(dto.getCompany().getId());

        dto = dto.toBuilder().password(password).company(companyDTO).build();

        log.info("{}:beforeInsert(obj), atualizado vinculacao antes da insercao do usuario", CLASS_NAME);

        return dto;
    }

    @Override
    protected UserDTO beforeUpdate(UserDTO dto) {
        log.info("{}:beforeUpdate(obj), iniciado manipulacao antes da atualizar do usuario", CLASS_NAME);

        UserDTO userDTO = super.findById(dto.getId());

        dto = dto.toBuilder().company(userDTO.getCompany()).build();

        log.info("{}:beforeUpdate(obj), atualizado vinculacao antes da atualizar o usuario", CLASS_NAME);

        return dto;
    }

    @Override
    public UserDetails loadUserByUsername(String values) throws UsernameNotFoundException {
        String CLASS_NAME = this.getClass().getSimpleName();
        log.info("{}:loadUserByUsername(obj), iniciado login", CLASS_NAME);

        LoginDTO dto;

        try {
            dto = jwtUtils.getDecoderAuthentication(values);
        } catch (CredentialException e) {
            log.error("{}:loadUserByUsername(obj), erro ao decodificar {}", CLASS_NAME, values);
            throw new DecoderException("Dados invalidos para login");
        }

        User user = findByEmailAndCompanyEin(dto.getEmail(), dto.getEin());

        log.info("{}:loadUserByUsername(obj), usuario localizado para login", CLASS_NAME);
        return new UserSpringSecurity(user.getCompany().getEin(), user.getEmail(), user.getPassword(), user.getRoles());
    }
}
