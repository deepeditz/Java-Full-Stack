package in.co.daily.expense.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.daily.expense.Bean.UserBean;
import in.co.daily.expense.Model.UserModel;
import in.co.daily.expense.Utility.DataUtility;
import in.co.daily.expense.Utility.ServletUtility;



@WebServlet(name = "UserListCtl",urlPatterns = "/userListCtl")
public class UserListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
    public UserListCtl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserModel model = new UserModel();
		UserBean bean = new UserBean();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id>0) {
			model.delete(id);
			ServletUtility.setSuccessMessage("Data Deleted", request);
		}
		
		List  list = null;
		try {
			list = model.list();
		} catch (Exception e) {
		e.printStackTrace();
		}
		ServletUtility.setList(list, request);
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	@Override
	protected String getView() {
		return DETView.USER_LIST_VIEW;
	}

}
