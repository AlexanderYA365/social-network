package com.getjavajob.training.yakovleva.web.controllers;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Group;
import com.getjavajob.training.yakovleva.service.AccountService;
import com.getjavajob.training.yakovleva.service.GroupService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {
    private static final Logger logger = LogManager.getLogger(SearchController.class);
    private final AccountService accountService;
    private final GroupService groupService;
    private String searchParameter;

    @Autowired
    public SearchController(AccountService accountService, GroupService groupService) {
        logger.info("SearchController");
        this.accountService = accountService;
        this.groupService = groupService;
    }

    @RequestMapping(value = "/result-search", method = RequestMethod.GET)
    public ModelAndView resultSearch(@RequestParam("search") String search) {
        logger.info("resultSearch(search = {})", search);
        searchParameter = search;
        ModelAndView modelAndView = new ModelAndView("result-search");
        modelAndView.addObject("search", search);
        return modelAndView;
    }

    //todo откорректировать поиск, баг, можно найти по айди человека и потом перейти в его сообщения
    @RequestMapping(value = "/getSearch", method = RequestMethod.GET)
    @ResponseBody
    public TableResult tableResult(final @RequestParam("draw") int draw,
                                   final @RequestParam("start") int start,
                                   final @RequestParam("length") int length) {
        logger.info("tableResult(draw = {}, start = {}, length = {})", draw, start, length);
        logger.info("searchParameter = {}", searchParameter);
        List<SearchResult> searchResults = searchCriteria(searchParameter, start, length);
        long size = accountService.getSizeRecords(searchParameter) + groupService.getSizeRecords(searchParameter);
        logger.info("records size = {}", size);
        return new TableResult(draw, size, size, searchResults);
    }

    @RequestMapping(value = "/get-accounts", method = RequestMethod.GET)
    @ResponseBody
    public TableResult getFoundAccount(final @RequestParam("draw") int draw,
                                       final @RequestParam("start") int start,
                                       final @RequestParam("length") int length) {
        logger.info("getFoundAccount(draw = {}, start = {}, length = {})", draw, start, length);
        logger.info("searchParameter = {}", searchParameter);
        List<SearchResult> searchResults = foundAccount(searchParameter, start, length);
        long size = accountService.getSizeRecords(searchParameter);
        logger.info("records size = {}", size);
        return new TableResult(draw, size, size, searchResults);
    }

    private List<SearchResult> foundAccount(String criteria, int start, int end) {
        logger.info("foundAccount(criteria = {}, start = {}, end = {})", criteria, start, end);
        List<Account> accounts = accountService.getAccountsCriteriaLimit(start, end, criteria);
        List<SearchResult> searchResults = new ArrayList<>();
        for (Account account : accounts) {
            searchResults.add(new SearchResult(account.getId(), account.getAccountDetails().getName(),
                    account.getAccountDetails().getSurname(), account.getAccountDetails().getLastName(), false));
        }
        logger.info("searchResults = {}", searchResults);
        return searchResults;
    }

    @RequestMapping(value = "/get-groups", method = RequestMethod.GET)
    @ResponseBody
    public TableResult getFoundGroups(final @RequestParam("draw") int draw,
                                      final @RequestParam("start") int start,
                                      final @RequestParam("length") int length) {
        logger.info("getFoundGroups(draw = {}, start = {}, length = {})", draw, start, length);
        logger.info("searchParameter = {}", searchParameter);
        List<SearchResult> searchResults = foundGroups(searchParameter, start, length);
        long size = groupService.getSizeRecords(searchParameter);
        logger.info("records size = {}", size);
        return new TableResult(draw, size, size, searchResults);
    }

    private List<SearchResult> foundGroups(String criteria, int start, int end) {
        logger.info("foundGroups(criteria = {})", criteria);
        List<Group> groups = groupService.getCriteriaLimit(start, end, criteria);
        List<SearchResult> searchResults = new ArrayList<>();
        for (Group group : groups) {
            searchResults.add(new SearchResult(group.getGroupId(), group.getGroupName(),
                    "", "", true));
        }
        logger.info("searchResults = {}", searchResults);
        return searchResults;
    }

    private List<SearchResult> searchCriteria(String criteria, int start, int end) {
        logger.info("criteria = {}", criteria);
        List<Account> accounts = accountService.getAccountsCriteriaLimit(start, end, criteria);
        List<Group> groups = groupService.getCriteriaLimit(start, end, criteria);
        List<SearchResult> searchResults = new ArrayList<>();
        for (Account account : accounts) {
            searchResults.add(new SearchResult(account.getId(), account.getAccountDetails().getName(),
                    account.getAccountDetails().getSurname(), account.getAccountDetails().getLastName(), false));
        }
        for (Group group : groups) {
            searchResults.add(new SearchResult(group.getGroupId(), group.getGroupName(),
                    "", "", true));
        }
        logger.info("searchResults = {}", searchResults);
        return searchResults;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public List<SearchResult> search(final @RequestParam("filter") String filter) {
        logger.info("search (filter - {})", filter);
        return getSearchResults(filter);
    }

    private List<SearchResult> getSearchResults(String filter) {
        return searchCriteria(filter, 0, 5);
    }

    static class SearchResult {
        public int id;
        public String name;
        public String surname;
        public String lastName;
        public boolean isGroup;

        public SearchResult(int id, String name, String surname, String lastName, boolean isGroup) {
            this.id = id;
            this.name = name;
            this.surname = surname;
            this.lastName = lastName;
            this.isGroup = isGroup;
        }

        @Override
        public String toString() {
            return "SearchResult{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", surname='" + surname + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", isGroup=" + isGroup +
                    '}';
        }

    }

    private class TableResult {
        private int draw;
        private long recordsTotal;
        private long recordsFiltered;
        private List<SearchResult> searchResults;

        public TableResult(int draw, long recordsTotal, long recordsFiltered, List<SearchResult> searchResults) {
            this.draw = draw;
            this.recordsTotal = recordsTotal;
            this.recordsFiltered = recordsFiltered;
            this.searchResults = searchResults;
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

        public List<SearchResult> getSearchResults() {
            return searchResults;
        }

        public void setSearchResults(List<SearchResult> searchResults) {
            this.searchResults = searchResults;
        }

        @Override
        public String toString() {
            return "TableResult{" +
                    "draw=" + draw +
                    ", recordsTotal=" + recordsTotal +
                    ", recordsFiltered=" + recordsFiltered +
                    ", searchResults=" + searchResults +
                    '}';
        }
    }

}