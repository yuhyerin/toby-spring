package chapter1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
	/** 
	 * 1. DB연결을 위한 Connection을 가져온다.
	 * 2. SQL문을 담은 Statement(또는 PreparedStatement) 를 만든다.
	 * 3. 만들어진 Statement를 실행한다.
	 * 4. 조회의 경우, SQL쿼리의 실행결과를 ResultSet으로 받아서 정보를 저장할 오브젝트에 옮겨준다.
	 * 5. 작업중에 생성된 Connection, Statement, ResultSet과 같은 리소스는 작업을 마친후 닫아준다.
	 * 6. JDBC API가 만들어내는 예외를 잡아서 직접 처리하거나,
	 * 메소드에 throws를 선언해서 예외가 발생하면 메소드 밖으로 던지게 한다. */
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		UserDao dao = new UserDao();
		
		User user = new User();
		user.setId("hyerin");
		user.setName("유혜린");
		user.setPassword("123");
		
		dao.add(user);
		
		System.out.println(user.getId()+" 등록 성공! ");
		
		User user2 = dao.get(user.getId());
		System.out.println(user2.getName());
		
		System.out.println(user2.getId()+" 조회 성공! ");
	}
	
	public void add(User user) throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost/hellotest?useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true&useSSL=false","ssafy","ssafy");
		
		PreparedStatement ps = conn.prepareStatement(
				"insert into users(id, name, password) values(?,?,?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());
		
		ps.executeUpdate();
		
		ps.close();
		conn.close();
	}
	
	public User get(String id) throws ClassNotFoundException,SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost/hellotest?useUnicode=true&characterEncoding=utf8&allowPublicKeyRetrieval=true&useSSL=false","ssafy","ssafy");
		
		PreparedStatement ps = conn.prepareStatement(
				"select * from users where id = ?");
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		rs.next();
		User user = new User();
		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));
		
		rs.close();
		ps.close();
		conn.close();
		
		return user;
	}
}
