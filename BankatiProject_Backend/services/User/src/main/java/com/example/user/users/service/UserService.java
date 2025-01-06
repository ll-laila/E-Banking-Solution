package com.example.user.users.service;

import com.example.user.serviceTiersClient.ServiceTiersClient;
import com.example.user.transactionClient.*;
import com.example.user.serviceTiersClient.ServiceTiersClient;
import com.example.user.transactionClient.TransactionClient;
import com.example.user.transactionClient.TransactionResponse;
import com.example.user.users.dto.CredentialsDto;
import com.example.user.users.dto.SignUpDto;
import com.example.user.users.dto.UserDto;
import com.example.user.users.dto.CredentialsDto;
import com.example.user.users.dto.SignUpDto;
import com.example.user.users.dto.UserDto;
import com.example.user.users.entity.*;
import com.example.user.users.exceptions.AppException;
import com.example.user.users.exceptions.UserNotFoundException;
import com.example.user.users.mapper.AgentServiceMapper;
import com.example.user.users.mapper.UserMapper;
import com.example.user.users.repository.AgentRepository;
import com.example.user.users.repository.ServiceRepository;
import com.example.user.users.repository.UserRepository;
import com.example.user.users.request.ServiceRequest;
import com.example.user.users.request.UserRequest;
import com.example.user.users.response.ServiceResponse;
import com.example.user.users.response.UserResponse;
import com.example.user.users.twilio.Const;
import com.example.user.users.twilio.ENVConfig;
import com.example.user.users.twilio.TwilioConfiguration;
import com.example.user.walletClient.WalletClient;
import com.example.user.walletClient.WalletRequest;
import com.example.user.walletClient.WalletResponse;
import com.example.user.walletCryptoClient.WalletCryptoClient;
import com.example.user.walletCryptoClient.WalletCryptoRequest;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.user.users.entity.Role.AGENT;
import static com.example.user.users.entity.Role.CLIENT;
import static java.lang.String.format;



