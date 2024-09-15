package com.getjavajob.training.yakovleva.web.controllers;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Application;
import com.getjavajob.training.yakovleva.common.Message;
import com.getjavajob.training.yakovleva.common.Relations;
import com.getjavajob.training.yakovleva.common.utilsEnum.ApplicationStatusType;
import com.getjavajob.training.yakovleva.common.utilsEnum.ApplicationType;
import com.getjavajob.training.yakovleva.common.utilsEnum.MessageType;
import com.getjavajob.training.yakovleva.service.AccountService;
import com.getjavajob.training.yakovleva.service.ApplicationService;
import com.getjavajob.training.yakovleva.service.MessageService;
import com.getjavajob.training.yakovleva.service.RelationsService;
import com.getjavajob.training.yakovleva.web.controllers.utils.CommonTableUtils;
import com.getjavajob.training.yakovleva.web.controllers.utils.FriendTableUtils;
import com.getjavajob.training.yakovleva.web.controllers.utils.SocialNetworkUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

import static java.lang.Integer.parseInt;

@Controller
@ControllerAdvice
@SessionAttributes({"account", "friend"})
public class FriendController {
    private static final Logger logger = LogManager.getLogger(FriendController.class);
    private static final int ENTRIES_FOR_PAGE = 6;
    private static final int FIRST_ENTRIES = 1;
    private final RelationsService relationsService;
    private final ApplicationService applicationService;
    private final AccountService accountService;
    private final MessageService messageService;
    private Account friendAccount;

    @Autowired
    public FriendController(RelationsService relationsService,
                            ApplicationService applicationService,
                            AccountService accountService,
                            MessageService messageService) {
        this.relationsService = relationsService;
        this.applicationService = applicationService;
        this.accountService = accountService;
        this.messageService = messageService;
        logger.info("FriendController()");
    }

    @RequestMapping(value = "/get-friend-wall-messages/{page}", method = RequestMethod.GET)
    @ResponseBody
    public List<Message> getWallMessages(@ModelAttribute("friend") Account friend,
                                         @PathVariable String page) {
        logger.info("getWallMessage(friend.id = {}, page = {})", friend.getId(), page);
        Pageable pageable = PageRequest.of(Integer.parseInt(page), ENTRIES_FOR_PAGE);
        return messageService.getWallMassageAccount(friend, pageable);
    }

    @RequestMapping(value = "/get-size-friend-wall-messages", method = RequestMethod.GET)
    @ResponseBody
    public long getSizeWallMessages(@ModelAttribute("friend") Account friend) {
        logger.info("getSizeWallMessage(friend.id = {})", friend.getId());
        return messageService.getSizeWallMassages(friend);
    }

