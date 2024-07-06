package in.co.daily.expense.Controller;

public interface DETView {
	
	public String APP_CONTEXT = "/DailyExpenseTracker";
	public String PAGE_FOLEDER = "/jsp";
	
	
	public String WELCOME_CTL = APP_CONTEXT + "/welcome";
	public String WELCOME_VIEW = PAGE_FOLEDER + "/Welcome.jsp";
	

	public String LOGIN_CTL = APP_CONTEXT + "/login";
	public String LOGIN_VIEW = PAGE_FOLEDER + "/LoginView.jsp";
	
	public String USER_REGISTRATION_CTL = APP_CONTEXT + "/registration";
	public String USER_REGISTRATION_VIEW = PAGE_FOLEDER +"/RegistrationView.jsp";
	
	public String USER_LIST_CTL = APP_CONTEXT + "/userListCtl";
	public String USER_LIST_VIEW = PAGE_FOLEDER + "/UserListView.jsp";
	
	public String ADMIN_CTL = APP_CONTEXT + "/Admin";
	public String ADMIN_VIEW = PAGE_FOLEDER + "/Admin.jsp";
	
	public String ADD_EXPENSES_CTL = APP_CONTEXT + "/AddExpenses";
	public String ADD_EXPENSES_VIEW = PAGE_FOLEDER + "/AddExpenses.jsp";
	
	public String EXPENSES_LIST_CTL = APP_CONTEXT + "/ExpensesList";
	public String EXPENSES_LIST_VIEW = PAGE_FOLEDER + "/ExpensesList.jsp";
	
	public String WALLET_CTL = APP_CONTEXT + "/walletsCtl";
	public String WALLET_VIEW = PAGE_FOLEDER + "/WalletsView.jsp";
	

	public String MANAGE_EXPENSES_CTL = APP_CONTEXT + "/ManageExpenses";
	public String MANAGE_EXPENSES_VIEW = PAGE_FOLEDER + "/ManageExpensesView.jsp";

}
