package com.daily.expense.Controller;

import com.daily.expense.Bean.ExpensesBean;
import com.daily.expense.Bean.UserBean;
import com.daily.expense.Exception.ApplicationException;
import com.daily.expense.Exception.DuplicateRecordException;
import com.daily.expense.Model.ExpenseModel;
import com.daily.expense.Utility.DataUtility;
import com.daily.expense.Utility.PropertyReader;
import com.daily.expense.Utility.ServletUtility;
import com.daily.expense.Validator.DataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/addExpenses")
public class AddExpenseController extends BaseCtl {

    private final ExpenseModel expenseModel;

    @Autowired
    public AddExpenseController(ExpenseModel expenseModel) {
        this.expenseModel = expenseModel;
    }

    @Override
    protected boolean validate(HttpServletRequest request) {
        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("title"))) {
            request.setAttribute("title", PropertyReader.getvalue("error.require", "Title"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("price"))) {
            request.setAttribute("price", PropertyReader.getvalue("error.require", "Price"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("description"))) {
            request.setAttribute("description", PropertyReader.getvalue("error.require", "Description"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected ExpensesBean populateBean(HttpServletRequest request) {
        ExpensesBean bean = new ExpensesBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setTitle(DataUtility.getString(request.getParameter("title")));
        bean.setDate(DataUtility.getDate(request.getParameter("date")));
        bean.setPrice(DataUtility.getLong(request.getParameter("price")));
        bean.setDescription(DataUtility.getString(request.getParameter("description")));
        HttpSession session = request.getSession();
        UserBean userBean = (UserBean) session.getAttribute("user");
        long userId = userBean.getId();
        bean.setUserId(userId);
        populateDTO(bean, request);
        return bean;
    }

    @PostMapping
    protected String doPost(HttpServletRequest request, Model model) {
        String op = DataUtility.getString(request.getParameter("operation"));
        ExpensesBean bean = populateBean(request);

        if (OP_SUBMIT.equalsIgnoreCase(op)) {
            try {
                long pk = expenseModel.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Expenses Added Successfully !!", request);
                return DETView.ADD_EXPENSES_VIEW;
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage(e.getMessage(), request);
            } catch (ApplicationException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return DETView.ADD_EXPENSES_VIEW;
    }

    @Override
    protected String getView() {
        return DETView.ADD_EXPENSES_VIEW;
    }
}
