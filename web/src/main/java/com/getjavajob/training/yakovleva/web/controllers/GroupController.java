package com.getjavajob.training.yakovleva.web.controllers;

import com.getjavajob.training.yakovleva.common.*;
import com.getjavajob.training.yakovleva.common.utilsEnum.ApplicationStatusType;
import com.getjavajob.training.yakovleva.common.utilsEnum.ApplicationType;
import com.getjavajob.training.yakovleva.common.utilsEnum.GroupRole;
import com.getjavajob.training.yakovleva.common.utilsEnum.MessageType;
import com.getjavajob.training.yakovleva.repository.GroupMembersRepository;
import com.getjavajob.training.yakovleva.service.*;
import com.getjavajob.training.yakovleva.web.controllers.utils.SocialNetworkUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Controller
@ControllerAdvice
@SessionAttributes({"account", "group", "members"})
public class GroupController {
    private static final Logger logger = LogManager.getLogger(GroupController.class);
    private static final int ENTRIES_FOR_PAGE = 6;
    private final GroupService groupService;
    private final ApplicationService applicationService;
    private final MessageService messageService;
    private final AccountService accountService;
    private final GroupMembersRepository groupMembersRepository;
    private final GroupMembersRepositoryService groupMembersRepositoryService;

    @Autowired
    public GroupController(GroupService groupService, ApplicationService applicationService,
                           MessageService messageService,
                           AccountService accountService,
                           GroupMembersRepository groupMembersRepository,
                           GroupMembersRepositoryService groupMembersRepositoryService) {
        logger.info("GroupController");
        this.groupService = groupService;
        this.applicationService = applicationService;
        this.messageService = messageService;
        this.accountService = accountService;
        this.groupMembersRepository = groupMembersRepository;
        this.groupMembersRepositoryService = groupMembersRepositoryService;
    }

    @RequestMapping(value = "/group-admin-panel", method = RequestMethod.GET)
    public ModelAndView groupAdminPanel(@ModelAttribute("members") GroupMembers members) {
        logger.info("groupAdminPanel(members = {})", members);
        ModelAndView modelAndView = new ModelAndView("/group/group-admin-panel");
        modelAndView.addObject("members", members);
        return modelAndView;
    }

