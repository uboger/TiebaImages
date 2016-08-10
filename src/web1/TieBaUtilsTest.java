package web1;


import java.util.List;

import org.junit.Test;

import boy.tieba.domain.User;
import boy.tieba.util.TieBaUtils;

public class TieBaUtilsTest {

	@Test
	public void test() {
		List<User> users = TieBaUtils.getUsersHeader("java",15);
		if(users!=null){
			for(User user : users){
				System.out.println(user.getName()+"\t" +user.isVip());
			}
		}
	}

}
