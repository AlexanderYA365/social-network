package com.getjavajob.training.yakovleva.web.controllers;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.AccountPhoto;
import com.getjavajob.training.yakovleva.common.Message;
import com.getjavajob.training.yakovleva.common.Phone;
import com.getjavajob.training.yakovleva.common.utilsEnum.MessageType;
import com.getjavajob.training.yakovleva.common.utilsEnum.PhoneType;
import com.getjavajob.training.yakovleva.common.utilsEnum.Role;
import com.getjavajob.training.yakovleva.service.AccountService;
import com.getjavajob.training.yakovleva.service.MessageService;
import com.getjavajob.training.yakovleva.service.PhoneService;
import com.getjavajob.training.yakovleva.web.controllers.utils.SocialNetworkUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
@ControllerAdvice
@SessionAttributes("account")
public class AccountController {
    private static final Logger logger = LogManager.getLogger(AccountController.class);
    private static final int BUFFER_SIZE = 4096;
    private static final int ENTRIES_FOR_PAGE = 6;
    private final AccountService accountService;
    private final MessageService messageService;
    private final PhoneService phoneService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountController(AccountService accountService,
                             MessageService messageService,
                             PhoneService phoneService,
                             PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.messageService = messageService;
        this.phoneService = phoneService;
        this.passwordEncoder = passwordEncoder;
        logger.info("AccountController");
    }

    @ModelAttribute("account")
    public Account account() {
        return new Account();
    }

    @RequestMapping(value = "/update-avatar", method = RequestMethod.POST)
    public ModelAndView updateAvatar(@RequestParam("file-name") String fileName,
                                     @RequestParam("file") MultipartFile file,
                                     @ModelAttribute("account") Account account) {
        logger.info("updateAvatar(fileName = {}, file, account.id = {})", fileName, account.getId());
        ModelAndView modelAndView = new ModelAndView("/account/my-account");
        try {
            if (!file.isEmpty()) {
                account.getAccountPhoto().setPhotoFileName("fileName");
                account.getAccountPhoto().setPhoto(file.getBytes());
                accountService.updatePhoto(account);
            }
        } catch (Exception ex) {
            logger.error("failed load photo exception = {}", ex);
        }
        logger.info(account.getAccountPhoto().getPhoto());
        return modelAndView;
    }

