package com.getjavajob.training.yakovleva.web.controllers;

import com.getjavajob.training.yakovleva.common.*;
import com.getjavajob.training.yakovleva.common.utilsEnum.*;
import com.getjavajob.training.yakovleva.dao.AccountDao;
import com.getjavajob.training.yakovleva.dao.RelationsDao;
import com.getjavajob.training.yakovleva.repository.AccountRepository;
import com.getjavajob.training.yakovleva.repository.GroupMembersRepository;
import com.getjavajob.training.yakovleva.repository.GroupRepository;
import com.getjavajob.training.yakovleva.repository.MessageRepository;
import com.getjavajob.training.yakovleva.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {
    private final MessageService messageService;
    private final AccountDao accountDao;
    private final GroupMembersRepository groupMembersRepository;
    private final GroupRepository groupRepository;
    private final GroupService groupService;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final PhoneService phoneService;
    private final MessageRepository messageRepository;
    private final RelationsDao relationsDao;
    private final ApplicationService applicationService;
    private final RelationsService relationsService;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public TestController(MessageService messageService, AccountDao accountDao,
                          GroupMembersRepository groupMembersRepository, GroupRepository groupRepository,
                          GroupService groupService,
                          AccountRepository accountRepository, AccountService accountService,
                          PhoneService phoneService, MessageRepository messageRepository,
                          RelationsDao relationsDao, ApplicationService applicationService,
                          RelationsService relationsService, PasswordEncoder passwordEncoder) {
        this.messageService = messageService;
        this.accountDao = accountDao;
        this.groupMembersRepository = groupMembersRepository;
        this.groupRepository = groupRepository;
        this.groupService = groupService;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.phoneService = phoneService;
        this.messageRepository = messageRepository;
        this.relationsDao = relationsDao;
        this.applicationService = applicationService;
        this.relationsService = relationsService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "/create-test-wall-message/{start}/{count}", method = RequestMethod.GET)
    public void createTestWallMessage(@PathVariable String count, @PathVariable String start) {
        int size = Integer.parseInt(count);
        int startPosition = Integer.parseInt(start);
        List<Message> messages = new ArrayList<>();
        List<Account> accounts = getAccounts();
        int startIdSender = accounts.get(0).getId();
        int startIdReceiver = accounts.get(1).getId();
        for (; startPosition < size; startPosition++, startIdReceiver++, startIdSender++) {
            Message message = new Message();
            message.setMessageType(MessageType.WALL);
            message.setMessage("Привет");
            message.setSenderId(startIdSender);
            message.setReceiverId(startIdReceiver);
            message.setPublicationDate(new Date());
            message.setEdited(false);
            messages.add(message);
        }
        messageRepository.saveAll(messages);
        System.out.println("finish");
    }

    @RequestMapping(value = "/create-test-private-message/{start}/{count}", method = RequestMethod.GET)
    public void createTestPrivateMessage( @PathVariable String start, @PathVariable String count) {
        int size = Integer.parseInt(count);
        int startPosition = Integer.parseInt(start);
        List<Message> messages = new ArrayList<>();
        List<Account> accounts = getAccounts();
        Account account = accountService.get("admin@admin.ru");
        for (; startPosition < size; startPosition++) {
            Message message = new Message();
            message.setMessageType(MessageType.PRIVATE);
            message.setMessage("Привет");
            message.setSenderId(account.getId());
            message.setReceiverId(accounts.get(startPosition).getId());
            message.setPublicationDate(new Date());
            message.setEdited(false);
            messages.add(message);
        }
        messageRepository.saveAll(messages);
        System.out.println("finish");
    }

    @RequestMapping(value = "/create-test-group/", method = RequestMethod.GET)
    public void createTestGroup() {
        String[] groupName = new String[]{"Велосипеды", "Самокаты", "Консоли", "Видео", "Фото", "Спорт",
                "Бег", "Животные", "Афиша", "Музыка"};
        String[] groupInfo = new String[]{"Группа о великах",
                "Группа о самокатах", "Группа о консолях", "Группа о съемки видео", "Группа о съемке фото",
                "Группа о спорте", "Группа о беге", "Группа о животных", "Группа о новинках кино и фото",
                "Группа о музыке"};
        List<Group> groups = new ArrayList<>();
        Account account = accountService.get("admin@admin.ru");
        for (int i = 0; i < 10; i++) {
            Group group = new Group();
            group.setGroupName(groupName[i]);
            group.setInfo(groupInfo[i]);
            group.setIdGroupCreator(account.getId());
            groups.add(group);
        }
        for (Group group : groups) {
            groupService.createAccountGroups(group);
        }
        System.out.println("finish");
    }

    @RequestMapping(value = "/create-test-group-members/{count-members}", method = RequestMethod.GET)
    public void createTestGroupMembers(@PathVariable("count-members") String countMembers) {
        int countMembersPerGroup = Integer.parseInt(countMembers);
        List<Account> accounts = getAccounts();
        List<Group> groups = groupRepository.findAll();
        List<GroupMembers> groupMembers = new ArrayList<>();
        Account account = accountService.get("admin@admin.ru");
        for (Group group : groups) {
            for (int j = 0; j < countMembersPerGroup; j++) {
                GroupMembers groupMember = new GroupMembers();
                groupMember.setGroup(group);
                groupMember.setGroupRole(getGroupRole(accounts.get(j), account));
                groupMember.setMember(accounts.get(j));
                groupMembers.add(groupMember);
            }
        }
        groupMembersRepository.saveAll(groupMembers);
        System.out.println("finish");
    }

    private GroupRole getGroupRole(Account members, Account account) {
        GroupRole role;
        if (members.getId() == account.getId()) {
            role = GroupRole.ADMIN;
        } else {
            role = GroupRole.MEMBER;
        }
        return role;
    }

    private List<Account> getAccounts() {
        return accountRepository.getAll();
    }

    @RequestMapping(value = "/create-applicant-group", method = RequestMethod.GET)
    public void createApplicant() {
        List<GroupMembers> groupMembers = groupMembersRepository.getAll();
        for (GroupMembers groupMember : groupMembers) {
            Application application = new Application();
            application.setApplicantId(groupMember.getGroup().getGroupId());
            application.setRecipientId(groupMember.getId());
            application.setApplicationType(ApplicationType.GROUP);
            application.setStatus(ApplicationStatusType.ACCEPTED);
            applicationService.create(application);
        }
        System.out.println("finish");
    }

    @RequestMapping(value = "/create-friend/{count}", method = RequestMethod.GET)
    public void createFriend(@PathVariable String count) {
        int size = Integer.parseInt(count);
        List<Account> accounts = getAccounts();
        for (int i = 0; i < size; i++) {
            for (int j = 1; j < 100; j++) {
                Relations relations = new Relations(accounts.get(i).getId(), accounts.get(j).getId());
                Application newApplication = new Application();
                if (j % 10 == 0) {
                    newApplication.setStatus(ApplicationStatusType.CONFIRMATION);
                } else {
                    newApplication.setStatus(ApplicationStatusType.ACCEPTED);
                }
                newApplication.setApplicationType(ApplicationType.USER);
                newApplication.setRecipientId(relations.getAccountId());
                newApplication.setApplicantId(relations.getFriendId());
                applicationService.create(newApplication);
                relationsService.create(relations);
            }
        }
    }

    @RequestMapping(value = "/create-test-account/{start}/{count}", method = RequestMethod.GET)
    public void createTestAccount(@PathVariable String count, @PathVariable String start) {
        String[] name = new String[]{"Александр", "Андрей", "Николай", "Филипп", "Илья", "Антон",
                "Максим", "Владимир", "Никита", "Сергей"};
        String[] surname = new String[]{"Яковлев", "Трофимов", "Назаренко", "Михайлов", "Тестовый",
                "Емельяненко", "Никто", "Самый", "Лучший", "Васильев"};
        String[] lastname = new String[]{"Иванович", "Николаевич", "Васильевич", "Александрович",
                "Андреевич", "Владимирович", "Олегович", "Филиппович", "Антонович", "Ильич"};
        String[] username = new String[]{"alex", "bob", "bon", "ten", "mark", "milk", "anystr", "sunni",
                "slipy", "vitto"};
        String[] email = new String[]{"@mail.ru", "@bk.ru", "@ya.ru", "@vk.ru", "@gmail.com",
                "@rambler.ru", "@outlook.com", "@bob.com", "@rk.com", "@sm.ru"};
        int size = Integer.parseInt(count);
        int startPosition = Integer.parseInt(start);
        List<Account> accounts = new ArrayList<>();
        List<AccountPhoto> accountPhotos = new ArrayList<>();
        List<List<Phone>> phones = new ArrayList<>();
        for (int i = startPosition, j = 0; i < size; i++) {
            if (i == 0) {
                accounts.add(createAccount(i, "Александр",
                        "Яковлев", "Николаевич", "admin", "@admin.ru", Role.ROLE_ADMIN));
                accountPhotos.add(createAccountPhoto(accounts.get(j)));
                phones.add(phones(accounts.get(j)));
            } else {
                accounts.add(createAccount(i, name[i % 10],
                        surname[i % 5], lastname[i % 4], username[i % 5], email[i % 10], Role.ROLE_USER));
                accountPhotos.add(createAccountPhoto(accounts.get(j)));
                phones.add(phones(accounts.get(j)));
            }
        }
        for (int i = 0; i < accounts.size(); i++) {
            accountService.create(accounts.get(i), accountPhotos.get(i), phones.get(i));
        }
        System.out.println("finish");
    }

    private AccountPhoto createAccountPhoto(Account account) {
        AccountPhoto accountPhoto = new AccountPhoto();
        accountPhoto.setAccount(account);
        accountPhoto.setPhotoFileName("photo");
        return accountPhoto;
    }

    private Account createAccount(int i, String name,
                                  String surname, String lastname,
                                  String username, String email, Role role) {
        Account account = new Account();
        account.setPassword(passwordEncoder.encode("Qwerty123"));
        account.setRole(role);
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setSurname(surname);
        accountDetails.setName(name);
        accountDetails.setLastName(lastname);
        accountDetails.setDate(new Date());
        accountDetails.setIcq(1);
        accountDetails.setEmail(username + i + email);
        accountDetails.setAddressHome("home address");
        accountDetails.setAddressJob("job address");
        accountDetails.setAboutMe("about me");
        account.setAccountDetails(accountDetails);
        if (i > 0) {
            account.setUsername(username + i + email);
        } else {
            account.setUsername(username + email);
        }
        return account;
    }

    private List<Phone> phones(Account account) {
        List<Phone> phones = new ArrayList<>();
        Phone phone = new Phone();
        phone.setPhoneNumber("14444444444");
        phone.setPhoneType(PhoneType.WORK);
        phone.setAccountId(account.getId());
        phones.add(phone);
        return phones;
    }

}