    @RequestMapping(value = "/send-group-new-message", method = RequestMethod.POST)
    public ModelAndView sendGroupNewMessage(@RequestParam(value = "new-message", required = false) String newMessage,
                                           @RequestParam(value = "deleteText", required = false) String deleteText,
                                           @ModelAttribute("account") Account account,
                                           @ModelAttribute("group") Group group) {
        logger.info("senGroupNewMessage(newMessage = {}, accountId = {}, groupId = {})",
                newMessage, account.getId(), group.getGroupId());
        if (newMessage != null) {
            Message message = new Message();
            message.setSenderId(account.getId());
            message.setMessage(newMessage);
            message.setReceiverId(group.getGroupId());
            message.setMessage(newMessage);
            message.setMessageType(MessageType.GROUP);
            message.setPublicationDate(new Date());
            messageService.createMassage(message);
        } else if (deleteText != null) {
            int messageId = Integer.parseInt(deleteText);
            messageService.delete(messageId);
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/show-group");
        modelAndView.addObject("groupId", group.getGroupId());
        return modelAndView;
    }

    @RequestMapping(value = "/get-group-messages/{idGroup}/{page}", method = RequestMethod.GET)
    @ResponseBody
    public List<Message> getGroupMessages(@PathVariable String idGroup, @PathVariable String page) {
        logger.info("getGroupMessages(idGroup = {})", idGroup);
        Group group = groupService.get(Integer.parseInt(idGroup));
        Pageable pageable = PageRequest.of(Integer.parseInt(page), ENTRIES_FOR_PAGE);
        return messageService.getWallMassagesGroup(group, pageable);
    }

    @RequestMapping(value = "/getGroupMembers", method = RequestMethod.GET)
    @ResponseBody
    public TableResult updateTable(final @RequestParam("draw") int draw,
                                   final @RequestParam("start") int start,
                                   final @RequestParam("length") int length,
                                   @ModelAttribute("members") GroupMembers members) {
        logger.info("updateTable(draw = {}, start = {}, length = {}, members = {})",
                draw, start, length, members);
        List<GroupMembers> groupMembers = groupMembersRepository.getMembersByGroup(members.getGroup());
        long max = groupMembers.size();
        return new TableResult(draw, max, max, groupMembers);
    }

    @RequestMapping(value = "/get-groups/{id}/{page}", method = RequestMethod.GET)
    @ResponseBody
    public List<GroupMembers> getGroups(@PathVariable String id, @PathVariable String page) {
        logger.info("getGroups(id = {}, page = {})", id, page);
        Account account = accountService.get(Integer.parseInt(id));
        Pageable firstPageWithSixElements = PageRequest.of(Integer.parseInt(page), 6);
        return groupMembersRepositoryService.getAccountGroups(account, firstPageWithSixElements);
    }

    @RequestMapping(value = "/get-size-groups/{id}", method = RequestMethod.GET)
    @ResponseBody
    public long getSizeGroups(@PathVariable String id) {
        logger.info("getSizeGroups(id ={})", id);
        return groupMembersRepositoryService.getSizeAccountGroups(Integer.parseInt(id));
    }

    @RequestMapping(value = "/update-role-member/{id}/{role}",
            produces = "application/json",
            method = RequestMethod.GET)
    public void updateRole(@PathVariable String id, @PathVariable String role,
                           @ModelAttribute("members") GroupMembers members) {
        logger.info("updateRole(id = {}, role = {}, members = {})", id, role, members.getGroup());
        GroupRole groupRole = GroupRole.valueOf(role);
        groupMembersRepository.updateRoleGroupMembers(groupRole, Integer.parseInt(id));
    }

    @RequestMapping(value = "/delete-member/{id}",
            produces = "application/json",
            method = {RequestMethod.DELETE, RequestMethod.GET})
    public void deleteMember(@PathVariable String id,
                             @ModelAttribute("members") GroupMembers members) {
        logger.info("deleteMember(id = {}, members = {})", id, members.getGroup());
        groupMembersRepository.deleteMemberFromGroupMembers(Integer.parseInt(id), members.getGroup().getGroupId());
    }

    @RequestMapping(value = "/account-find-group", method = RequestMethod.GET)
    public ModelAndView findGroupView() {
        logger.info("findGroupView()");
        return new ModelAndView("/group/account-find-group");
    }

    @RequestMapping(value = "/add-group/{id}", method = RequestMethod.GET)
    public ModelAndView addGroup(@PathVariable String id, @ModelAttribute("account") Account account) {
        logger.info("addGroup(id = {}, account.id = {})", id, account.getId());
        Group group = groupService.get(id);
        groupService.insertAccountGroup(group, account.getId());
        return new ModelAndView("/group/account-find-group");
    }

    @RequestMapping(value = "/create-group", method = RequestMethod.POST)
    public ModelAndView createGroup(@ModelAttribute("group") Group group,
                                    @ModelAttribute("account") Account account) {
        logger.info("createGroup(group = {}, account = {})", group, account);
        ModelAndView modelAndView = new ModelAndView("/group/create-group");
        List<Group> groups = groupService.getAllGroups();
        try {
            if (createNewGroup(groups, group.getGroupName())) {
                group.setIdGroupCreator(account.getId());
                boolean isGroupCreate = groupService.createAccountGroups(group);
                Group createdGroup = groupService.get(group.getGroupName());
                logger.info("createdGroup = {}", createdGroup);
                if (isGroupCreate) {
                    Application application = new Application(ApplicationType.GROUP, createdGroup.getGroupId(),
                            account.getId(), ApplicationStatusType.ACCEPTED);
                    boolean isApplication = applicationService.create(application);
                    GroupMembers groupMembers = new GroupMembers();
                    groupMembers.setGroupRole(GroupRole.ADMIN);
                    groupMembers.setGroup(createdGroup);
                    groupMembers.setMember(account);
                    groupMembersRepository.save(groupMembers);
                    logger.info("groupMembers - {}", groupMembers);
                    logger.info("accountId - {}", account.getId());
                    logger.info("result create application = {}", isApplication);
                    modelAndView.addObject("account", account);
                }
            }
        } catch (Exception e) {
            logger.error("Exception - " + e);
        }
        return modelAndView;
    }

    private boolean createNewGroup(List<Group> groups, String groupName) {
        boolean flag = false;
        logger.info("createNewGroup(groupName = {})", groupName);
        for (Group group : groups) {
            if (!group.getGroupName().equals(groupName)) {
                flag = true;
                break;
            }
        }
        logger.info("createNewGroup(flag = {})", flag);
        return flag;
    }

    @RequestMapping(value = "/group-add-members", method = RequestMethod.POST)
    public ModelAndView addMembers(@ModelAttribute("account") Account account,
                                   @ModelAttribute("members") GroupMembers groupMembers) {
        logger.info("addMembers(account = {}, groupMembers = {})", account, groupMembers);
        try {
            GroupMembers newMember = new GroupMembers();
            Application application = new Application();
            application.setApplicantId(groupMembers.getGroup().getGroupId());
            application.setRecipientId(account.getId());
            application.setApplicationType(ApplicationType.GROUP);
            application.setStatus(ApplicationStatusType.CONFIRMATION);
            applicationService.create(application);
            newMember.setMember(account);
            newMember.setGroup(groupService.get(groupMembers.getGroup().getGroupId()));
            logger.info("groupMembers.getGroup() = {}", groupMembers.getGroup());
            newMember.setGroupRole(GroupRole.SUBSCRIBER);
            logger.info("newMember = {}", newMember);
            groupMembersRepository.save(newMember);
            logger.info("application = {}", application);
            logger.info("newMember = {}", newMember);
        } catch (Exception ex) {
            logger.info("Exception - " + ex);
        }
        return new ModelAndView("main");
    }

    @RequestMapping(value = "/create-group", method = RequestMethod.GET)
    public ModelAndView createGroup() {
        logger.info("createGroup()");
        return new ModelAndView("/group/create-group");
    }

    @RequestMapping(value = "/delete-group", method = RequestMethod.POST)
    public ModelAndView deleteGroup(@ModelAttribute("groupId") int groupId,
                                    @ModelAttribute("account") Account account) {
        logger.info("deleteGroup(groupId = {}, account = {})", groupId, account.getId());
        Group group = groupService.get(groupId);
        Application application = applicationService.getGroupAccount(group, account.getId());
        if (applicationService.delete(application)) {
            groupMembersRepository.deleteByGroup(group);
        }
        return new ModelAndView("redirect:/account-group");
    }

    @RequestMapping(value = "/delete-group-message", method = RequestMethod.POST)
    public ModelAndView deleteGroupMessage(@ModelAttribute("account") Account account,
                                           @ModelAttribute("group") Group group,
                                           @RequestParam(value = "delete-group-message", required = false)
                                           String deleteText) {
        logger.info("deleteGroupMessage(deleteText - {})", deleteText);
        int messageId = Integer.parseInt(deleteText);
        messageService.delete(messageId);
        return getGroupData(group.getGroupId(), account);
    }

    @RequestMapping(value = "/get-group-wall-messages/{page}", method = RequestMethod.GET)
    @ResponseBody
    public List<Message> getWallMessages(@ModelAttribute("group") Group group,
                                         @PathVariable String page) {
        logger.info("getWallMessage(group.id = {}, page = {})", group.getGroupId(), page);
        Pageable pageable = PageRequest.of(Integer.parseInt(page), ENTRIES_FOR_PAGE);
        return messageService.getWallMassagesGroup(group, pageable);
    }

    @RequestMapping(value = "/get-size-group-wall-messages", method = RequestMethod.GET)
    @ResponseBody
    public long getSizeWallMessages(@ModelAttribute("group") Group group) {
        logger.info("getSizeWallMessage(group.id = {})", group.getGroupId());
        return messageService.getSizeWallMassages(group);
    }

    @RequestMapping(value = "/get-group-senders-avatar/{page}", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getSendersAvatar(@ModelAttribute("group") Group group,
                                         @PathVariable String page) {
        logger.info("getSendersAvatar(group.id = {}, page = {})", group.getGroupId(), page);
        Pageable pageable = PageRequest.of(Integer.parseInt(page), ENTRIES_FOR_PAGE);
        List<Account> senderAccounts = accountService.getSenderMessageGroup(group, pageable);
        SocialNetworkUtils socialNetworkUtils = new SocialNetworkUtils();
        return socialNetworkUtils.getPhotos(senderAccounts);
    }

    @RequestMapping(value = "/get-group-sender-account/{page}", method = RequestMethod.GET)
    @ResponseBody
    public List<Account> getSenders(@ModelAttribute("group") Group group,
                                    @PathVariable String page) {
        logger.info("getSenders(group.id = {}, page = {})", group.getGroupId(), page);
        Pageable pageable = PageRequest.of(Integer.parseInt(page), ENTRIES_FOR_PAGE);
        return accountService.getSenderMessageGroup(group, pageable);
    }

    @RequestMapping(value = "/show-group", method = RequestMethod.GET)
    public ModelAndView viewGroupUpdate(@ModelAttribute("account") Account account,
                                        @ModelAttribute("groupId") int idGroup) {
        logger.info("viewGroupUpdate(accountId - {}, idGroup - {})", account.getId(), idGroup);
        return getGroupData(idGroup, account);
    }

    @RequestMapping(value = "/show-group", method = RequestMethod.POST)
    public ModelAndView viewGroup(@ModelAttribute("account") Account account,
                                  @ModelAttribute("groupId") int idGroup) {
        logger.info("viewGroup(accountId - {}, idGroup - {})", account.getId(), idGroup);
        return getGroupData(idGroup, account);
    }

    @RequestMapping(value = "/group-settings", method = RequestMethod.GET)
    public ModelAndView settings() {
        logger.info("settings()");
        return new ModelAndView("/group/group-settings");
    }

    @RequestMapping(value = "/edit-group-settings", method = RequestMethod.POST)
    public ModelAndView updateSettings(@ModelAttribute("group") Group group) {
        logger.info("updateSettings(group - {})", group);
        groupService.update(group);
        return new ModelAndView("/group/group-settings");
    }

    @RequestMapping(value = "/update-group-logo", method = RequestMethod.POST)
    public ModelAndView updateAvatar(@RequestParam("file") MultipartFile file,
                                     @ModelAttribute("group") Group group) {
        logger.info("updateAvatar(group = {})", group);
        ModelAndView modelAndView = new ModelAndView("/group/group-settings");
        try {
            if (!file.isEmpty()) {
                group.setLogo(file.getBytes());
                groupService.update(group);
            }
        } catch (Exception ex) {
            logger.error("failed load photo");
            logger.error(ex);
        }
        return modelAndView;
    }

    private ModelAndView getGroupData(int idGroup, Account account) {
        logger.info("getGroupData(accountId - {}, idGroup - {})", account.getId(), idGroup);
        ModelAndView modelAndView = new ModelAndView("/group/show-group");
        try {
            Group group = groupService.get(idGroup);
            Application application = applicationService.getGroupAccount(group, account.getId());
            int groupFlag = application.getId() != 0 ? application.getStatus() : 3;
            List<GroupMembers> members = groupMembersRepository.getMembersByGroup(group);
            SocialNetworkUtils socialNetworkUtils = new SocialNetworkUtils();
            String groupLogo = socialNetworkUtils.getPhoto(group.getLogo());
            modelAndView.addObject("groupLogo", groupLogo);
            modelAndView.addObject("group", group);
            modelAndView.addObject("account", account);
            modelAndView.addObject("members", members);
            modelAndView.addObject("application", application);
            modelAndView.addObject("groupFlag", groupFlag);
        } catch (Exception e) {
            logger.error("Exception - " + e);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/account-group", method = RequestMethod.GET)
    public ModelAndView accountGroup(@ModelAttribute("account") Account account) {
        logger.info("accountGroup(account = {})", account);
        return new ModelAndView("/group/account-group");
    }

    class TableResult {
        private int draw;
        private long recordsTotal;
        private long recordsFiltered;
        private List<GroupMembers> data;

        public TableResult(int draw, long recordsTotal, long recordsFiltered, List<GroupMembers> data) {
            this.draw = draw;
            this.recordsTotal = recordsTotal;
            this.recordsFiltered = recordsFiltered;
            this.data = data;
        }

        public int getDraw() {
            return draw;
        }

        public void setDraw(int draw) {
            this.draw = draw;
        }

        public long getRecordsTotal() {
            return recordsTotal;
        }

        public void setRecordsTotal(int recordsTotal) {
            this.recordsTotal = recordsTotal;
        }

        public long getRecordsFiltered() {
            return recordsFiltered;
        }

        public void setRecordsFiltered(int recordsFiltered) {
            this.recordsFiltered = recordsFiltered;
        }

        public List<GroupMembers> getData() {
            return data;
        }

        public void setData(List<GroupMembers> data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "TableResult{" +
                    "draw=" + draw +
                    ", recordsTotal=" + recordsTotal +
                    ", recordsFiltered=" + recordsFiltered +
                    ", data=" + data +
                    '}';
        }
    }

}