    @RequestMapping(value = "/edit-account-settings", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView saveSettings(@ModelAttribute("account") Account account,
                              @ModelAttribute("typeRole") String role,
                              @RequestParam("phoneId") List<String> phoneId,
                              @RequestParam("typePhone") List<String> typePhone,
                              @RequestParam("phone") List<String> phone) {
        logger.info("saveSettings(account = {}, role = {}, phoneId = {}, typePhone = {}, phone = {})",
                account, role, phoneId, typePhone, phone);
        account.setRole(Role.valueOf(role));
        List<Phone> phones = createPhones(phoneId, typePhone, phone, account);
        changePhone(account, phones);
        accountService.update(account);
        SocialNetworkUtils socialNetworkUtils = new SocialNetworkUtils();
        String accountAvatar = socialNetworkUtils.getPhoto(account.getAccountPhoto().getPhoto());
        ModelAndView modelAndView = new ModelAndView("/account/my-account");
        modelAndView.addObject("accountAvatar", accountAvatar);
        return modelAndView;
    }

    private List<Phone> createPhones(List<String> phoneId, List<String> typePhone,
                                     List<String> phone, Account account) {
        List<Phone> phones = new ArrayList<>();
        for (int i = 0; i < phoneId.size(); i++) {
            Phone convertPhones = new Phone();
            convertPhones.setPhoneNumber(phone.get(i));
            convertPhones.setPhoneType(PhoneType.valueOf(typePhone.get(i)).getStatus());
            convertPhones.setAccountId(account.getId());
            convertPhones.setId(Integer.parseInt(phoneId.get(i)));
            phones.add(convertPhones);
        }
        return phones;
    }

    private void changePhone(Account account, List<Phone> phones) {
        logger.info("changePhone(account = {}. phones = {}) ", account, phones);
        for (Phone phone : phones) {
            for (int j = 0; j < account.getPhones().size(); j++) {
                if (phone.getId() == account.getPhones().get(j).getId()) {
                    account.getPhones().get(j).setPhoneType(phone.getPhoneType());
                    account.getPhones().get(j).setPhoneNumber(phone.getPhoneNumber());
                }
            }
            if (phone.getId() == 0) {
                account.getPhones().add(phone);
            }
        }
        System.out.println("update phone - " + account.getPhones());
    }

    private List<Phone> updatePhones(List<String> phoneId, List<String> typePhone,
                                     List<String> phone, Account account) {
        logger.info("updatePhones(phoneId = {}, typePhone = {}, phone = {}, account = {})",
                phoneId, typePhone, phone, account);
        List<Phone> phones = new ArrayList<>();
        for (int i = 0; i < phoneId.size(); i++) {
            Phone convertPhones = new Phone();
            convertPhones.setPhoneNumber(phone.get(i));
            convertPhones.setPhoneType(PhoneType.valueOf(typePhone.get(i)).getStatus());
            convertPhones.setAccountId(account.getId());
            convertPhones.setId(Integer.parseInt(phoneId.get(i)));
            phones.add(convertPhones);
        }
        deleteOldPhones(account, phones);
        account.getPhones().addAll(phones);
        return account.getPhones();
    }

    private void deleteOldPhones(Account account, List<Phone> phones) {
        logger.info("deleteOldPhones(account = {}, phones = {})", account, phones);
        account.getPhones().removeAll(phones);
        for (Phone phone : account.getPhones()) {
            phoneService.delete(phone);
        }
    }

    @RequestMapping(value = "/registration-account", method = RequestMethod.GET)
    public ModelAndView registration() {
        logger.info("registration()");
        return new ModelAndView("account/registration-account");
    }

    @RequestMapping(value = "/get-account-by-id/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Account getAccountById(@PathVariable String id) {
        logger.info("getAccountById(id = {})", id);
        return accountService.get(Integer.parseInt(id));
    }

    @RequestMapping(value = "/registration-account", method = RequestMethod.POST)
    public @ResponseBody ModelAndView
    createNewAccount(@ModelAttribute("account") Account account, @RequestParam("file") MultipartFile file,
                     @ModelAttribute("phone") String phone, @ModelAttribute("typePhone") String typePhone) {
        logger.info("createNewAccount(account = {}, file, phone = {}, typePhone = {})",
                account, phone, typePhone);
        AccountPhoto accountPhoto = new AccountPhoto();
        try {
            if (!file.isEmpty()) {
                accountPhoto.setPhoto(file.getBytes());
                accountPhoto.setPhotoFileName(file.getName());
            }
        } catch (Exception ex) {
            logger.error("failed load photo, exception -{}", ex);
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRole(Role.ROLE_USER);
        SocialNetworkUtils utils = new SocialNetworkUtils();
        List<Phone> phones = utils.convertPhones(phone, typePhone, account);
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        try {
            if (accountService.create(account, accountPhoto, phones)) {
                Account accountFromBase = accountService.getByUsername(account.getUsername());
                logger.info("accountFromBase - {}", accountFromBase);
                modelAndView.addObject("account", accountFromBase);
            } else {
                logger.error("registration exception");
                modelAndView.setViewName("/account/registration-account");
            }
        } catch (Exception ex) {
            logger.error("failed load photo, exception = {}", ex);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/edit-account-settings", method = RequestMethod.GET)
    public ModelAndView editSettings(@ModelAttribute("account") Account account) {
        logger.info("editSettings(account = {})", account);
        SocialNetworkUtils socialNetworkUtils = new SocialNetworkUtils();
        String accountAvatar = socialNetworkUtils.getPhoto(account.getAccountPhoto().getPhoto());
        ModelAndView modelAndView = new ModelAndView("/account/edit-account-settings");
        modelAndView.addObject("accountAvatar", accountAvatar);
        modelAndView.addObject("roles", Role.values());
        return modelAndView;
    }

    @RequestMapping(value = "/my-account", method = RequestMethod.GET)
    public ModelAndView settings(@ModelAttribute("account") Account account) {
        logger.info("settings(account = {})", account);
        SocialNetworkUtils socialNetworkUtils = new SocialNetworkUtils();
        String accountAvatar = socialNetworkUtils.getPhoto(account.getAccountPhoto().getPhoto());
        ModelAndView modelAndView = new ModelAndView("/account/my-account");
        modelAndView.addObject("accountAvatar", accountAvatar);
        return modelAndView;
    }

    @RequestMapping(value = "/update-account-settings", method = RequestMethod.POST)
    public ModelAndView update(@RequestParam("uploadXml") MultipartFile file) {
        logger.info("update(file)");
        Account account = new Account();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Account.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            File fileToUpdate = new File(Objects.requireNonNull(file.getOriginalFilename()));
            file.transferTo(fileToUpdate);
            account = (Account) unmarshaller.unmarshal(fileToUpdate);
            logger.info("account = {}", account);
            accountService.update(account);
        } catch (JAXBException e) {
            logger.error("Error with JAXB instance. Exception: " + e);
        } catch (IOException e) {
            logger.error("Error with transfer to file. Exception: " + e);
        }
        ModelAndView modelAndView = new ModelAndView("/account/my-account");
        modelAndView.addObject("account", account);
        return modelAndView;
    }

    @RequestMapping(value = "/save-account-settings", method = RequestMethod.GET)
    public void save(@ModelAttribute("account") Account account, HttpServletResponse response) {
        logger.info("save(account = {})", account);
        File file = new File("account.xml");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Account.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
            marshaller.marshal(account, file);
        } catch (JAXBException e) {
            logger.error("Error with JAXB instance. Exception: " + e);
        }
        try (FileInputStream inputStream = new FileInputStream(file)) {
            response.setContentType("application/xml");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
            response.setContentType("application/xml");
            response.setContentLength((int) file.length());
            byte[] buffer = new byte[BUFFER_SIZE];
            OutputStream outStream = response.getOutputStream();
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
            outStream.close();
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException: " + e);
        } catch (IOException e) {
            logger.error("IOException: " + e);
        }
    }

    @RequestMapping(value = "/sendWallMessage", method = RequestMethod.POST)
    public ModelAndView accountPageSendWallMessage(@RequestParam(value = "NewWallMessage",
            required = false) String newMessage, @RequestParam(value = "deleteText",
            required = false) String deleteText, @ModelAttribute("account") Account account) {
        logger.info("sendWallMessage:");
        logger.info("newMessage = {}", newMessage);
        logger.info("deleteText = {}", deleteText);
        logger.info("account = {}", account);
        createMessage(account, newMessage, deleteText);
        return new ModelAndView("redirect:main");
    }

    public void createMessage(Account account,
                              String newMessage,
                              String deleteText) {
        logger.info("createMessage(account = {}, newMessage = {}," +
                "deleteText = {})", account, newMessage, deleteText);
        try {
            if (newMessage != null) {
                logger.info("create new message");
                Message message = new Message();
                message.setSenderId(account.getId());
                message.setReceiverId(account.getId());
                message.setMessage(newMessage);
                message.setMessageType(MessageType.WALL);
                message.setPublicationDate(new Date());
                messageService.createMassage(message);
            } else if (deleteText != null) {
                logger.info("select delete text");
                int messageId = Integer.parseInt(deleteText);
                logger.info("messageId = {} ", messageId);
                messageService.delete(messageId);
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @RequestMapping(value = "/get-wall-messages/{page}", method = RequestMethod.GET)
    @ResponseBody
    public List<Message> getWallMessages(@ModelAttribute("account") Account account, @PathVariable String page) {
        logger.info("getWallMessage(account.id = {}, page = {})", account.getId(), page);
        Pageable pageable = PageRequest.of(Integer.parseInt(page), ENTRIES_FOR_PAGE);
        return messageService.getWallMassageAccount(account, pageable);
    }

    @RequestMapping(value = "/get-size-wall-messages", method = RequestMethod.GET)
    @ResponseBody
    public long getSizeWallMessages(@ModelAttribute("account") Account account) {
        logger.info("getSizeWallMessage(account.id = {})", account.getId());
        return messageService.getSizeWallMassages(account);
    }

    @RequestMapping(value = "/get-senders-avatar/{page}", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getSendersAvatar(@ModelAttribute("account") Account account,
                                         @PathVariable String page) {
        logger.info("getSendersAvatar(account.id = {}, page = {})", account.getId(), page);
        Pageable pageable = PageRequest.of(Integer.parseInt(page), ENTRIES_FOR_PAGE);
        List<Account> senderAccounts = accountService.getSenderMessageAccounts(account, pageable);
        SocialNetworkUtils socialNetworkUtils = new SocialNetworkUtils();
        return socialNetworkUtils.getPhotos(senderAccounts);
    }

    @RequestMapping(value = "/get-sender-account/{page}", method = RequestMethod.GET)
    @ResponseBody
    public List<Account> getSenders(@ModelAttribute("account") Account account,
                                    @PathVariable String page) {
        logger.info("getSenders(account.id = {}, page = {})", account.getId(), page);
        Pageable pageable = PageRequest.of(Integer.parseInt(page), ENTRIES_FOR_PAGE);
        return accountService.getSenderMessageAccounts(account, pageable);
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView mainPage(Principal principal) {
        logger.info("public main(principal = {})", principal);
        ModelAndView modelAndView = new ModelAndView("main");
        Account registeredAccount = accountService.get(principal.getName());
        SocialNetworkUtils socialNetworkUtils = new SocialNetworkUtils();
        String accountAvatar = socialNetworkUtils.getPhoto(registeredAccount.getAccountPhoto().getPhoto());
        modelAndView.addObject("account", registeredAccount);
        modelAndView.addObject("accountAvatar", accountAvatar);
        return modelAndView;
    }

    @RequestMapping(value = "/main", method = RequestMethod.POST)
    public ModelAndView accountPage(@ModelAttribute("account") Account account) {
        logger.info("public accountPage(account = {})", account);
        return new ModelAndView("main");
    }

}