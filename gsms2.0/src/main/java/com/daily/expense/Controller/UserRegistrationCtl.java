package in.co.daily.expense.Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.daily.expense.Bean.BaseBean;
import in.co.daily.expense.Bean.UserBean;
import in.co.daily.expense.Exception.ApplicationException;
import in.co.daily.expense.Exception.DuplicateRecordException;
import in.co.daily.expense.Model.UserModel;
import in.co.daily.expense.Utility.DataUtility;
import in.co.daily.expense.Utility.DataValidator;
import in.co.daily.expense.Utility.PropertyReader;
import in.co.daily.expense.Utility.ServletUtility;



@WebServlet(name = "UserRegistrationCtl", urlPatterns = "/registration")
public class UserRegistrationCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	public UserRegistrationCtl() {
		super();
	}

	 @Override
		protected boolean validate(HttpServletRequest request) {
			boolean pass = true;

			if (DataValidator.isNull(request.getParameter("fName"))) {
				request.setAttribute("fName", PropertyReader.getvalue("error.require", "First Name"));
				pass = false;

			} 
			if (DataValidator.isNull(request.getParameter("lName"))) {
				request.setAttribute("lName", PropertyReader.getvalue("error.require", "Last Name"));
				pass = false;

			}

			if (DataValidator.isNull(request.getParameter("email"))) {
				request.setAttribute("email", PropertyReader.getvalue("error.require", "Email Id"));
				pass = false;

			}

			if (DataValidator.isNull(request.getParameter("password"))) {
				request.setAttribute("password", PropertyReader.getvalue("error.require", "Password"));
				pass = false;

			}
			
			if (DataValidator.isNull(request.getParameter("phoneNo"))) {
				request.setAttribute("phoneNo", PropertyReader.getvalue("error.require", "Phone No"));
				pass = false;
			}
			
			return pass;
		}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		UserBean bean = new UserBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setFirstName(DataUtility.getString(request.getParameter("fName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lName")));
		bean.setEmail(DataUtility.getString(request.getParameter("email")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));
		bean.setPhoneNo(DataUtility.getString(request.getParameter("phoneNo")));
		bean.setRoleId(2); 
		bean.setRoleName("User");
		populateDTO(bean, request);
		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserModel model = new UserModel();
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		UserBean bean = new UserBean();
		bean = (UserBean) populateBean(request);
		if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(DETView.USER_REGISTRATION_CTL, request, response);
			return;
		}
		if (OP_SUBMIT.equalsIgnoreCase(op)) {
				try {
					long pk = model.add(bean);
					ServletUtility.setbean(bean, request);
					ServletUtility.setSuccessMessage("Registered Successfully", request);
					ServletUtility.forward(getView(), request, response);
					return;
				} catch (DuplicateRecordException e) {
					ServletUtility.setbean(bean, request);
					ServletUtility.setErrorMessage(e.getMessage(), request);
					ServletUtility.forward(getView(), request, response);
				} catch (ApplicationException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				ServletUtility.forward(getView(), request, response);

			}
	}

	@Override
	protected String getView() {
		return DETView.USER_REGISTRATION_VIEW;
	}

}
