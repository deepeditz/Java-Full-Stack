package in.co.daily.expense.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.daily.expense.Bean.BaseBean;
import in.co.daily.expense.Bean.ExpensesBean;
import in.co.daily.expense.Bean.UserBean;
import in.co.daily.expense.Model.ExpenseModel;
import in.co.daily.expense.Model.UserModel;
import in.co.daily.expense.Utility.DataUtility;
import in.co.daily.expense.Utility.ServletUtility;

@WebServlet(name = "ExpenseListCtl", urlPatterns = "/ExpensesList")
public class ExpenseListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	public ExpenseListCtl() {
		super();
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		ExpensesBean bean = new ExpensesBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setTitle(DataUtility.getString(request.getParameter("title")));
		bean.setDate(DataUtility.getDate(request.getParameter("date")));
		bean.setPrice(DataUtility.getLong(request.getParameter("price")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));
		populateDTO(bean, request);
		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ExpenseModel model = new ExpenseModel();
		ExpensesBean bean = new ExpensesBean();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0) {
			model.delete(id);
			ServletUtility.setSuccessMessage("Data Deleted !!", request);
		}
		List list = null;
		HttpSession session = request.getSession();
		UserBean userbBean = (UserBean) session.getAttribute("user");
		long roleId = userbBean.getRoleId();
		if (roleId == 2) {
			try {
				list = model.list(userbBean.getId());
			} catch (Exception e) {
			}
		}else {
			try {
				list = model.Adminlist();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (list == null || list.size() == 0) {
			ServletUtility.setErrorMessage("No Record Found", request);
		}
		ServletUtility.setList(list, request);
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));
		ExpenseModel model = new ExpenseModel();
		ExpensesBean bean = new ExpensesBean();
		bean = (ExpensesBean) populateBean(request);
		List list = null;
		HttpSession session = request.getSession();
		UserBean userbBean = (UserBean) session.getAttribute("user");
		long roleId = userbBean.getRoleId();

		if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(DETView.EXPENSES_LIST_CTL, request, response);
			return;
		}

		if (OP_SEARCH.equalsIgnoreCase(op)) {
			if (roleId == 2) {
				try {
					list = model.search(bean, userbBean.getId());
					ServletUtility.setList(list, request);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else {
				try {
					list = model.Adminsearch(bean);
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
		return DETView.EXPENSES_LIST_VIEW;
	}

}