    @RequestMapping(value = "/get-friend-senders-avatar/{page}", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getSendersAvatar(@ModelAttribute("friend") Account friend,
                                         @PathVariable String page) {
        logger.info("getSendersAvatar(friend.id = {}, page = {})", friend.getId(), page);
        Pageable pageable = PageRequest.of(Integer.parseInt(page), ENTRIES_FOR_PAGE);
        List<Account> senderAccounts = accountService.getSenderMessageAccounts(friend, pageable);
        SocialNetworkUtils socialNetworkUtils = new SocialNetworkUtils();
        return socialNetworkUtils.getPhotos(senderAccounts);
    }

    @RequestMapping(value = "/get-friend-sender-account/{page}", method = RequestMethod.GET)
    @ResponseBody
    public List<Account> getSenders(@ModelAttribute("friend") Account friend,
                                    @PathVariable String page) {
        logger.info("getSenders(friend.id = {}, page = {})", friend.getId(), page);
        Pageable pageable = PageRequest.of(Integer.parseInt(page), ENTRIES_FOR_PAGE);
        return accountService.getSenderMessageAccounts(friend, pageable);
    }

    private ModelAndView getAccountData(Account account, Account friend) {
        logger.info("getAccountData(accountId = {}, friendId = {})", account.getId(), friend.getId());
        ModelAndView modelAndView = new ModelAndView("/friend/show-friend");
        try {
            Relations relations = new Relations(account.getId(), friend.getId());
            Application application = applicationService.getAccount(relations);
            int friendFlag = application.getId() != 0 ? application.getStatus() : 1;
            SocialNetworkUtils socialNetworkUtils = new SocialNetworkUtils();
            String accountAvatar = socialNetworkUtils.getPhoto(friend.getAccountPhoto().getPhoto());
            modelAndView.addObject("friendFlag", friendFlag);
            modelAndView.addObject("friend", friend);
            modelAndView.addObject("account", account);
            modelAndView.addObject("accountAvatar", accountAvatar);
        } catch (Exception e) {
            logger.error("Exception = {}", e);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/write-wall-message-friend", method = RequestMethod.POST)
    public ModelAndView writeMessageFriend(@ModelAttribute("friend") Account friend,
                                           @ModelAttribute("account") Account account,
                                           @RequestParam(value = "write-message", required = false) String writeMessage,
                                           @RequestParam(value = "delete", required = false) String delete,
                                           @RequestParam(value = "add", required = false) String add) {
        logger.info("showFriend(friend = {}, account = {}, writeMessage = {}, delete = {}, add = {})",
                friend.getId(), account.getId(), writeMessage, delete, add);
        ModelAndView modelAndView = new ModelAndView("/friend/show-friend");
        Relations relations = new Relations(account.getId(), friend.getId());
        if (add != null) {
            addFriendAccount(friend, account, relations);
        }
        if (delete != null) {
            logger.info("delete friend");
            Application application = applicationService.getAccount(relations);
            applicationService.delete(application);
            relationsService.deleteByAccountId(relations);
        }
        if (writeMessage != null) {
            logger.info("write message");
            modelAndView = new ModelAndView("redirect:/account-write-message");
            modelAndView.addObject("idFriend", friend.getId());
            modelAndView.addObject("account", account);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/show-friend", method = RequestMethod.GET)
    public ModelAndView friendPage(@ModelAttribute("account") Account account,
                                   @ModelAttribute("friendId") int friendId) {
        logger.info("friendPage(friendId = {}, account = {})", friendId, account.getId());
        Account friend = accountService.get(friendId);
        return getAccountData(friend, account);
    }

    @RequestMapping(value = "/show-friend", method = RequestMethod.POST)
    public ModelAndView showFriend(@ModelAttribute("account") Account account,
                                   @ModelAttribute("friendId") int friendId) {
        logger.info("showFriend(friendId = {},account = {})",
                friendId, account.getId());
        ModelAndView modelAndView = new ModelAndView("/friend/show-friend");
        Account friend = accountService.get(friendId);
        Relations relations = new Relations(account.getId(), friend.getId());
        Application application = applicationService.getAccount(relations);
        int friendFlag = application.getId() != 0 ? application.getStatus() : 1;
        SocialNetworkUtils socialNetworkUtils = new SocialNetworkUtils();
        String accountAvatar = socialNetworkUtils.getPhoto(friend.getAccountPhoto().getPhoto());
        modelAndView.addObject("accountAvatar", accountAvatar);
        modelAndView.addObject("friendFlag", friendFlag);
        modelAndView.addObject("friend", friend);
        modelAndView.addObject("account", account);
        return modelAndView;
    }

    private void addFriendAccount(Account friend, Account account, Relations relations) {
        logger.info("addFriend(friend = {}, account = {}, relations = {})", friend, account, relations);
        Application newApplication = new Application();
        newApplication.setStatus(ApplicationStatusType.CONFIRMATION);
        newApplication.setApplicationType(ApplicationType.USER);
        newApplication.setRecipientId(friend.getId());
        newApplication.setApplicantId(account.getId());
        applicationService.create(newApplication);
        relationsService.create(relations);
    }

    @RequestMapping(value = "/add-friend-account", method = RequestMethod.GET)
    public ModelAndView addFriend() {
        logger.info("addFriend()");
        return new ModelAndView("/friend/add-friend-account");
    }

    @RequestMapping(value = "/denied-application", method = RequestMethod.GET)
    public ModelAndView deniedApplication(@SessionAttribute("account") Account account) {
        logger.info("deniedApplication(account = {})", account);
        return new ModelAndView("/friend/denied-application");
    }

    @RequestMapping(value = "/get-friends/{id}/{page}", method = RequestMethod.GET)
    @ResponseBody
    public List<Account> getFriends(@PathVariable String id, @PathVariable String page) {
        logger.info("getFriends(id = {}, page = {})", id, page);
        Account account = accountService.get(Integer.parseInt(id));
        int end = (Integer.parseInt(page) + FIRST_ENTRIES) * ENTRIES_FOR_PAGE;
        int start = Integer.parseInt(page) * ENTRIES_FOR_PAGE;
        return accountService.getFriendsAccount(account.getId(), start, end);
    }

    @RequestMapping(value = "/get-size-friend/{id}", method = RequestMethod.GET)
    @ResponseBody
    public long getSizeFriends(@PathVariable String id) {
        logger.info("getSizeFriend(id = {})", id);
        return relationsService.getSizeRecordsFriends(Integer.parseInt(id));
    }

    @RequestMapping(value = "/friend-requests", method = RequestMethod.GET)
    public ModelAndView applicationFriends(@ModelAttribute("account") Account account) {
        logger.info("applicationFriends(account = {})", account);
        friendAccount = account;
        return new ModelAndView("/friend/friend-requests");
    }

    @RequestMapping(value = "/get-application-rejected/{count}/{quantity}", method = RequestMethod.GET)
    @ResponseBody
    public CommonTableUtils getApplicationRejected(@PathVariable String count,
                                                   @PathVariable String quantity,
                                                   @ModelAttribute("account") Account account) {
        logger.info("getApplicationRejected(count = {}, account = {})", count, account);
        int position = parseInt(count);
        long length = applicationService.getSizeRecords(account.getId(), ApplicationStatusType.REJECTED,
                ApplicationType.USER);
        List<Account> accounts = accountService.getFriendRejected(account, position * 10, parseInt(quantity));
        return new CommonTableUtils(position, length, length, accounts);
    }

    @RequestMapping(value = "/get-friend-requests", method = RequestMethod.GET)
    @ResponseBody
    public FriendTableUtils getFriendRequests(final @RequestParam("draw") int draw,
                                              final @RequestParam("start") int start,
                                              final @RequestParam("length") int length) {
        logger.info("getFriendRequests(draw = {}, start = {}, length = {})", draw, start, length);
        logger.info("getFriendRequests(friendAccount = {})", friendAccount);
        List<Account> friendRequests = accountService.getFriendRequests(friendAccount);
        long size = friendRequests.size();
        return new FriendTableUtils(draw, size, size, friendRequests);
    }

    @RequestMapping(value = "/friend-add-wall-message", method = RequestMethod.POST)
    public ModelAndView addFriendWallMassage(@ModelAttribute("account") Account account,
                                             @ModelAttribute("friend") Account friend,
                                             @RequestParam("NewWallMessage") String newMessage) {
        logger.info("addFriendWallMassage(account.getId() = {}, friend.getId() = {}, newMessage = {})",
                account.getId(), friend.getId(), newMessage);
        if (newMessage != null) {
            Message message = new Message();
            message.setSenderId(account.getId());
            message.setMessage(newMessage);
            message.setReceiverId(friend.getId());
            message.setMessage(newMessage);
            message.setMessageType(MessageType.WALL);
            message.setPublicationDate(new Date());
            messageService.createMassage(message);
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/show-friend");
        modelAndView.addObject("friendId", friend.getId());
        return modelAndView;
    }

    @RequestMapping(value = "/account-friends", method = RequestMethod.GET)
    public ModelAndView friends() {
        logger.info("friends()");
        return new ModelAndView("/friend/account-friends");
    }

    @RequestMapping(value = "/denied-application/{id}",
            produces = "application/json",
            method = RequestMethod.GET)
    public void deniedApplication(@PathVariable String id, @ModelAttribute("account") Account account) {
        logger.info("deniedApplication(id = {}, account = {})", id, account);
        Application application = applicationService.get(parseInt(id), account.getId());
        application.setStatus(ApplicationStatusType.REJECTED);
        applicationService.update(application);
    }

    @RequestMapping(value = "/delete-application/{id}",
            produces = "application/json",
            method = RequestMethod.POST)
    public void deleteApplication(@PathVariable String id, @ModelAttribute("account") Account account) {
        logger.info("deniedApplication(id = {}, account = {})", id, account);
        Application application = applicationService.get(parseInt(id), account.getId());
        logger.info("application = {}", application);
        applicationService.delete(application);
    }

    @RequestMapping(value = "/add-application/{id}",
            produces = "application/json",
            method = RequestMethod.POST)
    public void addApplication(@PathVariable String id, @ModelAttribute("account") Account account) {
        logger.info("deniedApplication(id = {}, account = {})", id, account);
        Application application = applicationService.get(parseInt(id), account.getId());
        application.setStatus(ApplicationStatusType.ACCEPTED);
        Relations relations = new Relations(account.getId(), parseInt(id));
        relationsService.create(relations);
        applicationService.update(application);
    }

    @RequestMapping(value = "/accept-application-friend/{id}",
            produces = "application/json",
            method = RequestMethod.GET)
    public void acceptApplicationFriend(@PathVariable String id, @ModelAttribute("account") Account account) {
        logger.info("acceptApplicationFriend(id = {}, account = {})", id, account);
        Application application = applicationService.get(parseInt(id), account.getId());
        application.setStatus(ApplicationStatusType.ACCEPTED);
        Relations relations = new Relations(account.getId(), parseInt(id));
        relationsService.create(relations);
        applicationService.update(application);
    }

    @RequestMapping(value = "/account-friends", method = RequestMethod.POST)
    public ModelAndView friendsRedirect(@ModelAttribute("account") Account account,
                                        @ModelAttribute("friend") Account friend) {
        logger.info("friendsRedirect( account = {}, friend = {})", account, friend);
        return new ModelAndView("/friend/account-friends");
    }

    @RequestMapping(value = "/add-friend-account/{id}", method = RequestMethod.POST)
    public ModelAndView saveFriend(@PathVariable String id,
                                   @ModelAttribute("account") Account account) {
        logger.info("saveFriend(id = {})", id);
        ModelAndView modelAndView = new ModelAndView("/friend/add-friend-account");
        try {
            Account accountFriend = accountService.get(parseInt(id));
            Relations relations = new Relations();
            relations.setAccountId(account.getId());
            relations.setFriendId(accountFriend.getId());
            relationsService.create(relations);
        } catch (Exception e) {
            logger.error("Error saveFriend(). Exception: ", e);
        }
        return modelAndView;
    }

}