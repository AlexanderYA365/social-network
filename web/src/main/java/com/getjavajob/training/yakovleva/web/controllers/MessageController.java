package com.getjavajob.training.yakovleva.web.controllers;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Message;
import com.getjavajob.training.yakovleva.common.utilsEnum.MessageType;
import com.getjavajob.training.yakovleva.service.AccountService;
import com.getjavajob.training.yakovleva.service.MessageService;
import com.getjavajob.training.yakovleva.web.controllers.utils.SocialNetworkUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@ControllerAdvice
@SessionAttributes({"account", "friend", "selectUser"})
public class MessageController {
    private static final Logger logger = LogManager.getLogger(MessageController.class);
    private static final int ENTRIES_FOR_PAGE = 12;
    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public MessageController(AccountService accountService,
                             MessageService messageService) {
        logger.info("MessageController()");
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @RequestMapping(value = "/get-messages/{id}/{page}", method = RequestMethod.GET)
    @ResponseBody
    public List<Message> getMessages(@PathVariable String id, @PathVariable String page) {
        logger.info("getMessages(id = {}, page = {})", id, page);
        Account account = accountService.get(Integer.parseInt(id));
        Pageable pageable = PageRequest.of(Integer.parseInt(page), ENTRIES_FOR_PAGE);
        return messageService.getUniqueMessages(account, pageable);
    }

    @RequestMapping(value = "/get-size-messages/{id}", method = RequestMethod.GET)
    @ResponseBody
    public long getSizeMessage(@PathVariable String id) {
        logger.info("getSizeMessages(id = {})", id);
        Account account = accountService.get(Integer.parseInt(id));
        return messageService.getSizeUniqueMessages(account);
    }

    @RequestMapping(value = "/account-message", method = RequestMethod.GET)
    public ModelAndView messages(@ModelAttribute("account") Account account) {
        logger.info("messages(account = {})", account);
        ModelAndView modelAndView = new ModelAndView("/account/account-message");
        modelAndView.addObject("account", account);
        return modelAndView;
    }

    @RequestMapping(value = "/account-message", method = RequestMethod.POST)
    public ModelAndView goChat(@ModelAttribute("account") Account account, @RequestParam("selectUser") int idFriend) {
        logger.info("goChat(account = {}, idFriend = {})", account, idFriend);
        ModelAndView modelAndView = new ModelAndView("/chat");
        Account friend = accountService.get(idFriend);
        List<Message> messages = messageService.getMessagesByAccountIdAndReceiverIdAndMessageType(
                account.getId(), friend.getId(), MessageType.PRIVATE);
        SocialNetworkUtils socialNetworkUtils = new SocialNetworkUtils();
        String accountAvatar = socialNetworkUtils.getPhoto(account.getAccountPhoto().getPhoto());
        String friendAvatar = socialNetworkUtils.getPhoto(friend.getAccountPhoto().getPhoto());
        modelAndView.addObject("accountAvatar", accountAvatar);
        modelAndView.addObject("friendAvatar", friendAvatar);
        modelAndView.addObject("friend", friend);
        modelAndView.addObject("account", account);
        modelAndView.addObject("messages", messages);
        return modelAndView;
    }

}