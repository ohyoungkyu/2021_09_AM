package com.sbs.java.am.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sbs.java.am.Config;
import com.sbs.java.am.exception.SQLErrorException;
import com.sbs.java.am.util.DBUtil;
import com.sbs.java.am.util.SecSql;

@WebServlet("/article/doWrite")
public class ArticleDoWriteServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session =  request.getSession();
		
		if(session.getAttribute("loginedMemberId")== null) {
			response.getWriter().append(
					String.format("<script> alert('로그인 후 이용해주세요.'); location.replace('../member/login'); </script>"));
			return;
		}
		// 커넥터 드라이버 활성화
		String driverName = Config.getDBDriverClassName();

		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			System.err.printf("[ClassNotFoundException 예외, %s]\n", e.getMessage());
			response.getWriter().append("DB 드라이버 클래스 로딩 실패");
			return;
		}

		// DB 연결
		Connection con = null;

		try {
			con = DriverManager.getConnection(Config.getDBUrl(), Config.getDBId(), Config.getDBPw());
			String title = request.getParameter("title");
			String body = request.getParameter("body");
			
			int loginedMemberId = (int)session.getAttribute("loginedMemberId");

			SecSql sql = SecSql.from("INSERT INTO article");
			sql.append("SET regDate = NOW()");
			sql.append(", title = ?", title);
			sql.append(", `body` = ?", body);
			sql.append(", memberId = ?", loginedMemberId);

			int id = DBUtil.insert(con, sql);
			response.getWriter().append(
					String.format("<script> alert('%d번 글이 생성되었습니다.'); location.replace('list'); </script>", id));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(SQLErrorException e) {
			e.getOrigin().printStackTrace();
		}finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}