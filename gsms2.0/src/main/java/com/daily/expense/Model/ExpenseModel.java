package in.co.daily.expense.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.daily.expense.Bean.ExpensesBean;
import in.co.daily.expense.Bean.UserBean;
import in.co.daily.expense.Exception.ApplicationException;
import in.co.daily.expense.Exception.DuplicateRecordException;
import in.co.daily.expense.Utility.JDBCDataSource;

public class ExpenseModel {

	public Integer nextpk() throws Exception {

		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT MAX(ID) FROM expenses");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pk + 1;
	}
	
	public ExpensesBean findByTitle(String title) throws Exception {

		ExpensesBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM expenses WHERE title=?");
			ps.setString(1, title);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new ExpensesBean();
				bean.setId(rs.getLong(1));
				bean.setTitle(rs.getString(2));
				bean.setDate(rs.getDate(3));
				bean.setPrice(rs.getLong(4));
				bean.setDescription(rs.getString(5));
				bean.setUserId(rs.getLong(6));
				bean.setEmail(rs.getString(7));
			}
			rs.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

		return bean;
	}
	
	public ExpensesBean findByPk(long pk) throws Exception {
        ExpensesBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM expenses WHERE id=?");
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new ExpensesBean();
				bean.setId(rs.getLong(1));
				bean.setTitle(rs.getString(2));
				bean.setDate(rs.getDate(3));
				bean.setPrice(rs.getLong(4));
				bean.setDescription(rs.getString(5));
				bean.setUserId(rs.getLong(6));
				bean.setEmail(rs.getString(7));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	public long add(ExpensesBean bean) throws Exception {
		Connection conn = null;
		int pk = 0;

		ExpensesBean existbean = findByTitle(bean.getTitle());
		if (existbean != null) {
			throw new DuplicateRecordException("Item already exist");
		}
		
		UserModel Umodel = new UserModel();
		UserBean ubean = new UserBean();
		ubean = Umodel.findByPk(bean.getUserId());
		String Email = ubean.getEmail();

		try {

			conn = JDBCDataSource.getConnection();
			pk = nextpk();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("INSERT INTO expenses VALUES(?,?,?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setString(2, bean.getTitle());
			ps.setDate(3, new Date(bean.getDate().getTime()));
			ps.setLong(4, bean.getPrice());
			ps.setString(5, bean.getDescription());
			ps.setLong(6, bean.getUserId());
			ps.setString(7, Email);
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception e2) {
				e.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + e.getMessage());
			}
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return pk;
	}
	
	public List list(long Userid) throws Exception {
		ArrayList list = new ArrayList();
		Connection conn = null;
		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("SELECT * from expenses where Userid = ?");
		pstmt.setLong(1, Userid);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			ExpensesBean bean = new ExpensesBean();
			bean.setId(rs.getLong(1));
			bean.setTitle(rs.getString(2));
			bean.setDate(rs.getDate(3));
			bean.setPrice(rs.getLong(4));
			bean.setDescription(rs.getString(5));
			bean.setUserId(rs.getLong(6));
			bean.setEmail(rs.getString(7));
			list.add(bean);
		}
		return list;
	}
	
	public List Adminlist() throws Exception {
		ArrayList list = new ArrayList();
		
		Connection conn = null;
		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("SELECT * from expenses");
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			ExpensesBean bean = new ExpensesBean();
			bean.setId(rs.getLong(1));
			bean.setTitle(rs.getString(2));
			bean.setDate(rs.getDate(3));
			bean.setPrice(rs.getLong(4));
			bean.setDescription(rs.getString(5));
			bean.setUserId(rs.getLong(6));
			bean.setEmail(rs.getString(7));
			list.add(bean);
		}
		return list;
	}

	public static long delete(long id) {
		int i = 0;
		try {
			Connection conn = JDBCDataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement("DELETE from expenses where id=?");
			stmt.setLong(1, id);
			i = stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
	public List search(ExpensesBean bean,long Userid) throws Exception {
		StringBuffer sql = new StringBuffer("SELECT * from expenses WHERE 1=1");
		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			
			if(bean.getDate() != null && bean.getDate().getDate()>0) {
				java.sql.Date d=new java.sql.Date(bean.getDate().getTime());
				sql.append(" AND Date like'" + d + "%'");
			}
			sql.append("AND Userid = ?");
		}

		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, Userid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new ExpensesBean();
				bean.setId(rs.getLong(1));
				bean.setTitle(rs.getString(2));
				bean.setDate(rs.getDate(3));
				bean.setPrice(rs.getLong(4));
				bean.setDescription(rs.getString(5));
				bean.setUserId(rs.getLong(6));
				bean.setEmail(rs.getString(7));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
          e.printStackTrace();
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return list;
	}
	
	public List Adminsearch(ExpensesBean bean) throws Exception {
		StringBuffer sql = new StringBuffer("SELECT * from expenses WHERE 1=1");
		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			
			if(bean.getDate() != null && bean.getDate().getDate()>0) {
				java.sql.Date d=new java.sql.Date(bean.getDate().getTime());
				sql.append(" AND Date like'" + d + "%'");
			}
		}

		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new ExpensesBean();
				bean.setId(rs.getLong(1));
				bean.setTitle(rs.getString(2));
				bean.setDate(rs.getDate(3));
				bean.setPrice(rs.getLong(4));
				bean.setDescription(rs.getString(5));
				bean.setUserId(rs.getLong(6));
				bean.setEmail(rs.getString(7));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
          e.printStackTrace();
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return list;
	}
	
	public List searchbyDate(ExpensesBean bean, java.util.Date thisdate,java.util.Date todate,long Userid) throws Exception {
		
		StringBuffer sql = new StringBuffer("SELECT * from expenses where Date between ? and ? and Userid = ?");
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setDate(1, new Date(thisdate.getTime()));
			ps.setDate(2, new Date(todate.getTime()));
			ps.setLong(3, Userid);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bean = new ExpensesBean();
				bean.setId(rs.getLong(1));
				bean.setTitle(rs.getString(2));
				bean.setDate(rs.getDate(3));
				bean.setPrice(rs.getLong(4));
				bean.setDescription(rs.getString(5));
				bean.setUserId(rs.getLong(6));
				bean.setEmail(rs.getString(7));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
          e.printStackTrace();
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return list;
	}
	
	public List TodayExpensesSearch(ExpensesBean bean,long userId) throws Exception {
		StringBuffer sql = new StringBuffer("select * from expenses where Date = CAST(CURRENT_TIMESTAMP AS DATE) and Userid = ?");

		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, userId);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new ExpensesBean();
				bean.setId(rs.getLong(1));
				bean.setTitle(rs.getString(2));
				bean.setDate(rs.getDate(3));
				bean.setPrice(rs.getLong(4));
				bean.setDescription(rs.getString(5));
				bean.setUserId(rs.getLong(6));
				bean.setEmail(rs.getString(7));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
          e.printStackTrace();
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return list;
	}
	
	public List YesterDayExpensesSearch(ExpensesBean bean,long Userid) throws Exception {
		StringBuffer sql = new StringBuffer("SELECT * from expenses where Date = DATE_SUB(CURDATE(),INTERVAL 1 DAY) and Userid = ?");
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, Userid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new ExpensesBean();
				bean.setId(rs.getLong(1));
				bean.setTitle(rs.getString(2));
				bean.setDate(rs.getDate(3));
				bean.setPrice(rs.getLong(4));
				bean.setDescription(rs.getString(5));
				bean.setUserId(rs.getLong(6));
				bean.setEmail(rs.getString(7));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
          e.printStackTrace();
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return list;
	}
	
	public List WeeklyExpensesSearch(ExpensesBean bean,long Userid) throws Exception {
		StringBuffer sql = new StringBuffer("select * from expenses where Date> now() -  interval 7 day and Userid = ?");
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, Userid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new ExpensesBean();
				bean.setId(rs.getLong(1));
				bean.setTitle(rs.getString(2));
				bean.setDate(rs.getDate(3));
				bean.setPrice(rs.getLong(4));
				bean.setDescription(rs.getString(5));
				bean.setUserId(rs.getLong(6));
				bean.setEmail(rs.getString(7));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
          e.printStackTrace();
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return list;
	}
	
	public List CurrentYearExpensesSearch(ExpensesBean bean,long Userid) throws Exception {
		StringBuffer sql = new StringBuffer("select * from expenses where YEAR(date) = YEAR(CURDATE()) and Userid=?");
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, Userid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new ExpensesBean();
				bean.setId(rs.getLong(1));
				bean.setTitle(rs.getString(2));
				bean.setDate(rs.getDate(3));
				bean.setPrice(rs.getLong(4));
				bean.setDescription(rs.getString(5));
				bean.setUserId(rs.getLong(6));
				bean.setEmail(rs.getString(7));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
          e.printStackTrace();
		} finally {
			JDBCDataSource.closeconnection(conn);
		}
		return list;
	}
	
	
}
