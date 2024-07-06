package in.co.daily.expense.Controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.daily.expense.Bean.ExpensesBean;
import in.co.daily.expense.Bean.UserBean;
import in.co.daily.expense.Model.ExpenseModel;
import in.co.daily.expense.Utility.DataUtility;
import in.co.daily.expense.Utility.DataValidator;
import in.co.daily.expense.Utility.PropertyReader;
import in.co.daily.expense.Utility.ServletUtility;

@WebServlet(name = "ManageExpenseCtl" ,urlPatterns = "/ManageExpenses")
public class ManageExpenseCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
    
    public ManageExpenseCtl() {
        super();
    }

    @Override
   	protected boolean validate(HttpServletRequest request) {
   		boolean pass = true;

   		if (DataValidator.isNull(request.getParameter("thisDate"))) {
   			request.setAttribute("thisDate", PropertyReader.getvalue("error.require", "From Date"));
   			pass = false;
   		}

   		if (DataValidator.isNull(request.getParameter("todate"))) {
   			request.setAttribute("todate", PropertyReader.getvalue("error.require", "To Date"));
   			pass = false;
   		}
   		return pass;
   	}
    

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String op = DataUtility.getString(request.getParameter("operation"));
		ExpenseModel model = new ExpenseModel();
		ExpensesBean bean = new ExpensesBean();
		
		Date thisdate =  DataUtility.getDate(request.getParameter("thisDate"));
		Date todate =  DataUtility.getDate(request.getParameter("todate"));
		List list = null;
		HttpSession session = request.getSession();
		UserBean userbBean = (UserBean) session.getAttribute("user");
		long roleId =  userbBean.getRoleId();

		if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(DETView.MANAGE_EXPENSES_CTL, request, response);
			return;
		}
		if (OP_SEARCH.equalsIgnoreCase(op)) {
			if (roleId ==2 ) {
			try {
				list = model.searchbyDate(bean,thisdate,todate,userbBean.getId());
				ServletUtility.setList(list, request);
			} catch (Exception e) {
				e.printStackTrace();
			}
			}
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No Record Found", request);
			}
			ServletUtility.forward(getView(), request, response);
		}
	}

	@Override
	protected String getView() {
		return DETView.MANAGE_EXPENSES_VIEW;
	}

}
