package com.getjavajob.training.yakovleva.web.controllers;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@ControllerAdvice
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = LogManager.getLogger(AdminController.class);
    private final AccountService accountService;

    @Autowired
    public AdminController(AccountService accountService) {
        this.accountService = accountService;
        logger.info("AdminController");
    }

    @RequestMapping(value = "/delete/{id}",
            produces = "application/json",
            method = {RequestMethod.DELETE, RequestMethod.GET})
    @ResponseBody
    public void deleteAccount(@PathVariable String id) {
        logger.info("deleteAccount(id = {})", id);
        accountService.deleteById(Integer.parseInt(id));
    }

    @RequestMapping(value = "/admin-panel", method = RequestMethod.GET)
    public ModelAndView admin() {
        logger.info("admin()");
        return new ModelAndView("/admin-panel");
    }

    @RequestMapping(value = "/getAccounts", method = RequestMethod.GET)
    @ResponseBody
    public TableResult updateTable(final @RequestParam("draw") int draw,
                                   final @RequestParam("start") int start,
                                   final @RequestParam("length") int length) {
        logger.info("updateTable(draw = {}, start = {}, length = {})", draw, start, length);
        List<Account> accounts = accountService.getAllAccountsLimit(start, length);
        long max = accountService.getSizeRecords();
        return new TableResult(draw, max, max, accounts);
    }

    class TableResult {
        private int draw;
        private long recordsTotal;
        private long recordsFiltered;
        private List<Account> data;

        public TableResult(int draw, long recordsTotal, long recordsFiltered, List<Account> data) {
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

        public List<Account> getData() {
            return data;
        }

        public void setData(List<Account> data) {
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