@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AgentRepository agentRepository;


    @Autowired
    private WalletClient walletClient;

    @Autowired
    private WalletCryptoClient walletCryptoClient;

    @Autowired
    private ENVConfig envConfig;

    @Autowired
    private TwilioConfiguration twilioConfiguration;

    private static final String CHARACTERS = "0123456789";




    /*public UserDto login(CredentialsDto credentialsDto) {
        // First, try to find an Agent
        Optional<Agent> agentOptional = agentRepository.findByPhoneNumber(credentialsDto.getPhoneNumber());

        if (agentOptional.isPresent()) {
            Agent agent = agentOptional.get();

            // Directly use the matches method
            if (passwordEncoder.matches(credentialsDto.getPassword(), agent.getPassword())) {
                return userMapper.toUserDto(agent);
            }

            throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
        }

        // If no agent found, try to find a User
        Optional<User> userOptional = userRepository.findByPhoneNumber(credentialsDto.getPhoneNumber());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (passwordEncoder.matches(credentialsDto.getPassword(), user.getPassword())) {
                return userMapper.toUserDto(user);
            }

            throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
        }

        // If neither agent nor user is found
        throw new AppException("Unknown credentials", HttpStatus.NOT_FOUND);
    }*/

    public UserDto login(CredentialsDto credentialsDto) {
        Optional<User> userOptional = userRepository.findByPhoneNumber(credentialsDto.getPhoneNumber());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (!passwordEncoder.matches(credentialsDto.getPassword(), user.getPassword())) {
                throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
            }

            UserDto userDto = userMapper.toUserDto(user);

            // Enforce first login password change for clients
            if (user.getRole() == CLIENT && user.isFirstLogin()) {
                userDto.setMessage("Please change your password.");
            }

            return userDto;
        }

        throw new AppException("Unknown credentials", HttpStatus.NOT_FOUND);
    }


    public UserDto register(SignUpDto signUpDto) {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(signUpDto.getPhoneNumber());

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        // Validate role
        Role role = Role.fromString(String.valueOf(signUpDto.getRole()));
        if (role == null) {
            throw new AppException("Invalid role provided", HttpStatus.BAD_REQUEST);
        }

        // Map SignUpDto to User entity
        User user = userMapper.signUpToUser(signUpDto);
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setRole(role);

        User savedUser = userRepository.save(user);

        return userMapper.toUserDto(savedUser);
    }



    public void changePassword(String phoneNumber, String oldPassword, String newPassword) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new AppException("Old password is incorrect", HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setFirstLogin(false); // Mark first login as completed
        userRepository.save(user);
    }


   public String createClient(UserRequest userRequest) {
        // Ensure the role is CLIENT
        Role role = Role.fromString(String.valueOf(userRequest.role()));
        if (role != CLIENT) {
            throw new AppException("Only CLIENT role is allowed", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.toUser(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setRole(role);
        user.setFirstLogin(true);

        User savedUser = userRepository.save(user);


        // add Crypto wallet
        Map<String, Double> cryptos = new HashMap<>();
        cryptos.put("bitcoin", 0.0);
        cryptos.put("ethereum", 0.0);
        WalletCryptoRequest walletCrypto = new WalletCryptoRequest(null,savedUser.getId(),cryptos);
        var idWalletCrypto = walletCryptoClient.saveWalletCrypto(walletCrypto);

        // add wallet
        WalletRequest wallet = new WalletRequest(null,1000D,savedUser.getId(),null);
        var idWallet = walletClient.saveWallet(wallet);

        //twilio
        String formattedPhoneNumber = formatPhoneNumber(savedUser.getPhoneNumber());
        String msg =   "Bonjour "+ savedUser.getFirstName() +" "+savedUser.getLastName() + ", votre mot de passe est "+ userRequest.password() + ".";
        Message twilioMessage = (Message) sendSms(formattedPhoneNumber, Const.OTP_MESSAGE.replace("<otp>",msg) );
        log.info("Twilio Response : {}", twilioMessage.getStatus());

        return savedUser.getId();
    }

    public boolean deactivateAccount(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setIsPaymentAccountActivated(false);
            userRepository.save(user);
            return true;
        }
        return false;
    }


    public List<UserResponse> getAllClients() {
        return userRepository.findByRole(CLIENT)
                .stream()
                .map(userMapper::fromUser)
                .collect(Collectors.toList());
    }



    public String createAgent(UserRequest userRequest) {
        // Ensure the role is AGENT
        Role role = Role.fromString(String.valueOf(userRequest.role()));
        if (role != AGENT) {
            throw new AppException("Only AGENT role is allowed", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.toUser(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setRole(role);

        User savedUser = userRepository.save(user);


        // add wallet
        WalletRequest wallet = new WalletRequest(null,1000D,savedUser.getId(),null);
        var idWallet = walletClient.saveWallet(wallet);

        //twilio
        String formattedPhoneNumber=formatPhoneNumber(savedUser.getPhoneNumber());
        String msg =   "Bonjour "+ savedUser.getFirstName() +" "+savedUser.getLastName() + ", votre mot de passe est "+ userRequest.password() + ".";
        Message twilioMessage = (Message) sendSms(formattedPhoneNumber, Const.OTP_MESSAGE.replace("<otp>",msg) );
        log.info("Twilio Response : {}", twilioMessage.getStatus());

        return savedUser.getId();
    }


  /*  public UserDto findByLogin(String login) {
        // First, check if the login exists in the Agent table
        Agent agent = agentRepository.findByPhoneNumber(login)
                .orElse(null); // If not found, return null

        if (agent != null) {
            // If an agent is found, map it to UserDto
            UserDto userDto = userMapper.toUserDto(agent);

            // Set the role to 'agent'
            userDto.setRole(Role.AGENT);

            return userDto;
        }

        // If no agent is found, check in the User table
        User user = userRepository.findByPhoneNumber(login)
                .orElseThrow(() -> new AppException("Unknown user or agent", HttpStatus.NOT_FOUND));

        // Map the User entity to UserDto and return
        return userMapper.toUserDto(user);
    }
*/

    public UserDto findByLogin(String login) {
        log.info("Searching for user with login: {}", login);

        // Try to find user by phone number first
        Optional<User> userOptional = userRepository.findByPhoneNumber(login);

        // If still not found, throw an exception
        User user = userOptional.orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        return userMapper.toUserDto(user);
    }

    public String createUser(UserRequest userRequest) {
        // Validate role
        Role role = Role.fromString(String.valueOf(userRequest.role()));
        if (role == null) {
            throw new AppException("Invalid role provided", HttpStatus.BAD_REQUEST);
        }

        // Map and save the user
        User user = userMapper.toUser(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setRole(role);

        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    public void updateUser(@Valid UserRequest userRequest) {

        var user = userRepository.findById(userRequest.id())
                .orElseThrow(()-> new UserNotFoundException(
                        format("Cannot update user :: NO user found with the provided id :: %s", userRequest.id())
                ));

        mergeUser(user, userRequest);
        userRepository.save(user);
    }

    private void mergeUser(User user, @Valid UserRequest userRequest) {
        if (StringUtils.isNotBlank(userRequest.firstName())) {
            user.setFirstName(userRequest.firstName());
        }
        if (StringUtils.isNotBlank(userRequest.lastName())) {
            user.setLastName(userRequest.lastName());
        }
        if (StringUtils.isNotBlank(userRequest.email())) {
            user.setEmail(userRequest.email());
        }
        if (StringUtils.isNotBlank(String.valueOf(userRequest.role()))) {
            Role role = Role.fromString(String.valueOf(userRequest.role()));
            if (role != null) {
                user.setRole(role);
            }
        }
    }

    public List<UserResponse> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper :: fromUser)
                .collect(Collectors.toList());
    }

    public Boolean existsById(String userId) {
        return userRepository.findById(userId).isPresent();
    }

    public UserResponse findById(String userId) {
        return userRepository.findById(userId)
                .map(userMapper::fromUser)
                .orElseThrow(()-> new UserNotFoundException(format("Cannot find user :: %s", userId)));
    }



    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }




    public Object sendSms(String phoneNumber, String message) {

        if (phoneNumber!=null) {
            PhoneNumber to = new PhoneNumber(phoneNumber);
            PhoneNumber from = new PhoneNumber(envConfig.getTwilioSmsFromNo());
            return Message.creator(to,from,message).create();
        } else {
            throw new IllegalArgumentException(
                    "Phone number [" + phoneNumber + "] is not a valid number"
            );
        }

    }
    public String formatPhoneNumber(String phoneNumber) {

        String formatted = phoneNumber.substring(1);
        return "+212" + formatted;
    }




    //--------------------------------------BackOffice-----------------------------------//








    //--------------------------------------Agent-----------------------------------//

    @Autowired
    ServiceRepository serviceRepository;
    public ServiceResponse createService(com.example.user.users.entity.Service request) {
        // Ajouter des validations si nécessaire sur l'objet `request`
        if (request.getName() == null || request.getType() == null) {
            throw new IllegalArgumentException("Service request is invalid");
        }
        // Enregistrer le service
        serviceRepository.save(request);

        // Retourner la réponse
        return new ServiceResponse("service cree avec succee");
    }

    public List<User> getAllAgents() {
        return userRepository.findByRole(Role.AGENT);
    }

    public List<com.example.user.users.entity.Service> getAllServicesByAgentId(String agentId) {
        List<com.example.user.users.entity.Service> servicesAgent = serviceRepository.findAllByAgentId(agentId);
        return servicesAgent.stream()
                .map(AgentServiceMapper::ConvertToDto)
                .collect(Collectors.toList());
    }

    public ServiceResponse updateService(String serviceId, com.example.user.users.entity.Service request) {
        com.example.user.users.entity.Service agentService = serviceRepository.findServiceById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        agentService.setName(request.getName());
        agentService.setType(request.getType());

        serviceRepository.save(agentService);

        return new ServiceResponse("service update avec succee");
    }

    public com.example.user.users.entity.Service getServiceById(String id) {
       return serviceRepository.findServiceById(id)
               .map(AgentServiceMapper::ConvertToDto)
                .orElse(null);
   }
    public ServiceAgentResponse deleteService(String serviceId) {
        com.example.user.users.entity.Service agentService = serviceRepository.findServiceById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        serviceRepository.delete(agentService);

        return ServiceAgentResponse.builder().message("Service deleted successfully").build();
    }
    public List<UserResponse> getAllClientsByAgentId(String agentId) {
        // Vérifier que l'agentId n'est pas nul ou vide
        if (agentId == null || agentId.isEmpty()) {
            throw new IllegalArgumentException("Agent ID cannot be null or empty");
        }

        // Récupérer tous les utilisateurs associés à cet agentId
        List<User> clients = userRepository.findAllByAgentId(agentId);

        // Mapper les résultats en objets UserRequest
        return clients.stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getAgentId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getAddress(),
                        user.getCin(),
                        user.getBirthDate(),
                        user.getPhoneNumber(),
                        user.getRole(),
                        user.getPassword(),
                        user.isFirstLogin(),
                        user.getCommercialRn(),
                        user.getImage(),
                        user.getPatentNumber(),
                        user.getIsPaymentAccountActivated(),
                        user.getTypeHissab(),
                        user.getCurrency()
                ))
                .collect(Collectors.toList());
    }


    //--------------------------------------client-----------------------------------//


public TransactionRequest createPaymentTransaction(String senderId, String beneficiaryId, BigDecimal amount) {
    UserResponse beneficiary = findById(beneficiaryId);
    UserResponse sender = findById(senderId);

    return new TransactionRequest(
            null, // ID généré ultérieurement
            amount,
            beneficiary.id(),
            beneficiary.firstName()+" "+beneficiary.lastName(),
            beneficiary.phoneNumber(),
            AGENT,
            TransactionType.PAYMENT,
            TransactionStatus.COMPLETED, // Statut initial
            beneficiary.currency(),
            null, // Pas de date de validation au début
            sender.id(),
            sender.firstName()+" "+sender.lastName(),
            sender.phoneNumber(),
            CLIENT,
            sender.currency()
    );
}

    public TransactionRequest createTransferTransaction(String senderId, String beneficiaryId, BigDecimal amount) {
        UserResponse beneficiary =findById(beneficiaryId);
        UserResponse sender =findById(senderId);
        return new TransactionRequest(
                null, // ID généré ultérieurement
                amount,
                beneficiary.id(),
                beneficiary.firstName()+" "+beneficiary.lastName(),
                beneficiary.phoneNumber(),
                CLIENT,
                TransactionType.TRANSFER,
                TransactionStatus.COMPLETED, // Statut initial
                beneficiary.currency(),
                null, // Pas de date de validation au début
                sender.id(),
                sender.firstName()+" "+sender.lastName(),
                sender.phoneNumber(),
                CLIENT,
                sender.currency()
        );
    }

     public String getClientIdByPhoneNumber(String phoneNumber) {
         Optional<User> client = userRepository.findUserByPhoneNumber(phoneNumber);
         if (client.isPresent()) {
             return client.get().getId();
         } else {
             throw new RuntimeException("Aucun client trouvé avec le numéro de téléphone : " + phoneNumber);
         }
     }




    @Autowired
    private TransactionClient transactionClient;
    @Autowired
    private ServiceTiersClient serviceTiersClient;


    public WalletResponse getWalletByUserId(String userId) {
        return walletClient.getWalletByIdClient(userId).getBody();
    }
    public List<TransactionResponse> getTransactionsByUserId(String userId) {
        return transactionClient.getTransactionsByUser(userId);
    }
    public List<TransactionResponse> getAllTransactionsByUserId(String userId) {
        return transactionClient.getAllTransactionsByUserId(userId);
    }
    public boolean feedWallet(String userId, double amount) {
        ResponseEntity<Boolean> response = serviceTiersClient.feedWallet(userId, amount);
        return response.getBody() != null && response.getBody();
    }



}
